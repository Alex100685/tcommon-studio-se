// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.ui.wizards.metadata.connection.database;

import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.talend.commons.ui.runtime.image.ECoreImage;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.commons.ui.swt.dialogs.ErrorDialogWidthDetailArea;
import org.talend.commons.utils.VersionUtils;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.ITDQRepositoryService;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.database.EDatabase4DriverClassName;
import org.talend.core.database.EDatabaseTypeName;
import org.talend.core.model.metadata.IMetadataConnection;
import org.talend.core.model.metadata.MetadataFillFactory;
import org.talend.core.model.metadata.builder.ConvertionHelper;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.builder.connection.ConnectionFactory;
import org.talend.core.model.metadata.builder.connection.DatabaseConnection;
import org.talend.core.model.metadata.builder.database.ExtractMetaDataFromDataBase;
import org.talend.core.model.metadata.builder.database.ExtractMetaDataUtils;
import org.talend.core.model.metadata.builder.util.MetadataConnectionUtils;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.model.update.RepositoryUpdateManager;
import org.talend.core.repository.IDBMetadataProvider;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.repository.utils.AbstractResourceChangesService;
import org.talend.core.repository.utils.TDQServiceRegister;
import org.talend.core.runtime.CoreRuntimePlugin;
import org.talend.cwm.helper.ConnectionHelper;
import org.talend.cwm.helper.SwitchHelpers;
import org.talend.designer.core.IDesignerCoreService;
import org.talend.metadata.managment.ui.i18n.Messages;
import org.talend.repository.ProjectManager;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.IRepositoryService;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.RepositoryNodeUtilities;
import org.talend.repository.ui.utils.ConnectionContextHelper;
import org.talend.repository.ui.wizards.CheckLastVersionRepositoryWizard;
import org.talend.repository.ui.wizards.PropertiesWizardPage;
import org.talend.repository.ui.wizards.metadata.connection.Step0WizardPage;
import orgomg.cwm.resource.relational.Catalog;
import orgomg.cwm.resource.relational.Schema;

/**
 * DatabaseWizard present the DatabaseForm. Use to manage the metadata connection.
 */
public class DatabaseWizard extends CheckLastVersionRepositoryWizard implements INewWizard {

    private static Logger log = Logger.getLogger(DatabaseWizard.class);

    private PropertiesWizardPage propertiesWizardPage;

    private DatabaseWizardPage databaseWizardPage;

    private DatabaseConnection connection;

    private Property connectionProperty;

    private String originaleObjectLabel;

    private String originalVersion;

    private String originalPurpose;

    private String originalDescription;

    private String originalStatus;

    private boolean isToolBar;

    /**
     * Constructor for DatabaseWizard. Analyse Iselection to extract DatabaseConnection and the pathToSave. Start the
     * Lock Strategy.
     * 
     * @param selection
     * @param existingNames
     */
    public DatabaseWizard(IWorkbench workbench, boolean creation, ISelection selection, String[] existingNames) {
        super(workbench, creation);
        this.selection = selection;
        this.existingNames = existingNames;
        setNeedsProgressMonitor(true);
        RepositoryNode node = null;
        Object obj = ((IStructuredSelection) selection).getFirstElement();
        if (obj instanceof RepositoryNode) {
            node = (RepositoryNode) obj;
        } else {
            return;
        }

        switch (node.getType()) {
        case SIMPLE_FOLDER:
        case REPOSITORY_ELEMENT:
            pathToSave = RepositoryNodeUtilities.getPath(node);
            break;
        case SYSTEM_FOLDER:
            pathToSave = new Path(""); //$NON-NLS-1$
            break;
        }

        switch (node.getType()) {
        case SIMPLE_FOLDER:
        case SYSTEM_FOLDER:
            connection = ConnectionFactory.eINSTANCE.createDatabaseConnection();
            connectionProperty = PropertiesFactory.eINSTANCE.createProperty();
            connectionProperty.setAuthor(((RepositoryContext) CoreRuntimePlugin.getInstance().getContext()
                    .getProperty(Context.REPOSITORY_CONTEXT_KEY)).getUser());
            connectionProperty.setVersion(VersionUtils.DEFAULT_VERSION);
            connectionProperty.setStatusCode(""); //$NON-NLS-1$

            connectionItem = PropertiesFactory.eINSTANCE.createDatabaseConnectionItem();
            connectionItem.setProperty(connectionProperty);
            connectionItem.setConnection(connection);
            break;

        case REPOSITORY_ELEMENT:
            connection = (DatabaseConnection) ((ConnectionItem) node.getObject().getProperty().getItem()).getConnection();
            connectionProperty = node.getObject().getProperty();
            connectionItem = (ConnectionItem) node.getObject().getProperty().getItem();
            // set the repositoryObject, lock and set isRepositoryObjectEditable
            setRepositoryObject(node.getObject());
            isRepositoryObjectEditable();
            initLockStrategy();
            break;
        }
        if (!creation) {
            this.originaleObjectLabel = this.connectionItem.getProperty().getLabel();
            this.originalVersion = this.connectionItem.getProperty().getVersion();
            this.originalDescription = this.connectionItem.getProperty().getDescription();
            this.originalPurpose = this.connectionItem.getProperty().getPurpose();
            this.originalStatus = this.connectionItem.getProperty().getStatusCode();
        }
        // initialize the context mode
        ConnectionContextHelper.checkContextMode(connectionItem);
    }

