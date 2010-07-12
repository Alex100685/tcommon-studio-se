/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.core.model.metadata.builder.connection.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.talend.core.model.metadata.builder.connection.ConnectionPackage;
import org.talend.core.model.metadata.builder.connection.SAPConnection;
import org.talend.core.model.metadata.builder.connection.SAPFunctionUnit;
import org.talend.core.model.metadata.builder.connection.SAPIDocUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>SAP Connection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.talend.core.model.metadata.builder.connection.impl.SAPConnectionImpl#getHost <em>Host</em>}</li>
 *   <li>{@link org.talend.core.model.metadata.builder.connection.impl.SAPConnectionImpl#getUsername <em>Username</em>}</li>
 *   <li>{@link org.talend.core.model.metadata.builder.connection.impl.SAPConnectionImpl#getPassword <em>Password</em>}</li>
 *   <li>{@link org.talend.core.model.metadata.builder.connection.impl.SAPConnectionImpl#getClient <em>Client</em>}</li>
 *   <li>{@link org.talend.core.model.metadata.builder.connection.impl.SAPConnectionImpl#getSystemNumber <em>System Number</em>}</li>
 *   <li>{@link org.talend.core.model.metadata.builder.connection.impl.SAPConnectionImpl#getLanguage <em>Language</em>}</li>
 *   <li>{@link org.talend.core.model.metadata.builder.connection.impl.SAPConnectionImpl#getFuntions <em>Funtions</em>}</li>
 *   <li>{@link org.talend.core.model.metadata.builder.connection.impl.SAPConnectionImpl#getCurrentFucntion <em>Current Fucntion</em>}</li>
 *   <li>{@link org.talend.core.model.metadata.builder.connection.impl.SAPConnectionImpl#getIDocs <em>IDocs</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SAPConnectionImpl extends ConnectionImpl implements SAPConnection {
	/**
     * The default value of the '{@link #getHost() <em>Host</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getHost()
     * @generated
     * @ordered
     */
	protected static final String HOST_EDEFAULT = null;

	/**
     * The cached value of the '{@link #getHost() <em>Host</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getHost()
     * @generated
     * @ordered
     */
	protected String host = HOST_EDEFAULT;

	/**
     * The default value of the '{@link #getUsername() <em>Username</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getUsername()
     * @generated
     * @ordered
     */
	protected static final String USERNAME_EDEFAULT = null;

	/**
     * The cached value of the '{@link #getUsername() <em>Username</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getUsername()
     * @generated
     * @ordered
     */
	protected String username = USERNAME_EDEFAULT;

	/**
     * The default value of the '{@link #getPassword() <em>Password</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getPassword()
     * @generated
     * @ordered
     */
	protected static final String PASSWORD_EDEFAULT = null;

	/**
     * The cached value of the '{@link #getPassword() <em>Password</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getPassword()
     * @generated
     * @ordered
     */
	protected String password = PASSWORD_EDEFAULT;

	/**
     * The default value of the '{@link #getClient() <em>Client</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getClient()
     * @generated
     * @ordered
     */
	protected static final String CLIENT_EDEFAULT = null;

	/**
     * The cached value of the '{@link #getClient() <em>Client</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getClient()
     * @generated
     * @ordered
     */
	protected String client = CLIENT_EDEFAULT;

	/**
     * The default value of the '{@link #getSystemNumber() <em>System Number</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getSystemNumber()
     * @generated
     * @ordered
     */
	protected static final String SYSTEM_NUMBER_EDEFAULT = null;

	/**
     * The cached value of the '{@link #getSystemNumber() <em>System Number</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getSystemNumber()
     * @generated
     * @ordered
     */
	protected String systemNumber = SYSTEM_NUMBER_EDEFAULT;

	/**
     * The default value of the '{@link #getLanguage() <em>Language</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getLanguage()
     * @generated
     * @ordered
     */
	protected static final String LANGUAGE_EDEFAULT = null;

	/**
     * The cached value of the '{@link #getLanguage() <em>Language</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getLanguage()
     * @generated
     * @ordered
     */
	protected String language = LANGUAGE_EDEFAULT;

	/**
     * The cached value of the '{@link #getFuntions() <em>Funtions</em>}' containment reference list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getFuntions()
     * @generated
     * @ordered
     */
	protected EList funtions;

	/**
     * The default value of the '{@link #getCurrentFucntion() <em>Current Fucntion</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getCurrentFucntion()
     * @generated
     * @ordered
     */
	protected static final String CURRENT_FUCNTION_EDEFAULT = null;

	/**
     * The cached value of the '{@link #getCurrentFucntion() <em>Current Fucntion</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getCurrentFucntion()
     * @generated
     * @ordered
     */
	protected String currentFucntion = CURRENT_FUCNTION_EDEFAULT;

	/**
     * The cached value of the '{@link #getIDocs() <em>IDocs</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIDocs()
     * @generated
     * @ordered
     */
    protected EList iDocs;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected SAPConnectionImpl() {
        super();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected EClass eStaticClass() {
        return ConnectionPackage.Literals.SAP_CONNECTION;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String getHost() {
        return host;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setHost(String newHost) {
        String oldHost = host;
        host = newHost;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ConnectionPackage.SAP_CONNECTION__HOST, oldHost, host));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String getUsername() {
        return username;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setUsername(String newUsername) {
        String oldUsername = username;
        username = newUsername;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ConnectionPackage.SAP_CONNECTION__USERNAME, oldUsername, username));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String getPassword() {
        return password;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setPassword(String newPassword) {
        String oldPassword = password;
        password = newPassword;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ConnectionPackage.SAP_CONNECTION__PASSWORD, oldPassword, password));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String getClient() {
        return client;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setClient(String newClient) {
        String oldClient = client;
        client = newClient;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ConnectionPackage.SAP_CONNECTION__CLIENT, oldClient, client));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String getSystemNumber() {
        return systemNumber;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setSystemNumber(String newSystemNumber) {
        String oldSystemNumber = systemNumber;
        systemNumber = newSystemNumber;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ConnectionPackage.SAP_CONNECTION__SYSTEM_NUMBER, oldSystemNumber, systemNumber));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String getLanguage() {
        return language;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setLanguage(String newLanguage) {
        String oldLanguage = language;
        language = newLanguage;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ConnectionPackage.SAP_CONNECTION__LANGUAGE, oldLanguage, language));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EList getFuntions() {
        if (funtions == null) {
            funtions = new EObjectContainmentWithInverseEList(SAPFunctionUnit.class, this, ConnectionPackage.SAP_CONNECTION__FUNTIONS, ConnectionPackage.SAP_FUNCTION_UNIT__CONNECTION);
        }
        return funtions;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String getCurrentFucntion() {
        return currentFucntion;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setCurrentFucntion(String newCurrentFucntion) {
        String oldCurrentFucntion = currentFucntion;
        currentFucntion = newCurrentFucntion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ConnectionPackage.SAP_CONNECTION__CURRENT_FUCNTION, oldCurrentFucntion, currentFucntion));
    }

	/**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList getIDocs() {
        if (iDocs == null) {
            iDocs = new EObjectContainmentWithInverseEList(SAPIDocUnit.class, this, ConnectionPackage.SAP_CONNECTION__IDOCS, ConnectionPackage.SAPI_DOC_UNIT__CONNECTION);
        }
        return iDocs;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ConnectionPackage.SAP_CONNECTION__FUNTIONS:
                return ((InternalEList)getFuntions()).basicAdd(otherEnd, msgs);
            case ConnectionPackage.SAP_CONNECTION__IDOCS:
                return ((InternalEList)getIDocs()).basicAdd(otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ConnectionPackage.SAP_CONNECTION__FUNTIONS:
                return ((InternalEList)getFuntions()).basicRemove(otherEnd, msgs);
            case ConnectionPackage.SAP_CONNECTION__IDOCS:
                return ((InternalEList)getIDocs()).basicRemove(otherEnd, msgs);
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
            case ConnectionPackage.SAP_CONNECTION__HOST:
                return getHost();
            case ConnectionPackage.SAP_CONNECTION__USERNAME:
                return getUsername();
            case ConnectionPackage.SAP_CONNECTION__PASSWORD:
                return getPassword();
            case ConnectionPackage.SAP_CONNECTION__CLIENT:
                return getClient();
            case ConnectionPackage.SAP_CONNECTION__SYSTEM_NUMBER:
                return getSystemNumber();
            case ConnectionPackage.SAP_CONNECTION__LANGUAGE:
                return getLanguage();
            case ConnectionPackage.SAP_CONNECTION__FUNTIONS:
                return getFuntions();
            case ConnectionPackage.SAP_CONNECTION__CURRENT_FUCNTION:
                return getCurrentFucntion();
            case ConnectionPackage.SAP_CONNECTION__IDOCS:
                return getIDocs();
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
            case ConnectionPackage.SAP_CONNECTION__HOST:
                setHost((String)newValue);
                return;
            case ConnectionPackage.SAP_CONNECTION__USERNAME:
                setUsername((String)newValue);
                return;
            case ConnectionPackage.SAP_CONNECTION__PASSWORD:
                setPassword((String)newValue);
                return;
            case ConnectionPackage.SAP_CONNECTION__CLIENT:
                setClient((String)newValue);
                return;
            case ConnectionPackage.SAP_CONNECTION__SYSTEM_NUMBER:
                setSystemNumber((String)newValue);
                return;
            case ConnectionPackage.SAP_CONNECTION__LANGUAGE:
                setLanguage((String)newValue);
                return;
            case ConnectionPackage.SAP_CONNECTION__FUNTIONS:
                getFuntions().clear();
                getFuntions().addAll((Collection)newValue);
                return;
            case ConnectionPackage.SAP_CONNECTION__CURRENT_FUCNTION:
                setCurrentFucntion((String)newValue);
                return;
            case ConnectionPackage.SAP_CONNECTION__IDOCS:
                getIDocs().clear();
                getIDocs().addAll((Collection)newValue);
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
            case ConnectionPackage.SAP_CONNECTION__HOST:
                setHost(HOST_EDEFAULT);
                return;
            case ConnectionPackage.SAP_CONNECTION__USERNAME:
                setUsername(USERNAME_EDEFAULT);
                return;
            case ConnectionPackage.SAP_CONNECTION__PASSWORD:
                setPassword(PASSWORD_EDEFAULT);
                return;
            case ConnectionPackage.SAP_CONNECTION__CLIENT:
                setClient(CLIENT_EDEFAULT);
                return;
            case ConnectionPackage.SAP_CONNECTION__SYSTEM_NUMBER:
                setSystemNumber(SYSTEM_NUMBER_EDEFAULT);
                return;
            case ConnectionPackage.SAP_CONNECTION__LANGUAGE:
                setLanguage(LANGUAGE_EDEFAULT);
                return;
            case ConnectionPackage.SAP_CONNECTION__FUNTIONS:
                getFuntions().clear();
                return;
            case ConnectionPackage.SAP_CONNECTION__CURRENT_FUCNTION:
                setCurrentFucntion(CURRENT_FUCNTION_EDEFAULT);
                return;
            case ConnectionPackage.SAP_CONNECTION__IDOCS:
                getIDocs().clear();
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
            case ConnectionPackage.SAP_CONNECTION__HOST:
                return HOST_EDEFAULT == null ? host != null : !HOST_EDEFAULT.equals(host);
            case ConnectionPackage.SAP_CONNECTION__USERNAME:
                return USERNAME_EDEFAULT == null ? username != null : !USERNAME_EDEFAULT.equals(username);
            case ConnectionPackage.SAP_CONNECTION__PASSWORD:
                return PASSWORD_EDEFAULT == null ? password != null : !PASSWORD_EDEFAULT.equals(password);
            case ConnectionPackage.SAP_CONNECTION__CLIENT:
                return CLIENT_EDEFAULT == null ? client != null : !CLIENT_EDEFAULT.equals(client);
            case ConnectionPackage.SAP_CONNECTION__SYSTEM_NUMBER:
                return SYSTEM_NUMBER_EDEFAULT == null ? systemNumber != null : !SYSTEM_NUMBER_EDEFAULT.equals(systemNumber);
            case ConnectionPackage.SAP_CONNECTION__LANGUAGE:
                return LANGUAGE_EDEFAULT == null ? language != null : !LANGUAGE_EDEFAULT.equals(language);
            case ConnectionPackage.SAP_CONNECTION__FUNTIONS:
                return funtions != null && !funtions.isEmpty();
            case ConnectionPackage.SAP_CONNECTION__CURRENT_FUCNTION:
                return CURRENT_FUCNTION_EDEFAULT == null ? currentFucntion != null : !CURRENT_FUCNTION_EDEFAULT.equals(currentFucntion);
            case ConnectionPackage.SAP_CONNECTION__IDOCS:
                return iDocs != null && !iDocs.isEmpty();
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
        result.append(" (Host: ");
        result.append(host);
        result.append(", Username: ");
        result.append(username);
        result.append(", Password: ");
        result.append(password);
        result.append(", Client: ");
        result.append(client);
        result.append(", SystemNumber: ");
        result.append(systemNumber);
        result.append(", Language: ");
        result.append(language);
        result.append(", currentFucntion: ");
        result.append(currentFucntion);
        result.append(')');
        return result.toString();
    }

} //SAPConnectionImpl
