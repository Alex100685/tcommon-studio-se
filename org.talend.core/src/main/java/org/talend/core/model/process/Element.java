// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the  agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//   
// ============================================================================
package org.talend.core.model.process;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Abstract base class of elements in the model. All elements in the diagram must extends this class <br/>
 * 
 * $Id$
 * 
 */

public abstract class Element implements Cloneable, IElement {

    public static final int ALPHA_VALUE = 50;

    private List<IElementParameter> listParam = new ArrayList<IElementParameter>();

    // property change listeners
    protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        listeners.addPropertyChangeListener(listener);
    }

    protected void firePropertyChange(String prop, Object old, Object newValue) {
        listeners.firePropertyChange(prop, old, newValue);
    }

    protected void fireStructureChange(String prop, Object child) {
        listeners.firePropertyChange(prop, null, child);
    }

    // implemented in order to create listeners field
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        listeners = new PropertyChangeSupport(this);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        listeners.removePropertyChangeListener(l);
    }

    /**
     * Gives the value of the given property.
     * 
     * @param id
     * @return Object
     */
    public Object getPropertyValue(final String id) {
        // if (id.contains(":")) { // look for the parent first, then will retrieve the children
        // StringTokenizer token = new StringTokenizer(id, ":");
        // String parentId = token.nextToken();
        // String childId = token.nextToken();
        // for (int i = 0; i < listParam.size(); i++) {
        // if (listParam.get(i).getName().equals(parentId)) {
        // IElementParameter parent = listParam.get(i);
        // return parent.getChildParameters().get(childId).getValue();
        // }
        // }
        // } else {
        // for (int i = 0; i < listParam.size(); i++) {
        // if (listParam.get(i).getName().equals(id)) {
        // return listParam.get(i).getValue();
        // }
        // }
        // }
        IElementParameter param = this.getElementParameter(id);
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    /**
     * Set the property of the object.
     * 
     * @param id
     * @param value
     */
    public void setPropertyValue(final String id, final Object value) {
        for (int i = 0; i < listParam.size(); i++) {
            if (listParam.get(i).getName().equals(id)) {
                listParam.get(i).setValue(value);
            }
        }
    }

    public void addElementParameter(IElementParameter parameter) {
        listParam.add(parameter);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.ui.editor.IElement#getElementParameters()
     */
    public List<? extends IElementParameter> getElementParameters() {
        return listParam;
    }

    /**
     * The returned list can not be modified.
     * @return
     */
    public List<? extends IElementParameter> getElementParametersWithChildrens() {
        List<IElementParameter> fullListParam = new ArrayList<IElementParameter>(listParam);
        
        for (IElementParameter curParam : listParam) {
            for (String key : curParam.getChildParameters().keySet()) {
                IElementParameter childParam = curParam.getChildParameters().get(key);
                fullListParam.add(childParam);
            }
        }
        return fullListParam;
    }
    
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public void setElementParameters(List<? extends IElementParameter> parameters) {
        this.listParam = (List<IElementParameter>) parameters;
    }

    public IElementParameter getElementParameter(String name) {
        if (name.contains(":")) { // look for the parent first, then will retrieve the children
            StringTokenizer token = new StringTokenizer(name, ":");
            String parentId = token.nextToken();
            String childId = token.nextToken();
            for (int i = 0; i < listParam.size(); i++) {
                if (listParam.get(i).getName().equals(parentId)) {
                    IElementParameter parent = listParam.get(i);
                    return parent.getChildParameters().get(childId);
                }
            }
        } else {
            for (IElementParameter elementParam : listParam) {
                if (elementParam.getName().equals(name)) {
                    return elementParam;
                }
            }
        }

        // if not found, look for the name if it's the name of a children
        // this code is added only for compatibility and will be executed only one time
        // to initialize the child.
        // The parameters name are unique, so we just take the first one.
        for (IElementParameter elementParam : listParam) {
            for (String key : elementParam.getChildParameters().keySet()) {
                IElementParameter param = elementParam.getChildParameters().get(key);
                if (param.getName().equals(name)) {
                    return param;
                }
            }
        }
        return null;
    }

    /**
     * Note that this will return only the first element parameter only.
     * 
     * @param fieldType
     * @return
     */
    public IElementParameter getElementParameterFromField(EParameterFieldType fieldType) {
        for (IElementParameter elementParam : listParam) {
            if (elementParam.getField().equals(fieldType)) {
                return elementParam;
            }
        }
        return null;
    }

    public abstract String getElementName();
}
