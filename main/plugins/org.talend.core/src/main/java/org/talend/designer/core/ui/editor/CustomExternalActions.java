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
package org.talend.designer.core.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;

/**
 * DOC qzhang class global comment. Detailled comment
 */
public abstract class CustomExternalActions extends SelectionAction {

    public static final int INPUT = 0221;

    public static final int OUTPUT = 0223;

    public static final String ELEM_CLASS = "class"; //$NON-NLS-1$

    public static final String ELEM_LABEL = "label"; //$NON-NLS-1$

    public static final String ELEM_ID = "id"; //$NON-NLS-1$

    public static final String EXTENSION_ID = "org.talend.core.component_custom_action"; //$NON-NLS-1$

    protected static IWorkbenchPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
            .getActiveEditor();

    /**
     * DOC qzhang AbstractContextMenuProvider constructor comment.
     */
    public CustomExternalActions() {
        super(part);
    }

    /**
     * qzhang Comment method "getComponentType".
     * 
     * @return
     */
    public abstract int getComponentType();

    /**
     * DOC qzhang Comment method "getInstances".
     * 
     * @return
     */
    public static List<SelectionAction> getInstances(IWorkbenchPart part) {
        CustomExternalActions.part = part;
        List<SelectionAction> actions = new ArrayList<SelectionAction>();
        IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
        IConfigurationElement[] configurationElementsFor = extensionRegistry.getConfigurationElementsFor(EXTENSION_ID);
        for (IConfigurationElement configurationElement : configurationElementsFor) {
            try {
                CustomExternalActions action = (CustomExternalActions) configurationElement
                        .createExecutableExtension(ELEM_CLASS);
                String attribute = configurationElement.getAttribute(ELEM_LABEL);
                action.setText(attribute);
                attribute = configurationElement.getAttribute(ELEM_ID);
                action.setId(attribute);
                actions.add(action);
            } catch (CoreException e) {
                ExceptionHandler.process(e);
            }

        }
        return actions;
    }
}
