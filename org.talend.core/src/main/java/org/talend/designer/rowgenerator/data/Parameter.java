// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006-2007 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.designer.rowgenerator.data;

import java.util.List;

import org.talend.core.i18n.Messages;
import org.talend.expressionbuilder.test.shadow.Variable;

/**
 * class global comment. Detailled comment <br/> $Id: Parameter.java,v 1.4 2007/02/02 03:04:21 pub Exp $
 */
public abstract class Parameter {

    protected List<Variable> variables;

    /**
     * @uml.property name="name"
     */
    protected String name = ""; //$NON-NLS-1$

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
     * @uml.property name="value"
     */
    protected String value = ""; //$NON-NLS-1$

    /**
     * Getter of the property <tt>value</tt>.
     * 
     * @return Returns the value.
     * @uml.property name="value"
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Setter of the property <tt>value</tt>.
     * 
     * @param value The value to set.
     * @uml.property name="value"
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @uml.property name="comment"
     */
    protected String comment = ""; //$NON-NLS-1$

    /**
     * Getter of the property <tt>comment</tt>.
     * 
     * @return Returns the comment.
     * @uml.property name="comment"
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Setter of the property <tt>comment</tt>.
     * 
     * @param comment The comment to set.
     * @uml.property name="comment"
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @uml.property name="type"
     */
    protected ParameterType type;

    /**
     * Getter of the property <tt>type</tt>.
     * 
     * @return Returns the type.
     * @uml.property name="type"
     */
    public ParameterType getType() {
        return this.type;
    }

    /**
     * Setter of the property <tt>type</tt>.
     * 
     * @param type The type to set.
     * @uml.property name="type"
     */
    public void setType(ParameterType type) {
        this.type = type;
    }

    /**
     * Sets the properties from the input String.
     */
    public void parseProperties(String str) {

    }

    /**
     * qzhang Comment method "sameParameterAs".
     * 
     * @param obj
     * @return
     */
    public abstract boolean sameParameterAs(Parameter obj);

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Messages.getString("Parameter.Name") + name).append(
                Messages.getString("Parameter.Value") + this.getValue()).append(
                Messages.getString("Parameter.Comment") + this.getComment()).append(
                Messages.getString("Parameter.Type") + this.getType());

        return sb.toString();
    }

    /**
     * Getter for variables.
     * 
     * @return the variables
     */
    public List<Variable> getVariables() {
        return this.variables;
    }

    /**
     * Sets the variables.
     * 
     * @param variables the variables to set
     */
    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }
}
