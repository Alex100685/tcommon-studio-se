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
package org.talend.core.ui;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.exception.SystemException;
import org.talend.core.IService;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.IProcess;
import org.talend.core.model.properties.Item;
import org.talend.designer.core.model.utils.emf.talendfile.NodeType;

/**
 * hywang class global comment. Detailled comment
 */
public interface IRulesProviderService extends IService {

    public void syncAllRules();

    public void syncRule(Item item) throws SystemException;

    public IFile getRuleFile(Item item, String extension) throws SystemException;

    public IFile getFinalRuleFile(Item item) throws CoreException;

    public void generateFinalRuleFiles(String currentJavaProject, IProcess process) throws PersistenceException, SystemException,
            IOException, CoreException;

    public boolean isRuleComponent(INode node);

    public boolean isRuleComponent(NodeType node);
}
