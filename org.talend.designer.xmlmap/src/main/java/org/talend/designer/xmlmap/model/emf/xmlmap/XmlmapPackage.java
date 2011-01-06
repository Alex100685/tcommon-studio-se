/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.xmlmap.model.emf.xmlmap;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.talend.designer.core.model.utils.emf.talendfile.TalendFilePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.talend.designer.xmlmap.model.emf.xmlmap.XmlmapFactory
 * @model kind="package"
 * @generated
 */
public interface XmlmapPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "xmlmap";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.talend.org/xmlmap";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    XmlmapPackage eINSTANCE = org.talend.designer.xmlmap.model.emf.xmlmap.impl.XmlmapPackageImpl.init();

    /**
     * The meta object id for the '{@link org.talend.designer.xmlmap.model.emf.xmlmap.impl.XmlMapDataImpl <em>Xml Map Data</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.XmlMapDataImpl
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.XmlmapPackageImpl#getXmlMapData()
     * @generated
     */
    int XML_MAP_DATA = 0;

    /**
     * The feature id for the '<em><b>Input Trees</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_MAP_DATA__INPUT_TREES = TalendFilePackage.ABSTRACT_EXTERNAL_DATA_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Output Trees</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_MAP_DATA__OUTPUT_TREES = TalendFilePackage.ABSTRACT_EXTERNAL_DATA_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Xml Map Data</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int XML_MAP_DATA_FEATURE_COUNT = TalendFilePackage.ABSTRACT_EXTERNAL_DATA_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link org.talend.designer.xmlmap.model.emf.xmlmap.impl.InputXmlTreeImpl <em>Input Xml Tree</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.InputXmlTreeImpl
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.XmlmapPackageImpl#getInputXmlTree()
     * @generated
     */
    int INPUT_XML_TREE = 1;

    /**
     * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_XML_TREE__NODES = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_XML_TREE__NAME = 1;

    /**
     * The feature id for the '<em><b>Lookup</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_XML_TREE__LOOKUP = 2;

    /**
     * The number of structural features of the '<em>Input Xml Tree</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_XML_TREE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link org.talend.designer.xmlmap.model.emf.xmlmap.impl.OutputXmlTreeImpl <em>Output Xml Tree</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.OutputXmlTreeImpl
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.XmlmapPackageImpl#getOutputXmlTree()
     * @generated
     */
    int OUTPUT_XML_TREE = 2;

    /**
     * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_XML_TREE__NODES = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_XML_TREE__NAME = 1;

    /**
     * The number of structural features of the '<em>Output Xml Tree</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_XML_TREE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link org.talend.designer.xmlmap.model.emf.xmlmap.impl.TreeNodeImpl <em>Tree Node</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.TreeNodeImpl
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.XmlmapPackageImpl#getTreeNode()
     * @generated
     */
    int TREE_NODE = 3;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TREE_NODE__CHILDREN = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TREE_NODE__NAME = 1;

    /**
     * The feature id for the '<em><b>Expression</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TREE_NODE__EXPRESSION = 2;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TREE_NODE__TYPE = 3;

    /**
     * The feature id for the '<em><b>Nullable</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TREE_NODE__NULLABLE = 4;

    /**
     * The feature id for the '<em><b>Xpath</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TREE_NODE__XPATH = 5;

    /**
     * The feature id for the '<em><b>Loop</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TREE_NODE__LOOP = 6;

    /**
     * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TREE_NODE__OUTGOING_CONNECTIONS = 7;

    /**
     * The feature id for the '<em><b>Node Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TREE_NODE__NODE_TYPE = 8;

    /**
     * The number of structural features of the '<em>Tree Node</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TREE_NODE_FEATURE_COUNT = 9;

    /**
     * The meta object id for the '{@link org.talend.designer.xmlmap.model.emf.xmlmap.impl.OutputTreeNodeImpl <em>Output Tree Node</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.OutputTreeNodeImpl
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.XmlmapPackageImpl#getOutputTreeNode()
     * @generated
     */
    int OUTPUT_TREE_NODE = 4;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TREE_NODE__CHILDREN = TREE_NODE__CHILDREN;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TREE_NODE__NAME = TREE_NODE__NAME;

    /**
     * The feature id for the '<em><b>Expression</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TREE_NODE__EXPRESSION = TREE_NODE__EXPRESSION;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TREE_NODE__TYPE = TREE_NODE__TYPE;

    /**
     * The feature id for the '<em><b>Nullable</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TREE_NODE__NULLABLE = TREE_NODE__NULLABLE;

    /**
     * The feature id for the '<em><b>Xpath</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TREE_NODE__XPATH = TREE_NODE__XPATH;

    /**
     * The feature id for the '<em><b>Loop</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TREE_NODE__LOOP = TREE_NODE__LOOP;

    /**
     * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TREE_NODE__OUTGOING_CONNECTIONS = TREE_NODE__OUTGOING_CONNECTIONS;

    /**
     * The feature id for the '<em><b>Node Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TREE_NODE__NODE_TYPE = TREE_NODE__NODE_TYPE;

    /**
     * The feature id for the '<em><b>Default Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TREE_NODE__DEFAULT_VALUE = TREE_NODE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TREE_NODE__GROUP = TREE_NODE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TREE_NODE__INCOMING_CONNECTIONS = TREE_NODE_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Output Tree Node</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TREE_NODE_FEATURE_COUNT = TREE_NODE_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link org.talend.designer.xmlmap.model.emf.xmlmap.impl.ConnectionImpl <em>Connection</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.ConnectionImpl
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.XmlmapPackageImpl#getConnection()
     * @generated
     */
	int CONNECTION = 5;

				/**
     * The feature id for the '<em><b>Source</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CONNECTION__SOURCE = 0;

				/**
     * The feature id for the '<em><b>Target</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CONNECTION__TARGET = 1;

				/**
     * The number of structural features of the '<em>Connection</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int CONNECTION_FEATURE_COUNT = 2;

				/**
     * The meta object id for the '{@link org.talend.designer.xmlmap.model.emf.xmlmap.NodeType <em>Node Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.NodeType
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.XmlmapPackageImpl#getNodeType()
     * @generated
     */
    int NODE_TYPE = 6;


    /**
     * Returns the meta object for class '{@link org.talend.designer.xmlmap.model.emf.xmlmap.XmlMapData <em>Xml Map Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Xml Map Data</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.XmlMapData
     * @generated
     */
    EClass getXmlMapData();

    /**
     * Returns the meta object for the containment reference list '{@link org.talend.designer.xmlmap.model.emf.xmlmap.XmlMapData#getInputTrees <em>Input Trees</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Input Trees</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.XmlMapData#getInputTrees()
     * @see #getXmlMapData()
     * @generated
     */
    EReference getXmlMapData_InputTrees();

    /**
     * Returns the meta object for the reference list '{@link org.talend.designer.xmlmap.model.emf.xmlmap.XmlMapData#getOutputTrees <em>Output Trees</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Output Trees</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.XmlMapData#getOutputTrees()
     * @see #getXmlMapData()
     * @generated
     */
    EReference getXmlMapData_OutputTrees();

    /**
     * Returns the meta object for class '{@link org.talend.designer.xmlmap.model.emf.xmlmap.InputXmlTree <em>Input Xml Tree</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Input Xml Tree</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.InputXmlTree
     * @generated
     */
    EClass getInputXmlTree();

    /**
     * Returns the meta object for the containment reference list '{@link org.talend.designer.xmlmap.model.emf.xmlmap.InputXmlTree#getNodes <em>Nodes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Nodes</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.InputXmlTree#getNodes()
     * @see #getInputXmlTree()
     * @generated
     */
    EReference getInputXmlTree_Nodes();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.xmlmap.model.emf.xmlmap.InputXmlTree#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.InputXmlTree#getName()
     * @see #getInputXmlTree()
     * @generated
     */
    EAttribute getInputXmlTree_Name();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.xmlmap.model.emf.xmlmap.InputXmlTree#isLookup <em>Lookup</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Lookup</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.InputXmlTree#isLookup()
     * @see #getInputXmlTree()
     * @generated
     */
    EAttribute getInputXmlTree_Lookup();

    /**
     * Returns the meta object for class '{@link org.talend.designer.xmlmap.model.emf.xmlmap.OutputXmlTree <em>Output Xml Tree</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Output Xml Tree</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.OutputXmlTree
     * @generated
     */
    EClass getOutputXmlTree();

    /**
     * Returns the meta object for the containment reference list '{@link org.talend.designer.xmlmap.model.emf.xmlmap.OutputXmlTree#getNodes <em>Nodes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Nodes</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.OutputXmlTree#getNodes()
     * @see #getOutputXmlTree()
     * @generated
     */
    EReference getOutputXmlTree_Nodes();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.xmlmap.model.emf.xmlmap.OutputXmlTree#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.OutputXmlTree#getName()
     * @see #getOutputXmlTree()
     * @generated
     */
    EAttribute getOutputXmlTree_Name();

    /**
     * Returns the meta object for class '{@link org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode <em>Tree Node</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Tree Node</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode
     * @generated
     */
    EClass getTreeNode();

    /**
     * Returns the meta object for the containment reference list '{@link org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode#getChildren <em>Children</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Children</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode#getChildren()
     * @see #getTreeNode()
     * @generated
     */
    EReference getTreeNode_Children();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode#getName()
     * @see #getTreeNode()
     * @generated
     */
    EAttribute getTreeNode_Name();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode#getExpression <em>Expression</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Expression</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode#getExpression()
     * @see #getTreeNode()
     * @generated
     */
    EAttribute getTreeNode_Expression();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode#getType()
     * @see #getTreeNode()
     * @generated
     */
    EAttribute getTreeNode_Type();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode#isNullable <em>Nullable</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Nullable</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode#isNullable()
     * @see #getTreeNode()
     * @generated
     */
    EAttribute getTreeNode_Nullable();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode#getXpath <em>Xpath</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Xpath</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode#getXpath()
     * @see #getTreeNode()
     * @generated
     */
    EAttribute getTreeNode_Xpath();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode#isLoop <em>Loop</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Loop</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode#isLoop()
     * @see #getTreeNode()
     * @generated
     */
    EAttribute getTreeNode_Loop();

    /**
     * Returns the meta object for the reference list '{@link org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode#getOutgoingConnections <em>Outgoing Connections</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Outgoing Connections</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode#getOutgoingConnections()
     * @see #getTreeNode()
     * @generated
     */
    EReference getTreeNode_OutgoingConnections();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode#getNodeType <em>Node Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Node Type</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode#getNodeType()
     * @see #getTreeNode()
     * @generated
     */
    EAttribute getTreeNode_NodeType();

    /**
     * Returns the meta object for class '{@link org.talend.designer.xmlmap.model.emf.xmlmap.OutputTreeNode <em>Output Tree Node</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Output Tree Node</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.OutputTreeNode
     * @generated
     */
    EClass getOutputTreeNode();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.xmlmap.model.emf.xmlmap.OutputTreeNode#getDefaultValue <em>Default Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Default Value</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.OutputTreeNode#getDefaultValue()
     * @see #getOutputTreeNode()
     * @generated
     */
    EAttribute getOutputTreeNode_DefaultValue();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.xmlmap.model.emf.xmlmap.OutputTreeNode#isGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Group</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.OutputTreeNode#isGroup()
     * @see #getOutputTreeNode()
     * @generated
     */
    EAttribute getOutputTreeNode_Group();

    /**
     * Returns the meta object for the reference list '{@link org.talend.designer.xmlmap.model.emf.xmlmap.OutputTreeNode#getIncomingConnections <em>Incoming Connections</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Incoming Connections</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.OutputTreeNode#getIncomingConnections()
     * @see #getOutputTreeNode()
     * @generated
     */
    EReference getOutputTreeNode_IncomingConnections();

    /**
     * Returns the meta object for class '{@link org.talend.designer.xmlmap.model.emf.xmlmap.Connection <em>Connection</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Connection</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.Connection
     * @generated
     */
	EClass getConnection();

				/**
     * Returns the meta object for the reference '{@link org.talend.designer.xmlmap.model.emf.xmlmap.Connection#getSource <em>Source</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Source</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.Connection#getSource()
     * @see #getConnection()
     * @generated
     */
	EReference getConnection_Source();

				/**
     * Returns the meta object for the reference '{@link org.talend.designer.xmlmap.model.emf.xmlmap.Connection#getTarget <em>Target</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Target</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.Connection#getTarget()
     * @see #getConnection()
     * @generated
     */
	EReference getConnection_Target();

				/**
     * Returns the meta object for enum '{@link org.talend.designer.xmlmap.model.emf.xmlmap.NodeType <em>Node Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Node Type</em>'.
     * @see org.talend.designer.xmlmap.model.emf.xmlmap.NodeType
     * @generated
     */
    EEnum getNodeType();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    XmlmapFactory getXmlmapFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link org.talend.designer.xmlmap.model.emf.xmlmap.impl.XmlMapDataImpl <em>Xml Map Data</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.XmlMapDataImpl
         * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.XmlmapPackageImpl#getXmlMapData()
         * @generated
         */
        EClass XML_MAP_DATA = eINSTANCE.getXmlMapData();

        /**
         * The meta object literal for the '<em><b>Input Trees</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference XML_MAP_DATA__INPUT_TREES = eINSTANCE.getXmlMapData_InputTrees();

        /**
         * The meta object literal for the '<em><b>Output Trees</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference XML_MAP_DATA__OUTPUT_TREES = eINSTANCE.getXmlMapData_OutputTrees();

        /**
         * The meta object literal for the '{@link org.talend.designer.xmlmap.model.emf.xmlmap.impl.InputXmlTreeImpl <em>Input Xml Tree</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.InputXmlTreeImpl
         * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.XmlmapPackageImpl#getInputXmlTree()
         * @generated
         */
        EClass INPUT_XML_TREE = eINSTANCE.getInputXmlTree();

        /**
         * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INPUT_XML_TREE__NODES = eINSTANCE.getInputXmlTree_Nodes();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute INPUT_XML_TREE__NAME = eINSTANCE.getInputXmlTree_Name();

        /**
         * The meta object literal for the '<em><b>Lookup</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute INPUT_XML_TREE__LOOKUP = eINSTANCE.getInputXmlTree_Lookup();

        /**
         * The meta object literal for the '{@link org.talend.designer.xmlmap.model.emf.xmlmap.impl.OutputXmlTreeImpl <em>Output Xml Tree</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.OutputXmlTreeImpl
         * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.XmlmapPackageImpl#getOutputXmlTree()
         * @generated
         */
        EClass OUTPUT_XML_TREE = eINSTANCE.getOutputXmlTree();

        /**
         * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference OUTPUT_XML_TREE__NODES = eINSTANCE.getOutputXmlTree_Nodes();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OUTPUT_XML_TREE__NAME = eINSTANCE.getOutputXmlTree_Name();

        /**
         * The meta object literal for the '{@link org.talend.designer.xmlmap.model.emf.xmlmap.impl.TreeNodeImpl <em>Tree Node</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.TreeNodeImpl
         * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.XmlmapPackageImpl#getTreeNode()
         * @generated
         */
        EClass TREE_NODE = eINSTANCE.getTreeNode();

        /**
         * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TREE_NODE__CHILDREN = eINSTANCE.getTreeNode_Children();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TREE_NODE__NAME = eINSTANCE.getTreeNode_Name();

        /**
         * The meta object literal for the '<em><b>Expression</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TREE_NODE__EXPRESSION = eINSTANCE.getTreeNode_Expression();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TREE_NODE__TYPE = eINSTANCE.getTreeNode_Type();

        /**
         * The meta object literal for the '<em><b>Nullable</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TREE_NODE__NULLABLE = eINSTANCE.getTreeNode_Nullable();

        /**
         * The meta object literal for the '<em><b>Xpath</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TREE_NODE__XPATH = eINSTANCE.getTreeNode_Xpath();

        /**
         * The meta object literal for the '<em><b>Loop</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TREE_NODE__LOOP = eINSTANCE.getTreeNode_Loop();

        /**
         * The meta object literal for the '<em><b>Outgoing Connections</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TREE_NODE__OUTGOING_CONNECTIONS = eINSTANCE.getTreeNode_OutgoingConnections();

        /**
         * The meta object literal for the '<em><b>Node Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TREE_NODE__NODE_TYPE = eINSTANCE.getTreeNode_NodeType();

        /**
         * The meta object literal for the '{@link org.talend.designer.xmlmap.model.emf.xmlmap.impl.OutputTreeNodeImpl <em>Output Tree Node</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.OutputTreeNodeImpl
         * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.XmlmapPackageImpl#getOutputTreeNode()
         * @generated
         */
        EClass OUTPUT_TREE_NODE = eINSTANCE.getOutputTreeNode();

        /**
         * The meta object literal for the '<em><b>Default Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OUTPUT_TREE_NODE__DEFAULT_VALUE = eINSTANCE.getOutputTreeNode_DefaultValue();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OUTPUT_TREE_NODE__GROUP = eINSTANCE.getOutputTreeNode_Group();

        /**
         * The meta object literal for the '<em><b>Incoming Connections</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference OUTPUT_TREE_NODE__INCOMING_CONNECTIONS = eINSTANCE.getOutputTreeNode_IncomingConnections();

        /**
         * The meta object literal for the '{@link org.talend.designer.xmlmap.model.emf.xmlmap.impl.ConnectionImpl <em>Connection</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.ConnectionImpl
         * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.XmlmapPackageImpl#getConnection()
         * @generated
         */
		EClass CONNECTION = eINSTANCE.getConnection();

								/**
         * The meta object literal for the '<em><b>Source</b></em>' reference feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference CONNECTION__SOURCE = eINSTANCE.getConnection_Source();

								/**
         * The meta object literal for the '<em><b>Target</b></em>' reference feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference CONNECTION__TARGET = eINSTANCE.getConnection_Target();

								/**
         * The meta object literal for the '{@link org.talend.designer.xmlmap.model.emf.xmlmap.NodeType <em>Node Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.designer.xmlmap.model.emf.xmlmap.NodeType
         * @see org.talend.designer.xmlmap.model.emf.xmlmap.impl.XmlmapPackageImpl#getNodeType()
         * @generated
         */
        EEnum NODE_TYPE = eINSTANCE.getNodeType();

    }

} //XmlmapPackage
