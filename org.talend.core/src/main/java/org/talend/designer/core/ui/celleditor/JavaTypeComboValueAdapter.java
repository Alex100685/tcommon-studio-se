// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.core.ui.celleditor;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.talend.commons.ui.swt.tableviewer.behavior.CellEditorValueAdapter;
import org.talend.commons.utils.data.bean.IBeanPropertyAccessors;
import org.talend.core.model.metadata.types.JavaType;
import org.talend.core.model.metadata.types.JavaTypesManager;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 * @param <B> type of bean
 */
public class JavaTypeComboValueAdapter<B> extends CellEditorValueAdapter {

    private JavaType defaultJavaType;

    private IBeanPropertyAccessors<B, Boolean> nullableAccessors;

    /**
     * DOC amaumont JavaTypeComboValueAdapter constructor comment.
     * 
     * @param nullableAccessors
     */
    public JavaTypeComboValueAdapter(JavaType defaultJavaType, IBeanPropertyAccessors<B, Boolean> nullableAccessors) {
        super();
        this.defaultJavaType = defaultJavaType;
        this.nullableAccessors = nullableAccessors;
    }

    public Object getOriginalTypedValue(final CellEditor cellEditor, Object cellEditorTypedValue) {
        JavaType[] javaTypes = JavaTypesManager.getJavaTypes();
        int i = (Integer) cellEditorTypedValue;
        if (i >= 0) {
            return javaTypes[i].getId();
        }
        // else {
        // return null;
        // }
        throw new IllegalStateException("No selection is invalid"); //$NON-NLS-1$
    }

    public Object getCellEditorTypedValue(final CellEditor cellEditor, Object originalTypedValue) {
        String[] items = ((ComboBoxCellEditor) cellEditor).getItems();

        JavaType javaType = JavaTypesManager.getJavaTypeFromId((String) originalTypedValue);

        String label = javaType.getLabel();

        if (originalTypedValue == null && defaultJavaType != null) {
            label = defaultJavaType.getLabel();
        }

        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(label)) {
                return i;
            }
        }
        return -1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.commons.ui.swt.tableviewer.behavior.CellEditorValueAdapter#getColumnText(org.eclipse.jface.viewers.CellEditor,
     * java.lang.Object)
     */
    @Override
    public String getColumnText(CellEditor cellEditor, Object bean, Object originalTypedValue) {
        JavaType javaType = getJavaType(originalTypedValue);

        Class primitiveClass = javaType.getPrimitiveClass();
        Boolean nullable = nullableAccessors.get((B) bean);
        String displayedValue = null;
        if (primitiveClass != null && !nullable.equals(Boolean.TRUE)) {
            displayedValue = primitiveClass.getSimpleName();
        } else if (originalTypedValue.equals(JavaTypesManager.DIRECTORY.getId())
                || originalTypedValue.equals(JavaTypesManager.FILE.getId())
                || originalTypedValue.equals(JavaTypesManager.VALUE_LIST.getId())) {
            displayedValue = javaType.getLabel();
        } else {
            displayedValue = javaType.getNullableClass().getSimpleName();
        }
        displayedValue = getDefaultDisplayValue(displayedValue);
        return displayedValue;
    }

    protected JavaType getJavaType(Object originalTypedValue) {
        return JavaTypesManager.getJavaTypeFromId((String) originalTypedValue);
    }

    protected String getDefaultDisplayValue(String displayedValue) {
        if (displayedValue == null && defaultJavaType != null) {
            displayedValue = defaultJavaType.getLabel();
        }
        return displayedValue;
    }
};
