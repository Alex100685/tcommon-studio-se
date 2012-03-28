// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.ui.wizards.metadata.connection.files.salesforce;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.ui.runtime.image.ECoreImage;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.commons.ui.swt.dialogs.ErrorDialogWidthDetailArea;
import org.talend.core.model.metadata.IMetadataConnection;
import org.talend.core.model.metadata.IMetadataContextModeManager;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.metadata.builder.connection.SalesforceSchemaConnection;
import org.talend.core.model.metadata.builder.database.TableInfoParameters;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.model.update.RepositoryUpdateManager;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.cwm.helper.ConnectionHelper;
import org.talend.metadata.managment.ui.i18n.Messages;
import org.talend.repository.ProjectManager;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.ui.utils.ManagerConnection;
import org.talend.repository.ui.wizards.CheckLastVersionRepositoryWizard;
import org.talend.repository.ui.wizards.metadata.table.database.DatabaseTableFilterWizardPage;
import orgomg.cwm.objectmodel.core.Package;

/**
 * TableWizard present the TableForm width the MetaDataTable. Use to create a new table (need a connection to a DB).
 */

public class SalesforceModulesWizard extends CheckLastVersionRepositoryWizard implements INewWizard {

    private static Logger log = Logger.getLogger(SalesforceModulesWizard.class);

    private SelectorModulesWizardPage selectorWizardPage;

    // private DatabaseTableWizardPage tableWizardpage;

    private DatabaseTableFilterWizardPage tableFilterWizardPage;

    private SalesforceSchemaConnection temConnection;

    private IMetadataContextModeManager contextModeManager;

    private boolean skipStep;

    protected String[] existingNames;

    private SalesforceModuleParseAPI salesforceAPI = new SalesforceModuleParseAPI();

    private final ManagerConnection managerConnection;

    private Map<String, String> oldTableMap;

    private IMetadataConnection metadataConnection;

    private List<IMetadataTable> oldMetadataTable;

    private MetadataTable selectedMetadataTable;

    private SalesforceWizardPage page2 = null;

    private SalesforceWizardPage page3 = null;

    private Property connectionProperty = null;

    private static final String ALL_STEPS = " 5"; //$NON-NLS-1$

    /* hywang add for 0017426,catches used to store the uuids and labels of old tables and columns */
    // private static Map<String, String> originalColumnsMap = new HashMap<String, String>();
    //
    // private static Map<String, String> originalTablesMap = new HashMap<String, String>();

    /**
     * DOC ocarbone DatabaseTableWizard constructor comment.
     * 
     * @param workbench
     * @param idNodeDbConnection
     * @param metadataTable
     * @param existingNames
     * @param managerConnection
     */
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public SalesforceModulesWizard(IWorkbench workbench, boolean creation, IRepositoryViewObject object,
            MetadataTable metadataTable, String[] existingNames, boolean forceReadOnly, ManagerConnection managerConnection,
            IMetadataConnection metadataConnection) {
        super(workbench, creation, forceReadOnly);
        this.existingNames = existingNames;
        this.managerConnection = managerConnection;
        this.metadataConnection = metadataConnection;
        setNeedsProgressMonitor(true);

        // set the repositoryObject, lock and set isRepositoryObjectEditable
        setRepositoryObject(object);
        isRepositoryObjectEditable();
        initLockStrategy();
        this.selectedMetadataTable = metadataTable;
        this.connectionItem = (ConnectionItem) object.getProperty().getItem();
        if (connectionItem != null) {
            // oldTableMap = RepositoryUpdateManager.getOldTableIdAndNameMap(connectionItem, metadataTable, creation);
            // oldMetadataTable = RepositoryUpdateManager.getConversionMetadataTables(connectionItem.getConnection());
            cloneBaseConnection((SalesforceSchemaConnection) connectionItem.getConnection());
        }
        // originalColumnsMap.clear();
        // originalTablesMap.clear();
    }

    /**
     * DOC acer Comment method "setSkipStep".
     * 
     * @param skipStep
     */
    public void setSkipStep(boolean skipStep) {
        this.skipStep = skipStep;
    }

