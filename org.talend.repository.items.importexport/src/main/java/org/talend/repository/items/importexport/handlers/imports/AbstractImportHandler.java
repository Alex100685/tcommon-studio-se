// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.items.importexport.handlers.imports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.talend.commons.emf.CwmResource;
import org.talend.commons.emf.EmfHelper;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.utils.VersionUtils;
import org.talend.commons.utils.io.FileCopyUtils;
import org.talend.commons.utils.time.TimeMeasure;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.builder.connection.ConnectionPackage;
import org.talend.core.model.migration.IMigrationToolService;
import org.talend.core.model.properties.BusinessProcessItem;
import org.talend.core.model.properties.ByteArray;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.ContextItem;
import org.talend.core.model.properties.FileItem;
import org.talend.core.model.properties.FolderItem;
import org.talend.core.model.properties.FolderType;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.JobletProcessItem;
import org.talend.core.model.properties.LinkDocumentationItem;
import org.talend.core.model.properties.LinkType;
import org.talend.core.model.properties.MigrationTask;
import org.talend.core.model.properties.NotationHolder;
import org.talend.core.model.properties.ProcessItem;
import org.talend.core.model.properties.Project;
import org.talend.core.model.properties.PropertiesPackage;
import org.talend.core.model.properties.Property;
import org.talend.core.model.properties.ReferenceFileItem;
import org.talend.core.model.properties.RoutineItem;
import org.talend.core.model.properties.SnippetItem;
import org.talend.core.model.properties.User;
import org.talend.core.model.properties.helper.ByteArrayResource;
import org.talend.core.model.relationship.RelationshipItemBuilder;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.model.utils.MigrationUtil;
import org.talend.core.repository.constants.FileConstants;
import org.talend.core.repository.model.PropertiesProjectResourceImpl;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.runtime.CoreRuntimePlugin;
import org.talend.designer.business.model.business.BusinessPackage;
import org.talend.designer.business.model.business.BusinessProcess;
import org.talend.designer.core.model.utils.emf.talendfile.TalendFilePackage;
import org.talend.repository.ProjectManager;
import org.talend.repository.items.importexport.handlers.HandlerUtil;
import org.talend.repository.items.importexport.handlers.cache.RepositoryObjectCache;
import org.talend.repository.items.importexport.i18n.Messages;
import org.talend.repository.items.importexport.ui.wizard.imports.managers.ResourcesManager;
import org.talend.repository.items.importexport.ui.wizard.imports.models.ItemRecord;
import org.talend.repository.items.importexport.ui.wizard.imports.models.ItemRecord.State;
import org.talend.repository.model.ERepositoryStatus;

/**
 * DOC ggu class global comment. Detailled comment
 * 
 * 
 * most of importers should extends this class, just recommend.
 */
public abstract class AbstractImportHandler extends ImportHandler {

    @Override
    protected boolean isValidPath(IPath path) {
        return ImportCacheHelper.getInstance().getXmiResourceManager().isPropertyFile(path.lastSegment());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.items.importexport.handlers.IImportHandler#computeItemRecord(org.talend.repository.items
     * .importexport.ui.wizard.imports.managers.ResourcesManager, org.eclipse.core.runtime.IPath)
     */
    @Override
    public ItemRecord computeItemRecord(ResourcesManager resManager, IPath path) {
        ItemRecord itemRecord = new ItemRecord(path);
        computeProperty(resManager, itemRecord);
        return itemRecord;
    }