    /**
     * Constructor for DatabaseWizard. Analyse Iselection to extract DatabaseConnection and the pathToSave. Start the
     * Lock Strategy.
     * 
     * @param selection
     * @param existingNames
     */
    public DatabaseWizard(IWorkbench workbench, boolean creation, RepositoryNode node, String[] existingNames) {
        super(workbench, creation);
        this.existingNames = existingNames;
        setNeedsProgressMonitor(true);
        switch (node.getType()) {
        case SIMPLE_FOLDER:
        case REPOSITORY_ELEMENT:
            pathToSave = RepositoryNodeUtilities.getPath(node);
            break;
        case SYSTEM_FOLDER:
            pathToSave = new Path(""); //$NON-NLS-1$
            break;
        }

        switch (node.getType()) {
        case SIMPLE_FOLDER:
        case SYSTEM_FOLDER:
            connection = ConnectionFactory.eINSTANCE.createDatabaseConnection();
            connectionProperty = PropertiesFactory.eINSTANCE.createProperty();
            connectionProperty.setAuthor(((RepositoryContext) CoreRuntimePlugin.getInstance().getContext()
                    .getProperty(Context.REPOSITORY_CONTEXT_KEY)).getUser());
            connectionProperty.setVersion(VersionUtils.DEFAULT_VERSION);
            connectionProperty.setStatusCode(""); //$NON-NLS-1$

            connectionItem = PropertiesFactory.eINSTANCE.createDatabaseConnectionItem();
            connectionItem.setProperty(connectionProperty);
            connectionItem.setConnection(connection);
            break;

        case REPOSITORY_ELEMENT:
            connection = (DatabaseConnection) ((ConnectionItem) node.getObject().getProperty().getItem()).getConnection();
            connectionProperty = node.getObject().getProperty();
            connectionItem = (ConnectionItem) node.getObject().getProperty().getItem();
            // set the repositoryObject, lock and set isRepositoryObjectEditable
            setRepositoryObject(node.getObject());
            isRepositoryObjectEditable();
            initLockStrategy();
            break;
        }
        if (!creation) {
            this.originaleObjectLabel = this.connectionItem.getProperty().getLabel();
            this.originalVersion = this.connectionItem.getProperty().getVersion();
            this.originalDescription = this.connectionItem.getProperty().getDescription();
            this.originalPurpose = this.connectionItem.getProperty().getPurpose();
            this.originalStatus = this.connectionItem.getProperty().getStatusCode();
        }
        // initialize the context mode
        ConnectionContextHelper.checkContextMode(connectionItem);
    }

    /**
     * yzhang Comment method "setToolBar".
     * 
     * @param isToolbar
     */
    public void setToolBar(boolean isToolbar) {
        this.isToolBar = isToolbar;
    }

