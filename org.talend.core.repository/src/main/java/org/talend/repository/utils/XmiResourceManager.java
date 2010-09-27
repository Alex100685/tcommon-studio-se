// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.talend.commons.emf.EmfHelper;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.utils.workbench.resources.ResourceUtils;
import org.talend.core.model.properties.DatabaseConnectionItem;
import org.talend.core.model.properties.DelimitedFileConnectionItem;
import org.talend.core.model.properties.EbcdicConnectionItem;
import org.talend.core.model.properties.FileItem;
import org.talend.core.model.properties.FolderItem;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.JobDocumentationItem;
import org.talend.core.model.properties.JobletDocumentationItem;
import org.talend.core.model.properties.PositionalFileConnectionItem;
import org.talend.core.model.properties.Project;
import org.talend.core.model.properties.PropertiesPackage;
import org.talend.core.model.properties.Property;
import org.talend.core.model.properties.helper.ByteArrayResource;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.repository.constants.FileConstants;
import org.talend.repository.ProjectManager;
import org.talend.repository.model.ProxyRepositoryFactory;
import org.talend.repository.utils.ResourceFilenameHelper.FileName;

/**
 * DOC mhelleboid class global comment. Detailled comment <br/>
 * 
 * $Id: XmiResourceManager.java 44692 2010-06-30 04:29:32Z nrousseau $
 * 
 */
public class XmiResourceManager {

    private static final String OLD_PROJECT_FILENAME = "talendProject"; //$NON-NLS-1$

    // PTODO mhelleboid should use a custom ResourceFactory
    // PTODO mhelleboid test duplicate resourcesUri in resourceSet !

    public ResourceSet resourceSet = new ResourceSetImpl();

    private boolean useOldProjectFile;

    private boolean avoidUnloadResource;

    public XmiResourceManager() {
        setUseOldProjectFile(false);
        resourceSet.getLoadOptions().put("OPTION_DEFER_IDREF_RESOLUTION", Boolean.TRUE);
        resourceSet.getLoadOptions().put("OPTION_USE_PARSER_POOL", Boolean.TRUE);
    }

    public void resetResourceSet() {
        resourceSet = new ResourceSetImpl();
        resourceSet.getLoadOptions().put("OPTION_DEFER_IDREF_RESOLUTION", Boolean.TRUE);
        resourceSet.getLoadOptions().put("OPTION_USE_PARSER_POOL", Boolean.TRUE);
    }

    public Project loadProject(IProject project) throws PersistenceException {
        URI uri = getProjectResourceUri(project);
        if (!isAvoidUnloadResource()) {
            unloadResource(uri.toString());
        }
        // unloadResources();
        Resource resource = resourceSet.getResource(uri, true);
        Project emfProject = (Project) EcoreUtil
                .getObjectByType(resource.getContents(), PropertiesPackage.eINSTANCE.getProject());
        return emfProject;
    }

    public boolean hasTalendProjectFile(IProject project) {
        URI uri = getProjectResourceUri(project);
        try {
            project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            ExceptionHandler.process(e);
        }
        IPath path = URIHelper.convert(uri);
        IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
        return file.exists();
    }

    public Property loadProperty(IResource iResource) {
        // force unload old version, or the UI won't be synchronized all the time to the current file.
        // this is only if a user update itself a .item or .properties, or for SVN repository.
        //
        URI propertyUri = URIHelper.convert(iResource.getFullPath());
        // URI itemResourceURI = getItemResourceURI(propertyUri);
        // List<Resource> resources = new ArrayList<Resource>(resourceSet.getResources());
        // for (Resource res : resources) {
        // if (propertyUri.toString().equals(res.getURI().toString())) {
        // res.unload();
        // // resourceSet.getResources().remove(res);
        // }
        // // if (itemResourceURI.toString().equals(res.getURI().toString())) {
        // // res.unload();
        // // // resourceSet.getResources().remove(res);
        // // }
        // }
        // try {
        // iResource.refreshLocal(IResource.DEPTH_ONE, new NullProgressMonitor());
        // } catch (CoreException e) {
        // // do nothing
        // }
        Resource propertyResource = resourceSet.getResource(propertyUri, true);
        // resourceSet.getResources().add(propertyResource);
        Property property = (Property) EcoreUtil.getObjectByType(propertyResource.getContents(), PropertiesPackage.eINSTANCE
                .getProperty());
        // property.getItem().eResource()
        return property;
    }

