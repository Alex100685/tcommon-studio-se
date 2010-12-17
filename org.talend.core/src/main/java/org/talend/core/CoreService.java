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
package org.talend.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.osgi.baseadaptor.BaseData;
import org.eclipse.osgi.baseadaptor.bundlefile.BundleFile;
import org.eclipse.osgi.framework.adaptor.BundleData;
import org.eclipse.osgi.framework.internal.core.BundleHost;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.talend.commons.exception.BusinessException;
import org.talend.commons.exception.SystemException;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.commons.utils.generation.JavaUtils;
import org.talend.commons.utils.io.FilesUtils;
import org.talend.core.language.ECodeLanguage;
import org.talend.core.model.general.ILibrariesService;
import org.talend.core.model.general.Project;
import org.talend.core.model.metadata.ColumnNameChanged;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.MetadataTalendType;
import org.talend.core.model.metadata.MetadataTool;
import org.talend.core.model.metadata.QueryUtil;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.properties.Item;
import org.talend.core.model.relationship.RelationshipItemBuilder;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.model.repository.RepositoryManager;
import org.talend.core.model.routines.RoutineLibraryMananger;
import org.talend.core.model.utils.ContextParameterUtils;
import org.talend.core.model.utils.JavaResourcesHelper;
import org.talend.core.model.utils.PerlResourcesHelper;
import org.talend.core.model.utils.TalendTextUtils;
import org.talend.core.prefs.PreferenceManipulator;
import org.talend.core.ui.IRulesProviderService;
import org.talend.core.ui.images.OverlayImageProvider;
import org.talend.core.utils.KeywordsValidator;
import org.talend.designer.codegen.ICodeGeneratorService;
import org.talend.designer.codegen.ITalendSynchronizer;
import org.talend.designer.core.IDesignerCoreService;
import org.talend.designer.core.model.utils.emf.talendfile.NodeType;
import org.talend.designer.joblet.ui.IJobCheckService;
import org.talend.designer.runprocess.IRunProcessService;
import org.talend.repository.model.ComponentsFactoryProvider;
import org.talend.repository.model.IRepositoryNode;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * DOC Administrator class global comment. Detailled comment
 */
public class CoreService implements ICoreService {

    private static Logger log = Logger.getLogger(CoreService.class);

    public List<ColumnNameChanged> getColumnNameChanged(IMetadataTable oldTable, IMetadataTable newTable) {
        return MetadataTool.getColumnNameChanged(oldTable, newTable);
    }

    public List<ColumnNameChanged> getNewMetadataColumns(IMetadataTable oldTable, IMetadataTable newTable) {
        return MetadataTool.getNewMetadataColumns(oldTable, newTable);
    }

    public List<ColumnNameChanged> getRemoveMetadataColumns(IMetadataTable oldTable, IMetadataTable newTable) {
        return MetadataTool.getRemoveMetadataColumns(oldTable, newTable);
    }

    public void initializeForTalendStartupJob() {
        CorePlugin.getDefault().getRepositoryService().initializeForTalendStartupJob();
    }

    public String getLanTypeString() {
        return getPreferenceStore().getString(CorePlugin.PROJECT_LANGUAGE_TYPE);
    }

    public Image getImageWithDocExt(String extension) {
        return OverlayImageProvider.getImageWithDocExt(extension);
    }

    public ImageDescriptor getImageWithSpecial(Image source) {
        return OverlayImageProvider.getImageWithSpecial(source);
    }

