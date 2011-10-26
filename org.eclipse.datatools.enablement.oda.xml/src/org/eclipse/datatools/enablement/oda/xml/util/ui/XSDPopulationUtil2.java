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
package org.eclipse.datatools.enablement.oda.xml.util.ui;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.datatools.connectivity.oda.OdaException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xsd.XSDAttributeDeclaration;
import org.eclipse.xsd.XSDAttributeUse;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDTerm;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.impl.XSDNamedComponentImpl;
import org.eclipse.xsd.util.XSDResourceImpl;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class XSDPopulationUtil2 {

    private Map<String, String> namespaceToPrefix = new HashMap<String, String>();

    private int prefixNumberGenerated;

    private boolean enableGeneratePrefix = false;

    public XSDPopulationUtil2() {
    }

    public XSDSchema getXSDSchema(String fileName) throws URISyntaxException, MalformedURLException {
        // Create a resource set and load the main schema file into it.
        //
        ResourceSet resourceSet = new ResourceSetImpl();
        XSDResourceImpl xsdResource = (XSDResourceImpl) resourceSet.getResource(URI.createFileURI(fileName), true);
        XSDSchema xsdSchema = xsdResource.getSchema();
        return xsdSchema;
    }

    public List<ATreeNode> getAllRootNodes(XSDSchema xsdSchema) throws OdaException {
        List<ATreeNode> rootNodes = new ArrayList<ATreeNode>();
        List<String> rootNodesName = new ArrayList<String>();

        List all = new ArrayList(xsdSchema.getElementDeclarations());
        all.addAll(xsdSchema.getTypeDefinitions());
        all = XSDNamedComponentImpl.sortNamedComponents(all);

        for (Iterator i = all.iterator(); i.hasNext();) {
            Object o = i.next();
            if (o instanceof XSDElementDeclaration) {
                ATreeNode node = new ATreeNode();
                XSDElementDeclaration xsdElementDeclaration = (XSDElementDeclaration) o;
                String elementName = xsdElementDeclaration.getQName();
                node.setValue(elementName);
                node.setType(ATreeNode.ELEMENT_TYPE);
                node.setDataType(xsdElementDeclaration.getName());
                XSDTypeDefinition xsdTypeDefinition = xsdElementDeclaration.getTypeDefinition();
                XSDComplexTypeDefinition generalType = xsdSchema.resolveComplexTypeDefinitionURI(xsdElementDeclaration.getURI());
                if (generalType.getContainer() != null) {
                    xsdTypeDefinition = generalType;
                }
                if (xsdTypeDefinition != null && xsdTypeDefinition.getName() != null) {
                    node.setDataType(xsdTypeDefinition.getName());
                }
                if (!rootNodes.contains(node) && !rootNodesName.contains(node.getValue())) {
                    rootNodesName.add((String) node.getValue());
                    rootNodes.add(node);
                }
            } else {
                XSDTypeDefinition xsdTypeDefinition = (XSDTypeDefinition) o;
                if (xsdTypeDefinition instanceof XSDComplexTypeDefinition) {
                    ATreeNode node = new ATreeNode();
                    String elementName = xsdTypeDefinition.getQName();
                    node.setValue(elementName);
                    node.setType(ATreeNode.ELEMENT_TYPE);
                    node.setDataType(xsdTypeDefinition.getName());
                    if (!rootNodes.contains(node) && !rootNodesName.contains(node.getValue())) {
                        rootNodesName.add((String) node.getValue());
                        rootNodes.add(node);
                    }
                }
            }
        }
        return rootNodes;
    }

    private void addParticleDetail(XSDSchema xsdSchema, XSDParticle xsdParticle, ATreeNode parentNode, String currentPath)
            throws OdaException {
        XSDTerm xsdTerm = xsdParticle.getTerm();
        if (xsdTerm instanceof XSDElementDeclaration) {
            XSDElementDeclaration xsdElementDeclarationParticle = (XSDElementDeclaration) xsdTerm;
            ATreeNode partNode = new ATreeNode();
            String elementName = xsdElementDeclarationParticle.getName();
            String prefix = null;
            String namespace = xsdElementDeclarationParticle.getTargetNamespace();
            if (namespace != null) {
                prefix = namespaceToPrefix.get(namespace);
                if (prefix == null) {
                    prefix = ((XSDElementDeclaration) xsdTerm).getQName().contains(":") ? ((XSDElementDeclaration) xsdTerm)
                            .getQName().split(":")[0] : "";

                    if (isEnableGeneratePrefix() && (prefix == null || prefix.isEmpty())) {
                        // generate a new prefix
                        prefix = "p" + prefixNumberGenerated;
                        prefixNumberGenerated++;
                    }
                    if (prefix != null && !prefix.isEmpty()) {
                        namespaceToPrefix.put(namespace, prefix);
                    } else {
                        ATreeNode namespaceNode = new ATreeNode();
                        namespaceNode.setDataType("");
                        namespaceNode.setType(ATreeNode.NAMESPACE_TYPE);
                        namespaceNode.setValue(namespace);
                        partNode.addChild(namespaceNode);
                    }
                }
            }
            if (prefix != null && !prefix.isEmpty()) {
                elementName = prefix + ":" + elementName;
            }
            partNode.setValue(elementName);
            partNode.setType(ATreeNode.ELEMENT_TYPE);
            partNode.setDataType(xsdElementDeclarationParticle.getName());
            parentNode.addChild(partNode);
            if (xsdElementDeclarationParticle.getTypeDefinition() instanceof XSDComplexTypeDefinition) {
                if (!currentPath.contains("/" + elementName + "/")) {
                    String path = currentPath + elementName + "/";
                    XSDTypeDefinition xsdTypeDefinition = xsdElementDeclarationParticle.getTypeDefinition();
                    XSDComplexTypeDefinition generalType = xsdSchema
                            .resolveComplexTypeDefinitionURI(xsdElementDeclarationParticle.getURI());
                    if (generalType.getContainer() != null) {
                        xsdTypeDefinition = generalType;
                    }
                    if (xsdTypeDefinition != null && xsdTypeDefinition.getName() != null) {
                        partNode.setDataType(xsdTypeDefinition.getQName());
                    }
                    addComplexTypeDetails(xsdSchema, partNode, xsdTypeDefinition, prefix, namespace, path);
                }
            } else {
                String dataType = xsdElementDeclarationParticle.getTypeDefinition().getQName();
                if (xsdElementDeclarationParticle.getTypeDefinition().getBaseType() != null) {
                    dataType = xsdElementDeclarationParticle.getTypeDefinition().getBaseType().getQName();
                }
                partNode.setDataType(dataType);
            }
        } else if (xsdTerm instanceof XSDModelGroup) {
            XSDModelGroup xsdModelGroup = (XSDModelGroup) xsdTerm;

            for (Iterator j = xsdModelGroup.getParticles().iterator(); j.hasNext();) {
                XSDParticle childParticle = (XSDParticle) j.next();
                addParticleDetail(xsdSchema, childParticle, parentNode, currentPath);
            }
        }
    }

    public ATreeNode getSchemaTree(XSDSchema xsdSchema, ATreeNode selectedNode, boolean includeAttribute2) {
        List<ATreeNode> rootNodes = new ArrayList<ATreeNode>();

        prefixNumberGenerated = 1;
        List all = new ArrayList(xsdSchema.getElementDeclarations());
        // all = XSDNamedComponentImpl.sortNamedComponents(all);

        try {
            for (Iterator i = all.iterator(); i.hasNext();) {
                XSDElementDeclaration xsdElementDeclaration = (XSDElementDeclaration) i.next();
                String elementName = xsdElementDeclaration.getName();

                ATreeNode node = new ATreeNode();
                String prefix = null;
                String namespace = xsdElementDeclaration.getTargetNamespace();

                if (namespace != null) {
                    prefix = xsdElementDeclaration.getQName().contains(":") ? xsdElementDeclaration.getQName().split(":")[0] : "";
                    if (isEnableGeneratePrefix() && (prefix == null || prefix.isEmpty())) {
                        // generate a new prefix
                        prefix = "p" + prefixNumberGenerated;
                        prefixNumberGenerated++;
                    }
                    if (prefix != null && !prefix.isEmpty()) {
                        elementName = prefix + ":" + xsdElementDeclaration.getName();
                        namespaceToPrefix.put(namespace, prefix);
                    } else {
                        ATreeNode namespaceNode = new ATreeNode();
                        namespaceNode.setDataType("");
                        namespaceNode.setType(ATreeNode.NAMESPACE_TYPE);
                        namespaceNode.setValue(namespace);
                        node.addChild(namespaceNode);
                    }
                }

                if (!elementName.equals(selectedNode.getValue())) {
                    continue;
                }

                node.setValue(elementName);
                node.setType(ATreeNode.ELEMENT_TYPE);
                node.setDataType(xsdElementDeclaration.getName());

                XSDTypeDefinition xsdTypeDefinition = xsdElementDeclaration.getTypeDefinition();
                XSDComplexTypeDefinition generalType = xsdSchema.resolveComplexTypeDefinitionURI(xsdElementDeclaration.getURI());
                if (generalType.getContainer() != null) {
                    xsdTypeDefinition = generalType;
                }
                if (xsdTypeDefinition != null && xsdTypeDefinition.getName() != null) {
                    node.setDataType(xsdTypeDefinition.getName());
                }
                if (xsdTypeDefinition instanceof XSDComplexTypeDefinition) {
                    addComplexTypeDetails(xsdSchema, node, xsdTypeDefinition, prefix, namespace, "/" + elementName + "/");
                }
                List<String> namespaceList = new ArrayList(namespaceToPrefix.keySet());
                Collections.reverse(namespaceList);
                for (String currentNamespace : namespaceList) {
                    ATreeNode namespaceNode = null;
                    if (currentNamespace != null) {
                        prefix = namespaceToPrefix.get(currentNamespace);
                        namespaceNode = new ATreeNode();
                        namespaceNode.setDataType(prefix);
                        namespaceNode.setType(ATreeNode.NAMESPACE_TYPE);
                        namespaceNode.setValue(currentNamespace);
                        node.addAsFirstChild(namespaceNode);
                    }
                }

                rootNodes.add(node);
                break;
            }

            if (rootNodes.isEmpty()) {
                all = new ArrayList(xsdSchema.getTypeDefinitions());
                // all = XSDNamedComponentImpl.sortNamedComponents(all);

                for (Iterator i = all.iterator(); i.hasNext();) {
                    XSDTypeDefinition xsdTypeDefinition = (XSDTypeDefinition) i.next();
                    String elementName = xsdTypeDefinition.getQName();
                    if (!elementName.equals(selectedNode.getValue())) {
                        continue;
                    }
                    ATreeNode node = new ATreeNode();
                    node.setValue(elementName);
                    node.setType(ATreeNode.ELEMENT_TYPE);
                    node.setDataType(xsdTypeDefinition.getName());
                    XSDComplexTypeDefinition generalType = xsdSchema.resolveComplexTypeDefinitionURI(xsdTypeDefinition.getURI());
                    if (generalType.getContainer() != null) {
                        xsdTypeDefinition = generalType;
                    }
                    if (xsdTypeDefinition != null && xsdTypeDefinition.getName() != null) {
                        node.setDataType(xsdTypeDefinition.getQName());
                    }
                    if (xsdTypeDefinition instanceof XSDComplexTypeDefinition) {
                        String prefix = elementName.contains(":") ? elementName.split(":")[0] : "";
                        String namespace = xsdTypeDefinition.getTargetNamespace();
                        addComplexTypeDetails(xsdSchema, node, xsdTypeDefinition, prefix, namespace, "/" + elementName + "/");
                    }
                    if (xsdTypeDefinition.getTargetNamespace() != null) {
                        String prefix = namespaceToPrefix.get(xsdTypeDefinition.getTargetNamespace());
                        if (prefix != null) {
                            node.setValue(prefix + ":" + xsdTypeDefinition.getName());
                        }
                    }
                    List<String> namespaceList = new ArrayList(namespaceToPrefix.keySet());
                    Collections.reverse(namespaceList);
                    for (String namespace : namespaceList) {
                        ATreeNode namespaceNode = null;
                        if (namespace != null) {
                            String prefix = namespaceToPrefix.get(namespace);
                            namespaceNode = new ATreeNode();
                            namespaceNode.setDataType(prefix);
                            namespaceNode.setType(ATreeNode.NAMESPACE_TYPE);
                            namespaceNode.setValue(namespace);
                            node.addAsFirstChild(namespaceNode);
                        }
                    }

                    rootNodes.add(node);
                    break;
                }
            }

        } catch (OdaException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return rootNodes.get(0);
    }

    private void addComplexTypeDetails(XSDSchema xsdSchema, ATreeNode node, XSDTypeDefinition xsdTypeDefinition, String prefix,
            String namespace, String currentPath) throws OdaException {
        String prefixToUse = prefix;
        if (namespace != null && !namespaceToPrefix.containsKey(namespace)) {
            if (isEnableGeneratePrefix() && (prefix == null || prefix.isEmpty())) {
                // generate a new prefix
                prefixToUse = "p" + prefixNumberGenerated;
                prefixNumberGenerated++;
            }
            if (prefixToUse != null && !prefixToUse.isEmpty()) {
                namespaceToPrefix.put(namespace, prefixToUse);
            }
        }

        XSDComplexTypeDefinition xsdComplexTypeDefinition = (XSDComplexTypeDefinition) xsdTypeDefinition;
        if (xsdComplexTypeDefinition.getContentType() instanceof XSDParticle) {
            addParticleDetail(xsdSchema, (XSDParticle) xsdComplexTypeDefinition.getContentType(), node, currentPath);
        }
        for (Iterator attributeUses = xsdComplexTypeDefinition.getAttributeUses().iterator(); attributeUses.hasNext();) {
            XSDAttributeUse xsdAttributeUse = (XSDAttributeUse) attributeUses.next();
            XSDAttributeDeclaration xsdAttributeDeclaration = xsdAttributeUse.getAttributeDeclaration();
            String attributeDeclarationName = xsdAttributeDeclaration.getName();
            ATreeNode childNode = new ATreeNode();
            childNode.setValue(attributeDeclarationName);
            childNode.setType(ATreeNode.ATTRIBUTE_TYPE);
            String dataType = xsdAttributeDeclaration.getTypeDefinition().getQName();
            if (xsdAttributeDeclaration.getTypeDefinition().getBaseType() != null) {
                dataType = xsdAttributeDeclaration.getTypeDefinition().getBaseType().getQName();
            }
            if (dataType != null && dataType.length() > 0) {
                childNode.setDataType(dataType);
            }
            node.addChild(childNode);
        }
    }

    public boolean isEnableGeneratePrefix() {
        return enableGeneratePrefix;
    }

    public void setEnableGeneratePrefix(boolean enableGeneratePrefix) {
        this.enableGeneratePrefix = enableGeneratePrefix;
    }
}
