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
package org.talend.repository.ui.wizards.metadata.table.files;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.ui.swt.dialogs.ErrorDialogWidthDetailArea;
import org.talend.commons.utils.platform.PluginChecker;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.ITDQRepositoryService;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.builder.ConvertionHelper;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.Item;
import org.talend.core.model.update.RepositoryUpdateManager;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.service.IMetadataManagmentService;
import org.talend.metadata.managment.ui.i18n.Messages;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.ui.wizards.AbstractRepositoryFileTableWizard;
import orgomg.cwm.objectmodel.core.Dependency;

/**
 * DOC ocarbone class global comment.
 * 
 * $Id: FileDelimitedTableWizard.java 51998 2010-12-01 05:33:08Z hywang $
 * 
 */
public class FileDelimitedTableWizard extends AbstractRepositoryFileTableWizard implements INewWizard {

    private static Logger log = Logger.getLogger(FileDelimitedTableWizard.class);

    private FileTableWizardPage tableWizardpage;

    private Map<String, String> oldTableMap;

    private IMetadataTable oldMetadataTable;

    /**
     * Constructor for TableWizard.
     * 
     * @param ISelection
     */
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public FileDelimitedTableWizard(IWorkbench workbench, boolean creation, ConnectionItem connectionItem,
            MetadataTable metadataTable, boolean forceReadOnly) {
        super(workbench, creation, forceReadOnly);
        this.connectionItem = connectionItem;
        this.metadataTable = metadataTable;
        if (connectionItem != null) {
            oldTableMap = RepositoryUpdateManager.getOldTableIdAndNameMap(connectionItem, metadataTable, creation);
            oldMetadataTable = ConvertionHelper.convert(metadataTable);
            initConnectionCopy(connectionItem.getConnection());
        }
        setNeedsProgressMonitor(true);

        isRepositoryObjectEditable();
        initLockStrategy();
    }

    /**
     * Adding the page to the wizard.
     */

    public void addPages() {
        setWindowTitle(Messages.getString("SchemaWizard.windowTitle")); //$NON-NLS-1$

        tableWizardpage = new FileTableWizardPage(connectionItem, metadataTableCopy, isRepositoryObjectEditable());

        if (creation) {
            tableWizardpage.setTitle(Messages.getString(
                    "FileTableWizardPage.titleCreate", connectionItem.getProperty().getLabel())); //$NON-NLS-1$
            tableWizardpage.setDescription(Messages.getString("FileTableWizardPage.descriptionCreate")); //$NON-NLS-1$
            tableWizardpage.setPageComplete(false);
        } else {
            tableWizardpage.setTitle(Messages.getString("FileTableWizardPage.titleUpdate", metadataTable.getLabel())); //$NON-NLS-1$
            tableWizardpage.setDescription(Messages.getString("FileTableWizardPage.descriptionUpdate")); //$NON-NLS-1$
            tableWizardpage.setPageComplete(isRepositoryObjectEditable());
        }
        addPage(tableWizardpage);
    }

    /**
     * This method determine if the 'Finish' button is enable This method is called when 'Finish' button is pressed in
     * the wizard. We will create an operation and run it using wizard as execution context.
     */
    public boolean performFinish() {
        if (tableWizardpage.isPageComplete()) {
            applyConnectionCopy();
            // MOD qiongli 2011-11-23 TDQ-3930,TDQ-3797.pop a question dialog when there are anlaysises in TDQ need to
            // update.if user click cancel,will return and stop the retive action.
            boolean needUpdateAnalysis = false;
            ITDQRepositoryService tdqRepositoryService = null;
            if (GlobalServiceRegister.getDefault().isServiceRegistered(ITDQRepositoryService.class)) {
                tdqRepositoryService = (ITDQRepositoryService) org.talend.core.GlobalServiceRegister.getDefault().getService(
                        ITDQRepositoryService.class);
                needUpdateAnalysis = isNeedUpdateDQ(repositoryObject.getProperty().getItem(), tdqRepositoryService);
            }

            if (tdqRepositoryService != null && needUpdateAnalysis) {
                if (!tdqRepositoryService.confirmUpdateAnalysis(connectionItem)) {
                    return true;
                }
            }
            // update
            RepositoryUpdateManager.updateSingleSchema(connectionItem, metadataTable, oldMetadataTable, oldTableMap);

            IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
            try {
                factory.save(repositoryObject.getProperty().getItem());
                closeLockStrategy();
                // MOD qiongli 2011-10-21 TDQ-3797,if need to update analysises for DQ,update them after saving DB.
                if (tdqRepositoryService != null && needUpdateAnalysis) {
                    tdqRepositoryService.updateImpactOnAnalysis(connectionItem);
                }// ~
            } catch (PersistenceException e) {
                String detailError = e.toString();
                new ErrorDialogWidthDetailArea(getShell(), PID,
                        Messages.getString("CommonWizard.persistenceException"), detailError); //$NON-NLS-1$
                log.error(Messages.getString("CommonWizard.persistenceException") + "\n" + detailError); //$NON-NLS-1$ //$NON-NLS-2$
            }
            connectionCopy = null;
            metadataTableCopy = null;
            return true;
        } else {
            return false;
        }

    }

    /**
     * We will accept the selection in the workbench to see if we can initialize from it.
     * 
     * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
     */
    public void init(final IWorkbench workbench, final IStructuredSelection selection) {
        this.selection = selection;
    }

    @Override
    public ConnectionItem getConnectionItem() {
        return this.connectionItem;
    }

    /**
     * 
     * DOC qiongli:judge if need to update related Analyses for TDQ
     * 
     * @return
     */
    private boolean isNeedUpdateDQ(Item item, ITDQRepositoryService tdqRepoService) {

        if (!(PluginChecker.isTDQLoaded() || PluginChecker.isOnlyTopLoaded()) || item == null
                || !(item instanceof ConnectionItem) || tdqRepoService == null) {
            return false;
        }
        Connection connection = ((ConnectionItem) item).getConnection();
        if (connection == null || !tdqRepoService.hasClientDependences((ConnectionItem) item)) {
            return false;
        }
        EList<Dependency> supplierDependency = connection.getSupplierDependency();
        if (supplierDependency == null || supplierDependency.isEmpty()) {
            return false;
        }
        Map<String, String> schemaRenamedMap = RepositoryUpdateManager.getSchemaRenamedMap((ConnectionItem) item, oldTableMap);
        boolean isNeed = !schemaRenamedMap.isEmpty();
        if (!isNeed) {
            if (metadataTable != null && oldMetadataTable != null && oldTableMap.containsKey(metadataTable.getId())) {
                if (GlobalServiceRegister.getDefault().isServiceRegistered(IMetadataManagmentService.class)) {
                    IMetadataManagmentService service = (IMetadataManagmentService) GlobalServiceRegister.getDefault()
                            .getService(IMetadataManagmentService.class);
                    IMetadataTable newMetadataTable = service.convertMetadataTable(metadataTable);
                    isNeed = !oldMetadataTable.sameMetadataAs(newMetadataTable, IMetadataColumn.OPTIONS_NONE);
                }
            }
        }
        return isNeed;
    }

}
