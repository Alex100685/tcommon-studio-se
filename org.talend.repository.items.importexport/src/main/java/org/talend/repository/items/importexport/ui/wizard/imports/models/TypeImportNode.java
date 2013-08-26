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
package org.talend.repository.items.importexport.ui.wizard.imports.models;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.RepositoryNodeProviderRegistryReader;
import org.talend.core.ui.images.CoreImageProvider;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class TypeImportNode extends FolderImportNode {

    private Map<ERepositoryObjectType, TypeImportNode> typeNodesChildrenMap = new HashMap<ERepositoryObjectType, TypeImportNode>();

    private ERepositoryObjectType type;

    public TypeImportNode(ERepositoryObjectType type) {
        super(type.getFolder());
        this.type = type;
    }

    public ERepositoryObjectType getType() {
        return this.type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.items.importexport.ui.wizard.imports.models.ImportNode#getDisplayLabel()
     */
    @Override
    public String getDisplayLabel() {
        return type.getLabel();
    }

    @Override
    public Image getImage() {
        Image image = RepositoryNodeProviderRegistryReader.getInstance().getImage(type);
        if (image == null) {
            image = CoreImageProvider.getImage(type);
        }
        return image;
    }

    public Map<ERepositoryObjectType, TypeImportNode> getTypeNodesChildrenMap() {
        return this.typeNodesChildrenMap;
    }

}
