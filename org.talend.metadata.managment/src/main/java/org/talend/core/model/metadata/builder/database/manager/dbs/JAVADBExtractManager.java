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
package org.talend.core.model.metadata.builder.database.manager.dbs;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.talend.core.database.EDatabaseTypeName;
import org.talend.core.model.metadata.IMetadataConnection;
import org.talend.core.model.metadata.builder.database.DriverShim;
import org.talend.core.model.metadata.builder.database.manager.ExtractManager;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class JAVADBExtractManager extends ExtractManager {

    public JAVADBExtractManager(EDatabaseTypeName dbType) {
        super(dbType);
    }

    @Override
    public boolean closeConnect(IMetadataConnection metadataConnection, DriverShim wapperDriver) {
        boolean closed = super.closeConnect(metadataConnection, wapperDriver);
        if (!closed) {
            closeConnectForDerby(wapperDriver);
            closed = true;
        }
        return closed;

    }

    @Override
    public String getTableComment(IMetadataConnection metadataConnection, ResultSet resultSet, String nameKey)
            throws SQLException {
        if (metadataConnection != null
                && EDatabaseTypeName.JAVADB_EMBEDED.getDisplayName().equals(metadataConnection.getDbType())) {
            return EMPTY; // no commit for derby
        }
        return super.getTableComment(metadataConnection, resultSet, nameKey);
    }
}