    public Property forceReloadProperty(Property property) {
        URI propertyURI = property.eResource().getURI();
        unloadResources(property);
        Resource propertyResource = resourceSet.getResource(propertyURI, true);
        return (Property) EcoreUtil.getObjectByType(propertyResource.getContents(), PropertiesPackage.eINSTANCE.getProperty());
    }

    private IPath getFolderPath(IProject project, ERepositoryObjectType repositoryObjectType, IPath relativePath)
            throws PersistenceException {
        IFolder folder = project.getFolder(ERepositoryObjectType.getFolderName(repositoryObjectType)).getFolder(relativePath);
        return folder.getFullPath();
    }

    public Resource createProjectResource(IProject project) {
        URI uri = getProjectResourceUri(project);
        return resourceSet.createResource(uri);
    }

    private URI getProjectResourceUri(IProject project) {
        URI uri = URIHelper.convert(project.getFullPath().append(getProjectFilename()));
        return uri;
    }

    public Resource createPropertyResource(Resource itemResource) {
        URI propertyResourceURI = getPropertyResourceURI(itemResource.getURI());
        return resourceSet.createResource(propertyResourceURI);
    }

    public Resource createPropertyResource(URI propertyResourceURI) {
        return resourceSet.createResource(propertyResourceURI);
    }

    public Resource createItemResource(IProject project, Item item, IPath path, ERepositoryObjectType repositoryObjectType,
            boolean byteArrayResource) throws PersistenceException {
        URI itemResourceURI = getItemResourceURI(project, repositoryObjectType, path, item);

        Resource itemResource = createItemResource(byteArrayResource, itemResourceURI);

        return itemResource;
    }

    private Resource createItemResource(boolean byteArrayResource, URI itemResourceURI) {
        Resource itemResource;
        if (byteArrayResource) {
            itemResource = new ByteArrayResource(itemResourceURI);
            resourceSet.getResources().add(itemResource);
        } else {
            itemResource = resourceSet.createResource(itemResourceURI);
        }
        return itemResource;
    }

    public void deleteResource(Resource resource) throws PersistenceException {
        ResourceUtils.deleteFile(URIHelper.getFile(resource.getURI()));
        resourceSet.getResources().remove(resource);
    }

    public void deleteLogiclResource(Resource resource) throws PersistenceException {
        ResourceUtils.deleteRevisionFile(URIHelper.getFile(resource.getURI()));
        resourceSet.getResources().remove(resource);
    }

    public Resource getItemResource(Item item) {
        URI itemResourceURI = getItemResourceURI(getItemURI(item));
        Resource itemResource = resourceSet.getResource(itemResourceURI, false);

        if (itemResource == null) {
            if (item instanceof FileItem) {
                itemResource = new ByteArrayResource(itemResourceURI);
                resourceSet.getResources().add(itemResource);
            }
            itemResource = resourceSet.getResource(itemResourceURI, true);
        }

        return itemResource;
    }

