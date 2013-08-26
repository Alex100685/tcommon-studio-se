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
package org.talend.repository.items.importexport.ui.wizard.imports.managers;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
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
import org.osgi.framework.FrameworkUtil;
import org.talend.commons.CommonsPlugin;
import org.talend.commons.emf.CwmResource;
import org.talend.commons.emf.EmfHelper;
import org.talend.commons.emf.TalendXMIResource;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.utils.VersionUtils;
import org.talend.commons.utils.io.FileCopyUtils;
import org.talend.commons.utils.time.TimeMeasure;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.PluginChecker;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.hadoop.IHadoopClusterService;
import org.talend.core.model.general.ILibrariesService;
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
import org.talend.core.model.properties.SQLPatternItem;
import org.talend.core.model.properties.SnippetItem;
import org.talend.core.model.properties.TDQItem;
import org.talend.core.model.properties.User;
import org.talend.core.model.properties.helper.ByteArrayResource;
import org.talend.core.model.relationship.RelationshipItemBuilder;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.model.utils.MigrationUtil;
import org.talend.core.repository.constants.FileConstants;
import org.talend.core.repository.model.PropertiesProjectResourceImpl;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.repository.utils.XmiResourceManager;
import org.talend.core.runtime.CoreRuntimePlugin;
import org.talend.core.ui.IJobletProviderService;
import org.talend.designer.business.model.business.BusinessPackage;
import org.talend.designer.business.model.business.BusinessProcess;
import org.talend.designer.core.IDesignerCoreService;
import org.talend.designer.core.model.utils.emf.component.IMPORTType;
import org.talend.designer.core.model.utils.emf.talendfile.ElementParameterType;
import org.talend.designer.core.model.utils.emf.talendfile.ParametersType;
import org.talend.designer.core.model.utils.emf.talendfile.TalendFilePackage;
import org.talend.repository.ProjectManager;
import org.talend.repository.RepositoryWorkUnit;
import org.talend.repository.items.importexport.handlers.HandlerUtil;
import org.talend.repository.items.importexport.handlers.cache.RepositoryObjectCache;
import org.talend.repository.items.importexport.handlers.imports.ImportCacheHelper;
import org.talend.repository.items.importexport.i18n.Messages;
import org.talend.repository.items.importexport.ui.wizard.imports.models.ItemRecord;
import org.talend.repository.items.importexport.ui.wizard.imports.models.ItemRecord.State;
import org.talend.repository.model.ComponentsFactoryProvider;
import org.talend.repository.model.ERepositoryStatus;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.ui.actions.RestoreFolderUtil;

/**
 */
public class ImportItemUtil {

    private static Logger log = Logger.getLogger(ImportItemUtil.class);

    private final XmiResourceManager xmiResourceManager = new XmiResourceManager();

    private boolean hasErrors = false;

    private final Set<String> deletedItems = new HashSet<String>();

    private Map<ERepositoryObjectType, Set<String>> foldersCreated = new HashMap<ERepositoryObjectType, Set<String>>();

    private final Set<Project> updatedProjects = new HashSet<Project>();

    private final Map<IPath, Project> projects = new HashMap<IPath, Project>();

    private final Map<String, Set<String>> routineExtModulesMap = new HashMap<String, Set<String>>();

    private Map<String, List<MigrationTask>> migrationTasksToApplyPerProject = new HashMap<String, List<MigrationTask>>();

    private Map<String, Boolean> migrationTasksStatusPerProject = new HashMap<String, Boolean>();

    private boolean statAndLogsSettingsReloaded = false;

    private boolean implicitSettingsReloaded = false;

    private static boolean hasJoblets = false;

    private RestoreFolderUtil restoreFolder;

    private static final String ADAPT_NEW_MIGRATION_TASK_SYSTEM_ID = "org.talend.repository.model.migration.UpdateExistentMigrationTasksToAdaptNewMigrationSystemMigrationTask"; //$NON-NLS-1$

    public void clear() {
        deletedItems.clear();
    }

    public void setErrors(boolean errors) {
        hasErrors = errors;
    }

    public boolean hasErrors() {
        return hasErrors;
    }

