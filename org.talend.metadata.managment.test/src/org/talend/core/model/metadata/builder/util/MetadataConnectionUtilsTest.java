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
package org.talend.core.model.metadata.builder.util;

import junit.framework.Assert;

import org.junit.Test;
import org.talend.cwm.relational.TdSqlDataType;

/**
 * created by ggu on Jul 12, 2012 Detailled comment
 * 
 */
public class MetadataConnectionUtilsTest {

    private void PTODO() {
        // after test, will remove this method
        Assert.fail("PTODO: Please finish this test.");
    }

    // @Test
    public void testCheckConnection4MetadataConnection() throws Exception {
        PTODO();
    }

    // @Test
    public void testCheckConnection4DatabaseConnection() throws Exception {
        PTODO();
    }

    // @Test
    public void testGetSoftwareSystem() throws Exception {
        PTODO();
    }

    @Test
    public void testGetSybaseDBProductsName() {
        Assert.assertEquals(5, MetadataConnectionUtils.getSybaseDBProductsName().length);
    }

    // @Test
    public void testGetClassDriver() throws Exception {
        PTODO();
    }

    // @Test
    public void testGetCommonQueryStr() throws Exception {
        PTODO();
    }

    @Test
    public void testCreateDataType() {
        TdSqlDataType dataType1 = MetadataConnectionUtils.createDataType(1, null, 2, 1);
        Assert.assertNotNull(dataType1);
        Assert.assertNull(dataType1.getName()); // null name
        Assert.assertEquals(1, dataType1.getJavaDataType());
        Assert.assertEquals(2, dataType1.getNumericPrecision());
        Assert.assertEquals(1, dataType1.getNumericPrecisionRadix());

        TdSqlDataType dataType2 = MetadataConnectionUtils.createDataType(0, "test", 1, 2); //$NON-NLS-1$
        Assert.assertNotNull(dataType2);
        Assert.assertEquals("test", dataType2.getName()); //$NON-NLS-1$
        Assert.assertEquals(0, dataType2.getJavaDataType());
        Assert.assertEquals(1, dataType2.getNumericPrecision());
        Assert.assertEquals(2, dataType2.getNumericPrecisionRadix());
    }

    // @Test
    public void testGetXtentisBindingStub4MetadataConnection() {
        PTODO();
    }

    // @Test
    public void testGetXtentisBindingStub4MDMConnection() {
        PTODO();
    }

    // @Test
    public void testCreateTechnicalName() {
        Assert.assertEquals("no_name", MetadataConnectionUtils.createTechnicalName(null)); //$NON-NLS-1$
        // need test it by powermock for the SMPL_DATE_FMT
        PTODO();
    }

    // @Test
    public void testGetPackageFilter4Database() {
        PTODO();
    }

    // @Test
    public void testGetPackageFilter4MDM() {
        PTODO();
    }

    // @Test
    public void testFillConnectionInformation() {
        PTODO();
    }

    // @Test
    public void testSetMDMConnectionParameter() {
        PTODO();
    }

    // @Test
    public void testFillDbConnectionInformation() {
        PTODO();
    }

}
