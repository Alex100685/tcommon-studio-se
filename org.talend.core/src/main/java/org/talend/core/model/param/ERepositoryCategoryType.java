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
package org.talend.core.model.param;

/**
 * cli class global comment. Detailled comment
 */
public enum ERepositoryCategoryType {
    DELIMITED,
    POSITIONAL,
    REGEX,
    XML,
    LDIF,
    EXCEL,
    GENERIC,
    LDAP,
    WSDL,
    SALESFORCE,
    DATABASE,
    SAP,
    EBCDIC,
    HL7,
    MDM,
    RULE,
    CDC(DATABASE),
    HEADERFOOTER,
    //
    ;

    private ERepositoryCategoryType parent;

    ERepositoryCategoryType() {
        this(null);
    }

    ERepositoryCategoryType(ERepositoryCategoryType parent) {
        this.parent = parent;
    }

    public String getName() {
        if (parent != null) {
            return parent.getName() + ":" + name(); //$NON-NLS-1$
        }
        return name();
    }
}
