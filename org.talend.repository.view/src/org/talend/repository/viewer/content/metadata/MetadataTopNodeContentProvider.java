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

import org.talend.repository.model.ProjectRepositoryNode;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.viewer.content.ProjectRepoDirectChildrenNodeContentProvider;

public class MetadataTopNodeContentProvider extends ProjectRepoDirectChildrenNodeContentProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.viewer.content.ProjectRepoChildrenNodeContentProvider#getTopLevelNodeFromProjectRepositoryNode
     * (org.talend.repository.model.ProjectRepositoryNode)
     */
    @Override
    protected RepositoryNode getTopLevelNodeFromProjectRepositoryNode(ProjectRepositoryNode projectRepositoryNode) {
        return projectRepositoryNode.getMetadataNode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.viewer.content.LegacyRepositoryContentProvider#getRepositoryNodeChildren(java.lang.Object,
     * org.talend.repository.model.RepositoryNode)
     */
    @Override
    protected Object[] getRepositoryNodeChildren(RepositoryNode repositoryNode) {
        return NO_CHILDREN;
    }

}
