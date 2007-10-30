/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.core.model.utils.emf.component.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.talend.designer.core.model.utils.emf.component.ComponentPackage;
import org.talend.designer.core.model.utils.emf.component.IMPORTType;

import org.talend.designer.core.model.utils.emf.component.INSTALLType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IMPORT Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.talend.designer.core.model.utils.emf.component.impl.IMPORTTypeImpl#getINSTALL <em>INSTALL</em>}</li>
 *   <li>{@link org.talend.designer.core.model.utils.emf.component.impl.IMPORTTypeImpl#getMESSAGE <em>MESSAGE</em>}</li>
 *   <li>{@link org.talend.designer.core.model.utils.emf.component.impl.IMPORTTypeImpl#getMODULE <em>MODULE</em>}</li>
 *   <li>{@link org.talend.designer.core.model.utils.emf.component.impl.IMPORTTypeImpl#getNAME <em>NAME</em>}</li>
 *   <li>{@link org.talend.designer.core.model.utils.emf.component.impl.IMPORTTypeImpl#isREQUIRED <em>REQUIRED</em>}</li>
 *   <li>{@link org.talend.designer.core.model.utils.emf.component.impl.IMPORTTypeImpl#isSHOW <em>SHOW</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IMPORTTypeImpl extends EObjectImpl implements IMPORTType {
    /**
     * The cached value of the '{@link #getINSTALL() <em>INSTALL</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getINSTALL()
     * @generated
     * @ordered
     */
    protected EList iNSTALL = null;

    /**
     * The default value of the '{@link #getMESSAGE() <em>MESSAGE</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMESSAGE()
     * @generated
     * @ordered
     */
    protected static final String MESSAGE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getMESSAGE() <em>MESSAGE</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMESSAGE()
     * @generated
     * @ordered
     */
    protected String mESSAGE = MESSAGE_EDEFAULT;

    /**
     * The default value of the '{@link #getMODULE() <em>MODULE</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMODULE()
     * @generated
     * @ordered
     */
    protected static final String MODULE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getMODULE() <em>MODULE</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMODULE()
     * @generated
     * @ordered
     */
    protected String mODULE = MODULE_EDEFAULT;

    /**
     * The default value of the '{@link #getNAME() <em>NAME</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNAME()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getNAME() <em>NAME</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNAME()
     * @generated
     * @ordered
     */
    protected String nAME = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #isREQUIRED() <em>REQUIRED</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isREQUIRED()
     * @generated
     * @ordered
     */
    protected static final boolean REQUIRED_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isREQUIRED() <em>REQUIRED</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isREQUIRED()
     * @generated
     * @ordered
     */
    protected boolean rEQUIRED = REQUIRED_EDEFAULT;

    /**
     * This is true if the REQUIRED attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean rEQUIREDESet= false;

    /**
     * The default value of the '{@link #isSHOW() <em>SHOW</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSHOW()
     * @generated
     * @ordered
     */
    protected static final boolean SHOW_EDEFAULT = true;

    /**
     * The cached value of the '{@link #isSHOW() <em>SHOW</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSHOW()
     * @generated
     * @ordered
     */
    protected boolean sHOW = SHOW_EDEFAULT;

    /**
     * This is true if the SHOW attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean sHOWESet= false;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected IMPORTTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected EClass eStaticClass() {
        return ComponentPackage.Literals.IMPORT_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList getINSTALL() {
        if (iNSTALL == null) {
            iNSTALL = new EObjectContainmentEList(INSTALLType.class, this, ComponentPackage.IMPORT_TYPE__INSTALL);
        }
        return iNSTALL;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getMESSAGE() {
        return mESSAGE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMESSAGE(String newMESSAGE) {
        String oldMESSAGE = mESSAGE;
        mESSAGE = newMESSAGE;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.IMPORT_TYPE__MESSAGE, oldMESSAGE, mESSAGE));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getMODULE() {
        return mODULE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMODULE(String newMODULE) {
        String oldMODULE = mODULE;
        mODULE = newMODULE;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.IMPORT_TYPE__MODULE, oldMODULE, mODULE));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getNAME() {
        return nAME;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNAME(String newNAME) {
        String oldNAME = nAME;
        nAME = newNAME;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.IMPORT_TYPE__NAME, oldNAME, nAME));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isREQUIRED() {
        return rEQUIRED;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setREQUIRED(boolean newREQUIRED) {
        boolean oldREQUIRED = rEQUIRED;
        rEQUIRED = newREQUIRED;
        boolean oldREQUIREDESet = rEQUIREDESet;
        rEQUIREDESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.IMPORT_TYPE__REQUIRED, oldREQUIRED, rEQUIRED, !oldREQUIREDESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetREQUIRED() {
        boolean oldREQUIRED = rEQUIRED;
        boolean oldREQUIREDESet = rEQUIREDESet;
        rEQUIRED = REQUIRED_EDEFAULT;
        rEQUIREDESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, ComponentPackage.IMPORT_TYPE__REQUIRED, oldREQUIRED, REQUIRED_EDEFAULT, oldREQUIREDESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetREQUIRED() {
        return rEQUIREDESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSHOW() {
        return sHOW;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSHOW(boolean newSHOW) {
        boolean oldSHOW = sHOW;
        sHOW = newSHOW;
        boolean oldSHOWESet = sHOWESet;
        sHOWESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.IMPORT_TYPE__SHOW, oldSHOW, sHOW, !oldSHOWESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetSHOW() {
        boolean oldSHOW = sHOW;
        boolean oldSHOWESet = sHOWESet;
        sHOW = SHOW_EDEFAULT;
        sHOWESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, ComponentPackage.IMPORT_TYPE__SHOW, oldSHOW, SHOW_EDEFAULT, oldSHOWESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetSHOW() {
        return sHOWESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ComponentPackage.IMPORT_TYPE__INSTALL:
                return ((InternalEList)getINSTALL()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ComponentPackage.IMPORT_TYPE__INSTALL:
                return getINSTALL();
            case ComponentPackage.IMPORT_TYPE__MESSAGE:
                return getMESSAGE();
            case ComponentPackage.IMPORT_TYPE__MODULE:
                return getMODULE();
            case ComponentPackage.IMPORT_TYPE__NAME:
                return getNAME();
            case ComponentPackage.IMPORT_TYPE__REQUIRED:
                return isREQUIRED() ? Boolean.TRUE : Boolean.FALSE;
            case ComponentPackage.IMPORT_TYPE__SHOW:
                return isSHOW() ? Boolean.TRUE : Boolean.FALSE;
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
            case ComponentPackage.IMPORT_TYPE__INSTALL:
                getINSTALL().clear();
                getINSTALL().addAll((Collection)newValue);
                return;
            case ComponentPackage.IMPORT_TYPE__MESSAGE:
                setMESSAGE((String)newValue);
                return;
            case ComponentPackage.IMPORT_TYPE__MODULE:
                setMODULE((String)newValue);
                return;
            case ComponentPackage.IMPORT_TYPE__NAME:
                setNAME((String)newValue);
                return;
            case ComponentPackage.IMPORT_TYPE__REQUIRED:
                setREQUIRED(((Boolean)newValue).booleanValue());
                return;
            case ComponentPackage.IMPORT_TYPE__SHOW:
                setSHOW(((Boolean)newValue).booleanValue());
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
            case ComponentPackage.IMPORT_TYPE__INSTALL:
                getINSTALL().clear();
                return;
            case ComponentPackage.IMPORT_TYPE__MESSAGE:
                setMESSAGE(MESSAGE_EDEFAULT);
                return;
            case ComponentPackage.IMPORT_TYPE__MODULE:
                setMODULE(MODULE_EDEFAULT);
                return;
            case ComponentPackage.IMPORT_TYPE__NAME:
                setNAME(NAME_EDEFAULT);
                return;
            case ComponentPackage.IMPORT_TYPE__REQUIRED:
                unsetREQUIRED();
                return;
            case ComponentPackage.IMPORT_TYPE__SHOW:
                unsetSHOW();
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
            case ComponentPackage.IMPORT_TYPE__INSTALL:
                return iNSTALL != null && !iNSTALL.isEmpty();
            case ComponentPackage.IMPORT_TYPE__MESSAGE:
                return MESSAGE_EDEFAULT == null ? mESSAGE != null : !MESSAGE_EDEFAULT.equals(mESSAGE);
            case ComponentPackage.IMPORT_TYPE__MODULE:
                return MODULE_EDEFAULT == null ? mODULE != null : !MODULE_EDEFAULT.equals(mODULE);
            case ComponentPackage.IMPORT_TYPE__NAME:
                return NAME_EDEFAULT == null ? nAME != null : !NAME_EDEFAULT.equals(nAME);
            case ComponentPackage.IMPORT_TYPE__REQUIRED:
                return isSetREQUIRED();
            case ComponentPackage.IMPORT_TYPE__SHOW:
                return isSetSHOW();
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
        result.append(" (mESSAGE: ");
        result.append(mESSAGE);
        result.append(", mODULE: ");
        result.append(mODULE);
        result.append(", nAME: ");
        result.append(nAME);
        result.append(", rEQUIRED: ");
        if (rEQUIREDESet) result.append(rEQUIRED); else result.append("<unset>");
        result.append(", sHOW: ");
        if (sHOWESet) result.append(sHOW); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //IMPORTTypeImpl