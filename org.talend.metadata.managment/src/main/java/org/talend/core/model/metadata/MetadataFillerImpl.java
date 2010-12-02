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
package org.talend.core.model.metadata;

import java.sql.DatabaseMetaData;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.talend.core.database.EDatabaseTypeName;
import org.talend.core.database.conn.version.EDatabaseVersion4Drivers;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.builder.util.EDataBaseType;
import org.talend.core.model.metadata.builder.util.MetadataConnectionUtils;
import org.talend.cwm.helper.ConnectionHelper;
import org.talend.cwm.relational.TdColumn;
import org.talend.cwm.relational.TdTable;
import org.talend.cwm.relational.TdView;
import org.talend.mdm.webservice.WSPing;
import org.talend.mdm.webservice.XtentisBindingStub;
import org.talend.utils.sugars.ReturnCode;
import orgomg.cwm.objectmodel.core.ModelElement;
import orgomg.cwm.objectmodel.core.Package;
import orgomg.cwm.resource.relational.Catalog;
import orgomg.cwm.resource.relational.ColumnSet;


/**
 * DOC zshen  class global comment. Detailled comment
 */
public abstract class MetadataFillerImpl implements IMetadataFiller {

    private static Logger log = Logger.getLogger(MetadataFillFactory.class);



    private boolean isLinked = true;