    /**
     * Adding the page to the wizard and set Title, Description and PageComplete.
     */
    @Override
    public void addPages() {
        setWindowTitle(Messages.getString("DatabaseWizard.windowTitle")); //$NON-NLS-1$
        setDefaultPageImageDescriptor(ImageProvider.getImageDesc(ECoreImage.METADATA_CONNECTION_WIZ));
        if (isToolBar) {
            pathToSave = null;
        }
        propertiesWizardPage = new Step0WizardPage(connectionProperty, pathToSave, ERepositoryObjectType.METADATA_CONNECTIONS,
                !isRepositoryObjectEditable(), creation);
        databaseWizardPage = new DatabaseWizardPage(connectionItem, isRepositoryObjectEditable(), existingNames);

        if (creation) {
            propertiesWizardPage.setTitle(Messages.getString("DatabaseWizardPage.titleCreate.Step1")); //$NON-NLS-1$
            propertiesWizardPage.setDescription(Messages.getString("DatabaseWizardPage.descriptionCreate.Step1")); //$NON-NLS-1$
            propertiesWizardPage.setPageComplete(false);

            databaseWizardPage.setTitle(Messages.getString("DatabaseWizardPage.titleCreate.Step2")); //$NON-NLS-1$
            databaseWizardPage.setDescription(Messages.getString("DatabaseWizardPage.descriptionCreate.Step2")); //$NON-NLS-1$
            databaseWizardPage.setPageComplete(false);
        } else {
            propertiesWizardPage.setTitle(Messages.getString("DatabaseWizardPage.titleUpdate.Step1")); //$NON-NLS-1$
            propertiesWizardPage.setDescription(Messages.getString("DatabaseWizardPage.descriptionUpdate.Step1")); //$NON-NLS-1$
            propertiesWizardPage.setPageComplete(isRepositoryObjectEditable());

            databaseWizardPage.setTitle(Messages.getString("DatabaseWizardPage.titleUpdate.Step2")); //$NON-NLS-1$
            databaseWizardPage.setDescription(Messages.getString("DatabaseWizardPage.descriptionUpdate.Step2")); //$NON-NLS-1$
            databaseWizardPage.setPageComplete(isRepositoryObjectEditable());
        }
        addPage(propertiesWizardPage);
        addPage(databaseWizardPage);
    }

