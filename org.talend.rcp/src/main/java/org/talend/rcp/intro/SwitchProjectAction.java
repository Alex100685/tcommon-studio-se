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
package org.talend.rcp.intro;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.talend.core.CorePlugin;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.designer.business.diagram.custom.IDiagramModelService;
import org.talend.rcp.i18n.Messages;
import org.talend.repository.ui.login.LoginDialog;
import org.talend.repository.ui.views.IRepositoryView;

/**
 * Displays the Login Dialog for choose a project to Open. <br/>
 * 
 * $Id$
 * 
 */
public class SwitchProjectAction extends Action {

    private static final String PERSPECTIVE_ID = "org.talend.rcp.perspective"; //$NON-NLS-1$

    /**
     * Constructs a new SwitchProjectAction.
     */
    public SwitchProjectAction() {
        super(Messages.getString("SwitchProjectAction_actionLabel")); //$NON-NLS-1$
        setEnabled(false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        ProxyRepositoryFactory.getInstance().logOffProject();

        // for bug 7071
        IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (workbenchWindow.getActivePage() != null
                && GlobalServiceRegister.getDefault().isServiceRegistered(IDiagramModelService.class)
                && CorePlugin.getDefault().getDiagramModelService()
                        .isBusinessDiagramEditor(workbenchWindow.getActivePage().getActiveEditor())) {
            IViewReference findViewReference = workbenchWindow.getActivePage().findViewReference(IRepositoryView.VIEW_ID);
            if (findViewReference != null) {
                findViewReference.getView(false).setFocus();
            }
        }

        // The prefered method Actually is a restart :
        PlatformUI.getWorkbench().restart();

        // But the logically Method will be :
        // The Problem is that the RunProcess view is always "No process to run"

        // After loginDialog are open, the Ok Button must to open the SelectedProject.
        // logUserOnProject(new Shell());
        // try {
        // IWorkbench workbench = PlatformUI.getWorkbench();
        // workbench.openWorkbenchWindow(PERSPECTIVE_ID, null);
        //
        // RepositoryContext repositoryContext = (RepositoryContext)
        // Context.getContext().getProperty(Context.REPOSITORY_CONTEXT_KEY);
        //
        // IRepositoryView repositoryView = (IRepositoryView)
        // PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
        // .findView(RepositoryView.VIEW_ID);
        //
        // repositoryView.setRepositoryContext(repositoryContext);
        //
        // RefreshAction refreshAction = new RefreshAction(repositoryView);
        // refreshAction.run();
        //
        // } catch (Exception e) {
        // e.printStackTrace();
        // } finally {
        // for (int i = 1; i <= PlatformUI.getWorkbench().getWorkbenchWindows().length; i++) {
        // PlatformUI.getWorkbench().getWorkbenchWindows()[0].close();
        // }
        // }
    }

    private boolean logUserOnProject(Shell shell) {
        boolean logged = false;
        LoginDialog loginDialog = new LoginDialog(shell);
        logged = loginDialog.open() == LoginDialog.OK;
        return logged;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#setEnabled()
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }
    // setEnabled(CodeGeneratorEmittersPoolFactory.initialized);
}
