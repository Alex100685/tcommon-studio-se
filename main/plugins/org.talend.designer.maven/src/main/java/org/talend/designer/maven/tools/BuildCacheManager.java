// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.maven.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.embedder.IMaven;
import org.eclipse.m2e.core.embedder.MavenModelManager;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.runtime.maven.MavenArtifact;
import org.talend.core.runtime.process.ITalendProcessJavaProject;
import org.talend.core.runtime.process.TalendProcessArgumentConstant;
import org.talend.core.runtime.repository.item.ItemProductKeys;
import org.talend.core.runtime.util.ItemDateParser;
import org.talend.designer.maven.launch.MavenPomCommandLauncher;
import org.talend.designer.maven.model.BuildCacheInfo;
import org.talend.designer.maven.model.TalendMavenConstants;
import org.talend.designer.maven.utils.MavenProjectUtils;
import org.talend.designer.maven.utils.PomIdsHelper;
import org.talend.designer.maven.utils.PomUtil;
import org.talend.designer.runprocess.IRunProcessService;
import org.talend.repository.ProjectManager;

/**
 * DOC zwxue class global comment. Detailled comment
 */
public class BuildCacheManager {

    public final static String BUILD_AGGREGATOR_POM_NAME = "build-aggregator.pom"; //$NON-NLS-1$

    private final String SEPARATOR = "|"; //$NON-NLS-1$

    private static BuildCacheManager instance;

    /**
     * job cache
     */
    private Map<String, BuildCacheInfo> jobCache = new HashMap<>();

    private Map<String, BuildCacheInfo> currentJobCache = new HashMap<>();

    private Set<String> currentJobmodules = new HashSet<>();

    private Set<ITalendProcessJavaProject> subjobProjects = new HashSet<>();

    private List<ITalendProcessJavaProject> subjobMavenProjects = new LinkedList<>();

    /**
     * joblet cache
     */
    private Map<String, Date> jobletCache = new HashMap<>();

    private Map<String, Date> currentJobletCache = new HashMap<>();

    private Map<String, Property> currentJobletmodules = new HashMap<>();

    private IFile pomFile;

    private AggregatorPomsHelper aggregatorPomsHelper;

    private BuildCacheManager() {
    }

    public synchronized static BuildCacheManager getInstance() {
        if (instance == null) {
            instance = new BuildCacheManager();
        }
        return instance;
    }

    public boolean isJobBuild(Property property) {
        BuildCacheInfo cacheInfo = jobCache.get(getKey(property));
        if (cacheInfo == null) {
            return false;
        }
        Date cachedTimestamp = cacheInfo.getTimestamp();
        Date currentTimeStamp = getTimestamp(property);
        if (currentTimeStamp.compareTo(cachedTimestamp) != 0) {
            return false;
        }
        String cachedBuildType = cacheInfo.getBuildType();
        String currentBuildType = getBuildType(property);
        // default build type of standard/bd job could be null
        if (cachedBuildType == null && currentBuildType == null) {
            return true;
        }
        if (cachedBuildType == null || currentBuildType == null) {
            return false;
        }
        // child job of route
        if("ROUTE".equalsIgnoreCase(currentBuildType)) {
        	return false;
        }

        return currentBuildType.equals(cachedBuildType);
    }

    public void preRemoveJobCache(Property property) {
        jobCache.remove(getKey(property));
    }

    public void putJobCache(Property property) {
        currentJobCache.put(getKey(property), generateCacheInfo(property));
        currentJobmodules.add(getModulePath(property));
        ITalendProcessJavaProject subjobProject = getTalendJobJavaProject(property);
        subjobProjects.add(subjobProject);
        if (MavenProjectUtils.hasMavenNature(subjobProject.getProject())) {
            subjobMavenProjects.add(subjobProject);
        }
    }

    public void removeJobCache(Property property) {
        currentJobCache.remove(getKey(property));
        currentJobmodules.remove(getModulePath(property));
        ITalendProcessJavaProject subjobProject = getTalendJobJavaProject(property);
        subjobProjects.remove(subjobProject);
        if (MavenProjectUtils.hasMavenNature(subjobProject.getProject())) {
            subjobMavenProjects.remove(subjobProject);
        }
    }

