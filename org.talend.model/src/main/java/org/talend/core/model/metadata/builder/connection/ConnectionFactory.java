/**
 * <copyright> </copyright>
 * 
 * $Id$
 */
package org.talend.core.model.metadata.builder.connection;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 * @see org.talend.core.model.metadata.builder.connection.ConnectionPackage
 * @generated
 */
public interface ConnectionFactory extends EFactory {

    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    ConnectionFactory eINSTANCE = org.talend.core.model.metadata.builder.connection.impl.ConnectionFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Metadata</em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return a new object of class '<em>Metadata</em>'.
     * @generated
     */
    Metadata createMetadata();

    /**
     * Returns a new object of class '<em>Connection</em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return a new object of class '<em>Connection</em>'.
     * @generated
     */
    Connection createConnection();

    /**
     * Returns a new object of class '<em>Metadata Table</em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return a new object of class '<em>Metadata Table</em>'.
     * @generated
     */
    MetadataTable createMetadataTable();

    /**
     * Returns a new object of class '<em>Metadata Column</em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return a new object of class '<em>Metadata Column</em>'.
     * @generated
     */
    MetadataColumn createMetadataColumn();

    /**
     * Returns a new object of class '<em>Delimited File Connection</em>'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @return a new object of class '<em>Delimited File Connection</em>'.
     * @generated
     */
    DelimitedFileConnection createDelimitedFileConnection();

    /**
     * Returns a new object of class '<em>Positional File Connection</em>'.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @return a new object of class '<em>Positional File Connection</em>'.
     * @generated
     */
    PositionalFileConnection createPositionalFileConnection();

    /**
     * Returns a new object of class '<em>Ebcdic Connection</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Ebcdic Connection</em>'.
     * @generated
     */
    EbcdicConnection createEbcdicConnection();

    /**
     * Returns a new object of class '<em>MDM Connection</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>MDM Connection</em>'.
     * @generated
     */
    MDMConnection createMDMConnection();

    /**
     * Returns a new object of class '<em>Database Connection</em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return a new object of class '<em>Database Connection</em>'.
     * @generated
     */
    DatabaseConnection createDatabaseConnection();

    /**
     * Returns a new object of class '<em>SAP Connection</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>SAP Connection</em>'.
     * @generated
     */
    SAPConnection createSAPConnection();

    /**
     * Returns a new object of class '<em>SAP Function Unit</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>SAP Function Unit</em>'.
     * @generated
     */
    SAPFunctionUnit createSAPFunctionUnit();

    /**
     * Returns a new object of class '<em>SAP Function Parameter Column</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>SAP Function Parameter Column</em>'.
     * @generated
     */
    SAPFunctionParameterColumn createSAPFunctionParameterColumn();

    /**
     * Returns a new object of class '<em>SAP Function Parameter Table</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>SAP Function Parameter Table</em>'.
     * @generated
     */
    SAPFunctionParameterTable createSAPFunctionParameterTable();

    /**
     * Returns a new object of class '<em>Input SAP Function Parameter Table</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Input SAP Function Parameter Table</em>'.
     * @generated
     */
    InputSAPFunctionParameterTable createInputSAPFunctionParameterTable();

    /**
     * Returns a new object of class '<em>Output SAP Function Parameter Table</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Output SAP Function Parameter Table</em>'.
     * @generated
     */
    OutputSAPFunctionParameterTable createOutputSAPFunctionParameterTable();

    /**
     * Returns a new object of class '<em>Regexp File Connection</em>'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @return a new object of class '<em>Regexp File Connection</em>'.
     * @generated
     */
    RegexpFileConnection createRegexpFileConnection();

    /**
     * Returns a new object of class '<em>Xml File Connection</em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return a new object of class '<em>Xml File Connection</em>'.
     * @generated
     */
    XmlFileConnection createXmlFileConnection();

    /**
     * Returns a new object of class '<em>Schema Target</em>'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return a new object of class '<em>Schema Target</em>'.
     * @generated
     */
    SchemaTarget createSchemaTarget();

    /**
     * Returns a new object of class '<em>Queries Connection</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Queries Connection</em>'.
     * @generated
     */
    QueriesConnection createQueriesConnection();

    /**
     * Returns a new object of class '<em>Query</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Query</em>'.
     * @generated
     */
    Query createQuery();

    /**
     * Returns a new object of class '<em>Ldif File Connection</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Ldif File Connection</em>'.
     * @generated
     */
    LdifFileConnection createLdifFileConnection();

    /**
     * Returns a new object of class '<em>File Excel Connection</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>File Excel Connection</em>'.
     * @generated
     */
    FileExcelConnection createFileExcelConnection();

    /**
     * Returns a new object of class '<em>Xml XPath Loop Descriptor</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Xml XPath Loop Descriptor</em>'.
     * @generated
     */
    XmlXPathLoopDescriptor createXmlXPathLoopDescriptor();

    /**
     * Returns a new object of class '<em>Generic Schema Connection</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Generic Schema Connection</em>'.
     * @generated
     */
    GenericSchemaConnection createGenericSchemaConnection();

    /**
     * Returns a new object of class '<em>LDAP Schema Connection</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>LDAP Schema Connection</em>'.
     * @generated
     */
    LDAPSchemaConnection createLDAPSchemaConnection();

    /**
     * Returns a new object of class '<em>WSDL Schema Connection</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>WSDL Schema Connection</em>'.
     * @generated
     */
    WSDLSchemaConnection createWSDLSchemaConnection();

    /**
     * Returns a new object of class '<em>Salesforce Schema Connection</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Salesforce Schema Connection</em>'.
     * @generated
     */
    SalesforceSchemaConnection createSalesforceSchemaConnection();

    /**
     * Returns a new object of class '<em>CDC Connection</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>CDC Connection</em>'.
     * @generated
     */
    CDCConnection createCDCConnection();

    /**
     * Returns a new object of class '<em>CDC Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>CDC Type</em>'.
     * @generated
     */
    CDCType createCDCType();

    /**
     * Returns a new object of class '<em>Subscriber Table</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Subscriber Table</em>'.
     * @generated
     */
    SubscriberTable createSubscriberTable();

    /**
     * Returns a new object of class '<em>SAP Test Input Parameter Table</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>SAP Test Input Parameter Table</em>'.
     * @generated
     */
    SAPTestInputParameterTable createSAPTestInputParameterTable();

    /**
     * Returns a new object of class '<em>Concept</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Concept</em>'.
     * @generated
     */
    Concept createConcept();

    /**
     * Returns a new object of class '<em>Concept Target</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Concept Target</em>'.
     * @generated
     */
    ConceptTarget createConceptTarget();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    ConnectionPackage getConnectionPackage();

    /**
     * copy this MetadataColumn.
     * 
     * @return
     */
    MetadataColumn copy(MetadataColumn column, String newId);

} // ConnectionFactory
