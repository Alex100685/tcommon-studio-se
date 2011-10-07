/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.core.model.properties.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.talend.core.model.properties.ExecutionTask;
import org.talend.core.model.properties.ExecutionTaskProperties;
import org.talend.core.model.properties.PropertiesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Execution Task Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.talend.core.model.properties.impl.ExecutionTaskPropertiesImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.talend.core.model.properties.impl.ExecutionTaskPropertiesImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.talend.core.model.properties.impl.ExecutionTaskPropertiesImpl#getValue <em>Value</em>}</li>
 *   <li>{@link org.talend.core.model.properties.impl.ExecutionTaskPropertiesImpl#getExecutionTask <em>Execution Task</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExecutionTaskPropertiesImpl extends EObjectImpl implements ExecutionTaskProperties {
    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final int ID_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected int id = ID_EDEFAULT;

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
     * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getValue()
     * @generated
     * @ordered
     */
    protected static final String VALUE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getValue()
     * @generated
     * @ordered
     */
    protected String value = VALUE_EDEFAULT;

    /**
     * The cached value of the '{@link #getExecutionTask() <em>Execution Task</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExecutionTask()
     * @generated
     * @ordered
     */
    protected ExecutionTask executionTask;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ExecutionTaskPropertiesImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected EClass eStaticClass() {
        return PropertiesPackage.Literals.EXECUTION_TASK_PROPERTIES;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setId(int newId) {
        int oldId = id;
        id = newId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PropertiesPackage.EXECUTION_TASK_PROPERTIES__ID, oldId, id));
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
            eNotify(new ENotificationImpl(this, Notification.SET, PropertiesPackage.EXECUTION_TASK_PROPERTIES__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getValue() {
        return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setValue(String newValue) {
        String oldValue = value;
        value = newValue;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PropertiesPackage.EXECUTION_TASK_PROPERTIES__VALUE, oldValue, value));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExecutionTask getExecutionTask() {
        if (executionTask != null && executionTask.eIsProxy()) {
            InternalEObject oldExecutionTask = (InternalEObject)executionTask;
            executionTask = (ExecutionTask)eResolveProxy(oldExecutionTask);
            if (executionTask != oldExecutionTask) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, PropertiesPackage.EXECUTION_TASK_PROPERTIES__EXECUTION_TASK, oldExecutionTask, executionTask));
            }
        }
        return executionTask;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExecutionTask basicGetExecutionTask() {
        return executionTask;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setExecutionTask(ExecutionTask newExecutionTask) {
        ExecutionTask oldExecutionTask = executionTask;
        executionTask = newExecutionTask;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PropertiesPackage.EXECUTION_TASK_PROPERTIES__EXECUTION_TASK, oldExecutionTask, executionTask));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PropertiesPackage.EXECUTION_TASK_PROPERTIES__ID:
                return new Integer(getId());
            case PropertiesPackage.EXECUTION_TASK_PROPERTIES__NAME:
                return getName();
            case PropertiesPackage.EXECUTION_TASK_PROPERTIES__VALUE:
                return getValue();
            case PropertiesPackage.EXECUTION_TASK_PROPERTIES__EXECUTION_TASK:
                if (resolve) return getExecutionTask();
                return basicGetExecutionTask();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case PropertiesPackage.EXECUTION_TASK_PROPERTIES__ID:
                setId(((Integer)newValue).intValue());
                return;
            case PropertiesPackage.EXECUTION_TASK_PROPERTIES__NAME:
                setName((String)newValue);
                return;
            case PropertiesPackage.EXECUTION_TASK_PROPERTIES__VALUE:
                setValue((String)newValue);
                return;
            case PropertiesPackage.EXECUTION_TASK_PROPERTIES__EXECUTION_TASK:
                setExecutionTask((ExecutionTask)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void eUnset(int featureID) {
        switch (featureID) {
            case PropertiesPackage.EXECUTION_TASK_PROPERTIES__ID:
                setId(ID_EDEFAULT);
                return;
            case PropertiesPackage.EXECUTION_TASK_PROPERTIES__NAME:
                setName(NAME_EDEFAULT);
                return;
            case PropertiesPackage.EXECUTION_TASK_PROPERTIES__VALUE:
                setValue(VALUE_EDEFAULT);
                return;
            case PropertiesPackage.EXECUTION_TASK_PROPERTIES__EXECUTION_TASK:
                setExecutionTask((ExecutionTask)null);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case PropertiesPackage.EXECUTION_TASK_PROPERTIES__ID:
                return id != ID_EDEFAULT;
            case PropertiesPackage.EXECUTION_TASK_PROPERTIES__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case PropertiesPackage.EXECUTION_TASK_PROPERTIES__VALUE:
                return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
            case PropertiesPackage.EXECUTION_TASK_PROPERTIES__EXECUTION_TASK:
                return executionTask != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (id: ");
        result.append(id);
        result.append(", name: ");
        result.append(name);
        result.append(", value: ");
        result.append(value);
        result.append(')');
        return result.toString();
    }

} //ExecutionTaskPropertiesImpl