    public boolean isJobletBuild(Property property) {
        Date cachedTimestamp = jobletCache.get(getKey(property));
        if (cachedTimestamp == null) {
            return false;
        }
        Date currentTimeStamp = getTimestamp(property);
        return currentTimeStamp.compareTo(cachedTimestamp) == 0;
    }

    public void putJobletCache(Property property) {
        currentJobletCache.put(getKey(property), getTimestamp(property));
        currentJobletmodules.put(getModulePath(property), property);
    }

    public void removeJobletCache(Property property) {
        currentJobletCache.remove(getKey(property));
        currentJobletmodules.remove(getModulePath(property));
    }

    public void clearCurrentCache() {
        // clean job cache
        currentJobCache.clear();
        currentJobmodules.clear();
        subjobProjects.clear();
        subjobMavenProjects.clear();
        aggregatorPomsHelper = null;
    }

    public void clearCurrentJobletCache() {
        // clean joblet cache
        currentJobletCache.clear();
        currentJobletmodules.clear();
    }

    public void performBuildSuccess() {
        jobCache.putAll(currentJobCache);
        jobletCache.putAll(currentJobletCache);
        clearCurrentCache();
        clearCurrentJobletCache();
    }

    public void performBuildFailure() {
        clearCurrentCache();
        clearCurrentJobletCache();
    }

    public void build(IProgressMonitor monitor, Map<String, Object> argumentsMap) throws Exception {
        if (needTempAggregator()) {
            createBuildAggregatorPom();
            generateJobletPoms();
            try {
                for (ITalendProcessJavaProject project : subjobProjects) {
                    MavenModelManager mavenModelManager = MavenPlugin.getMavenModelManager();
                    Model projModel = mavenModelManager.readMavenModel(project.getProjectPom());
                    MavenArtifact artifact = new MavenArtifact();
                    artifact.setGroupId(projModel.getGroupId());
                    artifact.setArtifactId(projModel.getArtifactId());
                    artifact.setVersion(projModel.getVersion());
                    String artifactPath = PomUtil.getArtifactPath(artifact);

                    final IMaven maven = MavenPlugin.getMaven();
                    String localRepositoryPath = maven.getLocalRepositoryPath();
                    if (localRepositoryPath != null) {
                        File moduleFolder = new File(localRepositoryPath, artifactPath);

                        PomUtil.cleanLastUpdatedFile(moduleFolder.getParentFile());
                    }
                }

                String goal = (String) argumentsMap.get(TalendProcessArgumentConstant.ARG_GOAL);
                MavenPomCommandLauncher mavenLauncher = new MavenPomCommandLauncher(pomFile, goal);
                mavenLauncher.setArgumentsMap(argumentsMap);
                if (isBuildJob()) {
                    mavenLauncher.setIgnoreTestFailure(true);
                } else {
                    // run job, still skip tests anyway
                    mavenLauncher.setSkipTests(true);
                }
                mavenLauncher.execute(monitor);
            } finally {
                deleteBuildAggregatorPom();
            }
        }
    }

    private void generateJobletPoms() throws PersistenceException {
        for (Property property : currentJobletmodules.values()) {
            IRepositoryViewObject obj = ProxyRepositoryFactory.getInstance().getSpecificVersion(property.getId(),
                    property.getVersion(), true);
            if (obj != null) {
                IRunProcessService.get().generatePom(obj.getProperty().getItem());
            }
        }
    }

    public void buildAllSubjobMavenProjects() {
        for (ITalendProcessJavaProject subjobMavenProject : subjobMavenProjects) {
            subjobMavenProject.buildWholeCodeProject();
        }
    }

    /**
     * DOC nrousseau Comment method "needTempAggregator".
     *
     * @return
     */
    public boolean needTempAggregator() {
        return !currentJobmodules.isEmpty() || !currentJobletmodules.isEmpty();
    }

    public void clearAllCaches() {
        jobCache.clear();
        clearCurrentJobletCache();
        jobletCache.clear();
    }

    /**
     * only for Junit
     */
    public boolean isInCurrentJobletCache(Property property) {
        String key = getKey(property);
        return currentJobletCache.containsKey(key) || currentJobletmodules.containsKey(getModulePath(property));
    }

