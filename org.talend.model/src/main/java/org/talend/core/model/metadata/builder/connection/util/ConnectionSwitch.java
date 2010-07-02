/**
 * <copyright> </copyright>
 * 
 * $Id$
 */
package org.talend.core.model.metadata.builder.connection.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.talend.core.model.metadata.builder.connection.*;
import org.talend.core.model.metadata.builder.connection.AbstractMetadataObject;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.metadata.builder.connection.ConnectionPackage;
import org.talend.core.model.metadata.builder.connection.DatabaseConnection;
import org.talend.core.model.metadata.builder.connection.DelimitedFileConnection;
import org.talend.core.model.metadata.builder.connection.FileConnection;
import org.talend.core.model.metadata.builder.connection.GenericSchemaConnection;
import org.talend.core.model.metadata.builder.connection.LDAPSchemaConnection;
import org.talend.core.model.metadata.builder.connection.LdifFileConnection;
import org.talend.core.model.metadata.builder.connection.Metadata;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.metadata.builder.connection.PositionalFileConnection;
import org.talend.core.model.metadata.builder.connection.QueriesConnection;
import org.talend.core.model.metadata.builder.connection.Query;
import org.talend.core.model.metadata.builder.connection.RegexpFileConnection;
import org.talend.core.model.metadata.builder.connection.SchemaTarget;
import org.talend.core.model.metadata.builder.connection.XmlFileConnection;
import org.talend.core.model.metadata.builder.connection.XmlXPathLoopDescriptor;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 * @see org.talend.core.model.metadata.builder.connection.ConnectionPackage
 * @generated
 */
public class ConnectionSwitch {