    private URI getItemURI(Item item) {
        ProjectManager pManager = ProjectManager.getInstance();
        org.talend.core.model.general.Project project = new org.talend.core.model.general.Project(pManager.getProject(item));
        // referenced item
        if (project != null && !project.equals(pManager.getCurrentProject())) {
            String folder = null;
            if (item instanceof JobDocumentationItem) {
                folder = ERepositoryObjectType.getFolderName(ERepositoryObjectType.JOB_DOC);

            } else if (item instanceof JobletDocumentationItem) {
                folder = ERepositoryObjectType.getFolderName(ERepositoryObjectType.JOBLET_DOC);
            } else if (item instanceof DatabaseConnectionItem) {
                folder = ERepositoryObjectType.getFolderName(ERepositoryObjectType.METADATA_CONNECTIONS);
            } else if (item instanceof DelimitedFileConnectionItem) {
                folder = ERepositoryObjectType.getFolderName(ERepositoryObjectType.METADATA_FILE_DELIMITED);
            } else if (item instanceof EbcdicConnectionItem) {
                folder = ERepositoryObjectType.getFolderName(ERepositoryObjectType.METADATA_FILE_EBCDIC);
            } else if (item instanceof PositionalFileConnectionItem) {
                folder = ERepositoryObjectType.getFolderName(ERepositoryObjectType.METADATA_FILE_POSITIONAL);
            }

            if (folder != null) {
                IPath path = new Path(project.getTechnicalLabel());
                path = path.append(folder);
                path = path.append(item.getState().getPath());
                Property property = item.getProperty();
                String itemStr = property.getLabel() + "_" + property.getVersion() + "." + FileConstants.PROPERTIES_EXTENSION; //$NON-NLS-1$ //$NON-NLS-2$
                path = path.append(itemStr);
                return URIHelper.convert(path);
            }
        }
        return item.getProperty().eResource().getURI();
    }

    public List<Resource> getAffectedResources(Property property) {
        EcoreUtil.resolveAll(property.getItem());
        List<Resource> resources = new ArrayList<Resource>();
        // try to update and get new resource while the job's parent folder's name has been change which may cause
        // propertyResource == null.
        try {
            property = ProxyRepositoryFactory.getInstance().getUptodateProperty(property);
        } catch (PersistenceException e) {
            ExceptionHandler.process(e);
        }
        Resource propertyResource = property.eResource();
        URI itemResourceURI = getItemResourceURI(propertyResource.getURI());
        Resource itemResource = resourceSet.getResource(itemResourceURI, true);
        // MOD　by zshen to avoid property file be removed when chage connection.
        resources.add(propertyResource);
        resources.add(itemResource);

        return resources;
    }

    public void moveResource(Resource resource, IPath path) throws PersistenceException {
        ResourceUtils.moveResource(URIHelper.getFile(resource.getURI()), path);
        resource.setURI(URIHelper.convert(path));
    }

    public void saveResource(Resource resource) throws PersistenceException {
        EmfHelper.saveResource(resource);
    }

    public URI getItemResourceURI(URI propertyResourceURI) {
        return propertyResourceURI.trimFileExtension().appendFileExtension(FileConstants.ITEM_EXTENSION);
    }

    private URI getPropertyResourceURI(URI itemResourceURI) {
        return itemResourceURI.trimFileExtension().appendFileExtension(FileConstants.PROPERTIES_EXTENSION);
    }

    private URI getItemResourceURI(IProject project, ERepositoryObjectType repositoryObjectType, IPath path, Item item)
            throws PersistenceException {
        IPath folderPath = getFolderPath(project, repositoryObjectType, path);
        FileName fileName = ResourceFilenameHelper.create(item.getProperty());
        IPath resourcePath = ResourceFilenameHelper.getExpectedFilePath(fileName, folderPath, FileConstants.ITEM_EXTENSION);

        return URIHelper.convert(resourcePath);
    }

    public boolean isPropertyFile(IFile file) {
        return FileConstants.PROPERTIES_EXTENSION.equals(file.getFileExtension());
    }

    public boolean isPropertyFile(File file) {
        return file.getAbsolutePath().endsWith(FileConstants.PROPERTIES_EXTENSION);
    }

    public boolean isPropertyFile(String filename) {
        return filename.endsWith(FileConstants.PROPERTIES_EXTENSION);
    }

    public boolean isProjectFile(IFile file) {
        return getProjectFilename().equals(file.getName());
    }