    /**
     * This method is called when 'Finish' button is pressed in the wizard. Save metadata close Lock Strategy and close
     * wizard.
     */
    @Override
    public boolean performFinish() {
        if (databaseWizardPage.isPageComplete()) {
            /*
             * if create connection in TOS with context model,should use the original value when create catalog or //
             * schema,see bug 0016636,using metadataConnection can be sure that all the values has been parse to
             * original. hywang
             */

            // MOD by gdbu 2011-3-24 bug 19528
            EDatabaseTypeName dbType = EDatabaseTypeName.getTypeFromDbType(connection.getDatabaseType());
            if (dbType != EDatabaseTypeName.GENERAL_JDBC) {
                String driverClass = ExtractMetaDataUtils.getDriverClassByDbType(connection.getDatabaseType());
                ((DatabaseConnection) connectionItem.getConnection()).setDriverClass(driverClass);
            }
            // ~19528

            IMetadataConnection metadataConnection = ConvertionHelper.convert(connection);
            IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();

            ITDQRepositoryService tdqRepService = null;

            if (GlobalServiceRegister.getDefault().isServiceRegistered(ITDQRepositoryService.class)) {
                tdqRepService = (ITDQRepositoryService) GlobalServiceRegister.getDefault()
                        .getService(ITDQRepositoryService.class);
            }

            try {
                if (creation) {
                    String nextId = factory.getNextId();
                    connectionProperty.setId(nextId);
                    if (connectionItem.getConnection() instanceof DatabaseConnection) {
                        DatabaseConnection c = (DatabaseConnection) connectionItem.getConnection();
                        final boolean equals = c.getProductId().equals(EDatabaseTypeName.ORACLEFORSID.getProduct());
                        if (equals && !c.isContextMode()) {
                            if (c.getUiSchema() == null) {
                                c.setUiSchema(""); //$NON-NLS-1$
                            } else {
                                c.setUiSchema(c.getUiSchema().toUpperCase());
                            }
                        }
                    }
                    EDatabaseTypeName type = EDatabaseTypeName.getTypeFromDbType(metadataConnection.getDbType());
                    if (!type.equals(EDatabaseTypeName.GENERAL_JDBC)) {
                        this.connection.setDriverClass(EDatabase4DriverClassName.getDriverClassByDbType(metadataConnection
                                .getDbType()));
                    }
                    this.connection.setName(connectionProperty.getLabel());
                    this.connection.setLabel(connectionProperty.getLabel());

                    factory.create(connectionItem, propertiesWizardPage.getDestinationPath());

                    // MOD yyi 2011-04-14:20362 reload connection
                    ConnectionHelper.setUsingURL(connection, connection.getURL());

                    MetadataConnectionUtils.fillConnectionInformation(connectionItem);

                    // if after fillConnection there is still no dataPackages, need to fill them from extractor
                    List<Catalog> catalogs = ConnectionHelper.getCatalogs(connection);
                    List<Schema> schemas = ConnectionHelper.getSchema(connection);
                    if (catalogs.isEmpty() && schemas.isEmpty()) {
                        IDBMetadataProvider extractor = ExtractMetaDataFromDataBase.getProviderByDbType(metadataConnection
                                .getDbType());
                        if (extractor != null && type.isUseProvider()) {
                            extractor.fillConnection(connection);
                            factory.save(connectionItem);
                        }
                    }
                } else {
                    if (connectionItem.getConnection() instanceof DatabaseConnection) {
                        DatabaseConnection conn = (DatabaseConnection) connectionItem.getConnection();
                        boolean reloadCheck = false;
                        if (tdqRepService != null && ConnectionHelper.isUrlChanged(conn)) {
                            reloadCheck = openConfirmReloadDialog();
                            if (!reloadCheck) {
                                return false;
                            }
                        }
                        final boolean equals = EDatabaseTypeName.ORACLEFORSID.getProduct().equals(conn.getProductId());
                        if (equals && !conn.isContextMode()) {
                            if (conn.getUiSchema() != null && !"".equals(conn.getUiSchema())) {//$NON-NLS-1$
                                conn.setUiSchema(conn.getUiSchema().toUpperCase()); // MOD mzhao bug 4227 , don't set
                                                                                    // the
                                                                              // uiScheme after 4.2(included) as the
                                                                              // connection wizard is uniformed.
                            }
                        }
                        // update
                       RepositoryUpdateManager.updateDBConnection(connectionItem);
                        // bug 20700
                        if (reloadCheck) {
                            tdqRepService.reloadDatabase(connectionItem);

                        } else {
                            DatabaseConnection dbConn = SwitchHelpers.DATABASECONNECTION_SWITCH.doSwitch(conn);
                            if (dbConn != null && dbConn instanceof DatabaseConnection) {
                                updateConnectionInformation(dbConn);
                            }
                        }
                       
                    }
                    this.connection.setName(connectionProperty.getLabel());
                    factory.save(connectionItem);
                    // 0005170: Schema renamed - new name not pushed out to dependant jobs
                    boolean isModified = propertiesWizardPage.isNameModifiedByUser();
                    if (isModified) {
                        if (GlobalServiceRegister.getDefault().isServiceRegistered(IDesignerCoreService.class)) {
                            IDesignerCoreService service = (IDesignerCoreService) GlobalServiceRegister.getDefault().getService(
                                    IDesignerCoreService.class);
                            if (service != null) {
                                service.refreshComponentView(connectionItem);
                            }
                        }
                    }
                    ProxyRepositoryFactory.getInstance().saveProject(ProjectManager.getInstance().getCurrentProject());
                    closeLockStrategy();

                }
            } catch (Exception e) {
                String detailError = e.toString();
                new ErrorDialogWidthDetailArea(getShell(), PID, Messages.getString("CommonWizard.persistenceException"), //$NON-NLS-1$
                        detailError);
                log.error(Messages.getString("CommonWizard.persistenceException") + "\n" + detailError); //$NON-NLS-1$ //$NON-NLS-2$
                return false;
            }
            List<IRepositoryViewObject> list = new ArrayList<IRepositoryViewObject>();
            list.add(repositoryObject);
            if (GlobalServiceRegister.getDefault().isServiceRegistered(IRepositoryService.class)) {
                IRepositoryService service = (IRepositoryService) GlobalServiceRegister.getDefault().getService(
                        IRepositoryService.class);
                service.notifySQLBuilder(list);
            }

            if (tdqRepService != null) {
                tdqRepService.notifySQLExplorer(connectionItem);
                if (CoreRuntimePlugin.getInstance().isDataProfilePerspectiveSelected()) {
                    tdqRepService.openEditor(connectionItem);
                    tdqRepService.refresh();
                }
            }
            updateTdqDependencies();
            return true;
        } else {
            return false;
        }
    }
    
