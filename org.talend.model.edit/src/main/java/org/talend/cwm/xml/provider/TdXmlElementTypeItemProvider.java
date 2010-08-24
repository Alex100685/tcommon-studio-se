/**
 * <copyright> </copyright>
 * 
 * $Id$
 */
package org.talend.cwm.xml.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.talend.core.model.metadata.builder.connection.ConnectionFactory;
import org.talend.core.model.metadata.builder.connection.provider.MetadataEditPlugin;
import org.talend.cwm.relational.RelationalFactory;
import org.talend.cwm.softwaredeployment.SoftwaredeploymentFactory;
import org.talend.cwm.xml.TdXmlElementType;
import org.talend.cwm.xml.XmlFactory;
import org.talend.cwm.xml.XmlPackage;
import orgomg.cwm.objectmodel.core.CorePackage;
import orgomg.cwm.resource.xml.provider.ElementTypeItemProvider;

/**
 * This is the item provider adapter for a {@link org.talend.cwm.xml.TdXmlElementType} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class TdXmlElementTypeItemProvider extends ElementTypeItemProvider implements IEditingDomainItemProvider,
        IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {

    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TdXmlElementTypeItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            addXsdElementDeclarationPropertyDescriptor(object);
            addOwnedDocumentPropertyDescriptor(object);
            addJavaTypePropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Xsd Element Declaration feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addXsdElementDeclarationPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                .getRootAdapterFactory(), getResourceLocator(), getString("_UI_TdXmlElementType_xsdElementDeclaration_feature"),
                getString("_UI_PropertyDescriptor_description", "_UI_TdXmlElementType_xsdElementDeclaration_feature",
                        "_UI_TdXmlElementType_type"), XmlPackage.Literals.TD_XML_ELEMENT_TYPE__XSD_ELEMENT_DECLARATION, true,
                false, true, null, null, null));
    }

    /**
     * This adds a property descriptor for the Owned Document feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addOwnedDocumentPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                .getRootAdapterFactory(), getResourceLocator(), getString("_UI_TdXmlElementType_ownedDocument_feature"),
                getString("_UI_PropertyDescriptor_description", "_UI_TdXmlElementType_ownedDocument_feature",
                        "_UI_TdXmlElementType_type"), XmlPackage.Literals.TD_XML_ELEMENT_TYPE__OWNED_DOCUMENT, true, false, true,
                null, null, null));
    }

    /**
     * This adds a property descriptor for the Java Type feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addJavaTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                .getRootAdapterFactory(), getResourceLocator(), getString("_UI_TdXmlElementType_javaType_feature"), getString(
                "_UI_PropertyDescriptor_description", "_UI_TdXmlElementType_javaType_feature", "_UI_TdXmlElementType_type"),
                XmlPackage.Literals.TD_XML_ELEMENT_TYPE__JAVA_TYPE, true, false, false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
     * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
     * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(XmlPackage.Literals.TD_XML_ELEMENT_TYPE__XML_CONTENT);
        }
        return childrenFeatures;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EStructuralFeature getChildFeature(Object object, Object child) {
        // Check the type of the specified child object and return the proper feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns TdXmlElementType.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/TdXmlElementType"));
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((TdXmlElementType) object).getName();
        return label == null || label.length() == 0 ? getString("_UI_TdXmlElementType_type")
                : getString("_UI_TdXmlElementType_type") + " " + label;
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to update any cached
     * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void notifyChanged(Notification notification) {
        updateChildren(notification);

        switch (notification.getFeatureID(TdXmlElementType.class)) {
        case XmlPackage.TD_XML_ELEMENT_TYPE__JAVA_TYPE:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
            return;
        case XmlPackage.TD_XML_ELEMENT_TYPE__XML_CONTENT:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
            return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
     * that can be created under this object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, XmlFactory.eINSTANCE
                .createTdXmlElementType()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, XmlFactory.eINSTANCE
                .createTdXmlContent()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, XmlFactory.eINSTANCE
                .createTdXmlSchema()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createMetadata()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createConnection()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createMetadataColumn()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createMetadataTable()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createDelimitedFileConnection()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createPositionalFileConnection()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createEbcdicConnection()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createMDMConnection()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createDatabaseConnection()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createSAPConnection()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createSAPFunctionUnit()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createSAPIDocUnit()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createSAPFunctionParameterColumn()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createSAPFunctionParameterTable()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createInputSAPFunctionParameterTable()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createOutputSAPFunctionParameterTable()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createRegexpFileConnection()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createXmlFileConnection()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createQuery()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createLdifFileConnection()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createFileExcelConnection()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createGenericSchemaConnection()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createLDAPSchemaConnection()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createWSDLSchemaConnection()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createSalesforceSchemaConnection()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createCDCType()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createSubscriberTable()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createSAPTestInputParameterTable()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createConcept()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createHL7Connection()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createHeaderFooterConnection()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, ConnectionFactory.eINSTANCE
                .createGenericPackage()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, RelationalFactory.eINSTANCE
                .createTdTable()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, RelationalFactory.eINSTANCE
                .createTdView()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, RelationalFactory.eINSTANCE
                .createTdColumn()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, RelationalFactory.eINSTANCE
                .createTdSqlDataType()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, RelationalFactory.eINSTANCE
                .createTdTrigger()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, RelationalFactory.eINSTANCE
                .createTdProcedure()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT,
                SoftwaredeploymentFactory.eINSTANCE.createTdDataManager()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT,
                SoftwaredeploymentFactory.eINSTANCE.createTdSoftwareSystem()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT,
                SoftwaredeploymentFactory.eINSTANCE.createTdMachine()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.CLASSIFIER__FEATURE, ConnectionFactory.eINSTANCE
                .createMetadataColumn()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.CLASSIFIER__FEATURE, RelationalFactory.eINSTANCE
                .createTdColumn()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.CLASSIFIER__FEATURE, RelationalFactory.eINSTANCE
                .createTdProcedure()));

        newChildDescriptors.add(createChildParameter(XmlPackage.Literals.TD_XML_ELEMENT_TYPE__XML_CONTENT, XmlFactory.eINSTANCE
                .createTdXmlContent()));
    }

    /**
     * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
        Object childFeature = feature;
        Object childObject = child;

        boolean qualify = childFeature == CorePackage.Literals.NAMESPACE__OWNED_ELEMENT
                || childFeature == XmlPackage.Literals.TD_XML_ELEMENT_TYPE__XML_CONTENT
                || childFeature == CorePackage.Literals.CLASSIFIER__FEATURE;

        if (qualify) {
            return getString("_UI_CreateChild_text2", new Object[] { getTypeText(childObject), getFeatureText(childFeature),
                    getTypeText(owner) });
        }
        return super.getCreateChildText(owner, feature, child, selection);
    }

    /**
     * Return the resource locator for this item provider's resources.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return MetadataEditPlugin.INSTANCE;
    }

}
