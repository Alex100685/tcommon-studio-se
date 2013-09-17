// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.core.service;

import org.talend.core.IService;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.repository.model.RepositoryNode;

/**
 * created by wchen on 2013-8-16 Detailled comment
 * 
 */
public interface ITransformService extends IService {

    public boolean isTransformNode(RepositoryNode node);

    public boolean isTransformItem(Item item);

    public String getDisplayName(Item item);

    public ERepositoryObjectType getTransformRootType();

}
