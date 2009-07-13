/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package org.talend.designer.webservice.ws.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.wsdl.Definition;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.schema.Schema;
import javax.wsdl.factory.WSDLFactory;

import org.apache.ws.commons.schema.XmlSchemaCollection;
import org.talend.designer.webservice.ws.helper.conf.ServiceHelperConfiguration;

/**
 * This helper allow easy discovery of services and types
 * 
 * @author rlamarche
 */
public class ServiceDiscoveryHelper {

    private String wsdlUri;

    private WSDLFactory wsdlFactory;

    private Definition definition;

    private XmlSchemaCollection schemaCollection;

    private ServiceHelperConfiguration configuration;

    private File localWsdl;

    public ServiceDiscoveryHelper(String wsdlUri) throws WSDLException, IOException {
        this(wsdlUri, null);
    }

    public ServiceDiscoveryHelper(String wsdlUri, ServiceHelperConfiguration configuration) throws WSDLException, IOException {
        this.wsdlUri = wsdlUri;
        this.configuration = configuration;
        init();
    }

    /**
     * Read the wsdl and schema
     * 
     * @throws javax.wsdl.WSDLException
     */
    private void init() throws WSDLException, IOException {
        wsdlFactory = WSDLFactory.newInstance();
        if (configuration == null) {
            definition = wsdlFactory.newWSDLReader().readWSDL(wsdlUri);
        } else {
            definition = wsdlFactory.newWSDLReader().readWSDL(configuration.createWSDLLocator(wsdlUri));
        }
        schemaCollection = new XmlSchemaCollection();

        List<ExtensibilityElement> extensibilityElements = definition.getTypes().getExtensibilityElements();
        for (ExtensibilityElement el : extensibilityElements) {
            if (el instanceof Schema) {
                Schema schema = (Schema) el;
                schemaCollection.read(schema.getElement());
            }
        }

        localWsdl = File.createTempFile("service-", ".wsdl");
        localWsdl.deleteOnExit();

        wsdlFactory.newWSDLWriter().writeWSDL(definition, new FileOutputStream(localWsdl));
    }

    /**
     * Return the parsed wsdl, it contains all services
     * 
     * @return
     */
    public Definition getDefinition() {
        return definition;
    }

    /**
     * Return the xml schema collection
     * 
     * @return
     */
    public XmlSchemaCollection getSchema() {
        return schemaCollection;
    }

    public String getWsdlUri() {
        return wsdlUri;
    }

    public String getLocalWsdlUri() {
        return localWsdl.toURI().toString();
    }
}