    public void propagateFileName(Property lastVersionProperty, Property resourceProperty) throws PersistenceException {
        List<Resource> affectedResources = getAffectedResources(resourceProperty);
        List<Resource> resourcesToSave = new ArrayList<Resource>();

        Property previousVersionProperty = null;

        for (Resource resource : affectedResources) {
            ResourceFilenameHelper.FileName fileName = ResourceFilenameHelper.create(resource, resourceProperty,
                    lastVersionProperty);

            if (ResourceFilenameHelper.mustChangeVersion(fileName)) {
                IPath path = URIHelper.convert(resource.getURI());
                IPath bakPath = path.addFileExtension("bak"); //$NON-NLS-1$

                // Create copy
                copyResource(resource, bakPath);
                IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(bakPath);

                // move actual to new version
                moveResource(resource, ResourceFilenameHelper.getExpectedFilePath(fileName, false));
                resourcesToSave.add(resource);

                // restore copy as previous version
                ResourceUtils.moveResource(file, path);
                file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);

                if (isPropertyFile(file)) {
                    previousVersionProperty = loadProperty(file);
                }
            } else if (ResourceFilenameHelper.mustChangeLabel(fileName)) {
                resourceProperty.setLabel(lastVersionProperty.getLabel());
                if (!ResourceFilenameHelper.hasSameNameButDifferentCase(fileName)) {
                    moveResource(resource, ResourceFilenameHelper.getExpectedFilePath(fileName, false));
                }
                resourcesToSave.add(resource);
            }
        }

        if (previousVersionProperty != null) {
            List<Resource> previousVersionResources = getAffectedResources(previousVersionProperty);
            for (Resource resource : previousVersionResources) {
                FileName fileName = ResourceFilenameHelper.create(resource, previousVersionProperty, lastVersionProperty);

                if (ResourceFilenameHelper.mustChangeLabel(fileName)) {
                    IPath expectedFilePath = ResourceFilenameHelper.getExpectedFilePath(fileName, true);
                    previousVersionProperty.setLabel(lastVersionProperty.getLabel());
                    if (!ResourceFilenameHelper.hasSameNameButDifferentCase(fileName)) {
                        moveResource(resource, expectedFilePath);
                    }
                }
                resourcesToSave.add(resource);
            }
            if (lastVersionProperty.getItem().getParent() instanceof FolderItem) {
                FolderItem folderItem = (FolderItem) lastVersionProperty.getItem().getParent();
                folderItem.getChildren().add(previousVersionProperty.getItem());
            }
        }

        for (Resource resource : resourcesToSave) {
            saveResource(resource);
        }
    }

    private void copyResource(Resource resource, IPath path) throws PersistenceException {
        IFile file = URIHelper.getFile(resource.getURI());
        try {
            file.copy(path, true, null);
        } catch (CoreException e) {
            throw new PersistenceException(e);
        }
    }

    private String getProjectFilename() {
        if (useOldProjectFile) {
            return OLD_PROJECT_FILENAME;
        } else {
            return FileConstants.LOCAL_PROJECT_FILENAME;
        }
    }

    public void setUseOldProjectFile(boolean useOldProjectFile) {
        this.useOldProjectFile = useOldProjectFile;
    }

    public void unloadResources() {
        List<Resource> resources = new ArrayList<Resource>(resourceSet.getResources());
        for (Resource resource : resources) {
            resource.unload();
            resourceSet.getResources().remove(resource);
        }
    }

    public void unloadResources(Property property) {
        for (Resource resource : getAffectedResources(property)) {
            resource.unload();
            resourceSet.getResources().remove(resource);
        }
    }

    /**
     * Method "unloadResource" unload and remove the specification resource from the resource set. MOD mzhao
     * 
     * @param uriString the uri sting of resource.
     */
    public synchronized void unloadResource(String uriString) {
        List<Resource> resources = new ArrayList<Resource>(resourceSet.getResources());
        for (Resource res : resources) {
            if (uriString.equals(res.getURI().toString())) {
                res.unload();
                resourceSet.getResources().remove(res);
            }
        }
    }

    public boolean isAvoidUnloadResource() {
        return this.avoidUnloadResource;
    }

    public void setAvoidUnloadResource(boolean avoidUnloadResource) {
        this.avoidUnloadResource = avoidUnloadResource;
    }
}