    /**
     * Adding the page to the wizard.
     */
    @Override
    public void addPages() {
        setWindowTitle(Messages.getString("TableWizard.windowTitle")); //$NON-NLS-1$
        setDefaultPageImageDescriptor(ImageProvider.getImageDesc(ECoreImage.METADATA_TABLE_WIZ));
        TableInfoParameters tableInfoParameters = new TableInfoParameters();
        selectorWizardPage = new SelectorModulesWizardPage(connectionItem, isRepositoryObjectEditable(), tableInfoParameters,
                metadataConnection, temConnection, salesforceAPI);

        // // tableWizardpage = new DatabaseTableWizardPage(selectedMetadataTable, managerConnection, connectionItem,
        // // isRepositoryObjectEditable(), metadataConnection, null);
        // tableFilterWizardPage = new DatabaseTableFilterWizardPage(tableInfoParameters, this.connectionItem);
        // // if (creation && !skipStep) {
        //        tableFilterWizardPage.setDescription(Messages.getString("DatabaseTableWizard.description")); //$NON-NLS-1$
        // tableFilterWizardPage.setPageComplete(true);
        selectorWizardPage
                .setTitle(Messages.getString("TableWizardPage.titleCreate") + " \"" + connectionItem.getProperty().getLabel() //$NON-NLS-1$ //$NON-NLS-2$
                        + "\""); //$NON-NLS-1$
        selectorWizardPage.setDescription(Messages.getString("TableWizardPage.descriptionCreate")); //$NON-NLS-1$
        selectorWizardPage.setPageComplete(true);
        // page2 = new SalesforceWizardPage(2, connectionItem, temConnection, isRepositoryObjectEditable(),
        // existingNames,
        // salesforceAPI, contextModeManager);
        //        page2.setTitle(Messages.getString("FileWizardPage.titleCreate") + " 3 " //$NON-NLS-1$ //$NON-NLS-2$
        //                + Messages.getString("FileWizardPage.of") + ALL_STEPS); //$NON-NLS-1$ //$NON-NLS-2$
        //        page2.setDescription(Messages.getString("FileWizardPage.descriptionCreateStep2")); //$NON-NLS-1$
        //
        // // addPage(page2);
        //
        // page3 = new SalesforceWizardPage(3, connectionItem, temConnection, isRepositoryObjectEditable(),
        // existingNames,
        // salesforceAPI, contextModeManager);
        //
        //        page3.setTitle(Messages.getString("FileWizardPage.titleCreate") + " 4 " //$NON-NLS-1$ //$NON-NLS-2$
        //                + Messages.getString("FileWizardPage.of") + ALL_STEPS); //$NON-NLS-1$ //$NON-NLS-2$
        //        page3.setDescription(Messages.getString("FileWizardPage.descriptionCreateStep3")); //$NON-NLS-1$
        //
        // // addPage(page3);
        // page3.setPageComplete(false);
        //
        // page2.setPageComplete(true);
        // tableWizardpage
        //                    .setTitle(Messages.getString("TableWizardPage.titleCreate") + " \"" + connectionItem.getProperty().getLabel() //$NON-NLS-1$ //$NON-NLS-2$
        //                            + "\""); //$NON-NLS-1$
        //            tableWizardpage.setDescription(Messages.getString("TableWizardPage.descriptionCreate")); //$NON-NLS-1$
        // tableWizardpage.setPageComplete(false);

        // addPage(tableFilterWizardPage);
        addPage(selectorWizardPage);
        // addPage(page2);
        // addPage(page3);
        // addPage(tableWizardpage);

        // } else {
        // // tableWizardpage
        //            //                    .setTitle(Messages.getString("TableWizardPage.titleUpdate") + " \"" + connectionItem.getProperty().getLabel() //$NON-NLS-1$ //$NON-NLS-2$
        //            //                            + "\""); //$NON-NLS-1$
        //            //            tableWizardpage.setDescription(Messages.getString("TableWizardPage.descriptionUpdate")); //$NON-NLS-1$
        // // tableWizardpage.setPageComplete(false);
        // // addPage(tableWizardpage);
        // }

    }