    private boolean checkItem(ItemRecord itemRecord, boolean overwrite) {

        boolean result = false;

        try {
            Item item = itemRecord.getItem();
            if (item instanceof TDQItem) {
                return false; // hide tdq first
            }
            ERepositoryObjectType itemType = ERepositoryObjectType.getItemType(item);
            if (itemType == null) {
                itemRecord.addError(Messages.getString("ImportItemUtil.unsupportItem")); //$NON-NLS-1$
                return false; // can't import this item.
            }
            String itemPath = null;
            if (item.getState() != null) {
                itemPath = item.getState().getPath();
            } else {
                itemRecord.addError(Messages.getString("ImportItemUtil.unsupportItem"));
                return false;
            }
            final RepositoryObjectCache repObjectcache = ImportCacheHelper.getInstance().getRepObjectcache();
            repObjectcache.initialize(itemType);
            boolean isAllowMultipleName = (itemType == ERepositoryObjectType.SQLPATTERNS || itemType == ERepositoryObjectType.METADATA_FILE_XML);

            boolean nameAvailable = true;
            IRepositoryViewObject itemWithSameId = null;
            IRepositoryViewObject itemWithSameName = null;

            // take care, in cache it's RepositoryViewObject, not RepositoryObject
            for (IRepositoryViewObject current : repObjectcache.getItemsFromRepository().get(itemType)) {
                final Property property = itemRecord.getProperty();
                if (property != null) {
                    if (property.getLabel() != null && property.getLabel().equalsIgnoreCase(current.getLabel())
                            && property.getId() != current.getId()) {
                        // To check SQLPattern in same path. see bug 0005038: unable to add a SQLPattern into
                        // repository.
                        if (!isAllowMultipleName || current.getPath().equals(itemPath)) {
                            nameAvailable = false;
                        }
                        // overwrite the item with same label but diff id: 15787: import items does not overwrite some
                        // elements
                        if (!nameAvailable) {
                            itemWithSameName = current;
                        }
                    }
                    if (property.getId() != null && property.getId().equalsIgnoreCase(current.getId())) {
                        itemWithSameId = current;
                    }
                }
            }
            itemRecord.setExistingItemWithSameId(itemWithSameId);
            boolean idAvailable = itemWithSameId == null;

            boolean isSystem = false;
            // we do not import built in routines
            if (item.eClass().equals(PropertiesPackage.eINSTANCE.getRoutineItem())) {
                RoutineItem routineItem = (RoutineItem) item;
                if (item instanceof RoutineItem) {
                    RoutineItem rItem = (RoutineItem) item;
                    Set<String> set = routineExtModulesMap.get(rItem.getProperty().getId());
                    if (set == null) {
                        set = new HashSet<String>();
                        routineExtModulesMap.put(rItem.getProperty().getId(), set);
                    }
                    for (IMPORTType type : (List<IMPORTType>) rItem.getImports()) {
                        set.add(type.getMODULE());
                    }
                }

                if (routineItem.isBuiltIn()) {
                    isSystem = true;
                }
            }

            // we do not import system sql patterns
            if (item.eClass().equals(PropertiesPackage.eINSTANCE.getSQLPatternItem())) {
                SQLPatternItem sqlPatternItem = (SQLPatternItem) item;
                if (sqlPatternItem.isSystem()) {
                    isSystem = true;
                }
            }

            if (isSystem) {
                itemRecord.addError(Messages.getString("ImportItemUtil.isSystemItem"));
                return false;
            }

            if (nameAvailable) {
                if (idAvailable) {
                    if (!isSystem) {
                        result = true;
                    } /*
                       * else { itemRecord.addError(Messages.getString("RepositoryUtil.isSystemRoutine")); //$NON-NLS-1$
                       * }
                       */
                } else {
                    // same id but different name,no need to care overwrite cause the item will be considered as a
                    // different one,see bug 20445
                    itemRecord.setState(State.ID_EXISTED);
                    // if (overwrite) {
                    // result = true;
                    // } else {
                    // see bug 0005222: [Import items] [Errors and Warnings]
                    // id is already in use
                    result = true;
                    // RepositoryNode nodeWithSameId = RepositoryNodeUtilities.getRepositoryNode(itemWithSameId);
                    // IPath path = getPath(nodeWithSameId);
                    // itemRecord.addError(Messages.getString(
                    //                                "RepositoryUtil.idUsed", itemWithSameId.getLabel(), path.toOSString())); //$NON-NLS-1$
                    // }
                }
            } else {
                if (idAvailable) {
                    // same name but different id
                    itemRecord.setState(State.NAME_EXISTED);

                    if (!isSystem && overwrite) {
                        // if anything system, don't replace the source item if same name.
                        // if not from system, can overwrite.
                        itemRecord.setExistingItemWithSameId(itemWithSameName);
                        result = true;
                    }
                    // TDI-21399,TDI-21401
                    // if item is locked, cannot overwrite
                    if (result && overwrite && itemWithSameName != null) {
                        ERepositoryStatus status = itemWithSameName.getRepositoryStatus();
                        if (status == ERepositoryStatus.LOCK_BY_OTHER || status == ERepositoryStatus.LOCK_BY_USER) {
                            itemRecord.addError(Messages.getString("ImportItemUtil.itemLocked")); //$NON-NLS-1$
                            return false;
                        }
                    }

                } else {
                    // same name and same id
                    itemRecord.setState(State.NAME_AND_ID_EXISTED);
                    if (overwrite) {
                        result = true;
                    }
                    if (!isSystem && overwrite
                            && !itemWithSameName.getProperty().getLabel().equals(itemWithSameId.getProperty().getLabel())) {
                        // if anything system, don't replace the source item if same name.
                        // if not from system, can overwrite.
                        itemRecord.setExistingItemWithSameId(itemWithSameName);
                        result = true;
                    }
                }
                if (!result && !isSystem) {
                    itemRecord.addError(Messages.getString("ImportItemUtil.nameUsed")); //$NON-NLS-1$
                }
            }

            if (result && overwrite && itemRecord.getState() == State.NAME_AND_ID_EXISTED) {
                // if item is locked, cannot overwrite
                if (checkIfLocked(itemRecord)) {
                    itemRecord.addError(Messages.getString("ImportItemUtil.itemLocked")); //$NON-NLS-1$
                    result = false;
                }
            }
        } catch (Exception e) {
            log.error("Error when checking item :" + itemRecord.getPath(), e);
        }
        return result;
    }

    /**
     * DOC hcw Comment method "checkIfLocked".
     * 
     * @param itemRecord
     * @return
     * @throws PersistenceException
     */
    private boolean checkIfLocked(ItemRecord itemRecord) throws PersistenceException {
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

        ImportCacheHelper.getInstance().getRepObjectcache().setItemLockState(itemRecord, false);
        return false;
    }