    /*
     * (non-Javadoc)
     * 
     * @seeorg.talend.core.model.metadata.IMetadataFill#fillCatalogs(org.talend.core.model.metadata.builder.connection.
     * Connection, org.talend.designer.business.model.business.DatabaseMetadata, java.util.List)
     */
    public List<Catalog> fillCatalogs(Connection dbConn, DatabaseMetaData dbJDBCMetadata, List<String> catalogFilter) {
        return null;
    }



    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.core.model.metadata.IMetadataFill#fillSchemas(org.talend.core.model.metadata.builder.connection.Connection
     * , org.talend.designer.business.model.business.DatabaseMetadata, java.util.List)
     */
    public List<Package> fillSchemas(Connection dbConn, DatabaseMetaData dbJDBCMetadata, List<String> schemaFilter) {
        return null;
    }



    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.metadata.IMetadataFiller#fillTables(java.lang.Package, java.sql.DatabaseMetaData,
     * java.util.List, java.lang.String)
     */
    public List<TdTable> fillTables(Package pack, DatabaseMetaData dbJDBCMetadata, List<String> tableFilter, String tablePattern,
            String[] tableType) {
        return null;
    }

    /*
     * (non-Jsdoc)
     * 
     * @see
     * org.talend.core.model.metadata.IMetadataFiller#fillMetadataParams(org.talend.core.model.metadata.IMetadataConnection
     * , org.talend.core.model.metadata.builder.connection.Connection)
     */
    protected void fillMetadataParams(IMetadataConnection metadataBean, Connection connection) {
        if (metadataBean == null || connection == null) {
            return;
        }
        // set Url
        ConnectionHelper.setURL(connection, metadataBean.getUrl());
        // set Password
        ConnectionHelper.setPassword(connection, metadataBean.getPassword());
        // set userName
        ConnectionHelper.setUsername(connection, metadataBean.getUsername());
        // set serverName
        ConnectionHelper.setServerName(connection, metadataBean.getServerName());
        // set port
        ConnectionHelper.setPort(connection, metadataBean.getPort());
        // set sid
        ConnectionHelper.setSID(connection, metadataBean.getDbName());
        // status
        String status = metadataBean.getStatus();
        ConnectionHelper.setDevStatus(status, connection);
        // version
        String version = metadataBean.getVersion();
        ConnectionHelper.setVersion(version, connection);
        // purpose
        String purpose = metadataBean.getPurpose();
        ConnectionHelper.setPurpose(purpose, connection);
        // Description
        String description = metadataBean.getPurpose();
        ConnectionHelper.setDescription(description, connection);
        // author
        String author = metadataBean.getAuthor();
        ConnectionHelper.setAuthor(author, connection);

        // otherParameter
        String otherParameter = metadataBean.getOtherParameter();
        ConnectionHelper.setOtherParameter(otherParameter, connection);
        // retrieveAllMetadata
        boolean retrieveAllMetadata = metadataBean.isRetrieveAllMetadata();
        ConnectionHelper.setRetrieveAllMetadata(retrieveAllMetadata, connection);

    }



    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.core.model.metadata.IMetadataFiller#fillUIConnParams(org.talend.core.model.metadata.IMetadataConnection
     * , org.talend.core.model.metadata.builder.connection.Connection)
     */
    public Connection fillUIConnParams(IMetadataConnection metadataBean, Connection connection) {
        if (connection == null || metadataBean == null) {
            return null;
        }
        ReturnCode rc = checkConnection(metadataBean);

        if (!rc.isOk()) {
            return null;
            // need a dialog to show the error.
        }
        this.fillMetadataParams(metadataBean, connection);
        return connection;
    }



    /*
     * @see org.talend.core.model.metadata.IMetadataFill#fillUIParams(java.util.Map)
     */
    public IMetadataConnection fillUIParams(Map<String, String> paramMap) {
        if (paramMap == null) {
            return null;
        }
        IMetadataConnection metadataConnection = new MetadataConnection();
        Iterator<String> iter = paramMap.keySet().iterator();
        while (iter.hasNext()) {
            String paramKey = iter.next();
            String ParamValue = paramMap.get(paramKey);
            if ("driverPath".equalsIgnoreCase(paramKey)) {
                metadataConnection.setDriverJarPath(ParamValue);
            } else if ("sqlTypeName".equalsIgnoreCase(paramKey)) {
                // set dbType
                metadataConnection.setDbType(ParamValue);
                // set product(ProductId) and Schema(UISchema)
                EDatabaseTypeName edatabasetypeInstance = EDatabaseTypeName.getTypeFromDisplayName(ParamValue);
                String product = edatabasetypeInstance.getProduct();
                metadataConnection.setProduct(product);
                // set mapping(DbmsId)
                String mapping = MetadataTalendType.getDefaultDbmsFromProduct(product).getId();
                metadataConnection.setMapping(mapping);
                // set dbVersionString
                List<EDatabaseVersion4Drivers> dbTypeList = EDatabaseVersion4Drivers.indexOfByDbType(ParamValue);
                if (dbTypeList.size() == 1) {
                    metadataConnection.setDbVersionString(dbTypeList.get(0).getVersionValue());
                }
            } else if ("filePath".equalsIgnoreCase(paramKey)) {
                metadataConnection.setFileFieldName(ParamValue);
            } else if ("jdbcUrl".equalsIgnoreCase(paramKey)) {
                metadataConnection.setUrl(ParamValue);
            } else if ("aDDParameter".equalsIgnoreCase(paramKey)) {
                metadataConnection.setAdditionalParams(ParamValue);
            } else if ("driverClassName".equalsIgnoreCase(paramKey)) {
                metadataConnection.setDriverClass(ParamValue);
            } else if ("host".equalsIgnoreCase(paramKey)) {
                metadataConnection.setServerName(ParamValue);
            } else if ("port".equalsIgnoreCase(paramKey)) {
                metadataConnection.setPort(ParamValue);
            } else if ("dbName".equalsIgnoreCase(paramKey)) {
                metadataConnection.setDbName(ParamValue);
            } else if ("otherParameter".equalsIgnoreCase(paramKey)) {
                metadataConnection.setOtherParameter(ParamValue);
            } else if ("retrieveAllMetadata".equalsIgnoreCase(paramKey)) {
                metadataConnection.setRetrieveAllMetadata(Boolean.valueOf(ParamValue));
            } else if ("name".equalsIgnoreCase(paramKey)) {
                metadataConnection.setDataSourceName(ParamValue);
            } else if ("purpose".equalsIgnoreCase(paramKey)) {
                metadataConnection.setPurpose(ParamValue);
            } else if ("description".equalsIgnoreCase(paramKey)) {
                metadataConnection.setDescription(ParamValue);
            } else if ("author".equalsIgnoreCase(paramKey)) {
                metadataConnection.setAuthor(ParamValue);
            } else if ("status".equalsIgnoreCase(paramKey)) {
                metadataConnection.setStatus(ParamValue);
            } else if ("version".equalsIgnoreCase(paramKey)) {
                metadataConnection.setVersion(ParamValue);
            } else if ("password".equalsIgnoreCase(paramKey)) {
                metadataConnection.setPassword(ParamValue);
            } else if ("user".equalsIgnoreCase(paramKey)) {
                metadataConnection.setUsername(ParamValue);
            } else if ("universe".equalsIgnoreCase(paramKey)) {
                metadataConnection.setUniverse(ParamValue);
            } else if ("datafilter".equalsIgnoreCase(paramKey)) {
                metadataConnection.setDatafilter(ParamValue);
            }
        }
        return metadataConnection;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.metadata.IMetadataFiller#fillViews(orgomg.cwm.objectmodel.core.Package,
     * java.sql.DatabaseMetaData, java.util.List, java.lang.String)
     */
    public List<TdView> fillViews(Package pack, DatabaseMetaData dbJDBCMetadata, List<String> viewFilter, String viewPattern) {
        return null;
    }




    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.metadata.IMetadataFiller#fillColumns(orgomg.cwm.resource.relational.ColumnSet,
     * java.sql.DatabaseMetaData, java.util.List, java.lang.String)
     */
    public List<TdColumn> fillColumns(ColumnSet colSet, DatabaseMetaData dbJDBCMetadata, List<String> columnFilter,
            String columnPattern) {
        return null;
    }

    /**
     * 
     * zshen Comment method "isLinked".
     * 
     * @return get whether the subElements need to be linked to the parent element.
     */
    public boolean isLinked() {
        return isLinked;
    }

    /**
     * 
     * zshen Comment method "isLinked". set whether the subElements need to be linked to the parent element.
     * 
     * @return
     */
    public void setLinked(boolean isLinked) {
        this.isLinked = isLinked;
    }

    /**
     * 
     * zshen Comment method "checkConnection".
     * 
     * @param metadataBean connection information.can not be null.
     * @return the object of TypedReturnCode if connected have be build the object will take a java.sql.connection else
     * it will take a error message.
     */
    public ReturnCode checkConnection(IMetadataConnection metadataBean) {
        ReturnCode rc = new ReturnCode();
        if (EDataBaseType.MDM.getProductName().equalsIgnoreCase(metadataBean.getDbType())) {
            try {
                XtentisBindingStub stub = MetadataConnectionUtils.getXtentisBindingStub(metadataBean);
                // ping Web Service server
                stub.ping(new WSPing());
                rc.setOk(true);
                rc.setMessage("OK");
            } catch (Exception e) {
                log.warn(e, e);
                rc.setOk(false);
                rc.setMessage(e.getMessage());
            }
        } else {
            rc=MetadataConnectionUtils.checkConnection(metadataBean);
        }
        return rc;
    }




    protected boolean filterMetadaElement(List<String> packageList, String elementName) {
        if (packageList == null) {
            return true;
        }
        for (String name : packageList) {
            if (elementName.equalsIgnoreCase(name)) {
                return true;
            }
        }
            return false;
    }

    protected String getName(ModelElement element) {
        return element != null ? element.getName() : null;
    }


}