    protected void computeProperty(ResourcesManager manager, ItemRecord itemRecord) {
        InputStream stream = null;
        try {
            stream = manager.getStream(itemRecord.getPath());
            final Resource resource = createResource(itemRecord, itemRecord.getPath(), false);
            URIConverter uriConverter = resource.getResourceSet().getURIConverter();
            resource.getResourceSet().setURIConverter(new ExtensibleURIConverterImpl() {

                /*
                 * (non-Javadoc)
                 * 
                 * @see org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl#createInputStream(org.eclipse.
                 * emf.common.util.URI, java.util.Map)
                 */
                @Override
                public InputStream createInputStream(URI uri, Map<?, ?> options) throws IOException {
                    InputStream inputStream = null;
                    EPackage ePackage = resource.getResourceSet().getPackageRegistry().getEPackage(uri.toString());
                    if (ePackage != null || !"http".equals(uri.scheme())) { //$NON-NLS-1$
                        inputStream = super.createInputStream(uri, options);
                    } else {
                        inputStream = null;
                    }
                    return inputStream;
                }
            });
            EmfHelper.loadResource(resource, stream, null);
            resource.getResourceSet().setURIConverter(uriConverter);
            itemRecord.setProperty((Property) EcoreUtil.getObjectByType(resource.getContents(),
                    PropertiesPackage.eINSTANCE.getProperty()));
        } catch (Exception e) {
            // ignore, must be one invalid or unknown item
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    protected Resource createResource(ItemRecord itemRecord, IPath path, boolean byteArrayResource) throws FileNotFoundException {
        Resource resource;
        ResourceSet resourceSet = itemRecord.getResourceSet();
        final URI pathUri = HandlerUtil.getURI(path);
        if (byteArrayResource) {
            resource = new ByteArrayResource(pathUri);
            resourceSet.getResources().add(resource);
        } else {
            if (FileConstants.ITEM_EXTENSION.equals(path.getFileExtension())) {
                resource = createItemResource(pathUri);
                resourceSet.getResources().add(resource);
            } else {
                resource = resourceSet.createResource(pathUri);
            }
        }
        return resource;
    }

    protected Resource createItemResource(URI pathUri) {
        // related to the ImportItemUtil.createResource
        return new CwmResource(pathUri);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.items.importexport.handlers.IImportHandler#isSame(org.talend.repository.items.importexport
     * .ui.wizard.imports.models.ItemRecord,
     * org.talend.repository.items.importexport.ui.wizard.imports.models.ItemRecord)
     */
    @Override
    public boolean isSame(ItemRecord itemRecord1, ItemRecord itemRecord2) {
        return StringUtils.equals(itemRecord1.getProperty().getId(), itemRecord2.getProperty().getId())
                && StringUtils.equals(itemRecord1.getProperty().getVersion(), itemRecord2.getProperty().getVersion());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.items.importexport.handlers.IImportHandler#checkItem(org.talend.repository.items.importexport
     * .ui.wizard.imports.managers.ResourcesManager,
     * org.talend.repository.items.importexport.ui.wizard.imports.models.ItemRecord, boolean)
     */
    @Override
    public boolean checkItem(ResourcesManager resManager, ItemRecord itemRecord, boolean overwrite) {
        try {
            Item item = itemRecord.getItem();
            ERepositoryObjectType itemType = ERepositoryObjectType.getItemType(item);
            if (itemType == null) {
                itemRecord.addError(Messages.getString("AbstractImportHandler_unsupportItem")); //$NON-NLS-1$
                return false; // can't import this item.
            }
            if (item.getState() == null) {
                itemRecord.addError(Messages.getString("AbstractImportHandler_unsupportItem"));//$NON-NLS-1$
                return false;
            }

            final RepositoryObjectCache repObjectcache = ImportCacheHelper.getInstance().getRepObjectcache();
            repObjectcache.initialize(itemType);

            IRepositoryViewObject itemWithSameIdObj = null;
            IRepositoryViewObject itemWithSameNameObj = null;

            for (IRepositoryViewObject current : repObjectcache.getItemsFromRepository().get(itemType)) {
                final Property property = itemRecord.getProperty();
                if (property != null) {
                    if (isSameName(itemRecord, current)) {
                        itemWithSameNameObj = current;
                    }
                    if (property.getId() != null && property.getId().equals(current.getId())) {
                        itemWithSameIdObj = current;
                    }
                }
            }
            boolean nameAvailable = itemWithSameNameObj == null;
            boolean idAvailable = itemWithSameIdObj == null;

            if (nameAvailable) {
                if (!idAvailable) {
                    // same id but different name,no need to care overwrite cause the item will be considered as a
                    // different one,see bug 20445
                    itemRecord.setState(State.ID_EXISTED);
                    itemRecord.setExistingItemWithSameId(itemWithSameIdObj);
                    if (!overwrite) {
                        itemRecord.addError(Messages.getString("AbstractImportHandler_nameUsed")); //$NON-NLS-1$
                    }
                }
            } else {
                if (idAvailable) {
                    // same name but different id
                    itemRecord.setState(State.NAME_EXISTED);

                    if (overwrite) {
                        // if anything system, don't replace the source item if same name.
                        // if not from system, can overwrite.
                        itemRecord.setExistingItemWithSameId(itemWithSameNameObj);
                        // TDI-21399,TDI-21401
                        // if item is locked, cannot overwrite
                        if (itemWithSameNameObj != null) {
                            ERepositoryStatus status = itemWithSameNameObj.getRepositoryStatus();
                            if (status == ERepositoryStatus.LOCK_BY_OTHER || status == ERepositoryStatus.LOCK_BY_USER) {
                                itemRecord.addError(Messages.getString("AbstractImportHandler_itemLocked")); //$NON-NLS-1$
                            }
                        }
                    }
                } else {
                    // same name and same id
                    itemRecord.setState(State.NAME_AND_ID_EXISTED);
                    if (overwrite) {
                        if (itemWithSameNameObj != null
                                && itemWithSameIdObj != null
                                && !itemWithSameNameObj.getProperty().getLabel()
                                        .equals(itemWithSameIdObj.getProperty().getLabel())) { // ??
                            // if anything system, don't replace the source item if same name.
                            // if not from system, can overwrite.
                            itemRecord.setExistingItemWithSameId(itemWithSameNameObj);
                        }
                    }
                }
                if (!overwrite) {
                    itemRecord.addError(Messages.getString("AbstractImportHandler_nameUsed")); //$NON-NLS-1$
                }
            }

            if (overwrite && itemRecord.getState() == State.NAME_AND_ID_EXISTED) {
                // if item is locked, cannot overwrite
                if (checkIfLocked(itemRecord)) {
                    itemRecord.addError(Messages.getString("AbstractImportHandler_itemLocked")); //$NON-NLS-1$
                }

            }

        } catch (PersistenceException e) {
            log.error("Error when checking item :" + itemRecord.getPath(), e);
        }
        return true;
    }

    protected boolean isSameName(ItemRecord itemRecord, IRepositoryViewObject repObject) {
        final Property property = itemRecord.getProperty();
        if ((property.getLabel() != null && property.getLabel().equalsIgnoreCase(repObject.getLabel())) // same label
                && (property.getId() != null && !property.getId().equals(repObject.getId())) // different id
        ) {
            return true;
        }
        return false;
    }

    /**
     * DOC hcw Comment method "checkIfLocked".
     * 
     * @param itemRecord
     * @return
     * @throws PersistenceException
     */
    protected boolean checkIfLocked(ItemRecord itemRecord) throws PersistenceException {
        final RepositoryObjectCache repObjectcache = ImportCacheHelper.getInstance().getRepObjectcache();
        Boolean lockState = repObjectcache.getItemLockState(itemRecord);
        if (lockState != null) {
            return lockState.booleanValue();
        }

        List<IRepositoryViewObject> list = repObjectcache.findObjectsByItem(itemRecord);

        for (IRepositoryViewObject obj : list) {
            ERepositoryStatus status = obj.getRepositoryStatus();
            if (status == ERepositoryStatus.LOCK_BY_OTHER || status == ERepositoryStatus.LOCK_BY_USER) {
                itemRecord.setLocked(true);
                repObjectcache.setItemLockState(itemRecord, true);
                return true;
            }
        }

        repObjectcache.setItemLockState(itemRecord, false);
        return false;
    }

    @Override
    public void checkAndSetProject(ResourcesManager resManager, ItemRecord itemRecord) {
        InternalEObject author = (InternalEObject) itemRecord.getProperty().getAuthor();
        URI uri = null;
        if (author != null) {
            uri = author.eProxyURI();
        }

        IPath projectFilePath = HandlerUtil.getValidProjectFilePath(resManager, itemRecord.getPath());
        if (projectFilePath != null) {
            Project project = computeProject(resManager, itemRecord, projectFilePath);
            if (checkProject(project, itemRecord)) {
                // set item project into record.
                itemRecord.setItemProject(project);
                // we can try to import item
                // and we will try to resolve user
                if (uri != null) {
                    User user = (User) project.eResource().getEObject(uri.fragment());
                    itemRecord.getProperty().setAuthor(user);
                }
            }
        } else {
            ERepositoryObjectType itemType = ERepositoryObjectType.getItemType(itemRecord.getItem());
            if (itemType.isDIItemType()) {
                itemRecord.addError(Messages.getString("AbstractImportHandler_projectNotFound")); //$NON-NLS-1$
            }
        }

    }

    protected Project computeProject(ResourcesManager manager, ItemRecord itemRecord, IPath path) {
        InputStream stream = null;
        Map<IPath, Project> pathWithProjects = ImportCacheHelper.getInstance().getPathWithProjects();
        try {
            if (!pathWithProjects.containsKey(path)) {
                stream = manager.getStream(path);
                Resource resource = createResource(itemRecord, path, false);
                resource.load(stream, null);
                EmfHelper.loadResource(resource, stream, null);
                pathWithProjects.put(path,
                        (Project) EcoreUtil.getObjectByType(resource.getContents(), PropertiesPackage.eINSTANCE.getProject()));
            }
            return pathWithProjects.get(path);
        } catch (IOException e) {
            // ignore
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return null;
    }

    protected boolean checkProject(Project project, ItemRecord itemRecord) {
        boolean checkProject = false;
        if (GlobalServiceRegister.getDefault().isServiceRegistered(IMigrationToolService.class)) {
            IMigrationToolService migrationService = (IMigrationToolService) GlobalServiceRegister.getDefault().getService(
                    IMigrationToolService.class);
            Set<Project> updatedProjects = ImportCacheHelper.getInstance().getUpdatedProjects();
            // update the old project which hasn't adapted to the new migration task system.
            if (!updatedProjects.contains(project)) {
                migrationService.updateMigrationSystem(project, false);
                updatedProjects.add(project);
            }

            Project currentProject = ProjectManager.getInstance().getCurrentProject().getEmfProject();

            if (project != null) {
                if (checkMigrationTasks(currentProject, project, itemRecord)) {
                    checkProject = true;
                }
            } else {
                itemRecord.addError(Messages.getString("AbstractImportHandler_projectNotFound")); //$NON-NLS-1$
            }
        }

        return checkProject;
    }

    /**
     * DOC ycbai Comment method "checkMigrationTasks".
     * 
     * @param currentProject
     * @param importedProject
     * @param itemRecord
     * @return
     */
    protected boolean checkMigrationTasks(Project currentProject, Project importedProject, ItemRecord itemRecord) {

        Map<String, Boolean> migrationTasksStatusPerProject = ImportCacheHelper.getInstance().getMigrationTasksStatusPerProject();
        Map<String, List<MigrationTask>> migrationTasksToApplyPerProject = ImportCacheHelper.getInstance()
                .getMigrationTasksToApplyPerProject();

        String importedProjectLabel = importedProject.getTechnicalLabel();
        if (migrationTasksStatusPerProject.containsKey(importedProjectLabel)) {
            if (migrationTasksStatusPerProject.get(importedProjectLabel)) {
                itemRecord.setMigrationTasksToApply(migrationTasksToApplyPerProject.get(importedProjectLabel));
                return true;
            } else {
                String message = Messages.getString("AbstractImportHandler_cannotImportMessage", importedProjectLabel); //$NON-NLS-1$
                itemRecord.addError(message);
                return false;
            }
        }

        boolean canApplyMigration = false;
        if (GlobalServiceRegister.getDefault().isServiceRegistered(IMigrationToolService.class)) {
            IMigrationToolService migrationService = (IMigrationToolService) GlobalServiceRegister.getDefault().getService(
                    IMigrationToolService.class);
            List<MigrationTask> migrationTasks = new ArrayList<MigrationTask>();
            if (migrationService.checkMigrationTasks(importedProject)) {
                List<MigrationTask> currentProjectMigrationTasks = new ArrayList<MigrationTask>(currentProject.getMigrationTask());
                List<MigrationTask> importedProjectMigrationTasks = new ArrayList<MigrationTask>(
                        importedProject.getMigrationTask());
                MigrationUtil.removeMigrationTaskByIds(importedProjectMigrationTasks, getOptionnalMigrationTasks());
                MigrationUtil.removeMigrationTaskById(importedProjectMigrationTasks,
                        "org.talend.repository.model.migration.AutoUpdateRelationsMigrationTask"); //$NON-NLS-1$
                MigrationUtil.removeMigrationTaskByMigrationTasks(currentProjectMigrationTasks, importedProjectMigrationTasks);
                itemRecord.setMigrationTasksToApply(currentProjectMigrationTasks);
                migrationTasks = currentProjectMigrationTasks;
                canApplyMigration = true;
                migrationTasksStatusPerProject.put(importedProjectLabel, true);
            } else {
                String message = Messages.getString("AbstractImportHandler_cannotImportMessage", importedProjectLabel); //$NON-NLS-1$
                itemRecord.addError(message);
                log.info("'" + itemRecord.getItemName() + "' " + message);
                migrationTasksStatusPerProject.put(importedProjectLabel, false);
            }
            migrationTasksToApplyPerProject.put(importedProjectLabel, migrationTasks);
        }
        return canApplyMigration;
    }

    private List<String> getOptionnalMigrationTasks() {
        List<String> toReturn = new ArrayList<String>();

        toReturn.add("org.talend.repository.documentation.migrationtask.generatejobdocmigrationtask"); //$NON-NLS-1$
        // old task, added for an old version of TOS, not used anymore.
        toReturn.add("org.talend.repository.migration.ReplaceOldContextScriptCodeMigrationTask"); //$NON-NLS-1$
        toReturn.add("org.talend.designer.core.model.process.migration.SynchronizeSchemaOnlyForPerlDemo"); //$NON-NLS-1$
        toReturn.add("org.talend.repository.model.migration.RenametFSFilterRow"); //$NON-NLS-1$        
        return toReturn;
    }

    private void logError(Exception e) {
        ImportCacheHelper.getInstance().setImportingError(true);
        ExceptionHandler.process(e);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.items.importexport.handlers.imports.IImportHandler#importItemRecord(org.eclipse.core.runtime
     * .IProgressMonitor, org.talend.repository.items.importexport.ui.wizard.imports.managers.ResourcesManager,
     * org.talend.repository.items.importexport.ui.wizard.imports.models.ItemRecord, boolean,
     * org.eclipse.core.runtime.IPath, org.talend.core.model.repository.ERepositoryObjectType, java.util.Set,
     * java.util.Set)
     */
    @Override
    public void importItemRecord(IProgressMonitor monitor, ResourcesManager resManager, ItemRecord selectedItemRecord,
            boolean overwrite, IPath destinationPath, ERepositoryObjectType contentType, Set<String> overwriteDeletedItems,
            Set<String> idDeletedBeforeImport) {

        monitor.subTask(Messages.getString("AbstractImportHandler_importing", selectedItemRecord.getItemName())); //$NON-NLS-1$
        resolveItem(resManager, selectedItemRecord);

        // ????? why need check like this?
        int num = 0;
        for (Object obj : selectedItemRecord.getResourceSet().getResources()) {
            if (!(obj instanceof PropertiesProjectResourceImpl)) {
                if (obj instanceof XMIResourceImpl) {
                    num++;
                    if (num > 2) {// The is no explanation for this value and what is this loop for to I increased
                        // it to
                        // 2 so that metadata migration for 4.1 works
                        try {
                            throw new InvocationTargetException(new PersistenceException("The source file of "
                                    + selectedItemRecord.getLabel() + " has error,Please check it!"));
                        } catch (InvocationTargetException e) {
                            ExceptionHandler.process(e);
                        }
                        return;
                    }
                }
            }
        }

        final Item item = selectedItemRecord.getItem();
        if (item != null) {
            ProxyRepositoryFactory repFactory = ProxyRepositoryFactory.getInstance();
            ERepositoryObjectType itemType = ERepositoryObjectType.getItemType(item);

            IPath path = checkAndCreatePath(selectedItemRecord, destinationPath, itemType, contentType);

            try {
                Item tmpItem = item;

                // delete existing items before importing, this should be done
                // once for a different id
                String id = selectedItemRecord.getProperty().getId();

                IRepositoryViewObject lastVersion = selectedItemRecord.getExistingItemWithSameId();
                if (lastVersion != null
                        && overwrite
                        && !selectedItemRecord.isLocked()
                        && (selectedItemRecord.getState() == State.ID_EXISTED
                                || selectedItemRecord.getState() == State.NAME_EXISTED || selectedItemRecord.getState() == State.NAME_AND_ID_EXISTED)
                        && !ImportCacheHelper.getInstance().getDeletedItems().contains(id)) {
                    if (!overwriteDeletedItems.contains(id)) { // bug 10520.
                        ERepositoryStatus status = repFactory.getStatus(lastVersion);
                        if (status == ERepositoryStatus.DELETED) {
                            repFactory.restoreObject(lastVersion, path); // restore first.
                        }
                        overwriteDeletedItems.add(id);
                    }
                    /* only delete when name exsit rather than id exist */
                    if (selectedItemRecord.getState().equals(ItemRecord.State.NAME_EXISTED)
                            || selectedItemRecord.getState().equals(ItemRecord.State.NAME_AND_ID_EXISTED)) {
                        if (!idDeletedBeforeImport.contains(id)) {
                            // TDI-19535 (check if exists, delete all items with same id)
                            List<IRepositoryViewObject> allVersionToDelete = repFactory.getAllVersion(ProjectManager
                                    .getInstance().getCurrentProject(), lastVersion.getId(), false);
                            for (IRepositoryViewObject currentVersion : allVersionToDelete) {
                                repFactory.forceDeleteObjectPhysical(lastVersion, currentVersion.getVersion());
                            }
                            idDeletedBeforeImport.add(id);
                        }
                    }
                    lastVersion = null;

                }

                User author = selectedItemRecord.getProperty().getAuthor();
                if (author != null) {
                    if (!repFactory.setAuthorByLogin(tmpItem, author.getLogin())) {
                        // author will be the logged user in create method
                        tmpItem.getProperty().setAuthor(null);
                    }
                }

                beforeCreatingItem(selectedItemRecord);

                final RepositoryObjectCache repObjectcache = ImportCacheHelper.getInstance().getRepObjectcache();
                if (lastVersion == null || selectedItemRecord.getState().equals(ItemRecord.State.ID_EXISTED)) {
                    repFactory.create(tmpItem, path, true);

                    afterCreatedItem(resManager, selectedItemRecord);

                    selectedItemRecord.setImported(true);

                } else if (VersionUtils.compareTo(lastVersion.getProperty().getVersion(), tmpItem.getProperty().getVersion()) < 0) {
                    repFactory.forceCreate(tmpItem, path);

                    afterForceCreatedItem(resManager, selectedItemRecord);

                    selectedItemRecord.setImported(true);
                } else {
                    PersistenceException e = new PersistenceException(Messages.getString(
                            "AbstractImportHandler_persistenceException", tmpItem.getProperty())); //$NON-NLS-1$
                    selectedItemRecord.addError(e.getMessage());
                    logError(e);
                }
                if (selectedItemRecord.isImported()) {
                    selectedItemRecord.setImportPath(path.toPortableString());
                    selectedItemRecord.setRepositoryType(itemType);
                    selectedItemRecord.setItemId(selectedItemRecord.getProperty().getId());
                    selectedItemRecord.setItemVersion(selectedItemRecord.getProperty().getVersion());

                    repObjectcache.addToCache(tmpItem);
                }

                if (tmpItem.getState() != null && itemType != null) {
                    final Set<String> folders = ImportCacheHelper.getInstance().getRestoreFolder().getFolders(itemType);
                    if (folders != null) {
                        for (String folderPath : folders) {
                            if (folderPath != null && folderPath.equals(path.toString())) {
                                FolderItem folderItem = repFactory.getFolderItem(
                                        ProjectManager.getInstance().getCurrentProject(), itemType, path);
                                if (folderItem != null) {
                                    folderItem.getState().setDeleted(false);

                                    while (!(folderItem.getParent() instanceof Project)) {
                                        folderItem = (FolderItem) folderItem.getParent();
                                        if (folderItem.getType() == FolderType.SYSTEM_FOLDER_LITERAL) {
                                            break;
                                        }
                                        folderItem.getState().setDeleted(false);
                                    }

                                }
                                break;
                            }
                        }
                    }

                }

            } catch (Exception e) {
                selectedItemRecord.addError(e.getMessage());
                logError(e);
            }

        }
        String label = selectedItemRecord.getLabel();
        for (Resource resource : selectedItemRecord.getResourceSet().getResources()) {
            // Due to the system of lazy loading for db repository of ByteArray,
            // it can't be unloaded just after create the item.
            if (!(resource instanceof ByteArrayResource)) {
                resource.unload();
            }
        }
        TimeMeasure.step("importItemRecords", "Import item: " + label); //$NON-NLS-1$ //$NON-NLS-2$

        applyMigrationTasks(selectedItemRecord, monitor);
        TimeMeasure.step("importItemRecords", "applyMigrationTasks: " + label); //$NON-NLS-1$//$NON-NLS-2$

    }

    /**
     * 
     * DOC ggu Comment method "checkAndCreatePath". if the path is not existed, will create.
     * 
     * @param selectedItemRecord
     * @param destinationPath
     * @param contentType
     * @return
     */
    protected IPath checkAndCreatePath(ItemRecord selectedItemRecord, IPath destinationPath, ERepositoryObjectType curItemType,
            ERepositoryObjectType contentType) {
        final ProxyRepositoryFactory repFactory = ProxyRepositoryFactory.getInstance();
        final Item item = selectedItemRecord.getItem();

        IPath path = new Path(item.getState().getPath());
        if (destinationPath != null && curItemType.equals(contentType)) {
            path = destinationPath.append(path);
        }

        try {
            FolderItem folderItem = repFactory.getFolderItem(ProjectManager.getInstance().getCurrentProject(), curItemType, path);
            if (folderItem == null) {
                // if this folder does not exists (and it's parents), it will check if the folder was originally
                // deleted in source project.
                // if yes, it will set back the delete status to the folder, to keep the same as the original
                // project when import.
                // Without this code, deleted folders of items imported will not be in the recycle bin after import.
                // delete status is set finally in the function checkDeletedFolders
                IPath curPath = path;
                EList deletedFoldersFromOriginalProject = selectedItemRecord.getItemProject().getDeletedFolders();
                while (folderItem == null && !curPath.isEmpty() && !curPath.isRoot()) {
                    if (deletedFoldersFromOriginalProject.contains(new Path(curItemType.getFolder()).append(
                            curPath.toPortableString()).toPortableString())) {
                        final Map<ERepositoryObjectType, Set<String>> foldersCreated = ImportCacheHelper.getInstance()
                                .getFoldersCreated();
                        if (!foldersCreated.containsKey(curItemType)) {
                            foldersCreated.put(curItemType, new HashSet<String>());
                        }
                        foldersCreated.get(curItemType).add(curPath.toPortableString());
                    }
                    if (curPath.segments().length > 0) {
                        curPath = curPath.removeLastSegments(1);
                        folderItem = repFactory.getFolderItem(ProjectManager.getInstance().getCurrentProject(), curItemType,
                                curPath);
                    }

                }
            }
            repFactory.createParentFoldersRecursively(ProjectManager.getInstance().getCurrentProject(), curItemType, path, true);
        } catch (Exception e) {
            logError(e);
            path = new Path(""); //$NON-NLS-1$
        }
        return path;
    }

    protected void beforeCreatingItem(ItemRecord selectedItemRecord) {
        // noting to do specially.
    }

    protected void afterCreatedItem(ResourcesManager resManager, ItemRecord selectedItemRecord) throws Exception {

        // connections from migrations (from 4.0.x or previous version) doesn't support reference or
        // screenshots
        // so no need to call this code.

        // It's needed to avoid to call the save method mainly just before or after the copy of the old
        // connection since it will

        boolean haveRef = copyReferenceFiles(resManager, selectedItemRecord);
        if (haveRef) {
            ProxyRepositoryFactory repFactory = ProxyRepositoryFactory.getInstance();
            repFactory.save(selectedItemRecord.getItem(), true);
        }

    }

    protected void afterForceCreatedItem(ResourcesManager resManager, ItemRecord selectedItemRecord) throws Exception {
        // nothing to do
    }

    protected boolean copyReferenceFiles(ResourcesManager manager, ItemRecord selectedItemRecord) throws IOException {
        OutputStream os = null;
        InputStream is = null;

        boolean haveRef = false;
        Item tmpItem = selectedItemRecord.getItem();
        List<ReferenceFileItem> refItems = tmpItem.getReferenceResources();
        URI propertyResourceURI = EcoreUtil.getURI(tmpItem.getProperty());
        for (ReferenceFileItem refItem : refItems) {
            haveRef = true;
            URI relativePlateformDestUri = propertyResourceURI.trimFileExtension().appendFileExtension(refItem.getExtension());
            try {
                URL fileURL = FileLocator.toFileURL(new java.net.URL(
                        "platform:/resource" + relativePlateformDestUri.toPlatformString(true))); //$NON-NLS-1$
                os = new FileOutputStream(fileURL.getFile());
                is = manager.getStream(selectedItemRecord.getPath().removeFileExtension()
                        .addFileExtension(refItem.getExtension()));
                FileCopyUtils.copyStreams(is, os);
            } finally {
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
            }
        }
        return haveRef;
    }

    public void resolveItem(ResourcesManager manager, ItemRecord itemRecord) {
        if (itemRecord.isResolved()) {
            return;
        }

        InputStream stream = null;

        try {
            final Item item = itemRecord.getItem();
            boolean byteArray = (item instanceof FileItem);
            IPath itemPath = HandlerUtil.getItemPath(itemRecord.getPath(), item);
            stream = manager.getStream(itemPath);
            Resource resource = createResource(itemRecord, itemPath, byteArray);

            if (byteArray) {
                // TDI-24612
                // This part fixes a problem of import of routines from .tar.gz.
                // Seems either the Tar stream or emf got problems to read this.
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int i = 0;
                while ((i = stream.read(buf)) != -1) {
                    baos.write(buf, 0, i);
                }
                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                resource.load(bais, null);
            } else {
                resource.load(stream, null);
            }

            for (ReferenceFileItem rfItem : (List<ReferenceFileItem>) item.getReferenceResources()) {
                itemPath = HandlerUtil.getReferenceItemPath(itemRecord.getPath(), rfItem.getExtension());
                stream = manager.getStream(itemPath);
                Resource rfResource = createResource(itemRecord, itemPath, true);
                rfResource.load(stream, null);
            }
            resetItemReference(itemRecord, resource);
            // EcoreUtil.resolveAll(itemRecord.getResourceSet());
        } catch (IOException e) {
            // ignore
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

        itemRecord.setResolved(true);
    }

    /**
     * 
     * cLi Comment method "resetItemReference".
     * 
     * resolve the encode some special character(bug 6252), maybe, It's not better to resolve this by manually.
     * 
     * such as, "[" is "%5B", "]" is "%5D", etc.
     */
    @SuppressWarnings("unchecked")
    private void resetItemReference(ItemRecord itemRecord, Resource resource) {
        Item item = itemRecord.getItem();
        EList<EObject> contents = resource.getContents();
        /*
         * ignore job. no need, because it can't be allowed input special char for name.
         */
        if (item instanceof ProcessItem) {
            // ((ProcessItem) item).setProcess((ProcessType) EcoreUtil.getObjectByType(contents,
            // TalendFilePackage.eINSTANCE
            // .getProcessType()));
        } else
        /*
         * ignore joblet. no need, because it can't be allowed input special char for name.
         */
        if (item instanceof JobletProcessItem) {
            // JobletProcessItem jobletProcessItem = (JobletProcessItem) item;
            //
            // jobletProcessItem.setJobletProcess((JobletProcess) EcoreUtil.getObjectByType(contents,
            // JobletPackage.eINSTANCE
            // .getJobletProcess()));
            // jobletProcessItem
            // .setIcon((ByteArray) EcoreUtil.getObjectByType(contents, PropertiesPackage.eINSTANCE.getByteArray()));
        } else
        // connectionItem
        if (item instanceof ConnectionItem) {
            ((ConnectionItem) item).setConnection((Connection) EcoreUtil.getObjectByType(contents,
                    ConnectionPackage.eINSTANCE.getConnection()));
        } else
        // context
        if (item instanceof ContextItem) {
            EList contexts = ((ContextItem) item).getContext();
            contexts.clear();
            contexts.addAll(EcoreUtil.getObjectsByType(contents, TalendFilePackage.eINSTANCE.getContextType()));
        } else
        // file
        if (item instanceof FileItem) {
            /*
             * ignore routine, no need, because it can't be allowed input special char for name.
             */
            if (item instanceof RoutineItem) {
                return;
            }
            FileItem fileItem = (FileItem) item;
            fileItem.setContent((ByteArray) EcoreUtil.getObjectByType(contents, PropertiesPackage.eINSTANCE.getByteArray()));
        } else
        // snippet
        if (item instanceof SnippetItem) {
            EList variables = ((SnippetItem) item).getVariables();
            variables.clear();
            variables.addAll(EcoreUtil.getObjectsByType(contents, PropertiesPackage.eINSTANCE.getSnippetVariable()));
        } else
        // link doc
        if (item instanceof LinkDocumentationItem) {
            ((LinkDocumentationItem) item).setLink((LinkType) EcoreUtil.getObjectByType(contents,
                    PropertiesPackage.eINSTANCE.getLinkType()));
        } else
        // business
        if (item instanceof BusinessProcessItem) {
            BusinessProcessItem businessProcessItem = (BusinessProcessItem) item;

            businessProcessItem.setSemantic((BusinessProcess) EcoreUtil.getObjectByType(contents,
                    BusinessPackage.eINSTANCE.getBusinessProcess()));

            businessProcessItem.setNotationHolder((NotationHolder) EcoreUtil.getObjectByType(contents,
                    PropertiesPackage.eINSTANCE.getNotationHolder()));
        }

    }

    /**
     * DOC ycbai Comment method "applyMigrationTasks".
     * 
     * @param itemRecord
     * @param monitor
     */
    private void applyMigrationTasks(ItemRecord itemRecord, IProgressMonitor monitor) {
        if (GlobalServiceRegister.getDefault().isServiceRegistered(IMigrationToolService.class)) {
            IMigrationToolService migrationService = (IMigrationToolService) GlobalServiceRegister.getDefault().getService(
                    IMigrationToolService.class);
            Context ctx = CoreRuntimePlugin.getInstance().getContext();
            RepositoryContext repositoryContext = (RepositoryContext) ctx.getProperty(Context.REPOSITORY_CONTEXT_KEY);
            org.talend.core.model.general.Project project = repositoryContext.getProject();
            ERepositoryObjectType repositoryType = itemRecord.getRepositoryType();
            Item item = null;
            try {
                List<IRepositoryViewObject> allVersion = ProxyRepositoryFactory.getInstance().getAllVersion(
                        ProjectManager.getInstance().getCurrentProject(), itemRecord.getItemId(), itemRecord.getImportPath(),
                        repositoryType);
                for (IRepositoryViewObject repositoryObject : allVersion) {
                    if (repositoryObject.getProperty().getVersion().equals(itemRecord.getItemVersion())) {
                        item = repositoryObject.getProperty().getItem();
                    }
                }
                if (item == null) {
                    return;
                }
                migrationService.executeMigrationTasksForImport(project, item, itemRecord.getMigrationTasksToApply(), monitor);
                itemRecord.setExistingItemWithSameId(null);
                itemRecord.clear();
                itemRecord.setProperty(item.getProperty());
            } catch (Exception e) {
                logError(e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.items.importexport.handlers.imports.IImportHandler#findRelatedItemRecord(org.eclipse.core
     * .runtime.IProgressMonitor, org.talend.repository.items.importexport.ui.wizard.imports.managers.ResourcesManager,
     * org.talend.repository.items.importexport.ui.wizard.imports.models.ItemRecord, java.util.List)
     */
    @Override
    public List<ItemRecord> findRelatedItemRecord(IProgressMonitor monitor, ResourcesManager resManager,
            ItemRecord selectedItemRecord, List<ItemRecord> allPopulatedItemRecords) {
        return Collections.emptyList(); // default, it's nothing.
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.items.importexport.handlers.imports.IImportHandler#afterImportingItemRecords(org.talend
     * .repository.items.importexport.ui.wizard.imports.models.ItemRecord)
     */
    @Override
    public void afterImportingItemRecords(IProgressMonitor monitor, ResourcesManager resManager, ItemRecord selectedItemRecord) {
        if (selectedItemRecord == null) {
            return;
        }
        final ProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        IRepositoryViewObject object;
        try {
            Property property = selectedItemRecord.getProperty();
            if (property == null) {
                object = factory.getSpecificVersion(selectedItemRecord.getItemId(), selectedItemRecord.getItemVersion(), true);
                property = object.getProperty();
            }
            RelationshipItemBuilder.getInstance().addOrUpdateItem(property.getItem(), true);
            selectedItemRecord.setProperty(null);
            factory.unloadResources(property);
        } catch (PersistenceException e) {
            ExceptionHandler.process(e);
        }

    }
}