    public boolean isContainContextParam(String code) {
        return ContextParameterUtils.isContainContextParam(code);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ICoreService#setFlagForQueryUtils(boolean)
     */
    public void setFlagForQueryUtils(boolean flag) {
        QueryUtil.isContextQuery = flag;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ICoreService#getFlagFromQueryUtils()
     */
    public boolean getContextFlagFromQueryUtils() {
        return QueryUtil.isContextQuery;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ICoreService#getRoot()
     */
    public IRepositoryNode getRoot() {
        return RepositoryManager.getRepositoryView().getRoot();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ICoreService#getRoutineAndJars()
     */
    public Map<String, List<URI>> getRoutineAndJars() {
        return RoutineLibraryMananger.getInstance().getRoutineAndJars();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ICoreService#getTemplateString()
     */
    public String getTemplateString() {
        return ITalendSynchronizer.TEMPLATE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ICoreService#getParameterUNIQUENAME()
     */
    public String getParameterUNIQUENAME(NodeType node) {
        return ElementParameterParser.getUNIQUENAME(node);
    }

    public boolean isAlreadyBuilt(Project project) {
        return !project.getEmfProject().getItemsRelations().isEmpty();
    }

    public void removeItemRelations(Item item) {
        RelationshipItemBuilder.getInstance().removeItemRelations(item);
    }

    public String getJavaJobFolderName(String jobName, String version) {
        return JavaResourcesHelper.getJobFolderName(jobName, version);
    }

    public String getJavaProjectFolderName(Item item) {
        return JavaResourcesHelper.getProjectFolderName(item);
    }

    public IResource getSpecificResourceInJavaProject(IPath path) throws CoreException {
        return JavaResourcesHelper.getSpecificResourceInJavaProject(path);
    }

    public String getContextFileNameForPerl(String projectName, String jobName, String version, String context) {
        return PerlResourcesHelper.getContextFileName(projectName, jobName, version, context);
    }

    public String getRootProjectNameForPerl(Item item) {
        return PerlResourcesHelper.getRootProjectName(item);
    }

    public IResource getSpecificResourceInPerlProject(IPath path) throws CoreException {
        return PerlResourcesHelper.getSpecificResourceInPerlProject(path);
    }

    public void syncLibraries(IProgressMonitor... monitorWrap) {
        CorePlugin.getDefault().getLibrariesService().syncLibraries(monitorWrap);
    }

    public void componentsReset() {
        ComponentsFactoryProvider.getInstance().reset();
    }

    public void initializeComponents(IProgressMonitor monitor) {
        ComponentsFactoryProvider.getInstance().initializeComponents(monitor);
    }

    public void removeJobLaunch(IRepositoryViewObject objToDelete) {
        if (GlobalServiceRegister.getDefault().isServiceRegistered(IDesignerCoreService.class)) {
            IDesignerCoreService designerCoreService = (IDesignerCoreService) GlobalServiceRegister.getDefault().getService(
                    IDesignerCoreService.class);
            designerCoreService.removeJobLaunch(objToDelete);
        }
    }

    public void deleteRoutinefile(IRepositoryViewObject objToDelete) {
        if (GlobalServiceRegister.getDefault().isServiceRegistered(ICodeGeneratorService.class)) {
            ICodeGeneratorService codeGenService = (ICodeGeneratorService) GlobalServiceRegister.getDefault().getService(
                    ICodeGeneratorService.class);
            codeGenService.createRoutineSynchronizer().deleteRoutinefile(objToDelete);
        }
    }

    public boolean checkJob(String name) throws BusinessException {
        IJobCheckService jobCheckService = (IJobCheckService) GlobalServiceRegister.getDefault().getService(
                IJobCheckService.class);
        if (jobCheckService != null) {
            return jobCheckService.checkJob(name);
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ICoreService#syncAllRoutines()
     */
    public void syncAllRoutines() throws SystemException {
        ICodeGeneratorService codeGenService = (ICodeGeneratorService) GlobalServiceRegister.getDefault().getService(
                ICodeGeneratorService.class);
        codeGenService.createRoutineSynchronizer().syncAllRoutines();

    }

    public void syncAllRules() {
        if (PluginChecker.isRulesPluginLoaded()) {
            IRulesProviderService rulesService = (IRulesProviderService) GlobalServiceRegister.getDefault().getService(
                    IRulesProviderService.class);
            if (rulesService != null) {
                rulesService.syncAllRules();
            }
        }

    }

    public Job initializeTemplates() {
        return CorePlugin.getDefault().getCodeGeneratorService().initializeTemplates();
    }

    public void createStatsLogAndImplicitParamter(Project project) {
        IDesignerCoreService designerCoreService = CorePlugin.getDefault().getDesignerCoreService();
        if (designerCoreService != null) {
            designerCoreService.createStatsLogAndImplicitParamter(project);
        }
    }

    public void deleteAllJobs(boolean fromPluginModel) {
        IRunProcessService runProcessService = (IRunProcessService) GlobalServiceRegister.getDefault().getService(
                IRunProcessService.class);
        runProcessService.deleteAllJobs(false);
    }

    public void addWorkspaceTaskDone(String task) {
        PreferenceManipulator prefManipulator = new PreferenceManipulator(CorePlugin.getDefault().getPreferenceStore());
        prefManipulator.addWorkspaceTaskDone(task);
    }

    public String filterSpecialChar(String input) {
        return TalendTextUtils.filterSpecialChar(input);
    }

    public String getLastUser() {
        PreferenceManipulator prefManipulator = new PreferenceManipulator(CorePlugin.getDefault().getPreferenceStore());
        return prefManipulator.getLastUser();
    }

    public boolean isKeyword(String word) {
        return KeywordsValidator.isKeyword(word);
    }

    public List<String> readWorkspaceTasksDone() {
        PreferenceManipulator prefManipulator = new PreferenceManipulator(CorePlugin.getDefault().getPreferenceStore());
        return prefManipulator.readWorkspaceTasksDone();
    }

    public String validateValueForDBType(String columnName) {
        return MetadataTool.validateValueForDBType(columnName);
    }

    public void synchronizeMapptingXML() {
        String dirPath = "/" + "mappings"; //$NON-NLS-1$
        Path filePath = new Path(dirPath);
        Bundle b = Platform.getBundle("org.talend.core");//$NON-NLS-1$
        URL url = null;
        try {
            if (b != null) {
                url = FileLocator.toFileURL(FileLocator.find(b, filePath, null));
            } else {
                url = MetadataTalendType.class.getResource(dirPath);
                IPath path = new Path(url.getPath());
                path = path.removeLastSegments(2);
                url = new URL("file:/" + path.toPortableString() + dirPath); //$NON-NLS-1$
            }
        } catch (IOException e) {
            ExceptionHandler.process(e);
        }
        if (url != null) {
            try {
                IRunProcessService runProcessService = (IRunProcessService) GlobalServiceRegister.getDefault().getService(
                        IRunProcessService.class);
                if (runProcessService != null) {
                    IProject project = runProcessService.getProject(ECodeLanguage.JAVA);
                    if (project != null) {
                        // create xmlmapping folder in .Java/src
                        IFolder rep = project.getFolder(JavaUtils.JAVA_SRC_DIRECTORY + File.separator
                                + JavaUtils.JAVA_XML_MAPPING);
                        if (!rep.exists()) {
                            rep.create(true, true, null);
                        }

                        File mappingSource = new File(url.getPath());
                        FilenameFilter filter = new FilenameFilter() {

                            public boolean accept(File dir, String name) {
                                if (name.endsWith(".xml")) {
                                    return true;
                                }
                                return false;
                            }
                        };

                        for (File file : mappingSource.listFiles(filter)) {
                            String targetName = getTargetName(file);
                            IFile targetFile = project.getFile(JavaUtils.JAVA_SRC_DIRECTORY + File.separator
                                    + JavaUtils.JAVA_XML_MAPPING + File.separator + targetName);
                            copyFile(file, targetFile);
                        }

                    }
                }
            } catch (CoreException e) {
                ExceptionHandler.process(e);
            } catch (IOException e) {
                ExceptionHandler.process(e);
            }
        }
    }

    private String getTargetName(File file) {
        String targetName = file.getName();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder analyser;
        try {
            analyser = documentBuilderFactory.newDocumentBuilder();
            Document document = analyser.parse(file);
            NodeList dbmsNodes = document.getElementsByTagName("dbms"); //$NON-NLS-1$
            String dbmsIdValue = "";
            for (int iDbms = 0; iDbms < dbmsNodes.getLength(); iDbms++) {
                Node dbmsNode = dbmsNodes.item(iDbms);
                NamedNodeMap dbmsAttributes = dbmsNode.getAttributes();
                dbmsIdValue = dbmsAttributes.getNamedItem("id").getNodeValue(); //$NON-NLS-1$

            }
            if (dbmsIdValue != null && !"".equals(dbmsIdValue)) {
                final String[] fileNameSplit = targetName.split("_");
                String idA = "_id";
                String idB = "id_";
                final int indexA = dbmsIdValue.indexOf(idA);
                final int indexB = dbmsIdValue.indexOf(idB);
                String secondeName = "";
                if (indexA > 0) {
                    secondeName = dbmsIdValue.substring(0, dbmsIdValue.length() - idA.length());
                } else if (indexB == 0) {
                    secondeName = dbmsIdValue.substring(idB.length(), dbmsIdValue.length());
                } else if (indexA == -1 && indexB == -1) {
                    secondeName = dbmsIdValue;
                }
                if (secondeName != null && !"".equals(secondeName)) {
                    targetName = fileNameSplit[0] + "_" + secondeName.toLowerCase() + ".xml";
                }

            }
        } catch (ParserConfigurationException e) {
            ExceptionHandler.process(e);
        } catch (SAXException e) {
            ExceptionHandler.process(e);
        } catch (IOException e) {
            ExceptionHandler.process(e);
        }
        return targetName;
    }

    public void copyFile(File in, IFile out) throws CoreException, IOException {
        if (out.exists()) {
            out.delete(true, null);
        }
        FileInputStream fis = new FileInputStream(in);
        if (!out.exists()) {
            out.create(fis, true, null);
        }
        fis.close();
    }

    public void synchronizeSapLib() {
        ILibrariesService libService = (ILibrariesService) GlobalServiceRegister.getDefault().getService(ILibrariesService.class);
        final String jarJco = libService.getJavaLibrariesPath() + File.separatorChar + "sapjco.jar";
        String targetPath = "";
        File source = new File(jarJco);
        if (source.exists()) {
            Bundle bundle = Platform.getBundle(PluginChecker.getSapWizardPluginId());
            if (bundle instanceof BundleHost) {
                BundleHost bundleHost = (BundleHost) bundle;
                final BundleData bundleData = bundleHost.getBundleData();
                if (bundleData instanceof BaseData) {
                    BaseData baseData = (BaseData) bundleData;
                    final BundleFile bundleFile = baseData.getBundleFile();
                    final File baseFile = bundleFile.getBaseFile();
                    targetPath = baseFile.getAbsolutePath() + File.separator + "sapjco.jar";

                    File target = new File(targetPath);
                    try {
                        FilesUtils.copyFile(source, target);
                    } catch (FileNotFoundException e) {
                        ExceptionHandler.process(e);
                    } catch (IOException e) {
                        ExceptionHandler.process(e);
                    }
                }
            }
        }

    }

    public IPreferenceStore getPreferenceStore() {
        return CorePlugin.getDefault().getPreferenceStore();
    }

    public boolean isOpenedItemInEditor(IRepositoryViewObject object) {
        return RepositoryManager.isOpenedItemInEditor(object);
    }
}
