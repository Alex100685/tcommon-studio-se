/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.xmlmap.model.emf.xmlmap.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.talend.designer.xmlmap.model.emf.xmlmap.Connection;
import org.talend.designer.xmlmap.model.emf.xmlmap.NodeType;
import org.talend.designer.xmlmap.model.emf.xmlmap.TreeNode;
import org.talend.designer.xmlmap.model.emf.xmlmap.XmlmapPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tree Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.talend.designer.xmlmap.model.emf.xmlmap.impl.TreeNodeImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link org.talend.designer.xmlmap.model.emf.xmlmap.impl.TreeNodeImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.talend.designer.xmlmap.model.emf.xmlmap.impl.TreeNodeImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.talend.designer.xmlmap.model.emf.xmlmap.impl.TreeNodeImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.talend.designer.xmlmap.model.emf.xmlmap.impl.TreeNodeImpl#isNullable <em>Nullable</em>}</li>
 *   <li>{@link org.talend.designer.xmlmap.model.emf.xmlmap.impl.TreeNodeImpl#getXpath <em>Xpath</em>}</li>
 *   <li>{@link org.talend.designer.xmlmap.model.emf.xmlmap.impl.TreeNodeImpl#isLoop <em>Loop</em>}</li>
 *   <li>{@link org.talend.designer.xmlmap.model.emf.xmlmap.impl.TreeNodeImpl#getOutgoingConnections <em>Outgoing Connections</em>}</li>
 *   <li>{@link org.talend.designer.xmlmap.model.emf.xmlmap.impl.TreeNodeImpl#getNodeType <em>Node Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TreeNodeImpl extends EObjectImpl implements TreeNode {
    /**
     * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getChildren()
     * @generated
     * @ordered
     */
    protected EList<TreeNode> children;

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getExpression() <em>Expression</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExpression()
     * @generated
     * @ordered
     */
    protected static final String EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getExpression() <em>Expression</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExpression()
     * @generated
     * @ordered
     */
    protected String expression = EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected static final String TYPE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected String type = TYPE_EDEFAULT;

    /**
     * The default value of the '{@link #isNullable() <em>Nullable</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isNullable()
     * @generated
     * @ordered
     */
    protected static final boolean NULLABLE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isNullable() <em>Nullable</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isNullable()
     * @generated
     * @ordered
     */
    protected boolean nullable = NULLABLE_EDEFAULT;

    /**
     * The default value of the '{@link #getXpath() <em>Xpath</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getXpath()
     * @generated
     * @ordered
     */
    protected static final String XPATH_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getXpath() <em>Xpath</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getXpath()
     * @generated
     * @ordered
     */
    protected String xpath = XPATH_EDEFAULT;

    /**
     * The default value of the '{@link #isLoop() <em>Loop</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isLoop()
     * @generated
     * @ordered
     */
    protected static final boolean LOOP_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isLoop() <em>Loop</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isLoop()
     * @generated
     * @ordered
     */
    protected boolean loop = LOOP_EDEFAULT;

    /**
     * The cached value of the '{@link #getOutgoingConnections() <em>Outgoing Connections</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOutgoingConnections()
     * @generated
     * @ordered
     */
    protected EList<Connection> outgoingConnections;

    /**
     * The default value of the '{@link #getNodeType() <em>Node Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNodeType()
     * @generated
     * @ordered
     */
    protected static final NodeType NODE_TYPE_EDEFAULT = NodeType.ELEMENT;

    /**
     * The cached value of the '{@link #getNodeType() <em>Node Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNodeType()
     * @generated
     * @ordered
     */
    protected NodeType nodeType = NODE_TYPE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TreeNodeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XmlmapPackage.Literals.TREE_NODE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<TreeNode> getChildren() {
        if (children == null) {
            children = new EObjectContainmentEList<TreeNode>(TreeNode.class, this, XmlmapPackage.TREE_NODE__CHILDREN);
        }
        return children;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XmlmapPackage.TREE_NODE__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getExpression() {
        return expression;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setExpression(String newExpression) {
        String oldExpression = expression;
        expression = newExpression;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XmlmapPackage.TREE_NODE__EXPRESSION, oldExpression, expression));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getType() {
        return type;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setType(String newType) {
        String oldType = type;
        type = newType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XmlmapPackage.TREE_NODE__TYPE, oldType, type));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isNullable() {
        return nullable;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNullable(boolean newNullable) {
        boolean oldNullable = nullable;
        nullable = newNullable;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XmlmapPackage.TREE_NODE__NULLABLE, oldNullable, nullable));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getXpath() {
        return xpath;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setXpath(String newXpath) {
        String oldXpath = xpath;
        xpath = newXpath;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XmlmapPackage.TREE_NODE__XPATH, oldXpath, xpath));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isLoop() {
        return loop;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLoop(boolean newLoop) {
        boolean oldLoop = loop;
        loop = newLoop;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XmlmapPackage.TREE_NODE__LOOP, oldLoop, loop));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Connection> getOutgoingConnections() {
        if (outgoingConnections == null) {
            outgoingConnections = new EObjectResolvingEList<Connection>(Connection.class, this, XmlmapPackage.TREE_NODE__OUTGOING_CONNECTIONS);
        }
        return outgoingConnections;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NodeType getNodeType() {
        return nodeType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNodeType(NodeType newNodeType) {
        NodeType oldNodeType = nodeType;
        nodeType = newNodeType == null ? NODE_TYPE_EDEFAULT : newNodeType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XmlmapPackage.TREE_NODE__NODE_TYPE, oldNodeType, nodeType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case XmlmapPackage.TREE_NODE__CHILDREN:
                return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case XmlmapPackage.TREE_NODE__CHILDREN:
                return getChildren();
            case XmlmapPackage.TREE_NODE__NAME:
                return getName();
            case XmlmapPackage.TREE_NODE__EXPRESSION:
                return getExpression();
            case XmlmapPackage.TREE_NODE__TYPE:
                return getType();
            case XmlmapPackage.TREE_NODE__NULLABLE:
                return isNullable();
            case XmlmapPackage.TREE_NODE__XPATH:
                return getXpath();
            case XmlmapPackage.TREE_NODE__LOOP:
                return isLoop();
            case XmlmapPackage.TREE_NODE__OUTGOING_CONNECTIONS:
                return getOutgoingConnections();
            case XmlmapPackage.TREE_NODE__NODE_TYPE:
                return getNodeType();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case XmlmapPackage.TREE_NODE__CHILDREN:
                getChildren().clear();
                getChildren().addAll((Collection<? extends TreeNode>)newValue);
                return;
            case XmlmapPackage.TREE_NODE__NAME:
                setName((String)newValue);
                return;
            case XmlmapPackage.TREE_NODE__EXPRESSION:
                setExpression((String)newValue);
                return;
            case XmlmapPackage.TREE_NODE__TYPE:
                setType((String)newValue);
                return;
            case XmlmapPackage.TREE_NODE__NULLABLE:
                setNullable((Boolean)newValue);
                return;
            case XmlmapPackage.TREE_NODE__XPATH:
                setXpath((String)newValue);
                return;
            case XmlmapPackage.TREE_NODE__LOOP:
                setLoop((Boolean)newValue);
                return;
            case XmlmapPackage.TREE_NODE__OUTGOING_CONNECTIONS:
                getOutgoingConnections().clear();
                getOutgoingConnections().addAll((Collection<? extends Connection>)newValue);
                return;
            case XmlmapPackage.TREE_NODE__NODE_TYPE:
                setNodeType((NodeType)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case XmlmapPackage.TREE_NODE__CHILDREN:
                getChildren().clear();
                return;
            case XmlmapPackage.TREE_NODE__NAME:
                setName(NAME_EDEFAULT);
                return;
            case XmlmapPackage.TREE_NODE__EXPRESSION:
                setExpression(EXPRESSION_EDEFAULT);
                return;
            case XmlmapPackage.TREE_NODE__TYPE:
                setType(TYPE_EDEFAULT);
                return;
            case XmlmapPackage.TREE_NODE__NULLABLE:
                setNullable(NULLABLE_EDEFAULT);
                return;
            case XmlmapPackage.TREE_NODE__XPATH:
                setXpath(XPATH_EDEFAULT);
                return;
            case XmlmapPackage.TREE_NODE__LOOP:
                setLoop(LOOP_EDEFAULT);
                return;
            case XmlmapPackage.TREE_NODE__OUTGOING_CONNECTIONS:
                getOutgoingConnections().clear();
                return;
            case XmlmapPackage.TREE_NODE__NODE_TYPE:
                setNodeType(NODE_TYPE_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case XmlmapPackage.TREE_NODE__CHILDREN:
                return children != null && !children.isEmpty();
            case XmlmapPackage.TREE_NODE__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case XmlmapPackage.TREE_NODE__EXPRESSION:
                return EXPRESSION_EDEFAULT == null ? expression != null : !EXPRESSION_EDEFAULT.equals(expression);
            case XmlmapPackage.TREE_NODE__TYPE:
                return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
            case XmlmapPackage.TREE_NODE__NULLABLE:
                return nullable != NULLABLE_EDEFAULT;
            case XmlmapPackage.TREE_NODE__XPATH:
                return XPATH_EDEFAULT == null ? xpath != null : !XPATH_EDEFAULT.equals(xpath);
            case XmlmapPackage.TREE_NODE__LOOP:
                return loop != LOOP_EDEFAULT;
            case XmlmapPackage.TREE_NODE__OUTGOING_CONNECTIONS:
                return outgoingConnections != null && !outgoingConnections.isEmpty();
            case XmlmapPackage.TREE_NODE__NODE_TYPE:
                return nodeType != NODE_TYPE_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (name: ");
        result.append(name);
        result.append(", expression: ");
        result.append(expression);
        result.append(", type: ");
        result.append(type);
        result.append(", nullable: ");
        result.append(nullable);
        result.append(", xpath: ");
        result.append(xpath);
        result.append(", loop: ");
        result.append(loop);
        result.append(", nodeType: ");
        result.append(nodeType);
        result.append(')');
        return result.toString();
    }

} //TreeNodeImpl