    /**
     * The cached model package
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected static ConnectionPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ConnectionSwitch() {
        if (modelPackage == null) {
            modelPackage = ConnectionPackage.eINSTANCE;
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    public Object doSwitch(EObject theEObject) {
        return doSwitch(theEObject.eClass(), theEObject);
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected Object doSwitch(EClass theEClass, EObject theEObject) {
        if (theEClass.eContainer() == modelPackage) {
            return doSwitch(theEClass.getClassifierID(), theEObject);
        }
        else {
            List eSuperTypes = theEClass.getESuperTypes();
            return
                eSuperTypes.isEmpty() ?
                    defaultCase(theEObject) :
                    doSwitch((EClass)eSuperTypes.get(0), theEObject);
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected Object doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
            case ConnectionPackage.METADATA: {
                Metadata metadata = (Metadata)theEObject;
                Object result = caseMetadata(metadata);
                if (result == null) result = caseAbstractMetadataObject(metadata);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.CONNECTION: {
                Connection connection = (Connection)theEObject;
                Object result = caseConnection(connection);
                if (result == null) result = caseAbstractMetadataObject(connection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.METADATA_COLUMN: {
                MetadataColumn metadataColumn = (MetadataColumn)theEObject;
                Object result = caseMetadataColumn(metadataColumn);
                if (result == null) result = caseAbstractMetadataObject(metadataColumn);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.ABSTRACT_METADATA_OBJECT: {
                AbstractMetadataObject abstractMetadataObject = (AbstractMetadataObject)theEObject;
                Object result = caseAbstractMetadataObject(abstractMetadataObject);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.METADATA_TABLE: {
                MetadataTable metadataTable = (MetadataTable)theEObject;
                Object result = caseMetadataTable(metadataTable);
                if (result == null) result = caseAbstractMetadataObject(metadataTable);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.FILE_CONNECTION: {
                FileConnection fileConnection = (FileConnection)theEObject;
                Object result = caseFileConnection(fileConnection);
                if (result == null) result = caseConnection(fileConnection);
                if (result == null) result = caseAbstractMetadataObject(fileConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.DELIMITED_FILE_CONNECTION: {
                DelimitedFileConnection delimitedFileConnection = (DelimitedFileConnection)theEObject;
                Object result = caseDelimitedFileConnection(delimitedFileConnection);
                if (result == null) result = caseFileConnection(delimitedFileConnection);
                if (result == null) result = caseConnection(delimitedFileConnection);
                if (result == null) result = caseAbstractMetadataObject(delimitedFileConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.POSITIONAL_FILE_CONNECTION: {
                PositionalFileConnection positionalFileConnection = (PositionalFileConnection)theEObject;
                Object result = casePositionalFileConnection(positionalFileConnection);
                if (result == null) result = caseFileConnection(positionalFileConnection);
                if (result == null) result = caseConnection(positionalFileConnection);
                if (result == null) result = caseAbstractMetadataObject(positionalFileConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.EBCDIC_CONNECTION: {
                EbcdicConnection ebcdicConnection = (EbcdicConnection)theEObject;
                Object result = caseEbcdicConnection(ebcdicConnection);
                if (result == null) result = caseFileConnection(ebcdicConnection);
                if (result == null) result = caseConnection(ebcdicConnection);
                if (result == null) result = caseAbstractMetadataObject(ebcdicConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.MDM_CONNECTION: {
                MDMConnection mdmConnection = (MDMConnection)theEObject;
                Object result = caseMDMConnection(mdmConnection);
                if (result == null) result = caseConnection(mdmConnection);
                if (result == null) result = caseAbstractMetadataObject(mdmConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.DATABASE_CONNECTION: {
                DatabaseConnection databaseConnection = (DatabaseConnection)theEObject;
                Object result = caseDatabaseConnection(databaseConnection);
                if (result == null) result = caseConnection(databaseConnection);
                if (result == null) result = caseAbstractMetadataObject(databaseConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.SAP_CONNECTION: {
                SAPConnection sapConnection = (SAPConnection)theEObject;
                Object result = caseSAPConnection(sapConnection);
                if (result == null) result = caseConnection(sapConnection);
                if (result == null) result = caseAbstractMetadataObject(sapConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.SAP_FUNCTION_UNIT: {
                SAPFunctionUnit sapFunctionUnit = (SAPFunctionUnit)theEObject;
                Object result = caseSAPFunctionUnit(sapFunctionUnit);
                if (result == null) result = caseAbstractMetadataObject(sapFunctionUnit);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.SAP_FUNCTION_PARAMETER_COLUMN: {
                SAPFunctionParameterColumn sapFunctionParameterColumn = (SAPFunctionParameterColumn)theEObject;
                Object result = caseSAPFunctionParameterColumn(sapFunctionParameterColumn);
                if (result == null) result = caseAbstractMetadataObject(sapFunctionParameterColumn);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.SAP_FUNCTION_PARAMETER_TABLE: {
                SAPFunctionParameterTable sapFunctionParameterTable = (SAPFunctionParameterTable)theEObject;
                Object result = caseSAPFunctionParameterTable(sapFunctionParameterTable);
                if (result == null) result = caseAbstractMetadataObject(sapFunctionParameterTable);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.INPUT_SAP_FUNCTION_PARAMETER_TABLE: {
                InputSAPFunctionParameterTable inputSAPFunctionParameterTable = (InputSAPFunctionParameterTable)theEObject;
                Object result = caseInputSAPFunctionParameterTable(inputSAPFunctionParameterTable);
                if (result == null) result = caseSAPFunctionParameterTable(inputSAPFunctionParameterTable);
                if (result == null) result = caseAbstractMetadataObject(inputSAPFunctionParameterTable);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.OUTPUT_SAP_FUNCTION_PARAMETER_TABLE: {
                OutputSAPFunctionParameterTable outputSAPFunctionParameterTable = (OutputSAPFunctionParameterTable)theEObject;
                Object result = caseOutputSAPFunctionParameterTable(outputSAPFunctionParameterTable);
                if (result == null) result = caseSAPFunctionParameterTable(outputSAPFunctionParameterTable);
                if (result == null) result = caseAbstractMetadataObject(outputSAPFunctionParameterTable);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.REGEXP_FILE_CONNECTION: {
                RegexpFileConnection regexpFileConnection = (RegexpFileConnection)theEObject;
                Object result = caseRegexpFileConnection(regexpFileConnection);
                if (result == null) result = caseFileConnection(regexpFileConnection);
                if (result == null) result = caseConnection(regexpFileConnection);
                if (result == null) result = caseAbstractMetadataObject(regexpFileConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.XML_FILE_CONNECTION: {
                XmlFileConnection xmlFileConnection = (XmlFileConnection)theEObject;
                Object result = caseXmlFileConnection(xmlFileConnection);
                if (result == null) result = caseConnection(xmlFileConnection);
                if (result == null) result = caseAbstractMetadataObject(xmlFileConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.SCHEMA_TARGET: {
                SchemaTarget schemaTarget = (SchemaTarget)theEObject;
                Object result = caseSchemaTarget(schemaTarget);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.QUERIES_CONNECTION: {
                QueriesConnection queriesConnection = (QueriesConnection)theEObject;
                Object result = caseQueriesConnection(queriesConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.QUERY: {
                Query query = (Query)theEObject;
                Object result = caseQuery(query);
                if (result == null) result = caseAbstractMetadataObject(query);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.LDIF_FILE_CONNECTION: {
                LdifFileConnection ldifFileConnection = (LdifFileConnection)theEObject;
                Object result = caseLdifFileConnection(ldifFileConnection);
                if (result == null) result = caseConnection(ldifFileConnection);
                if (result == null) result = caseAbstractMetadataObject(ldifFileConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.FILE_EXCEL_CONNECTION: {
                FileExcelConnection fileExcelConnection = (FileExcelConnection)theEObject;
                Object result = caseFileExcelConnection(fileExcelConnection);
                if (result == null) result = caseFileConnection(fileExcelConnection);
                if (result == null) result = caseConnection(fileExcelConnection);
                if (result == null) result = caseAbstractMetadataObject(fileExcelConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.XML_XPATH_LOOP_DESCRIPTOR: {
                XmlXPathLoopDescriptor xmlXPathLoopDescriptor = (XmlXPathLoopDescriptor)theEObject;
                Object result = caseXmlXPathLoopDescriptor(xmlXPathLoopDescriptor);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.GENERIC_SCHEMA_CONNECTION: {
                GenericSchemaConnection genericSchemaConnection = (GenericSchemaConnection)theEObject;
                Object result = caseGenericSchemaConnection(genericSchemaConnection);
                if (result == null) result = caseConnection(genericSchemaConnection);
                if (result == null) result = caseAbstractMetadataObject(genericSchemaConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.LDAP_SCHEMA_CONNECTION: {
                LDAPSchemaConnection ldapSchemaConnection = (LDAPSchemaConnection)theEObject;
                Object result = caseLDAPSchemaConnection(ldapSchemaConnection);
                if (result == null) result = caseConnection(ldapSchemaConnection);
                if (result == null) result = caseAbstractMetadataObject(ldapSchemaConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.WSDL_SCHEMA_CONNECTION: {
                WSDLSchemaConnection wsdlSchemaConnection = (WSDLSchemaConnection)theEObject;
                Object result = caseWSDLSchemaConnection(wsdlSchemaConnection);
                if (result == null) result = caseConnection(wsdlSchemaConnection);
                if (result == null) result = caseAbstractMetadataObject(wsdlSchemaConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.SALESFORCE_SCHEMA_CONNECTION: {
                SalesforceSchemaConnection salesforceSchemaConnection = (SalesforceSchemaConnection)theEObject;
                Object result = caseSalesforceSchemaConnection(salesforceSchemaConnection);
                if (result == null) result = caseConnection(salesforceSchemaConnection);
                if (result == null) result = caseAbstractMetadataObject(salesforceSchemaConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.CDC_CONNECTION: {
                CDCConnection cdcConnection = (CDCConnection)theEObject;
                Object result = caseCDCConnection(cdcConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.CDC_TYPE: {
                CDCType cdcType = (CDCType)theEObject;
                Object result = caseCDCType(cdcType);
                if (result == null) result = caseAbstractMetadataObject(cdcType);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.SUBSCRIBER_TABLE: {
                SubscriberTable subscriberTable = (SubscriberTable)theEObject;
                Object result = caseSubscriberTable(subscriberTable);
                if (result == null) result = caseMetadataTable(subscriberTable);
                if (result == null) result = caseAbstractMetadataObject(subscriberTable);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.SAP_TEST_INPUT_PARAMETER_TABLE: {
                SAPTestInputParameterTable sapTestInputParameterTable = (SAPTestInputParameterTable)theEObject;
                Object result = caseSAPTestInputParameterTable(sapTestInputParameterTable);
                if (result == null) result = caseSAPFunctionParameterTable(sapTestInputParameterTable);
                if (result == null) result = caseAbstractMetadataObject(sapTestInputParameterTable);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.CONCEPT: {
                Concept concept = (Concept)theEObject;
                Object result = caseConcept(concept);
                if (result == null) result = caseMetadataTable(concept);
                if (result == null) result = caseAbstractMetadataObject(concept);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.CONCEPT_TARGET: {
                ConceptTarget conceptTarget = (ConceptTarget)theEObject;
                Object result = caseConceptTarget(conceptTarget);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.HL7_CONNECTION: {
                HL7Connection hl7Connection = (HL7Connection)theEObject;
                Object result = caseHL7Connection(hl7Connection);
                if (result == null) result = caseFileConnection(hl7Connection);
                if (result == null) result = caseConnection(hl7Connection);
                if (result == null) result = caseAbstractMetadataObject(hl7Connection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case ConnectionPackage.HEADER_FOOTER_CONNECTION: {
                HeaderFooterConnection headerFooterConnection = (HeaderFooterConnection)theEObject;
                Object result = caseHeaderFooterConnection(headerFooterConnection);
                if (result == null) result = caseConnection(headerFooterConnection);
                if (result == null) result = caseAbstractMetadataObject(headerFooterConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Metadata</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Metadata</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseMetadata(Metadata object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Connection</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     * 
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseConnection(Connection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Metadata Table</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Metadata Table</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseMetadataTable(MetadataTable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Metadata Column</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Metadata Column</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseMetadataColumn(MetadataColumn object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Abstract Metadata Object</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Abstract Metadata Object</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseAbstractMetadataObject(AbstractMetadataObject object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>File Connection</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>File Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseFileConnection(FileConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delimited File Connection</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delimited File Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseDelimitedFileConnection(DelimitedFileConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Positional File Connection</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Positional File Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object casePositionalFileConnection(PositionalFileConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Ebcdic Connection</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Ebcdic Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseEbcdicConnection(EbcdicConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>MDM Connection</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>MDM Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseMDMConnection(MDMConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Database Connection</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Database Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseDatabaseConnection(DatabaseConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>SAP Connection</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>SAP Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseSAPConnection(SAPConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>SAP Function Unit</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>SAP Function Unit</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseSAPFunctionUnit(SAPFunctionUnit object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>SAP Function Parameter Column</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>SAP Function Parameter Column</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseSAPFunctionParameterColumn(SAPFunctionParameterColumn object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>SAP Function Parameter Table</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>SAP Function Parameter Table</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseSAPFunctionParameterTable(SAPFunctionParameterTable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Input SAP Function Parameter Table</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Input SAP Function Parameter Table</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseInputSAPFunctionParameterTable(InputSAPFunctionParameterTable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Output SAP Function Parameter Table</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Output SAP Function Parameter Table</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseOutputSAPFunctionParameterTable(OutputSAPFunctionParameterTable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Regexp File Connection</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Regexp File Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseRegexpFileConnection(RegexpFileConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Xml File Connection</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Xml File Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseXmlFileConnection(XmlFileConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Schema Target</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     * 
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Schema Target</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseSchemaTarget(SchemaTarget object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Queries Connection</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Queries Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseQueriesConnection(QueriesConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Query</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Query</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseQuery(Query object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Ldif File Connection</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Ldif File Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseLdifFileConnection(LdifFileConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>File Excel Connection</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>File Excel Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseFileExcelConnection(FileExcelConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Xml XPath Loop Descriptor</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Xml XPath Loop Descriptor</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseXmlXPathLoopDescriptor(XmlXPathLoopDescriptor object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Generic Schema Connection</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Generic Schema Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseGenericSchemaConnection(GenericSchemaConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>LDAP Schema Connection</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>LDAP Schema Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseLDAPSchemaConnection(LDAPSchemaConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>WSDL Schema Connection</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>WSDL Schema Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseWSDLSchemaConnection(WSDLSchemaConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Salesforce Schema Connection</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Salesforce Schema Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseSalesforceSchemaConnection(SalesforceSchemaConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>CDC Connection</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>CDC Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseCDCConnection(CDCConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>CDC Type</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>CDC Type</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseCDCType(CDCType object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Subscriber Table</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Subscriber Table</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseSubscriberTable(SubscriberTable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>SAP Test Input Parameter Table</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>SAP Test Input Parameter Table</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseSAPTestInputParameterTable(SAPTestInputParameterTable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Concept</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Concept</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseConcept(Concept object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Concept Target</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Concept Target</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseConceptTarget(ConceptTarget object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>HL7 Connection</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>HL7 Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseHL7Connection(HL7Connection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Header Footer Connection</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Header Footer Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseHeaderFooterConnection(HeaderFooterConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch, but this is the last
     * case anyway. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public Object defaultCase(EObject object) {
        return null;
    }

} // ConnectionSwitch
