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
package org.talend.metadata.managment.ui;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;
import org.talend.core.service.IMetadataManagmentUiService;
import org.talend.metadata.managment.ui.i18n.Messages;
import org.talend.repository.ui.dialog.OpenXSDFileDialog;

/**
 * 
 * ggu class global comment. Detailled comment
 */
public class MetadataManagmentUiService implements IMetadataManagmentUiService {

    public String getAndOpenXSDFileDialog(Path initPath) {
        OpenXSDFileDialog openXSDFileDialog = new OpenXSDFileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getShell());
        openXSDFileDialog.setTitle(Messages.getString("RepositoryToComponentProperty.xmlFileSelection")); //$NON-NLS-1$
        openXSDFileDialog.setPath(initPath);
        int dialogValue = openXSDFileDialog.open();
        if (dialogValue == Window.OK) {
            return openXSDFileDialog.getNewValue();
        }
        return null; // don't set
    }

}
