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
package org.talend.designer.xmlmap.util;

import org.talend.designer.xmlmap.model.emf.xmlmap.InputXmlTree;
import org.talend.designer.xmlmap.model.emf.xmlmap.OutputTreeNode;
import org.talend.designer.xmlmap.model.emf.xmlmap.OutputXmlTree;
import org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode;

/**
 * wchen class global comment. Detailled comment
 */
public class XmlMapUtil {

    public static final String DOCUMENT = "id_Document";

    public static final String XPATH_SEPARATOR = "/";

    public static final String EXPRESSION_SEPARATOR = ".";

    public static final String DEFAULT_DATA_TYPE = "id_String";

    public static final String CHILDREN_LEFT_SEPARATOR = "[";

    public static final String CHILDREN_RIGHT_SEPARATOR = "]";

    public static final String XPATH_ATTRIBUTE = "@";

    public static final String XPATH_NAMESPACE = "xmlns";

    // public static final String REQ_SETLOOP ="setLoop";

    public static int getXPathLength(String xPath) {
        if (xPath == null) {
            return 0;
        }
        return xPath.split(XPATH_SEPARATOR).length;

    }

    public static String parseExpression(String xPath) {
        if (xPath == null) {
            return xPath;
        }

        String[] split = xPath.split(XPATH_SEPARATOR);

        // normal column
        if (split.length == 2) {
            return xPath.replaceAll(XPATH_SEPARATOR, EXPRESSION_SEPARATOR);
        } else if (split.length > 2) {
            // separator after root
            int indexOf = xPath.indexOf("/", xPath.indexOf("/") + 1);
            if (indexOf != -1) {
                String rootPath = xPath.substring(0, indexOf);
                rootPath = rootPath.replace(XPATH_SEPARATOR, EXPRESSION_SEPARATOR);
                String childrenPath = xPath.substring(indexOf, xPath.length());

                return rootPath + CHILDREN_LEFT_SEPARATOR + childrenPath + CHILDREN_RIGHT_SEPARATOR;

            }
        }

        return xPath.replaceAll(EXPRESSION_SEPARATOR, XPATH_SEPARATOR);

    }

    public static TreeNode getInputDocumentRoot(TreeNode model) {
        if (model.eContainer() instanceof InputXmlTree) {
            return model;
        } else if (model.eContainer() instanceof TreeNode) {
            return getInputDocumentRoot((TreeNode) model.eContainer());
        }
        return null;
    }

    public static OutputTreeNode getOutputDocumentRoot(OutputTreeNode model) {
        if (model.eContainer() instanceof OutputXmlTree) {
            return model;
        } else if (model.eContainer() instanceof OutputTreeNode) {
            return getOutputDocumentRoot((OutputTreeNode) model.eContainer());
        }
        return null;
    }

    public static void cleanSubGroup(OutputTreeNode node) {
        for (TreeNode treeNode : node.getChildren()) {
            OutputTreeNode outputNode = (OutputTreeNode) treeNode;
            if (outputNode.isGroup()) {
                outputNode.setGroup(false);
            }
            cleanSubGroup(outputNode);

        }

    }

    public static void main(String[] args) {

        String a = "row1/xml/cf/dd/e/f";
        parseExpression(a);
    }

}
