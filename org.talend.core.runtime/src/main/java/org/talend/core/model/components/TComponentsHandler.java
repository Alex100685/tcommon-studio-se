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
package org.talend.core.model.components;

import java.util.List;
import java.util.Set;

/**
 * Created by Marvin Wang on Jan. 10, 2012 for Handling component drawer in palette.
 */
public interface TComponentsHandler {

    /**
     * Filters the components from the given components, returns the components that what you want to show in palette.
     * Added by Marvin Wang on Jan. 10, 2012.
     * 
     * @param allComponents
     * @return
     */
    List<IComponent> filterComponents(Set<IComponent> allComponents);

    /**
     * Sorts the filtered components as you want, if you would not like to sort, just return the given components. Added
     * by Marvin Wang on Jan. 10, 2012.
     * 
     * @param filteredComponents
     * @return
     */
    List<IComponent> sortComponents(List<IComponent> filteredComponents);
}