    @SuppressWarnings("unchecked")
    public List<ItemRecord> importItemRecords(final ResourcesManager manager, final List<ItemRecord> itemRecords,
            final IProgressMonitor monitor, final boolean overwrite, final IPath destinationPath, final String contentType) {

        TimeMeasure.display = CommonsPlugin.isDebugMode();
        TimeMeasure.displaySteps = CommonsPlugin.isDebugMode();
        TimeMeasure.measureActive = CommonsPlugin.isDebugMode();

        TimeMeasure.begin("importItemRecords");

        hasJoblets = false;
        statAndLogsSettingsReloaded = false;
        implicitSettingsReloaded = false;
        restoreFolder = new RestoreFolderUtil();

        Collections.sort(itemRecords, new Comparator<ItemRecord>() {

            @Override
            public int compare(ItemRecord o1, ItemRecord o2) {
                if (o1.getProperty().getItem() instanceof RoutineItem && o2.getProperty().getItem() instanceof RoutineItem) {
                    return 0;
                } else if (!(o1.getProperty().getItem() instanceof RoutineItem)
                        && !(o2.getProperty().getItem() instanceof RoutineItem)) {
                    return 0;
                } else if (o1.getProperty().getItem() instanceof RoutineItem) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        monitor.beginTask(Messages.getString("ImportItemUtil.importSelectedItems"), itemRecords.size() * 2 + 1); //$NON-NLS-1$

        RepositoryWorkUnit repositoryWorkUnit = new RepositoryWorkUnit(Messages.getString("ImportItemUtil.importSelectedItems")) { //$NON-NLS-1$

            @Override
            public void run() throws PersistenceException {
                final IWorkspaceRunnable op = new IWorkspaceRunnable() {

                    @Override
                    public void run(IProgressMonitor monitor) throws CoreException {

                        final IProxyRepositoryFactory factory = CoreRuntimePlugin.getInstance().getProxyRepositoryFactory();
                        // bug 10520
                        final Set<String> overwriteDeletedItems = new HashSet<String>();
                        final Set<String> idDeletedBeforeImport = new HashSet<String>();

                        Map<String, String> nameToIdMap = new HashMap<String, String>();

                        for (ItemRecord itemRecord : itemRecords) {
                            if (!monitor.isCanceled()) {
                                if (itemRecord.isValid()) {
                                    if (itemRecord.getState() == State.ID_EXISTED) {
                                        String id = nameToIdMap.get(itemRecord.getProperty().getLabel()
                                                + ERepositoryObjectType.getItemType(itemRecord.getProperty().getItem())
                                                        .toString());
                                        if (id == null) {
                                            /*
                                             * if id exsist then need to genrate new id for this job,in this case the
                                             * job won't override the old one
                                             */
                                            id = EcoreUtil.generateUUID();
                                            nameToIdMap.put(itemRecord.getProperty().getLabel()
                                                    + ERepositoryObjectType.getItemType(itemRecord.getProperty().getItem())
                                                            .toString(), id);
                                        }
                                        itemRecord.getProperty().setId(id);
                                    }
                                }
                            }
                        }

                        for (ItemRecord itemRecord : itemRecords) {
                            if (!monitor.isCanceled()) {
                                if (itemRecord.isValid()) {
                                    importItemRecord(manager, itemRecord, overwrite, destinationPath, overwriteDeletedItems,
                                            idDeletedBeforeImport, contentType, monitor);
                                    IRepositoryViewObject object;
                                    try {
                                        Property property = itemRecord.getProperty();
                                        if (property == null) {
                                            object = factory.getSpecificVersion(itemRecord.getItemId(),
                                                    itemRecord.getItemVersion(), true);
                                            property = object.getProperty();
                                        }
                                        RelationshipItemBuilder.getInstance().addOrUpdateItem(property.getItem(), true);
                                        itemRecord.setProperty(null);
                                        ProxyRepositoryFactory.getInstance().unloadResources(property);
                                    } catch (PersistenceException e) {
                                        ExceptionHandler.process(e);
                                    }
                                    statAndLogsSettingsReloaded = false;
                                    implicitSettingsReloaded = false;

                                    monitor.worked(1);
                                }
                            }
                        }

                        // deploy routines Jar
                        if (!getRoutineExtModulesMap().isEmpty()) {
                            Set<String> extRoutines = new HashSet<String>();
                            for (String id : getRoutineExtModulesMap().keySet()) {
                                Set<String> set = getRoutineExtModulesMap().get(id);
                                if (set != null) {
                                    extRoutines.addAll(set);
                                }
                            }
                            if (manager instanceof ProviderManager || manager instanceof ZipFileManager) {
                                deployJarToDesForArchive(manager, extRoutines);
                            } else {
                                deployJarToDes(manager, extRoutines);
                            }
                        }

                        if (PluginChecker.isJobLetPluginLoaded()) {
                            IJobletProviderService service = (IJobletProviderService) GlobalServiceRegister.getDefault()
                                    .getService(IJobletProviderService.class);
                            if (service != null) {
                                service.loadComponentsFromProviders();
                            }
                        }
                        checkDeletedFolders();
                        monitor.done();

                        TimeMeasure.step("importItemRecords", "before save");
                        if (RelationshipItemBuilder.getInstance().isNeedSaveRelations()) {
                            RelationshipItemBuilder.getInstance().saveRelations();
                            TimeMeasure.step("importItemRecords", "save relations");
                        } else {
                            // only save the project here if no relation need to be saved, since project will already be
                            // saved
                            // with relations
                            try {
                                factory.saveProject(ProjectManager.getInstance().getCurrentProject());
                            } catch (PersistenceException e) {
                                throw new CoreException(new Status(IStatus.ERROR, FrameworkUtil.getBundle(this.getClass())
                                        .getSymbolicName(), "Import errors", e));
                            }
                            TimeMeasure.step("importItemRecords", "save project");
                        }
                    }

                };
                IWorkspace workspace = ResourcesPlugin.getWorkspace();
                try {
                    ISchedulingRule schedulingRule = workspace.getRoot();
                    // the update the project files need to be done in the workspace runnable to avoid all
                    // notification
                    // of changes before the end of the modifications.
                    workspace.run(op, schedulingRule, IWorkspace.AVOID_UPDATE, monitor);
                } catch (CoreException e) {
                    // ?
                }
            }
        };
        repositoryWorkUnit.setAvoidUnloadResources(true);
        repositoryWorkUnit.setUnloadResourcesAfterRun(true);
        ProxyRepositoryFactory.getInstance().executeRepositoryWorkUnit(repositoryWorkUnit);

        monitor.done();

        // for (ItemRecord itemRecord : itemRecords) {
        // itemRecord.clear();
        // }

        clearAllData();
        if (hasJoblets) {
            ComponentsFactoryProvider.getInstance().resetSpecificComponents();
        }

        TimeMeasure.end("importItemRecords");
        TimeMeasure.display = false;
        TimeMeasure.displaySteps = false;
        TimeMeasure.measureActive = false;

        return itemRecords;
    }

    private void checkDeletedFolders() {
        ProxyRepositoryFactory repFactory = ProxyRepositoryFactory.getInstance();
        if (!foldersCreated.isEmpty()) {
            for (ERepositoryObjectType itemType : foldersCreated.keySet()) {
                for (String folder : foldersCreated.get(itemType)) {
                    FolderItem folderItem = repFactory.getFolderItem(ProjectManager.getInstance().getCurrentProject(), itemType,
                            new Path(folder));
                    if (folderItem != null) {
                        folderItem.getState().setDeleted(true);
                    }
                }
            }
            foldersCreated.clear();
        }
    }

    public void clearAllData() {
        deletedItems.clear();
        if ((!CommonsPlugin.isSameProjectLogonCommline() && CommonsPlugin.isHeadless()) || !CommonsPlugin.isHeadless()
                || !ProjectManager.getInstance().getCurrentProject().isLocal()) {
            ImportCacheHelper.getInstance().getRepObjectcache().clear();
        }
        xmiResourceManager.unloadResources();
        xmiResourceManager.resetResourceSet();
        projects.clear();
        foldersCreated.clear();
    }

    private void importItemRecord(ResourcesManager manager, ItemRecord itemRecord, boolean overwrite, IPath destinationPath,
            final Set<String> overwriteDeletedItems, final Set<String> idDeletedBeforeImport, String contentType,
            final IProgressMonitor monitor) {
        monitor.subTask(Messages.getString("ImportItemUtil.importing", itemRecord.getItemName())); //$NON-NLS-1$
        resolveItem(manager, itemRecord);

        int num = 0;
        for (Object obj : itemRecord.getResourceSet().getResources()) {
            if (!(obj instanceof PropertiesProjectResourceImpl)) {
                if (obj instanceof XMIResourceImpl) {
                    num++;
                    if (num > 2) {// The is no explanation for this value and what is this loop for to I increased
                        // it to
                        // 2 so that metadata migration for 4.1 works
                        try {
                            throw new InvocationTargetException(new PersistenceException("The source file of "
                                    + itemRecord.getLabel() + " has error,Please check it!"));
                        } catch (InvocationTargetException e) {
                            ExceptionHandler.process(e);
                        }
                        return;
                    }
                }
            }
        }

        final Item item = itemRecord.getItem();
        if (item != null) {
            ProxyRepositoryFactory repFactory = ProxyRepositoryFactory.getInstance();
            ERepositoryObjectType itemType = ERepositoryObjectType.getItemType(item);

            IPath path = new Path(item.getState().getPath());
            if (destinationPath != null && itemType.name().equals(contentType)) {
                path = destinationPath.append(path);
            }

            try {
                FolderItem folderItem = repFactory
                        .getFolderItem(ProjectManager.getInstance().getCurrentProject(), itemType, path);
                if (folderItem == null) {
                    // if this folder does not exists (and it's parents), it will check if the folder was originally
                    // deleted in source project.
                    // if yes, it will set back the delete status to the folder, to keep the same as the original
                    // project when import.
                    // Without this code, deleted folders of items imported will not be in the recycle bin after import.
                    // delete status is set finally in the function checkDeletedFolders
                    IPath curPath = path;
                    EList deletedFoldersFromOriginalProject = itemRecord.getItemProject().getDeletedFolders();
                    while (folderItem == null && !curPath.isEmpty() && !curPath.isRoot()) {
                        if (deletedFoldersFromOriginalProject.contains(new Path(itemType.getFolder()).append(
                                curPath.toPortableString()).toPortableString())) {
                            if (!foldersCreated.containsKey(itemType)) {
                                foldersCreated.put(itemType, new HashSet<String>());
                            }
                            foldersCreated.get(itemType).add(curPath.toPortableString());
                        }
                        if (curPath.segments().length > 0) {
                            curPath = curPath.removeLastSegments(1);
                            folderItem = repFactory.getFolderItem(ProjectManager.getInstance().getCurrentProject(), itemType,
                                    curPath);
                        }

                    }
                }
                repFactory.createParentFoldersRecursively(ProjectManager.getInstance().getCurrentProject(), itemType, path, true);
            } catch (Exception e) {
                logError(e);
                path = new Path(""); //$NON-NLS-1$
            }

            try {
                Item tmpItem = item;

                // delete existing items before importing, this should be done
                // once for a different id
                String id = itemRecord.getProperty().getId();

                IRepositoryViewObject lastVersion = itemRecord.getExistingItemWithSameId();
                if (lastVersion != null
                        && overwrite
                        && !itemRecord.isLocked()
                        && (itemRecord.getState() == State.ID_EXISTED || itemRecord.getState() == State.NAME_EXISTED || itemRecord
                                .getState() == State.NAME_AND_ID_EXISTED) && !deletedItems.contains(id)) {
                    if (!overwriteDeletedItems.contains(id)) { // bug 10520.
                        ERepositoryStatus status = repFactory.getStatus(lastVersion);
                        if (status == ERepositoryStatus.DELETED) {
                            repFactory.restoreObject(lastVersion, path); // restore first.
                        }
                        overwriteDeletedItems.add(id);
                    }
                    /* only delete when name exsit rather than id exist */
                    if (itemRecord.getState().equals(ItemRecord.State.NAME_EXISTED)
                            || itemRecord.getState().equals(ItemRecord.State.NAME_AND_ID_EXISTED)) {
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

                    // List<IRepositoryObject> list = cache.findObjectsByItem(itemRecord);
                    // if (!list.isEmpty()) {
                    // // this code will delete all version of item with same
                    // // id
                    // repFactory.forceDeleteObjectPhysical(list.get(0));
                    // deletedItems.add(id);
                    // }
                }

                User author = itemRecord.getProperty().getAuthor();
                if (author != null) {
                    if (!repFactory.setAuthorByLogin(tmpItem, author.getLogin())) {
                        tmpItem.getProperty().setAuthor(null); // author will be
                        // the logged
                        // user in
                        // create method
                    }
                }

                if (item instanceof JobletProcessItem) {
                    hasJoblets = true;
                }

                if (tmpItem instanceof ProcessItem && !statAndLogsSettingsReloaded && !implicitSettingsReloaded) {
                    ProcessItem processItem = (ProcessItem) tmpItem;
                    ParametersType paType = processItem.getProcess().getParameters();
                    boolean statsPSettingRemoved = false;

                    // for commanline import project setting
                    if (itemRecord.isRemoveProjectStatslog()) {
                        if (paType != null) {
                            String paramName = "STATANDLOG_USE_PROJECT_SETTINGS";
                            EList listParamType = paType.getElementParameter();
                            for (int j = 0; j < listParamType.size(); j++) {
                                ElementParameterType pType = (ElementParameterType) listParamType.get(j);
                                if (pType != null && paramName.equals(pType.getName())) {
                                    pType.setValue(Boolean.FALSE.toString());
                                    statsPSettingRemoved = true;
                                    break;
                                }
                            }
                        }
                    }

                    // 14446: item apply project setting param if use project setting
                    String statslogUsePSetting = null;
                    String implicitUsePSetting = null;
                    if (paType != null) {
                        EList listParamType = paType.getElementParameter();
                        for (int j = 0; j < listParamType.size(); j++) {
                            ElementParameterType pType = (ElementParameterType) listParamType.get(j);
                            if (pType != null) {
                                if (!statsPSettingRemoved && "STATANDLOG_USE_PROJECT_SETTINGS".equals(pType.getName())) {
                                    statslogUsePSetting = pType.getValue();
                                }
                                if ("IMPLICITCONTEXT_USE_PROJECT_SETTINGS".equals(pType.getName())) {
                                    implicitUsePSetting = pType.getValue();
                                }
                                if (statsPSettingRemoved && implicitUsePSetting != null || !statsPSettingRemoved
                                        && implicitUsePSetting != null && statslogUsePSetting != null) {
                                    break;
                                }
                            }
                        }
                    }
                    if (GlobalServiceRegister.getDefault().isServiceRegistered(IDesignerCoreService.class)) {
                        IDesignerCoreService designerCoreService = (IDesignerCoreService) GlobalServiceRegister.getDefault()
                                .getService(IDesignerCoreService.class);
                        if (statslogUsePSetting != null && Boolean.parseBoolean(statslogUsePSetting)
                                && !statAndLogsSettingsReloaded) {
                            designerCoreService.reloadParamFromProjectSettings(paType, "STATANDLOG_USE_PROJECT_SETTINGS");
                            statAndLogsSettingsReloaded = true;
                        }
                        if (implicitUsePSetting != null && Boolean.parseBoolean(implicitUsePSetting) && !implicitSettingsReloaded) {
                            designerCoreService.reloadParamFromProjectSettings(paType, "IMPLICITCONTEXT_USE_PROJECT_SETTINGS");
                            implicitSettingsReloaded = true;
                        }
                    }

                }

                final RepositoryObjectCache repObjectcache = ImportCacheHelper.getInstance().getRepObjectcache();
                if (lastVersion == null || itemRecord.getState().equals(ItemRecord.State.ID_EXISTED)) {
                    // import has not been developed to cope with migration in mind
                    // so some model may not be able to load like the ConnectionItems
                    // in that case items needs to be copied before migration
                    // here we check that the loading of the item failed before calling the create method
                    boolean isConnectionEmptyBeforeMigration = tmpItem instanceof ConnectionItem
                            && ((ConnectionItem) tmpItem).getConnection().eResource() == null
                            && !itemRecord.getMigrationTasksToApply().isEmpty();

                    repFactory.create(tmpItem, path, true);
                    if (isConnectionEmptyBeforeMigration) {// copy the file before migration, this is bad because it
                        // should not refer to Filesytem
                        // but this is a quick hack and anyway the migration task only works on files
                        // IPath itemPath = itemRecord.getPath().removeFileExtension().addFileExtension(
                        // FileConstants.ITEM_EXTENSION);

                        InputStream is = manager.getStream(itemRecord.getPath().removeFileExtension()
                                .addFileExtension(FileConstants.ITEM_EXTENSION));
                        try {
                            URI propertyResourceURI = EcoreUtil.getURI(((ConnectionItem) tmpItem).getProperty());
                            URI relativePlateformDestUri = propertyResourceURI.trimFileExtension().appendFileExtension(
                                    FileConstants.ITEM_EXTENSION);
                            URL fileURL = FileLocator.toFileURL(new java.net.URL(
                                    "platform:/resource" + relativePlateformDestUri.toPlatformString(true))); //$NON-NLS-1$
                            OutputStream os = new FileOutputStream(fileURL.getFile());
                            try {
                                FileCopyUtils.copyStreams(is, os);
                            } finally {
                                os.close();
                            }
                        } finally {
                            is.close();
                        }
                        repFactory.unloadResources(tmpItem.getProperty());
                    } else {
                        // connections from migrations (from 4.0.x or previous version) doesn't support reference or
                        // screenshots
                        // so no need to call this code.

                        // It's needed to avoid to call the save method mainly just before or after the copy of the old
                        // connection since it will
                        copyScreenshotFile(manager, itemRecord);
                        boolean haveRef = copyReferenceFiles(manager, tmpItem, itemRecord.getPath());
                        if (haveRef) {
                            repFactory.save(tmpItem, true);
                        }
                    }
                    itemRecord.setImportPath(path.toPortableString());
                    itemRecord.setRepositoryType(itemType);
                    itemRecord.setItemId(itemRecord.getProperty().getId());
                    itemRecord.setItemVersion(itemRecord.getProperty().getVersion());
                    itemRecord.setImported(true);
                    repObjectcache.addToCache(tmpItem);
                } else if (VersionUtils.compareTo(lastVersion.getProperty().getVersion(), tmpItem.getProperty().getVersion()) < 0) {
                    repFactory.forceCreate(tmpItem, path);
                    itemRecord.setImportPath(path.toPortableString());
                    itemRecord.setItemId(itemRecord.getProperty().getId());
                    itemRecord.setRepositoryType(itemType);
                    itemRecord.setItemVersion(itemRecord.getProperty().getVersion());
                    itemRecord.setImported(true);
                    repObjectcache.addToCache(tmpItem);
                } else {
                    PersistenceException e = new PersistenceException(Messages.getString(
                            "ImportItemUtil.persistenceException", tmpItem.getProperty())); //$NON-NLS-1$
                    itemRecord.addError(e.getMessage());
                    logError(e);
                }

                if (tmpItem != null) {
                    // RelationshipItemBuilder.getInstance().addOrUpdateItem(tmpItem, true);
                    if (tmpItem.getState() != null) {
                        if (itemType != null) {
                            final Set<String> folders = restoreFolder.getFolders(itemType);
                            if (folders != null) {
                                for (String folderPath : folders) {
                                    if (folderPath != null && folderPath.equals(path.toString())) {
                                        FolderItem folderItem = repFactory.getFolderItem(ProjectManager.getInstance()
                                                .getCurrentProject(), itemType, path);
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
                    }
                }

            } catch (Exception e) {
                itemRecord.addError(e.getMessage());
                logError(e);
            }

        }
        String label = itemRecord.getLabel();
        for (Resource resource : itemRecord.getResourceSet().getResources()) {
            // Due to the system of lazy loading for db repository of ByteArray,
            // it can't be unloaded just after create the item.
            if (!(resource instanceof ByteArrayResource)) {
                resource.unload();
            }
        }
        TimeMeasure.step("importItemRecords", "Import item: " + label);

        applyMigrationTasks(itemRecord, monitor);
        TimeMeasure.step("importItemRecords", "applyMigrationTasks: " + label);
    }

    // added by dlin 2011-7-25 don't like .item and .property ,just copy .screenshot file will be ok
    private void copyScreenshotFile(ResourcesManager manager, ItemRecord itemRecord) throws IOException {
        int id = itemRecord.getItem().eClass().getClassifierID();
        if (id != PropertiesPackage.PROCESS_ITEM && id != PropertiesPackage.JOBLET_PROCESS_ITEM) {
            return;
        }
        OutputStream os = null;
        InputStream is = null;
        try {
            URI propertyResourceURI = EcoreUtil.getURI(itemRecord.getItem().getProperty());
            URI relativePlateformDestUri = propertyResourceURI.trimFileExtension().appendFileExtension(
                    FileConstants.SCREENSHOT_EXTENSION);
            URL fileURL = FileLocator.toFileURL(new java.net.URL(
                    "platform:/resource" + relativePlateformDestUri.toPlatformString(true))); //$NON-NLS-1$
            // for migration task ,there is not .screeenshot file in preceding version - begin
            boolean hasScreenshotFile = false;
            Iterator it = manager.getPaths().iterator();
            IPath screenshotNeeded = itemRecord.getPath().removeFileExtension()
                    .addFileExtension(FileConstants.SCREENSHOT_EXTENSION);
            while (it.hasNext()) {
                IPath path = (IPath) it.next();
                if (path.equals(screenshotNeeded)) {
                    hasScreenshotFile = true;
                    break;
                }
            }
            if (!hasScreenshotFile) {
                return;
            }
            // for migration task ,there is not .screeenshot file in preceding version - begin
            os = new FileOutputStream(fileURL.getFile());
            manager.getPaths().iterator().next();
            is = manager.getStream(screenshotNeeded);
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

    private boolean copyReferenceFiles(ResourcesManager manager, Item tmpItem, IPath pathToRead) throws IOException {
        OutputStream os = null;
        InputStream is = null;

        boolean haveRef = false;
        List<ReferenceFileItem> refItems = tmpItem.getReferenceResources();
        URI propertyResourceURI = EcoreUtil.getURI(tmpItem.getProperty());
        for (ReferenceFileItem refItem : refItems) {
            haveRef = true;
            URI relativePlateformDestUri = propertyResourceURI.trimFileExtension().appendFileExtension(refItem.getExtension());
            try {
                URL fileURL = FileLocator.toFileURL(new java.net.URL(
                        "platform:/resource" + relativePlateformDestUri.toPlatformString(true))); //$NON-NLS-1$
                os = new FileOutputStream(fileURL.getFile());
                is = manager.getStream(pathToRead.removeFileExtension().addFileExtension(refItem.getExtension()));
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

    private void logError(Exception e) {
        hasErrors = true;
        // IStatus status;
        //        String messageStatus = e.getMessage() != null ? e.getMessage() : ""; //$NON-NLS-1$
        ExceptionHandler.process(e);

        // status = new Status(IStatus.ERROR, RepositoryLocalProviderPlugin.PLUGIN_ID, IStatus.OK, messageStatus, e);
        // RepositoryLocalProviderPlugin.getDefault().getLog().log(status);
    }

    /**
     * need to returns sorted items by version to correctly import them later.
     */
    public List<ItemRecord> populateItems(ResourcesManager collector, boolean overwrite, IProgressMonitor progressMonitor) {
        TimeMeasure.display = CommonsPlugin.isDebugMode();
        TimeMeasure.displaySteps = CommonsPlugin.isDebugMode();
        TimeMeasure.measureActive = CommonsPlugin.isDebugMode();

        TimeMeasure.begin("populateItems");

        final RepositoryObjectCache repObjectcache = ImportCacheHelper.getInstance().getRepObjectcache();
        if ((!CommonsPlugin.isSameProjectLogonCommline() && CommonsPlugin.isHeadless()) || !CommonsPlugin.isHeadless()
                || !ProjectManager.getInstance().getCurrentProject().isLocal()) {
            repObjectcache.clear();
        }
        projects.clear();
        routineExtModulesMap.clear();
        List<ItemRecord> items = new ArrayList<ItemRecord>();

        int nbItems = 0;

        for (IPath path : collector.getPaths()) {
            if (isPropertyPath(path)) {
                nbItems++;
            }
        }

        progressMonitor.beginTask("Populate items to import", nbItems); //$NON-NLS-1$

        for (IPath path : collector.getPaths()) {
            if (!progressMonitor.isCanceled()) {
                if (isPropertyPath(path)) {
                    // IPath itemPath = getItemPath(path);
                    // if (collector.getPaths().contains(itemPath)) { //commet by tdq import
                    ItemRecord itemRecord = computeItemRecord(collector, path);
                    if (itemRecord.getProperty() != null) {
                        boolean alreadyInList = false;
                        for (ItemRecord currentItemRecord : items) {
                            if (StringUtils.equals(currentItemRecord.getProperty().getId(), itemRecord.getProperty().getId())
                                    && StringUtils.equals(currentItemRecord.getProperty().getVersion(), itemRecord.getProperty()
                                            .getVersion())) {
                                // if have any duplicate item from same project & same folder, just don't do
                                // anything,
                                // no need to display.
                                alreadyInList = true;
                                break;
                            }
                        }
                        if (alreadyInList) {
                            continue;
                        }
                        items.add(itemRecord);

                        if (checkItem(itemRecord, overwrite)) {
                            InternalEObject author = (InternalEObject) itemRecord.getProperty().getAuthor();
                            URI uri = null;
                            if (author != null) {
                                uri = author.eProxyURI();
                            }

                            IPath projectFilePath = HandlerUtil.getValidProjectFilePath(collector, path);
                            if (projectFilePath != null) {
                                Project project = computeProject(collector, itemRecord, projectFilePath);
                                if (checkProject(project, itemRecord)) {
                                    if (!checkHadoopSubitem(collector, itemRecord)) {
                                        // FIXME, need check more
                                    }

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
                                    itemRecord.addError(Messages.getString("ImportItemUtil.ProjectNotFound")); //$NON-NLS-1$
                                }
                            }
                        }
                    }
                    // }
                    progressMonitor.worked(1);
                }
            } else {
                break;
            }
        }

        Collections.sort(items, new Comparator<ItemRecord>() {

            @Override
            public int compare(ItemRecord o1, ItemRecord o2) {
                return VersionUtils.compareTo(o1.getProperty().getVersion(), o2.getProperty().getVersion());
            }
        });

        if (!CommonsPlugin.isHeadless() || !ProjectManager.getInstance().getCurrentProject().isLocal()) {
            for (List<IRepositoryViewObject> list : repObjectcache.getItemsFromRepository().values()) {
                list.clear();
            }
            repObjectcache.getItemsFromRepository().clear();
        }

        TimeMeasure.end("populateItems");
        TimeMeasure.display = false;
        TimeMeasure.displaySteps = false;
        TimeMeasure.measureActive = false;

        return items;
    }

    private boolean checkProject(Project project, ItemRecord itemRecord) {
        boolean checkProject = false;
        if (GlobalServiceRegister.getDefault().isServiceRegistered(IMigrationToolService.class)) {
            IMigrationToolService migrationService = (IMigrationToolService) GlobalServiceRegister.getDefault().getService(
                    IMigrationToolService.class);
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
                itemRecord.addError(Messages.getString("ImportItemUtil.ProjectNotFound")); //$NON-NLS-1$
            }
        }

        return checkProject;
    }

    /**
     * DOC ycbai Comment method "checkHadoopSubitem".
     * 
     * Check whether or not the itemRecord is a subitem record of a hadoop cluster item record.
     * 
     * @param itemRecord
     * @return
     */
    public static boolean checkHadoopSubitem(ResourcesManager manager, ItemRecord itemRecord) {
        Item item = itemRecord.getItem();
        if (item == null) {
            return false;
        }
        IHadoopClusterService hadoopClusterService = null;
        if (GlobalServiceRegister.getDefault().isServiceRegistered(IHadoopClusterService.class)) {
            hadoopClusterService = (IHadoopClusterService) GlobalServiceRegister.getDefault().getService(
                    IHadoopClusterService.class);
        }
        if (hadoopClusterService != null && hadoopClusterService.isHadoopSubItem(item)) {
            new ImportItemUtil().resolveItem(manager, itemRecord);
            return hadoopClusterService.isValidHadoopSubItem(item);
        }

        return false;
    }

    /**
     * DOC ycbai Comment method "collectHadoopSubrecords".
     * 
     * Collect all subitem records belong to a hadoop cluster.
     * 
     * @param totalItemRecords
     * @param itemRecord
     * @return
     */
    public static Set<ItemRecord> collectHadoopSubrecords(ResourcesManager manager, ItemRecord[] totalItemRecords,
            ItemRecord itemRecord) {
        Set<ItemRecord> subnodes = new HashSet<ItemRecord>();
        Item item = itemRecord.getItem();
        if (item == null) {
            return subnodes;
        }
        IHadoopClusterService hadoopClusterService = null;
        if (GlobalServiceRegister.getDefault().isServiceRegistered(IHadoopClusterService.class)) {
            hadoopClusterService = (IHadoopClusterService) GlobalServiceRegister.getDefault().getService(
                    IHadoopClusterService.class);
        }
        if (hadoopClusterService != null && hadoopClusterService.isHadoopClusterItem(item)) {
            new ImportItemUtil().resolveItem(manager, itemRecord);
            List<String> subitemIds = hadoopClusterService.getSubitemIdsOfHadoopCluster(item);
            if (subitemIds.size() == 0) {
                return subnodes;
            }
            for (ItemRecord ir : totalItemRecords) {
                if (ir.getProperty() != null && subitemIds.contains(ir.getProperty().getId())) {
                    subnodes.add(ir);
                }
            }
        }

        return subnodes;
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

    /**
     * DOC ycbai Comment method "checkMigrationTasks".
     * 
     * @param currentProject
     * @param importedProject
     * @param itemRecord
     * @return
     */
    private boolean checkMigrationTasks(Project currentProject, Project importedProject, ItemRecord itemRecord) {
        String importedProjectLabel = importedProject.getTechnicalLabel();
        if (migrationTasksStatusPerProject.containsKey(importedProjectLabel)) {
            if (migrationTasksStatusPerProject.get(importedProjectLabel)) {
                itemRecord.setMigrationTasksToApply(migrationTasksToApplyPerProject.get(importedProjectLabel));
                return true;
            } else {
                String message = Messages.getString("ImportItemUtil.cannotImportMessage", importedProjectLabel); //$NON-NLS-1$
                itemRecord.addError(message);
                log.info("'" + itemRecord.getItemName() + "' " + message);
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
                String message = Messages.getString("ImportItemUtil.cannotImportMessage", importedProjectLabel); //$NON-NLS-1$
                itemRecord.addError(message);
                log.info("'" + itemRecord.getItemName() + "' " + message);
                migrationTasksStatusPerProject.put(importedProjectLabel, false);
            }
            migrationTasksToApplyPerProject.put(importedProjectLabel, migrationTasks);
        }
        return canApplyMigration;
    }

    private ItemRecord computeItemRecord(ResourcesManager collector, IPath path) {
        ItemRecord itemRecord = new ItemRecord(path);
        computeProperty(collector, itemRecord);
        return itemRecord;
    }

    private void computeProperty(ResourcesManager manager, ItemRecord itemRecord) {
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
                    if (ePackage != null || !"http".equals(uri.scheme())) {
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

    public void resolveItem(ResourcesManager manager, ItemRecord itemRecord) {
        if (itemRecord.isResolved()) {
            return;
        }

        InputStream stream = null;

        try {
            final Item item = itemRecord.getItem();
            boolean byteArray = (item instanceof FileItem);
            IPath itemPath = getItemPath(itemRecord.getPath(), item);
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
                itemPath = getReferenceItemPath(itemRecord.getPath(), rfItem.getExtension());
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

    private Project computeProject(ResourcesManager manager, ItemRecord itemRecord, IPath path) {
        InputStream stream = null;

        try {
            if (!projects.containsKey(path)) {
                stream = manager.getStream(path);
                Resource resource = createResource(itemRecord, path, false);
                resource.load(stream, null);
                EmfHelper.loadResource(resource, stream, null);
                projects.put(path,
                        (Project) EcoreUtil.getObjectByType(resource.getContents(), PropertiesPackage.eINSTANCE.getProject()));
            }
            return projects.get(path);
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

    private Resource createResource(ItemRecord itemRecord, IPath path, boolean byteArrayResource) throws FileNotFoundException {
        Resource resource;
        ResourceSet resourceSet = itemRecord.getResourceSet();
        if (byteArrayResource) {
            resource = new ByteArrayResource(getURI(path));
            resourceSet.getResources().add(resource);
        } else {
            if (FileConstants.ITEM_EXTENSION.equals(path.getFileExtension())) {
                String projectName = "";
                if (itemRecord.getItemProject() != null) {
                    projectName = itemRecord.getItemProject().getTechnicalLabel();
                    projectName = projectName.toLowerCase();
                }
                // note: do similar code as the CwmResourceFactory
                String business = projectName + "/businessProcess/"; //$NON-NLS-1$
                String context = projectName + "/context/"; //$NON-NLS-1$
                String process = projectName + "/process/"; //$NON-NLS-1$
                String joblet = projectName + "/joblets/"; //$NON-NLS-1$
                String pathString = path.toPortableString();
                pathString = pathString.toLowerCase();
                // PTODO, maybe will bring bugs, like mr job,route, maybe jobscript
                if (pathString.contains(process) || pathString.contains(context) || pathString.contains(business)
                        || pathString.contains(joblet)) {
                    resource = new TalendXMIResource(getURI(path));
                } else {
                    resource = new CwmResource(getURI(path));
                }
                resourceSet.getResources().add(resource);
            } else {
                resource = resourceSet.createResource(getURI(path));
            }
        }
        return resource;
    }

    private URI getURI(IPath path) {
        return HandlerUtil.getURI(path);
    }

    private IPath getItemPath(IPath path, Item item) {
        return HandlerUtil.getItemPath(path, item);
    }

    private IPath getReferenceItemPath(IPath path, String extension) {
        return HandlerUtil.getReferenceItemPath(path, extension);
    }

    private boolean isPropertyPath(IPath path) {
        return xmiResourceManager.isPropertyFile(path.lastSegment());
    }

    private void deployJarToDes(final ResourcesManager manager, Set<String> extRoutines) {
        File file = null;
        if (extRoutines.isEmpty()) {
            return;
        }
        if (GlobalServiceRegister.getDefault().isServiceRegistered(ILibrariesService.class)) {
            ILibrariesService libService = (ILibrariesService) GlobalServiceRegister.getDefault().getService(
                    ILibrariesService.class);
            for (Object element : manager.getPaths()) {
                String value = element.toString();
                file = new File(value);
                if (extRoutines.contains(file.getName())) {
                    try {
                        libService.deployLibrary(file.toURL());
                    } catch (MalformedURLException e) {
                        ExceptionHandler.process(e);
                    } catch (IOException e) {
                        ExceptionHandler.process(e);
                    }
                }

            }
        }
    }

    private void deployJarToDesForArchive(final ResourcesManager manager, Set<String> extRoutines) {
        if (extRoutines.isEmpty()) {
            return;
        }
        if (GlobalServiceRegister.getDefault().isServiceRegistered(ILibrariesService.class)) {
            ILibrariesService libService = (ILibrariesService) GlobalServiceRegister.getDefault().getService(
                    ILibrariesService.class);
            IPath tmpDir = new Path(System.getProperty("user.dir") + File.separatorChar + "tmpJar"); //$NON-NLS-1$  

            File dirFile = tmpDir.toFile();
            for (IPath path : manager.getPaths()) {
                String fileName = path.lastSegment();
                if (extRoutines.contains(fileName)) {
                    try {
                        InputStream is = manager.getStream(path);
                        if (!dirFile.exists()) {
                            dirFile.mkdirs();
                        }
                        File temFile = tmpDir.append(fileName).toFile();
                        if (temFile.exists()) {
                            temFile.delete();
                        }
                        byte[] b = new byte[1024];
                        int length = 0;
                        BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(temFile, true));
                        while ((length = is.read(b)) != -1) {
                            fos.write(b, 0, length);
                        }
                        fos.close();
                        //
                        libService.deployLibrary(temFile.toURI().toURL());

                        temFile.delete();

                    } catch (MalformedURLException e) {
                        ExceptionHandler.process(e);
                    } catch (IOException e) {
                        ExceptionHandler.process(e);
                    }
                }
            }
            dirFile.delete();
        }
    }

    public Map<String, Set<String>> getRoutineExtModulesMap() {
        return routineExtModulesMap;
    }

}