    private void createBuildAggregatorPom() throws Exception {
        pomFile = getAggregatorPomsHelper().getProjectPomsFolder().getFile(new Path(BUILD_AGGREGATOR_POM_NAME));
        Model model = new Model();
        model.setModelVersion("4.0.0"); //$NON-NLS-1$
        model.setGroupId(TalendMavenConstants.DEFAULT_GROUP_ID);
        model.setArtifactId("build.aggregator"); //$NON-NLS-1$
        model.setVersion("7.0.0"); //$NON-NLS-1$
        model.setPackaging(TalendMavenConstants.PACKAGING_POM);
        model.setModules(new ArrayList<String>());
        model.getModules().addAll(currentJobmodules);
        model.getModules().addAll(currentJobletmodules.keySet());
        Parent parent = new Parent();      
        parent.setGroupId(PomIdsHelper.getProjectGroupId());
        parent.setArtifactId(PomIdsHelper.getProjectArtifactId());
        parent.setVersion(PomIdsHelper.getProjectVersion());        
        model.setParent(parent);
        PomUtil.savePom(null, model, pomFile);
    }

    private void deleteBuildAggregatorPom() throws CoreException {
        pomFile = getAggregatorPomsHelper().getProjectPomsFolder().getFile(new Path(BUILD_AGGREGATOR_POM_NAME));
        if (pomFile.exists()) {
            pomFile.delete(true, false, null);
        }
    }

    private String getKey(Property property) {
        String projectTechName = ProjectManager.getInstance().getProject(property).getTechnicalLabel();
        String jobId = property.getId();
        String jobVersion = property.getVersion();
        String key = projectTechName + SEPARATOR + jobId + SEPARATOR + jobVersion;
        return key;
    }

    private BuildCacheInfo generateCacheInfo(Property property) {
        return new BuildCacheInfo(getKey(property), getBuildType(property), getTimestamp(property));
    }

    private Date getTimestamp(Property property) {
        return ItemDateParser.parseAdditionalDate(property, ItemProductKeys.DATE.getModifiedKey());
    }

    private String getBuildType(Property property) {
        return (String) property.getAdditionalProperties().get(TalendProcessArgumentConstant.ARG_BUILD_TYPE);
    }

    private String getModulePath(Property property) {
        IPath jobProjectPath = AggregatorPomsHelper.getItemPomFolder(property).getLocation();
        IPath basePath = getAggregatorPomsHelper().getProjectPomsFolder().getLocation();
        String modulePath = jobProjectPath.makeRelativeTo(basePath).toPortableString();
        return modulePath;
    }

    private ITalendProcessJavaProject getTalendJobJavaProject(Property property) {
        if (GlobalServiceRegister.getDefault().isServiceRegistered(IRunProcessService.class)) {
            IRunProcessService service = (IRunProcessService) GlobalServiceRegister.getDefault()
                    .getService(IRunProcessService.class);
            return service.getTalendJobJavaProject(property);
        }
        return null;
    }

    private AggregatorPomsHelper getAggregatorPomsHelper() {
        if (aggregatorPomsHelper == null) {
            aggregatorPomsHelper = new AggregatorPomsHelper();
        }
        return aggregatorPomsHelper;
    }

    private boolean isBuildJob() {
        if (GlobalServiceRegister.getDefault().isServiceRegistered(IRunProcessService.class)) {
            IRunProcessService service = (IRunProcessService) GlobalServiceRegister.getDefault()
                    .getService(IRunProcessService.class);
            return service.isExportConfig();
        }
        return false;
    }

    public boolean containsMultipleVersionModules() {
        return containsMultipleVersionModules(currentJobletmodules.keySet()) || containsMultipleVersionModules(currentJobmodules);
    }

    private static boolean containsMultipleVersionModules(Set<String> mods) {
        Set<String> joblets = new HashSet<String>();
        for (String mod : mods) {
            int idx = mod.lastIndexOf('_');
            if (idx == -1) {
                continue;
            }
            String jobletWithoutVersion = mod.substring(0, idx);
            if (joblets.contains(jobletWithoutVersion)) {
                return true;
            } else {
                joblets.add(jobletWithoutVersion);
            }
        }
        return false;
    }

}
