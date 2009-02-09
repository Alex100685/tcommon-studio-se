// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.migrationtool.model.summary;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.talend.core.CorePlugin;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.model.migration.IMigrationToolService;
import org.talend.migrationtool.MigrationToolService;

/**
 * DOC smallet class global comment. Detailled comment <br/>
 * 
 */
public class AlertUserOnLogin implements IStartup {

    public static boolean executed;

    private boolean startUnderPluginModel;

    public void startup(boolean underPluginModel) {
        startUnderPluginModel = underPluginModel;
        earlyStartup();
    }

    public void earlyStartup() {
        if (!startUnderPluginModel && !CorePlugin.getDefault().getRepositoryService().isRCPMode()) {
            return;
        }
        final IWorkbench workbench = PlatformUI.getWorkbench();
        workbench.getDisplay().asyncExec(new Runnable() {

            public void run() {
                IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
                if (window != null) {
                    IMigrationToolService service = (IMigrationToolService) GlobalServiceRegister.getDefault().getService(
                            IMigrationToolService.class);

                    MigrationToolService m = (MigrationToolService) service;

                    if (!m.doneThisSession.isEmpty()) {
                        SummaryDialog loginDialog = new SummaryDialog(new Shell(), m.doneThisSession);
                        loginDialog.open();
                    }
                }
            }
        });

        executed = true;
    }
}