    private boolean openConfirmReloadDialog() {
        return MessageDialog.openQuestion(Display.getCurrent().getActiveShell(),
                Messages.getString("DatabaseWizard.ReloadTitle"), //$NON-NLS-1$
                Messages.getString("DatabaseWizard.ReloadMessages"));//$NON-NLS-1$
    }

    /**
     * DOC xqliu Comment method "updateTdqDependencies".
     */
    private void updateTdqDependencies() {
        if (connectionItem != null) {
            String oldVersion = this.originalVersion;
            String newVersioin = connectionItem.getProperty().getVersion();
            if (oldVersion != null && newVersioin != null && !newVersioin.equals(oldVersion)) {
                AbstractResourceChangesService resChangeService = TDQServiceRegister.getInstance().getResourceChangeService(
                        AbstractResourceChangesService.class);
                if (resChangeService != null) {
                    resChangeService.updateDependeciesWhenVersionChange(connectionItem, oldVersion, newVersioin);
                }
            }
        }
    }

    @Override
    public boolean performCancel() {
        if (!creation) {
            connectionItem.getProperty().setVersion(this.originalVersion);
            connectionItem.getProperty().setLabel(this.originaleObjectLabel);
            connectionItem.getProperty().setDescription(this.originalDescription);
            connectionItem.getProperty().setPurpose(this.originalPurpose);
            connectionItem.getProperty().setStatusCode(this.originalStatus);
        }
        return super.performCancel();
    }

    /**
     * We will accept the selection in the workbench to see if we can initialize from it.
     * 
     * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
     */
    public void init(final IWorkbench workbench, final IStructuredSelection selection2) {
        super.setWorkbench(workbench);
        this.selection = selection2;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.wizards.RepositoryWizard#getConnectionItem()
     */
    public ConnectionItem getConnectionItem() {
        return this.connectionItem;
    }

    /**
     * 
     * DOC Comment method "updateConnectionInformation".
     * 
     * @param dbConn
     */
    private void updateConnectionInformation(DatabaseConnection dbConn) {
        java.sql.Connection sqlConn = null;
        String dbType = null;
        try {
            IMetadataConnection metaConnection = MetadataFillFactory.getDBInstance().fillUIParams(dbConn);
            dbConn = (DatabaseConnection) MetadataFillFactory.getDBInstance().fillUIConnParams(metaConnection, dbConn);
            sqlConn = (java.sql.Connection) MetadataConnectionUtils.checkConnection(metaConnection).getObject();

            dbType = metaConnection.getDbType();
            if (sqlConn != null) {
                DatabaseMetaData dbMetaData = ExtractMetaDataUtils.getDatabaseMetaData(sqlConn, dbType);
                MetadataFillFactory.getDBInstance().fillCatalogs(dbConn, dbMetaData,
                        MetadataConnectionUtils.getPackageFilter(dbConn, dbMetaData, true));
                MetadataFillFactory.getDBInstance().fillSchemas(dbConn, dbMetaData,
                        MetadataConnectionUtils.getPackageFilter(dbConn, dbMetaData, false));
            }
        } finally {
            // bug 22619
            if (dbType != null
                    && (dbType.equals(EDatabaseTypeName.HSQLDB.getDisplayName())
                            || dbType.equals(EDatabaseTypeName.HSQLDB_SERVER.getDisplayName())
                            || dbType.equals(EDatabaseTypeName.HSQLDB_WEBSERVER.getDisplayName()) || dbType
                            .equals(EDatabaseTypeName.HSQLDB_IN_PROGRESS.getDisplayName()))) {
                ExtractMetaDataUtils.closeConnection();
            }
            Driver driver = MetadataConnectionUtils.getDerbyDriver();
            if (driver != null) {
                try {
                    driver.connect("jdbc:derby:;shutdown=true", null); //$NON-NLS-1$
                } catch (SQLException e) {
                    // exception of shutdown success. no need to catch.
                }
            }
        }
    }
}
