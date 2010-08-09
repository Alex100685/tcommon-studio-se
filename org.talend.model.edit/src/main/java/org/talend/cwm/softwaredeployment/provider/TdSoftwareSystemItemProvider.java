/**
 * <copyright> </copyright>
 * 
 * $Id$
 */
package org.talend.cwm.softwaredeployment.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.talend.core.model.metadata.builder.connection.ConnectionFactory;
import org.talend.core.model.metadata.builder.connection.provider.MetadataEditPlugin;
import org.talend.cwm.relational.RelationalFactory;
import org.talend.cwm.softwaredeployment.SoftwaredeploymentFactory;
import org.talend.cwm.softwaredeployment.TdSoftwareSystem;
import org.talend.cwm.xml.XmlFactory;
import orgomg.cwm.foundation.softwaredeployment.provider.SoftwareSystemItemProvider;
import orgomg.cwm.objectmodel.core.CorePackage;

/**
 * This is the item provider adapter for a {@link org.talend.cwm.softwaredeployment.TdSoftwareSystem} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class TdSoftwareSystemItemProvider extends SoftwareSystemItemProvider implements IEditingDomainItemProvider,
        IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {

    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TdSoftwareSystemItemProvider(AdapterFactory adapterFactory) {
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

        }
        return itemPropertyDescriptors;
    }

    /**
     * This returns TdSoftwareSystem.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/TdSoftwareSystem"));
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((TdSoftwareSystem) object).getName();
        return label == null || label.length() == 0 ? getString("_UI_TdSoftwareSystem_type")
                : getString("_UI_TdSoftwareSystem_type") + " " + label;
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

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT,
                SoftwaredeploymentFactory.eINSTANCE.createTdDataManager()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT,
                SoftwaredeploymentFactory.eINSTANCE.createTdSoftwareSystem()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT,
                SoftwaredeploymentFactory.eINSTANCE.createTdMachine()));

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

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, XmlFactory.eINSTANCE
                .createTdXmlElementType()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, XmlFactory.eINSTANCE
                .createTdXmlContent()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.NAMESPACE__OWNED_ELEMENT, XmlFactory.eINSTANCE
                .createTdXmlSchema()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.CLASSIFIER__FEATURE, ConnectionFactory.eINSTANCE
                .createMetadataColumn()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.CLASSIFIER__FEATURE, RelationalFactory.eINSTANCE
                .createTdColumn()));

        newChildDescriptors.add(createChildParameter(CorePackage.Literals.CLASSIFIER__FEATURE, RelationalFactory.eINSTANCE
                .createTdProcedure()));
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
