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
package org.talend.designer.rowgenerator.data;

import java.util.ArrayList;
import java.util.List;

import org.talend.core.i18n.Messages;

/**
 * class global comment. Detailled comment <br/> $Id: Function.java,v 1.10 2007/02/02 08:07:02 pub Exp $
 */
public class Function implements Cloneable {

    private String category;

    private TalendType talendType;

    private boolean isUserDefined;

    /**
     * Getter for category.
     * 
     * @return the category
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Sets the category.
     * 
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    @Override
    public Object clone() {
        Function fun = new Function();
        try {
            fun.setName(getName());
            fun.setDescription(getDescription());
            fun.setPreview(getPreview());
            List<Parameter> parameters1 = new ArrayList<Parameter>();
            for (Parameter para : (List<Parameter>) getParameters()) {
                if (para instanceof StringParameter) {
                    StringParameter p0 = (StringParameter) para;
                    StringParameter p1 = new StringParameter();
                    p1.setComment(p0.getComment());
                    p1.setName(p0.getName());
                    p1.setType(p0.getType());
                    p1.setValue(p0.getValue());
                    parameters1.add(p1);
                }
                if (para instanceof IntParameter) {
                    IntParameter p0 = (IntParameter) para;
                    IntParameter p1 = new IntParameter();
                    p1.setComment(p0.getComment());
                    p1.setName(p0.getName());
                    p1.setType(p0.getType());
                    p1.setValue(p0.getValue());
                    parameters1.add(p1);
                }
                if (para instanceof DoubleParameter) {
                    DoubleParameter p0 = (DoubleParameter) para;
                    DoubleParameter p1 = new DoubleParameter();
                    p1.setComment(p0.getComment());
                    p1.setName(p0.getName());
                    p1.setType(p0.getType());
                    p1.setValue(p0.getValue());
                    parameters1.add(p1);
                }

                if (para instanceof ListParameter) {
                    ListParameter p0 = (ListParameter) para;
                    ListParameter p1 = new ListParameter();
                    p1.setComment(p0.getComment());
                    p1.setName(p0.getName());
                    p1.setType(p0.getType());
                    p1.setValue(p0.getValue());
                    p1.setValues(p0.getValues());
                    parameters1.add(p1);
                }

                if (para instanceof ObjectParameter) {
                    ObjectParameter p0 = (ObjectParameter) para;
                    ObjectParameter p1 = new ObjectParameter();
                    p1.setComment(p0.getComment());
                    p1.setName(p0.getName());
                    p1.setType(p0.getType());
                    p1.setValue(p0.getValue());
                    parameters1.add(p1);
                }

                if (para instanceof BooleanParameter) {
                    BooleanParameter p0 = (BooleanParameter) para;
                    BooleanParameter p1 = new BooleanParameter();
                    p1.setComment(p0.getComment());
                    p1.setName(p0.getName());
                    p1.setType(p0.getType());
                    p1.setValue(p0.getValue());
                    parameters1.add(p1);
                }

                if (para instanceof LongParameter) {
                    LongParameter p0 = (LongParameter) para;
                    LongParameter p1 = new LongParameter();
                    p1.setComment(p0.getComment());
                    p1.setName(p0.getName());
                    p1.setType(p0.getType());
                    p1.setValue(p0.getValue());
                    parameters1.add(p1);
                }

                if (para instanceof DateParameter) {
                    DateParameter p0 = (DateParameter) para;
                    DateParameter p1 = new DateParameter();
                    p1.setComment(p0.getComment());
                    p1.setName(p0.getName());
                    p1.setType(p0.getType());
                    p1.setValue(p0.getValue());
                    parameters1.add(p1);
                }
                if (para instanceof CharParameter) {
                    CharParameter p0 = (CharParameter) para;
                    CharParameter p1 = new CharParameter();
                    p1.setComment(p0.getComment());
                    p1.setName(p0.getName());
                    p1.setType(p0.getType());
                    p1.setValue(p0.getValue());
                    parameters1.add(p1);
                }
            }
            fun.setParameters(parameters1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fun;
    }

    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public Function clone(String[] parameters) {
        Function function = (Function) clone();
        if (parameters != null) {
            for (int i = 0; i < function.getParameters().size(); i++) {
                Parameter pa = (Parameter) function.getParameters().get(i);
                pa.setValue(parameters[i]);
            }
        }
        return function;
    }

    public boolean sameFunctionAs(Function fun) {
        if (this == fun) {
            return true;
        }
        if (fun == null) {
            return false;
        }
        if (getClass() != fun.getClass()) {
            return false;
        }
        if (this.name == null) {
            if (fun.name != null) {
                return false;
            }
        } else if (!this.name.equals(fun.name)) {
            return false;
        }
        if (this.description == null) {
            if (fun.description != null) {
                return false;
            }
        } else if (!this.description.equals(fun.description)) {
            return false;
        }
        if (this.preview == null) {
            if (fun.preview != null) {
                return false;
            }
        } else if (!this.preview.equals(fun.preview)) {
            return false;
        }

        if (this.parameters == null) {
            if (fun.parameters != null) {
                return false;
            }
        } else if (!sameParameters(fun)) {
            return false;
        }
        return true;
    }

    /**
     * qzhang Comment method "sameParameters".
     * 
     * @param fun
     * @return
     */
    private boolean sameParameters(Function fun) {
        if (this.parameters.size() != fun.parameters.size()) {
            return false;
        }
        for (int i = 0; i < this.parameters.size(); i++) {
            final Parameter parameter = (Parameter) this.parameters.get(i);
            final Parameter object = (Parameter) fun.parameters.get(i);
            if (!(parameter).sameParameterAs(object)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @uml.property name="name"
     */
    private String name = ""; //$NON-NLS-1$

    /**
     * Getter of the property <tt>name</tt>.
     * 
     * @return Returns the name.
     * @uml.property name="name"
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter of the property <tt>name</tt>.
     * 
     * @param name The name to set.
     * @uml.property name="name"
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @uml.property name="preview"
     */
    private String preview = ""; //$NON-NLS-1$

    /**
     * Getter of the property <tt>preview</tt>.
     * 
     * @return Returns the preview.
     * @uml.property name="preview"
     */
    public String getPreview() {
        return this.preview;
    }

    /**
     * Setter of the property <tt>preview</tt>.
     * 
     * @param preview The preview to set.
     * @uml.property name="preview"
     */
    public void setPreview(String preview) {
        this.preview = preview;
    }

    /**
     * @uml.property name="description"
     */
    private String description = ""; //$NON-NLS-1$

    /**
     * Getter of the property <tt>description</tt>.
     * 
     * @return Returns the description.
     * @uml.property name="description"
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Setter of the property <tt>description</tt>.
     * 
     * @param description The description to set.
     * @uml.property name="description"
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @uml.property name="parameters"
     * @uml.associationEnd multiplicity="(0 -1)" inverse="function:org.talend.designer.rowgenerator.data.Parameter"
     */
    private List parameters = new java.util.ArrayList();

    /**
     * Getter of the property <tt>parameters</tt>.
     * 
     * @return Returns the parameters.
     * @uml.property name="parameters"
     */
    public List getParameters() {
        return this.parameters;
    }

    /**
     * Returns all elements of this collection in an array.
     * 
     * @return an array containing all of the elements in this collection
     * @see java.util.Collection#toArray()
     * @uml.property name="parameters"
     */
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public Parameter[] parametersToArray() {
        return (Parameter[]) this.parameters.toArray(new Parameter[this.parameters.size()]);
    }

    /**
     * Setter of the property <tt>parameters</tt>.
     * 
     * @param parameters the parameters to set.
     * @uml.property name="parameters"
     */
    public void setParameters(List parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Messages.getString("Function.FunctionName", name))
                .append("   " + this.getDescription()).append("\n").append("    "); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        Parameter[] p = this.parametersToArray();

        for (int i = 0; i < p.length; i++) {
            Parameter para = p[i];
            sb.append(para).append("\n").append("    "); //$NON-NLS-1$ //$NON-NLS-2$
        }
        return sb.toString();
    }

    /**
     * Getter for talendType.
     * 
     * @return the talendType
     */
    public TalendType getTalendType() {
        return this.talendType;
    }

    /**
     * Sets the talendType.
     * 
     * @param talendType the talendType to set
     */
    public void setTalendType(TalendType talendType) {
        this.talendType = talendType;
    }

    public boolean isUserDefined() {
        return this.isUserDefined;
    }

    public void setUserDefined(boolean isUserDefined) {
        this.isUserDefined = isUserDefined;
    }
}
