// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.viewer.content.example;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonContentProvider;

public class ExampleCompContentProvider implements ICommonContentProvider {

    static BusinessModelsContainer theContainer;

    static public class BusinessModelsContainer {

        private final String label;

        public BusinessModelsContainer(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    @Override
    public Object[] getElements(Object inputElement) {// only return the container for Jobs
        return new Object[] { theContainer };
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof BusinessModelsContainer) {
            return new Object[] { "bm1", "bm2", "bm3" }; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
        } else {
            return new Object[0];
        }
    }

    @Override
    public Object getParent(Object element) {
        return null;
    }

    @Override
    public boolean hasChildren(Object element) {
        return (element instanceof BusinessModelsContainer);
    }

    @Override
    public void dispose() {
        // do nothing
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        // do nothing
    }

    @Override
    public void restoreState(IMemento aMemento) {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveState(IMemento aMemento) {
        // TODO Auto-generated method stub

    }

    @Override
    public void init(ICommonContentExtensionSite aConfig) {
        theContainer = new BusinessModelsContainer(aConfig.getExtension().getDescriptor().getName());
    } // implements ITreeContentProvider {
}
