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
package org.talend.core.repository.hadoop.custom;

/**
 * created by ycbai on 2013-3-13 Detailled comment
 * 
 */
public enum ECustomVersionGroup {

    COMMON,

    HIVE,

    HBASE,

    ALL;

    public String getName() {
        return name();
    }

}
