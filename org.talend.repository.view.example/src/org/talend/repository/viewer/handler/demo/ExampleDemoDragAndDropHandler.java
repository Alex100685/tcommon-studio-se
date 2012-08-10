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
package org.talend.repository.viewer.handler.demo;

import java.util.List;

import org.talend.core.model.components.IComponent;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.process.INode;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.utils.IComponentName;
import org.talend.core.model.utils.IDragAndDropServiceHandler;
import org.talend.repository.model.ExampleDemoRepositoryNodeType;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.demo.ExampleDemoConnection;

/**
 * DOC ggu class global comment. Detailled comment <br/>
 * 
 * $Id: talend.epf 55206 2011-02-15 17:32:14Z mhirt $
 * 
 * NOTE: this calss is not finished, because need related some components.
 */
public class ExampleDemoDragAndDropHandler implements IDragAndDropServiceHandler {

    /**
     * DOC ggu ExampleDemoDragAndDropHandler constructor comment.
     */
    public ExampleDemoDragAndDropHandler() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.core.model.utils.IDragAndDropServiceHandler#canHandle(org.talend.core.model.metadata.builder.connection
     * .Connection)
     */
    @Override
    public boolean canHandle(Connection connection) {
        return connection instanceof ExampleDemoConnection;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.core.model.utils.IDragAndDropServiceHandler#getComponentValue(org.talend.core.model.metadata.builder
     * .connection.Connection, java.lang.String)
     */
    @Override
    public String getComponentValue(Connection connection, String value) {
        if (value != null && canHandle(connection)) {
            ExampleDemoConnection demoConn = (ExampleDemoConnection) connection;
            if ("TYPE".equals(value)) {
                return demoConn.getType();
            } else if ("VALID".equals(value)) {
                return Boolean.toString(demoConn.isValid());
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.core.model.utils.IDragAndDropServiceHandler#filterNeededComponents(org.talend.core.model.properties
     * .Item, org.talend.repository.model.RepositoryNode, org.talend.core.model.repository.ERepositoryObjectType)
     */
    @Override
    public List<IComponent> filterNeededComponents(Item item, RepositoryNode seletetedNode, ERepositoryObjectType type) {
        // PTODO find the matched components with the item.
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.core.model.utils.IDragAndDropServiceHandler#getCorrespondingComponentName(org.talend.core.model.properties
     * .Item, org.talend.core.model.repository.ERepositoryObjectType)
     */
    @Override
    public IComponentName getCorrespondingComponentName(Item item, ERepositoryObjectType type) {
        // PTODO return the default component
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.core.model.utils.IDragAndDropServiceHandler#setComponentValue(org.talend.core.model.metadata.builder
     * .connection.Connection, org.talend.core.model.process.INode, java.lang.String)
     */
    @Override
    public void setComponentValue(Connection connection, INode node, String repositoryValue) {
        if (node != null && canHandle(connection)) {
            ExampleDemoConnection demoConn = (ExampleDemoConnection) connection;
            // PTODO get the values from node, and set to the matched attributes(repositoryValue) of connection
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.utils.IDragAndDropServiceHandler#getType(java.lang.String)
     */
    @Override
    public ERepositoryObjectType getType(String repositoryType) {
        // PTODO accordding to the checked type to return real object type.
        if ("EXAMPLE_DEMO".equals(repositoryType)) {
            return ExampleDemoRepositoryNodeType.repositoryExampleDemoType;
        }
        return null;
    }

}
