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
package org.talend.core.ui.branding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.talend.core.CorePlugin;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.PluginChecker;
import org.talend.core.language.ECodeLanguage;
import org.talend.core.tis.ICoreTisService;
import org.talend.repository.model.RepositoryNode;

/**
 * DefaultBrandingConfiguration is as TIS BrandingConfiguration(don't hide repository Nodes or Components),all other
 * brandingConfigure should subclass of this one.
 */
public class DefaultBrandingConfiguration implements IBrandingConfiguration {

    protected IActionBarHelper helper;

    protected IActionBarConfigurer actionBarConfigurer;

    private String title = ""; //$NON-NLS-1$

    public IActionBarHelper getHelper() {
        return this.helper;
    }

    public void setHelper(IActionBarHelper helper) {
        this.helper = helper;
    }

    public IActionBarConfigurer getActionBarConfigurer() {
        return this.actionBarConfigurer;
    }

    public void setActionBarConfigurer(IActionBarConfigurer actionBarConfigurer) {
        this.actionBarConfigurer = actionBarConfigurer;
    }

    public void fillMenuBar(IMenuManager menuBar) {
        if (helper != null) {
            helper.fillMenuBar(menuBar);
        }
    }

    public void fillCoolBar(ICoolBarManager coolBar) {
        if (helper != null) {
            helper.fillCoolBar(coolBar);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#getHiddenRepositoryCategory()
     */
    public List<RepositoryNode> getHiddenRepositoryCategory(RepositoryNode parent) {

        List<RepositoryNode> nodes = new ArrayList<RepositoryNode>();

        // // 2.1 Joblet
        // RepositoryNode jobletNode = new RepositoryNode(null, parent, ENodeType.SYSTEM_FOLDER);
        // jobletNode.setProperties(EProperties.LABEL, ERepositoryObjectType.JOBLET);
        // jobletNode.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.JOBLET);
        // nodes.add(jobletNode);
        //
        // // SAP
        // RepositoryNode metadataSAPConnectionNode = new RepositoryNode(null, parent, ENodeType.SYSTEM_FOLDER);
        // metadataSAPConnectionNode.setProperties(EProperties.LABEL, ERepositoryObjectType.METADATA_SAPCONNECTIONS);
        // metadataSAPConnectionNode.setProperties(EProperties.CONTENT_TYPE,
        // ERepositoryObjectType.METADATA_SAPCONNECTIONS);
        // nodes.add(metadataSAPConnectionNode);
        //
        // // EBCDIC
        // RepositoryNode metadataEbcdicConnectionNode = new RepositoryNode(null, parent, ENodeType.SYSTEM_FOLDER);
        // metadataEbcdicConnectionNode.setProperties(EProperties.LABEL, ERepositoryObjectType.METADATA_FILE_EBCDIC);
        // metadataEbcdicConnectionNode.setProperties(EProperties.CONTENT_TYPE,
        // ERepositoryObjectType.METADATA_FILE_EBCDIC);
        // nodes.add(metadataEbcdicConnectionNode);
        //
        // // Reference Projects
        // RepositoryNode refProject = new RepositoryNode(null, parent, ENodeType.SYSTEM_FOLDER);
        // refProject.setProperties(EProperties.LABEL, ERepositoryObjectType.REFERENCED_PROJECTS);
        // refProject.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.REFERENCED_PROJECTS);
        // nodes.add(refProject);

        return nodes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#initPerspective(org.eclipse.ui.IPageLayout)
     */
    public void initPerspective(IPageLayout layout) {
        String componentSettingViewerId = "org.talend.designer.core.ui.views.properties.ComponentSettingsView";//$NON-NLS-1$
        String navigatorId = "org.eclipse.ui.views.ResourceNavigator"; //$NON-NLS-1$
        String outlineId = "org.eclipse.ui.views.ContentOutline"; //$NON-NLS-1$
        String codeId = "org.talend.designer.core.codeView"; //$NON-NLS-1$
        String repositoryId = "org.talend.repository.views.repository"; //$NON-NLS-1$

        String runProcessViewId = "org.talend.designer.runprocess.ui.views.processview"; //$NON-NLS-1$
        String problemsViewId = "org.talend.designer.core.ui.views.ProblemsView"; //$NON-NLS-1$
        String modulesViewId = "org.talend.designer.codegen.perlmodule.ModulesView"; //$NON-NLS-1$
        String ecosystemViewId = "org.talend.designer.components.ecosystem.ui.views.EcosystemView"; //$NON-NLS-1$
        String schedulerViewId = "org.talend.scheduler.views.Scheduler"; //$NON-NLS-1$
        String contextsViewId = "org.talend.designer.core.ui.views.ContextsView"; //$NON-NLS-1$
        String gefPaletteViewId = "org.eclipse.gef.ui.palette_view"; //$NON-NLS-1$
        String jobSettingsViewId = "org.talend.designer.core.ui.views.jobsettings.JobSettingsView"; //$NON-NLS-1$
        String jobHierarchyViewId = "org.talend.designer.core.ui.hierarchy.JobHierarchyViewPart"; //$NON-NLS-1$

        // leftTopLayout
        IFolderLayout leftTopLayout = layout.createFolder("navigatorLayout", IPageLayout.LEFT, new Float(0.3), //$NON-NLS-1$
                IPageLayout.ID_EDITOR_AREA);
        leftTopLayout.addView(repositoryId);
        leftTopLayout.addView(navigatorId);

        // leftBottomLayout
        IFolderLayout leftBottomLayout = layout.createFolder("outlineCodeLayout", IPageLayout.BOTTOM, new Float(0.6), //$NON-NLS-1$
                repositoryId);
        leftBottomLayout.addView(outlineId);
        leftBottomLayout.addView(codeId);

        IFolderLayout rightTopLayout = layout.createFolder("paletteLayout", IPageLayout.RIGHT, new Float(0.8), //$NON-NLS-1$
                IPageLayout.ID_EDITOR_AREA);
        rightTopLayout.addView(gefPaletteViewId);

        // bottomLayout
        IFolderLayout bottomLayout = layout.createFolder("bottomLayout", IPageLayout.BOTTOM, new Float(0.6), //$NON-NLS-1$
                IPageLayout.ID_EDITOR_AREA);
        // bottomLayout.addView(propertyId);
        bottomLayout.addView(jobSettingsViewId);
        bottomLayout.addView(contextsViewId);
        bottomLayout.addView(componentSettingViewerId);

        bottomLayout.addView(runProcessViewId);
        bottomLayout.addView(problemsViewId);
        bottomLayout.addView(modulesViewId);
        bottomLayout.addView(ecosystemViewId);
        bottomLayout.addView(schedulerViewId);
        bottomLayout.addView(jobHierarchyViewId);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#getAvailableComponents()
     */
    public String[] getAvailableComponents() {
        return null;
    }

    private int getIndex(List<PaletteEntry> children, String label) {
        int ret = -1;
        for (int i = 0; i < children.size(); i++) {
            PaletteEntry n = children.get(i);
            if (label.equals(n.getLabel())) {
                ret = i;
            }
        }
        return ret;
    }

    /**
     * 
     * DOC achen Comment method "hideComponents".
     * 
     * @param container
     * @param label
     */
    protected void hideComponents(PaletteEntry container, String label) {
        if (container instanceof PaletteContainer) {
            List<PaletteEntry> children = ((PaletteContainer) container).getChildren();
            int index = getIndex(children, label);
            if (index != -1) {
                children.remove(index);
            } else {
                for (PaletteEntry n : children) {
                    hideComponents(n, label);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#getAvailableLanguages()
     */
    public String[] getAvailableLanguages() {
        String[] languages = { ECodeLanguage.JAVA.getName(), ECodeLanguage.PERL.getName() };

        return languages;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#getJobEditorSettings()
     */
    public Map<String, Object> getJobEditorSettings() {
        // no specific settings by default.
        return new HashMap<String, Object>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#isUseMailLoginCheck()
     */
    public boolean isUseMailLoginCheck() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#isUseProductRegistration()
     */
    public boolean isUseProductRegistration() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#isAllowDebugMode()
     */
    public boolean isAllowDebugMode() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#isUseDemoProjects()
     */
    public boolean isUseDemoProjects() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#getAdditionalTitle()
     */
    public String getAdditionalTitle() {
        return title;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#setAdditionalTitle(java.lang.String)
     */
    public void setAdditionalTitle(String title) {
        this.title = title;
    }

    public String getInitialWindowPerspectiveId() {
        return "org.talend.rcp.perspective"; //$NON-NLS-1$
    }

    public String getTISProductNameForWelcome() {
        return "Integration Suite Studio"; //$NON-NLS-1$
    }

    public void generateWelcomeHeaderImage() {
        if (PluginChecker.isCoreTISPluginLoaded()) {
            String version = (String) CorePlugin.getDefault().getBundle().getHeaders().get(
                    org.osgi.framework.Constants.BUNDLE_VERSION);
            ICoreTisService service = (ICoreTisService) GlobalServiceRegister.getDefault().getService(ICoreTisService.class);
            service.drawWelcomeLogo(version);
        }
    }
}
