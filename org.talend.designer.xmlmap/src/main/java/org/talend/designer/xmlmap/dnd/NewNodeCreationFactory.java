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
package org.talend.designer.xmlmap.dnd;

import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.CreationFactory;

/**
 * wchen class global comment. Detailled comment
 */
public class NewNodeCreationFactory implements CreationFactory {

    private Object toTransfer;

    public NewNodeCreationFactory(Object toTransfer) {
        this.toTransfer = toTransfer;
    }

    public Object getNewObject() {
        return toTransfer;
    }

    public Object getObjectType() {
        return RequestConstants.REQ_CREATE;
    }

}
