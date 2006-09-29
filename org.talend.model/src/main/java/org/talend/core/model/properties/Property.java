/**
 * <copyright> </copyright>
 * 
 * $Id$
 */
package org.talend.core.model.properties;

import java.util.Date;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Property</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.talend.core.model.properties.Property#getId <em>Id</em>}</li>
 * <li>{@link org.talend.core.model.properties.Property#getLabel <em>Label</em>}</li>
 * <li>{@link org.talend.core.model.properties.Property#getPurpose <em>Purpose</em>}</li>
 * <li>{@link org.talend.core.model.properties.Property#getDescription <em>Description</em>}</li>
 * <li>{@link org.talend.core.model.properties.Property#getCreationDate <em>Creation Date</em>}</li>
 * <li>{@link org.talend.core.model.properties.Property#getModificationDate <em>Modification Date</em>}</li>
 * <li>{@link org.talend.core.model.properties.Property#getVersion <em>Version</em>}</li>
 * <li>{@link org.talend.core.model.properties.Property#getStatusCode <em>Status Code</em>}</li>
 * <li>{@link org.talend.core.model.properties.Property#getItem <em>Item</em>}</li>
 * <li>{@link org.talend.core.model.properties.Property#getAuthor <em>Author</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.talend.core.model.properties.PropertiesPackage#getProperty()
 * @model
 * @generated
 */
public interface Property extends EObject {

    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Id</em>' attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Id</em>' attribute.
     * @see #setId(String)
     * @see org.talend.core.model.properties.PropertiesPackage#getProperty_Id()
     * @model id="true"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link org.talend.core.model.properties.Property#getId <em>Id</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(String value);

    /**
     * Returns the value of the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Label</em>' attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Label</em>' attribute.
     * @see #setLabel(String)
     * @see org.talend.core.model.properties.PropertiesPackage#getProperty_Label()
     * @model required="true"
     * @generated
     */
    String getLabel();

    /**
     * Sets the value of the '{@link org.talend.core.model.properties.Property#getLabel <em>Label</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Label</em>' attribute.
     * @see #getLabel()
     * @generated
     */
    void setLabel(String value);

    /**
     * Returns the value of the '<em><b>Purpose</b></em>' attribute. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Purpose</em>' attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Purpose</em>' attribute.
     * @see #setPurpose(String)
     * @see org.talend.core.model.properties.PropertiesPackage#getProperty_Purpose()
     * @model
     * @generated
     */
    String getPurpose();

    /**
     * Sets the value of the '{@link org.talend.core.model.properties.Property#getPurpose <em>Purpose</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Purpose</em>' attribute.
     * @see #getPurpose()
     * @generated
     */
    void setPurpose(String value);

    /**
     * Returns the value of the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Description</em>' attribute isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Description</em>' attribute.
     * @see #setDescription(String)
     * @see org.talend.core.model.properties.PropertiesPackage#getProperty_Description()
     * @model
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link org.talend.core.model.properties.Property#getDescription <em>Description</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Description</em>' attribute.
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

    /**
     * Returns the value of the '<em><b>Creation Date</b></em>' attribute. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Creation Date</em>' attribute isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Creation Date</em>' attribute.
     * @see #setCreationDate(Date)
     * @see org.talend.core.model.properties.PropertiesPackage#getProperty_CreationDate()
     * @model required="true"
     * @generated
     */
    Date getCreationDate();

    /**
     * Sets the value of the '{@link org.talend.core.model.properties.Property#getCreationDate <em>Creation Date</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Creation Date</em>' attribute.
     * @see #getCreationDate()
     * @generated
     */
    void setCreationDate(Date value);

    /**
     * Returns the value of the '<em><b>Modification Date</b></em>' attribute. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Modification Date</em>' attribute isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Modification Date</em>' attribute.
     * @see #setModificationDate(Date)
     * @see org.talend.core.model.properties.PropertiesPackage#getProperty_ModificationDate()
     * @model required="true"
     * @generated
     */
    Date getModificationDate();

    /**
     * Sets the value of the '{@link org.talend.core.model.properties.Property#getModificationDate <em>Modification Date</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Modification Date</em>' attribute.
     * @see #getModificationDate()
     * @generated
     */
    void setModificationDate(Date value);

    /**
     * Returns the value of the '<em><b>Author</b></em>' reference. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Author</em>' attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Author</em>' reference.
     * @see #setAuthor(User)
     * @see org.talend.core.model.properties.PropertiesPackage#getProperty_Author()
     * @model
     * @generated
     */
    User getAuthor();

    /**
     * Sets the value of the '{@link org.talend.core.model.properties.Property#getAuthor <em>Author</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Author</em>' reference.
     * @see #getAuthor()
     * @generated
     */
    void setAuthor(User value);

    /**
     * Returns the value of the '<em><b>Version</b></em>' attribute. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Version</em>' attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Version</em>' attribute.
     * @see #setVersion(String)
     * @see org.talend.core.model.properties.PropertiesPackage#getProperty_Version()
     * @model required="true"
     * @generated
     */
    String getVersion();

    /**
     * Sets the value of the '{@link org.talend.core.model.properties.Property#getVersion <em>Version</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Version</em>' attribute.
     * @see #getVersion()
     * @generated
     */
    void setVersion(String value);

    /**
     * Returns the value of the '<em><b>Status Code</b></em>' attribute. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Status Code</em>' attribute isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Status Code</em>' attribute.
     * @see #setStatusCode(String)
     * @see org.talend.core.model.properties.PropertiesPackage#getProperty_StatusCode()
     * @model
     * @generated
     */
    String getStatusCode();

    /**
     * Sets the value of the '{@link org.talend.core.model.properties.Property#getStatusCode <em>Status Code</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Status Code</em>' attribute.
     * @see #getStatusCode()
     * @generated
     */
    void setStatusCode(String value);

    /**
     * Returns the value of the '<em><b>Item</b></em>' reference. It is bidirectional and its opposite is '{@link org.talend.core.model.properties.Item#getProperty <em>Property</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Item</em>' reference isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Item</em>' reference.
     * @see #setItem(Item)
     * @see org.talend.core.model.properties.PropertiesPackage#getProperty_Item()
     * @see org.talend.core.model.properties.Item#getProperty
     * @model opposite="property"
     * @generated
     */
    Item getItem();

    /**
     * Sets the value of the '{@link org.talend.core.model.properties.Property#getItem <em>Item</em>}' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value the new value of the '<em>Item</em>' reference.
     * @see #getItem()
     * @generated
     */
    void setItem(Item value);

} // Property