    /**
     * This method determine if the 'Finish' button is enable This method is called when 'Finish' button is pressed in
     * the wizard. We will create an operation and run it using wizard as execution context.
     */
    @Override
    public boolean performFinish() {

        boolean formIsPerformed = false;
        // formIsPerformed = page3.isPageComplete();
        formIsPerformed = true;
        connectionItem.setConnection((SalesforceSchemaConnection) EcoreUtil.copy(temConnection));
        // if (page3 == null) {
        // formIsPerformed = page1.isPageComplete();
        // } else {
        // formIsPerformed = page3.isPageComplete();
        // }

        if (formIsPerformed) {
            IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
            try {
                // factory.save(connectionItem);
                if (creation) {
                    // String nextId = factory.getNextId();
                    // connectionProperty.setId(nextId);
                    factory.create(connectionItem, new Path(""));
                } else {
                    // update
                    RepositoryUpdateManager.updateFileConnection(connectionItem);
                    factory.save(connectionItem);
                    closeLockStrategy();
                }

                // if (creation) {
                // String nextId = factory.getNextId();
                // connectionProperty.setId(nextId);
                // factory.create(connectionItem, salesforceSchemaWizardPage0.getDestinationPath());
                // } else {
                // // update
                // RepositoryUpdateManager.updateFileConnection(connectionItem);
                // factory.save(connectionItem);
                // closeLockStrategy();
                // }
                ProxyRepositoryFactory.getInstance().saveProject(ProjectManager.getInstance().getCurrentProject());

                ProxyRepositoryFactory.getInstance().saveProject(ProjectManager.getInstance().getCurrentProject());

            } catch (PersistenceException e) {
                String detailError = e.toString();
                new ErrorDialogWidthDetailArea(getShell(), PID, Messages.getString("CommonWizard.persistenceException"), //$NON-NLS-1$
                        detailError);
                log.error(Messages.getString("CommonWizard.persistenceException") + "\n" + detailError); //$NON-NLS-1$ //$NON-NLS-2$
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    // private void generateOriginalColumnsMap(Collection<? extends Package> dataPackageFromOrignalConnection) {
    // for (orgomg.cwm.objectmodel.core.Package pkg : dataPackageFromOrignalConnection) {
    // for (ModelElement mol : pkg.getOwnedElement()) {
    // if (mol instanceof MetadataTable) {
    // MetadataTable table = (MetadataTable) mol;
    // String oldTableUuid = ResourceHelper.getUUID(table);
    // originalTablesMap.put(table.getLabel(), oldTableUuid);
    // for (ModelElement col : table.getFeature()) {
    // if (col instanceof MetadataColumn) {
    // MetadataColumn column = (MetadataColumn) col;
    // String oldColumnUuid = ResourceHelper.getUUID(column);
    // originalColumnsMap.put(column.getLabel(), oldColumnUuid);
    // }
    // }
    // }
    // if (mol instanceof Catalog) {
    // Catalog catlog = (Catalog) mol;
    // List<Schema> subschemas = CatalogHelper.getSchemas(catlog);
    // if (!subschemas.isEmpty()) {
    // generateOriginalColumnsMap(subschemas);
    // }
    // }
    // }
    //
    // }
    // }

    // private void replaceUUidsForColumnsAndTables(Collection<? extends Package> copyDataPackage) {
    // for (orgomg.cwm.objectmodel.core.Package pkg : copyDataPackage) {
    // for (ModelElement mol : pkg.getOwnedElement()) {
    // if (mol instanceof MetadataTable) {
    // MetadataTable table = (MetadataTable) mol;
    // if (originalTablesMap.keySet().contains(table.getLabel())) {
    // Resource resource = table.eResource();
    // if (resource != null && resource instanceof XMLResource) {
    // XMLResource xmlResource = (XMLResource) resource;
    // xmlResource.setID(table, originalTablesMap.get(table.getLabel()));
    // }
    // }
    // for (ModelElement col : table.getFeature()) {
    // if (col instanceof MetadataColumn) {
    // MetadataColumn column = (MetadataColumn) col;
    // if (originalColumnsMap.keySet().contains(column.getLabel())) {
    // Resource resource = column.eResource();
    // if (resource != null && resource instanceof XMLResource) {
    // XMLResource xmlResource = (XMLResource) resource;
    // xmlResource.setID(column, originalColumnsMap.get(column.getLabel()));
    // }
    // }
    // }
    // }
    // }
    // if (mol instanceof Catalog) {
    // Catalog catlog = (Catalog) mol;
    // List<Schema> subschemas = CatalogHelper.getSchemas(catlog);
    // if (!subschemas.isEmpty()) {
    // replaceUUidsForColumnsAndTables(subschemas);
    // }
    // }
    // }
    //
    // }
    // }

    /**
     * We will accept the selection in the workbench to see if we can initialize from it.
     * 
     * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
     */
    public void init(final IWorkbench workbench, final IStructuredSelection selection2) {
        this.selection = selection2;
    }

    /**
     * execute saveMetaData() on TableForm.
     */
    private void saveMetaData() {
        IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        try {
            factory.save(connectionItem);
        } catch (PersistenceException e) {
            String detailError = e.toString();
            new ErrorDialogWidthDetailArea(getShell(), PID, Messages.getString("CommonWizard.persistenceException"), detailError); //$NON-NLS-1$
            log.error(Messages.getString("CommonWizard.persistenceException") + "\n" + detailError); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.wizards.RepositoryWizard#performCancel()
     */
    @Override
    public boolean performCancel() {
        selectorWizardPage.performCancel();
        temConnection = null;
        return super.performCancel();
    }

    /**
     * clone a new DB connection
     */
    private void cloneBaseConnection(SalesforceSchemaConnection connection) {
        temConnection = (SalesforceSchemaConnection) EcoreUtil.copy(connection);
        EList<Package> dataPackage = connection.getDataPackage();
        Collection<Package> newDataPackage = EcoreUtil.copyAll(dataPackage);
        ConnectionHelper.addPackages(newDataPackage, temConnection);
    }
}
