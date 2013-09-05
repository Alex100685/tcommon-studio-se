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
package org.talend.repository.items.importexport.handlers.model.internal;

import org.talend.repository.items.importexport.handlers.imports.IImportHandler;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class ImportProviderRegistry extends BasicRegistry {

    private IImportHandler importHandler;

    public ImportProviderRegistry(String bundleId, String id) {
        super(bundleId, id);
    }

    /**
     * Getter for importHandler.
     * 
     * @return the importHandler
     */
    public IImportHandler getImportHandler() {
        return this.importHandler;
    }

    /**
     * Sets the importHandler.
     * 
     * @param importHandler the importHandler to set
     */
    public void setImportHandler(IImportHandler handler) {
        this.importHandler = handler;
    }

}
