// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.commons.utils.io;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.stub;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.soap.Node;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/**
 * DOC xqliu class global comment. Detailled comment
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ FilesUtils.class })
public class FilesUtilsTest {

    /**
     * Test method for
     * {@link org.talend.commons.utils.io.FilesUtils#migrateFolder(java.io.File, java.lang.String[], java.util.Map, org.apache.log4j.Logger)}
     * .
     */
    @Test
    public void testMigrateFolder() {
        File fileMdmConnection = new File(
                "/Talend/Talend_All_trunk/runtime/TOP_runtime/TOP_DEFAULT_PRJ/TDQ_Metadata/MDM Connections");

        String[] mdmFileExtentionNames = { ".prv" };

        Map<String, String> replaceStringMap = new HashMap<String, String>();
        replaceStringMap.put("TdXMLDocument", "TdXmlSchema");
        replaceStringMap.put("TdXMLElement", "TdXmlElementType");

        Logger log = Logger.getLogger(FilesUtils.class);

        FilesUtils.migrateFolder(fileMdmConnection, mdmFileExtentionNames, replaceStringMap, log);
    }

    /**
     * Test method for
     * {@link org.talend.commons.utils.io.FilesUtils#migrateFolder(java.io.File, java.io.File, boolean, java.io.FileFilter,java.io.FileFilter, boolean, boolean, org.eclipse.core.runtime.IProgressMonitor)}
     * .
     * 
     * @throws IOException
     */
    @Test
    public void testCopyFolder() throws IOException {
        File source = new File("");
        File target = new File("");
        FilesUtils.copyFolder(source, target, false, null, null, true, null);
        assertTrue(!target.exists());
    }

    /**
     * Test method for {@link org.talend.commons.utils.io.FilesUtils#getUUID(java.lang.String)}
     */
    @Test
    public void testGetUUID() {
        try {
            String uuid = "12345678"; //$NON-NLS-1$
            String xmlFileName = "/test.xml"; //$NON-NLS-1$
            String xmi_id = "xmi:id"; //$NON-NLS-1$

            Document mockDocument = mock(Document.class);
            Node mockNode1 = mock(Node.class);
            NodeList mockNodeList = mock(NodeList.class);
            Node mockNode2 = mock(Node.class);
            Node mockNode3 = mock(Node.class);
            NamedNodeMap mockNamedNodeMap = mock(NamedNodeMap.class);
            Node mockNode4 = mock(Node.class);

            when(mockDocument.getFirstChild()).thenReturn(mockNode1);
            when(mockNode1.getChildNodes()).thenReturn(mockNodeList);
            when(mockNodeList.getLength()).thenReturn(1);
            when(mockNodeList.item(org.mockito.Matchers.anyInt())).thenReturn(mockNode2);
            when(mockNode2.getNextSibling()).thenReturn(mockNode3);
            when(mockNode3.getAttributes()).thenReturn(mockNamedNodeMap);
            when(mockNamedNodeMap.getNamedItem(xmi_id)).thenReturn(mockNode4);
            when(mockNode4.getNodeValue()).thenReturn(uuid);

            stub(method(FilesUtils.class, "parse")).toReturn(mockDocument);

            Assert.assertEquals(uuid, FilesUtils.getUUID(xmlFileName));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * 
     * test for parse(String xmlFile).
     * 
     * @throws Exception
     */
    @Test
    public void testParse_1() throws Exception {
        String path = "D:\\��������\\Row_Count_0.1.definition";
        FilesUtils.parse(path);
    }

    /**
     * 
     * test for parse(File file).
     * 
     * @throws Exception
     */
    @Test
    public void testParse_2() throws Exception {
        File f = new File("D:\\testParse.xml");
        BufferedWriter output = new BufferedWriter(new FileWriter(f));
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<dataquality.indicators.definition:IndicatorDefinition xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:dataquality.indicators.definition=\"http://dataquality.indicators.definition\" xmi:id=\"_ccFOkBF2Ed2PKb6nEJEvhw\" name=\"Row Count1\" label=\"Row Count\">\n"
                + "  <taggedValue xmi:id=\"_8mgIwoqnEd-SocVM_rHvUg\" tag=\"Description\" value=\"counts the number of rows\"/>\n"
                + "  <sqlGenericExpression xmi:id=\"_HtGuc4LCEeGXWpdd1rakSg\" body=\"count(//&lt;%=__TABLE_NAME__%>)\" language=\"MDM\"/>\n"
                + "</dataquality.indicators.definition:IndicatorDefinition>";
        output.write(str);
        output.close();
        assertTrue(f.exists());
        FilesUtils.parse(f);
        f.delete();
        assertFalse(f.exists());
    }
}
