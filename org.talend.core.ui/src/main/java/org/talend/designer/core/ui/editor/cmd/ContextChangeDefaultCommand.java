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
package org.talend.designer.core.ui.editor.cmd;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.ITDQRepositoryService;
import org.talend.core.model.process.IContext;
import org.talend.core.model.process.IContextManager;
import org.talend.core.runtime.i18n.Messages;
import org.talend.core.ui.context.view.AbstractContextView;

/**
 * Command that will change the default context.
 * 
 * $Id$
 * 
 */
public class ContextChangeDefaultCommand extends Command {

    IContext newDefault;

    IContext oldDefault;

    IContextManager contextManager;

    public ContextChangeDefaultCommand(IContextManager contextManager, IContext newDefault) {
        this.newDefault = newDefault;
        this.contextManager = contextManager;
        this.oldDefault = contextManager.getDefaultContext();
        this.setLabel(Messages.getString("ContextChangeDefaultCommand.label")); //$NON-NLS-1$
    }

    /**
     * qzhang Comment method "refreshContextView".
     */
    private void refreshContextView() {
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        // refresh context view of DI
        IViewPart view = page.findView(AbstractContextView.CTX_ID_DESIGNER);
        if (view instanceof AbstractContextView) {
            ((AbstractContextView) view).updateContextView(true, false);
        }
        // refresh context view of DQ
        if (GlobalServiceRegister.getDefault().isServiceRegistered(ITDQRepositoryService.class)) {
            ITDQRepositoryService tdqRepositoryService = (ITDQRepositoryService) GlobalServiceRegister.getDefault().getService(
                    ITDQRepositoryService.class);
            if (tdqRepositoryService != null) {
                tdqRepositoryService.updateContextView(true, false);
            }
        }
    }

    @Override
    public void execute() {
        contextManager.setDefaultContext(newDefault);
        contextManager.fireContextsChangedEvent();
        refreshContextView();
    }

    @Override
    public void redo() {
        execute();
    }

    @Override
    public void undo() {
        contextManager.setDefaultContext(oldDefault);
        contextManager.fireContextsChangedEvent();
        // refreshContextView();
    }
}
