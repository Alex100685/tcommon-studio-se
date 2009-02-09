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
package org.talend.core.ui.metadata.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.commands.Command;
import org.talend.core.CorePlugin;
import org.talend.core.model.metadata.IEbcdicConstant;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.MetadataTool;
import org.talend.core.model.metadata.MultiSchemasUtil;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.INode;

/**
 * nrousseau class global comment. Detailled comment
 */
public class RepositoryChangeMetadataForEBCDICCommand extends Command {

    private INode node;

    private final String propName;

    private final Object oldPropValue, newPropValue;

    private IMetadataTable newOutputMetadata, oldOutputMetadata;

    private Integer index;

    /**
     * 
     * nrousseau RepositoryChangeMetadataForEBCDICCommand constructor comment.
     * 
     * only for DND
     */
    public RepositoryChangeMetadataForEBCDICCommand(INode node, String propName, Object propValue,
            IMetadataTable newOutputMetadata) {
        this(node, propName, propValue, null, newOutputMetadata, null, null);
    }

    public RepositoryChangeMetadataForEBCDICCommand(INode node, String propName, Object newPropValue,
            IMetadataTable newOutputMetadata, int index) {
        this(node, propName, newPropValue, null, newOutputMetadata, null, index);
    }

    public RepositoryChangeMetadataForEBCDICCommand(INode node, String propName, Object newPropValue, Object oldPropValue,
            IMetadataTable newOutputMetadata, IMetadataTable oldOutputMetadata, int index) {
        this(node, propName, newPropValue, oldPropValue, newOutputMetadata, oldOutputMetadata, new Integer(index));
    }

    private RepositoryChangeMetadataForEBCDICCommand(INode node, String propName, Object newPropValue, Object oldPropValue,
            IMetadataTable newOutputMetadata, IMetadataTable oldOutputMetadata, Integer index) {
        super();
        this.node = node;
        this.propName = propName;
        this.newPropValue = newPropValue;
        this.newOutputMetadata = newOutputMetadata.clone();
        this.index = index;
        this.oldPropValue = oldPropValue;
        this.oldOutputMetadata = oldOutputMetadata;
    }

    @Override
    public void execute() {
        if (node == null) {
            return;
        }
        IElementParameter schemasTableParam = node.getElementParameter(propName);
        if (schemasTableParam != null) {

            List<Map<String, Object>> paramValues = (List<Map<String, Object>>) schemasTableParam.getValue();
            if (paramValues == null) {
                paramValues = new ArrayList<Map<String, Object>>();
                schemasTableParam.setValue(paramValues);
            }
            //
            Map<String, Object> valueMap = null;
            if (index != null) {
                if (paramValues.size() > 0) {
                    valueMap = paramValues.get(index.intValue());
                }

                setLineValue(paramValues, valueMap);

            } else { // for dnd
                if (paramValues.size() > 0) {
                    for (Map<String, Object> line : paramValues) {
                        String schemaName = (String) line.get(IEbcdicConstant.FIELD_SCHEMA);
                        if (schemaName != null && schemaName.equals(newPropValue)) {
                            IMetadataTable table = MetadataTool.getMetadataTableFromNode(node, (String) newPropValue);
                            if (table != null) {
                                MetadataTool.copyTable(newOutputMetadata, table);
                            }

                        }
                    }
                } else {
                    // create new line
                    setLineValue(paramValues, valueMap);
                }

            }

        }
    }

    private void setLineValue(List<Map<String, Object>> paramValues, Map<String, Object> valueMap) {
        //
        if (valueMap == null) {
            valueMap = new HashMap<String, Object>();
            paramValues.add(valueMap);
        }

        valueMap.put(IEbcdicConstant.FIELD_SCHEMA, newPropValue);
        // if no value, set default field value
        Object fieldValue = valueMap.get(IEbcdicConstant.FIELD_CODE);
        if (fieldValue == null || "".equals(fieldValue)) { //$NON-NLS-1$
            valueMap.put(IEbcdicConstant.FIELD_CODE, newOutputMetadata.getLabel());
        }
        valueMap.put(IEbcdicConstant.FIELD_SCHEMA + IEbcdicConstant.REF_TYPE, IEbcdicConstant.REF_ATTR_REPOSITORY);

        if (oldOutputMetadata != null) {
            CorePlugin.getDefault().getDesignerCoreService().removeConnection(node, oldOutputMetadata.getTableName());
            node.getMetadataList().remove(oldOutputMetadata);
        }

        String uinqueTableName = node.getProcess().generateUniqueConnectionName(
                MultiSchemasUtil.getConnectionBaseName((String) newPropValue));
        newOutputMetadata.setLabel((String) newPropValue);
        newOutputMetadata.setTableName(uinqueTableName);

        node.getProcess().addUniqueConnectionName(uinqueTableName);
        node.getMetadataList().add(newOutputMetadata);
    }

    @Override
    public void undo() {
        // TODO
    }

}
