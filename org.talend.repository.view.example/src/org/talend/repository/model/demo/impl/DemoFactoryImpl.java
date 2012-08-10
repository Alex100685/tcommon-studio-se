/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.demo.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.talend.repository.model.demo.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DemoFactoryImpl extends EFactoryImpl implements DemoFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static DemoFactory init() {
        try {
            DemoFactory theDemoFactory = (DemoFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.talend.com/exampleDemo"); 
            if (theDemoFactory != null) {
                return theDemoFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new DemoFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DemoFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case DemoPackage.EXAMPLE_DEMO_CONNECTION_ITEM: return createExampleDemoConnectionItem();
            case DemoPackage.EXAMPLE_DEMO_CONNECTION: return createExampleDemoConnection();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExampleDemoConnectionItem createExampleDemoConnectionItem() {
        ExampleDemoConnectionItemImpl exampleDemoConnectionItem = new ExampleDemoConnectionItemImpl();
        return exampleDemoConnectionItem;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExampleDemoConnection createExampleDemoConnection() {
        ExampleDemoConnectionImpl exampleDemoConnection = new ExampleDemoConnectionImpl();
        return exampleDemoConnection;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DemoPackage getDemoPackage() {
        return (DemoPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static DemoPackage getPackage() {
        return DemoPackage.eINSTANCE;
    }

} //DemoFactoryImpl
