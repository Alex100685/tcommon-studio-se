// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//   
// ============================================================================
package org.talend.core.context;

import java.util.Map;

import org.talend.core.model.general.Project;
import org.talend.core.model.properties.User;

/**
 * DOC smallet class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class RepositoryContext {

    private User user;

    private Project project;

    private Map<String, String> fields;

    /**
     * DOC smallet RepositoryContext constructor comment.
     * 
     */
    public RepositoryContext() {
        super();
    }

    /**
     * Getter for user.
     * 
     * @return the user
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Sets the user.
     * 
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter for project.
     * 
     * @return the project
     */
    public Project getProject() {
        return this.project;
    }

    /**
     * Sets the project.
     * 
     * @param project the project to set
     */
    public void setProject(Project project) {
        this.project = project;
    }

    public Map<String, String> getFields() {
        return this.fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.user == null) ? 0 : this.user.hashCode());
        result = prime * result + ((this.project == null) ? 0 : this.project.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RepositoryContext other = (RepositoryContext) obj;
        if (this.user == null) {
            if (other.user != null) {
                return false;
            }
        } else if (!this.user.equals(other.user)) {
            return false;
        }
        if (this.project == null) {
            if (other.project != null) {
                return false;
            }
        } else if (!this.project.equals(other.project)) {
            return false;
        }
        return true;
    }

}
