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
package org.talend.repository.viewer.content.metadata;

import org.talend.repository.model.RepositoryNode;
import org.talend.repository.tester.NodeTester;
import org.talend.repository.viewer.content.FolderListenerSingleTopContentProvider;

public abstract class AbstractMetadataContentProvider extends FolderListenerSingleTopContentProvider {

    NodeTester nodeTester = new NodeTester();

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.viewer.content.SingleTopLevelContentProvider#isRootNodeType(java.lang.Object)
     */
    @Override
    protected boolean isRootNodeType(Object element) {
        if (element instanceof RepositoryNode) {
            return nodeTester.isMetadataTopNode((RepositoryNode) element);
        } else {
            return false;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.viewer.content.SingleTopLevelContentProvider#extractPotentialRootNode(java.lang.Object)
     */
    @Override
    protected RepositoryNode extractPotentialRootNode(Object element) {
        return (RepositoryNode) element;
    }

}
