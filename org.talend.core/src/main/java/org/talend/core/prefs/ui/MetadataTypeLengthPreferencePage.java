// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.core.prefs.ui;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.talend.commons.utils.workbench.preferences.ComboFieldEditor;
import org.talend.core.CorePlugin;
import org.talend.core.i18n.Messages;
import org.talend.core.language.ECodeLanguage;
import org.talend.core.language.LanguageManager;
import org.talend.core.model.metadata.MetadataTalendType;
import org.talend.core.model.metadata.types.JavaType;
import org.talend.core.model.metadata.types.JavaTypesManager;

/**
 * DOC qwei class global comment. Detailled comment
 */
public class MetadataTypeLengthPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

    private StringFieldEditor valueLengthField, fieldLengthField;

    private Composite parent;

    private ComboFieldEditor comboValueTypeField, comboFieldTypeField;

    private Group valueGroup, fieldGroup, dbTypeGroup;

    private StringFieldEditor booleanField, bigDecimalField, byteField, integerField, bytearrayField, longField, characterField;

    private StringFieldEditor objectField, dateField, shortField, doubleField, stringField, floatField, listField, datetimeField;

    public MetadataTypeLengthPreferencePage() {
        super(GRID);
        setPreferenceStore(CorePlugin.getDefault().getPreferenceStore());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
     */
    @Override
    protected void createFieldEditors() {
        // TODO Auto-generated method stub
        parent = new Composite(getFieldEditorParent(), SWT.NONE);
        GridLayout gridLayout = new GridLayout(1, false);
        GridData gridData = new GridData(GridData.FILL_BOTH);
        parent.setLayoutData(gridData);
        parent.setLayout(gridLayout);

        /**
         * set value Group
         */
        valueGroup = new Group(parent, SWT.NONE);
        valueGroup.setText(Messages.getString("MetadataTypeLengthPreferencePage.VALUE"));
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        valueGroup.setLayoutData(gd);
        Composite com = new Composite(valueGroup, SWT.NONE);
        if (LanguageManager.getCurrentLanguage() == ECodeLanguage.JAVA) {
            JavaType[] strTypeValue = JavaTypesManager.JAVA_TYPES;
            String[] strDisplay = new String[strTypeValue.length];
            String[] strValue = new String[strTypeValue.length];
            for (int i = 0; i < strDisplay.length; i++) {
                strDisplay[i] = strTypeValue[i].getNullableClass().getSimpleName();
                strValue[i] = strTypeValue[i].getId();
            }
            String[][] strvalueType = new String[strDisplay.length][2];
            for (int i = 0; i < strDisplay.length; i++) {
                strvalueType[i][0] = strDisplay[i];
                strvalueType[i][1] = strValue[i];

            }
            comboValueTypeField = new ComboFieldEditor(MetadataTypeLengthConstants.VALUE_DEFAULT_TYPE, Messages
                    .getString("MetadataTypeLengthConstants.VALUE_DEFAULT_TYPE"), strvalueType, com);
        } else {
            String[] strTypeValue = MetadataTalendType.getPerlTypes();
            String[][] strvalueType = new String[strTypeValue.length][2];
            for (int i = 0; i < strTypeValue.length; i++) {
                strvalueType[i][0] = strTypeValue[i];
                strvalueType[i][1] = strTypeValue[i];

            }
            comboValueTypeField = new ComboFieldEditor(MetadataTypeLengthConstants.VALUE_DEFAULT_TYPE, Messages
                    .getString("MetadataTypeLengthConstants.VALUE_DEFAULT_TYPE"), strvalueType, com);
        }

        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 4;
        com.setLayoutData(gridData);
        gridLayout = (GridLayout) com.getLayout();
        gridLayout.numColumns = 6;
        com.setLayout(gridLayout);
        valueLengthField = new StringFieldEditor(MetadataTypeLengthConstants.VALUE_DEFAULT_LENGTH, Messages
                .getString("MetadataTypeLengthConstants.VALUE_DEFAULT_LENGTH"), valueGroup);
        addField(comboValueTypeField);
        addField(valueLengthField);

        /**
         * set Fields
         * 
         */
        fieldGroup = new Group(parent, SWT.NONE);
        fieldGroup.setText(Messages.getString("MetadataTypeLengthPreferencePage.FIELD"));
        gd = new GridData(GridData.FILL_HORIZONTAL);
        fieldGroup.setLayoutData(gd);
        com = new Composite(fieldGroup, SWT.NONE);
        if (LanguageManager.getCurrentLanguage() == ECodeLanguage.JAVA) {
            JavaType[] strTypeValue = JavaTypesManager.JAVA_TYPES;
            String[] strDisplay = new String[strTypeValue.length];
            String[] strValue = new String[strTypeValue.length];
            for (int i = 0; i < strDisplay.length; i++) {
                strDisplay[i] = strTypeValue[i].getNullableClass().getSimpleName();
                strValue[i] = strTypeValue[i].getId();
            }
            String[][] strvalueType = new String[strDisplay.length][2];
            for (int i = 0; i < strDisplay.length; i++) {
                strvalueType[i][0] = strDisplay[i];
                strvalueType[i][1] = strValue[i];

            }
            comboFieldTypeField = new ComboFieldEditor(MetadataTypeLengthConstants.FIELD_DEFAULT_TYPE, Messages
                    .getString("MetadataTypeLengthConstants.FIELD_DEFAULT_TYPE"), strvalueType, com);
        } else {
            String[] strTypeValue = MetadataTalendType.getPerlTypes();
            String[][] strvalueType = new String[strTypeValue.length][2];
            for (int i = 0; i < strTypeValue.length; i++) {
                strvalueType[i][0] = strTypeValue[i];
                strvalueType[i][1] = strTypeValue[i];

            }
            comboFieldTypeField = new ComboFieldEditor(MetadataTypeLengthConstants.PERL_FIELD_DEFAULT_TYPE, Messages
                    .getString("MetadataTypeLengthConstants.FIELD_DEFAULT_TYPE"), strvalueType, com);
        }

        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 4;
        com.setLayoutData(gridData);
        gridLayout = (GridLayout) com.getLayout();
        gridLayout.numColumns = 6;
        com.setLayout(gridLayout);
        fieldLengthField = new StringFieldEditor(MetadataTypeLengthConstants.FIELD_DEFAULT_LENGTH, Messages
                .getString("MetadataTypeLengthConstants.FIELD_DEFAULT_LENGTH"), fieldGroup);
        addField(comboFieldTypeField);
        addField(fieldLengthField);

        /**
         * set data Type
         */
        dbTypeGroup = new Group(parent, SWT.NONE);
        dbTypeGroup.setText(Messages.getString("MetadataTypeLengthPreferencePage.DBTYPE"));
        gd = new GridData(GridData.FILL_HORIZONTAL);
        dbTypeGroup.setLayoutData(gd);
        booleanField = new StringFieldEditor(MetadataTypeLengthConstants.ID_BOOLEAN, Messages
                .getString("MetadataTypeLengthConstants.BOOLEAN_LENGTH"), dbTypeGroup);
        bigDecimalField = new StringFieldEditor(MetadataTypeLengthConstants.ID_BIGDECIMAL, Messages
                .getString("MetadataTypeLengthConstants.BIGDECIMAL_LENGTH"), dbTypeGroup);
        byteField = new StringFieldEditor(MetadataTypeLengthConstants.ID_BYTE, Messages
                .getString("MetadataTypeLengthConstants.BYTE_LENGTH"), dbTypeGroup);
        integerField = new StringFieldEditor(MetadataTypeLengthConstants.ID_INTEGER, Messages
                .getString("MetadataTypeLengthConstants.INTEGER_LENGTH"), dbTypeGroup);
        bytearrayField = new StringFieldEditor(MetadataTypeLengthConstants.ID_BYTEARRAY, Messages
                .getString("MetadataTypeLengthConstants.BYTEARRAY_LENGTH"), dbTypeGroup);
        longField = new StringFieldEditor(MetadataTypeLengthConstants.ID_LONG, Messages
                .getString("MetadataTypeLengthConstants.LONG_LENGTH"), dbTypeGroup);
        characterField = new StringFieldEditor(MetadataTypeLengthConstants.ID_CHARACTER, Messages
                .getString("MetadataTypeLengthConstants.CHARACTER_LENGTH"), dbTypeGroup);
        objectField = new StringFieldEditor(MetadataTypeLengthConstants.ID_OBJECT, Messages
                .getString("MetadataTypeLengthConstants.OBJECT_LENGTH"), dbTypeGroup);
        dateField = new StringFieldEditor(MetadataTypeLengthConstants.ID_DATE, Messages
                .getString("MetadataTypeLengthConstants.DATE_LENGHT"), dbTypeGroup);
        shortField = new StringFieldEditor(MetadataTypeLengthConstants.ID_SHORT, Messages
                .getString("MetadataTypeLengthConstants.SHORT_LENGTH"), dbTypeGroup);
        doubleField = new StringFieldEditor(MetadataTypeLengthConstants.ID_DOUBLE, Messages
                .getString("MetadataTypeLengthConstants.DOUBLE_LENGTH"), dbTypeGroup);
        stringField = new StringFieldEditor(MetadataTypeLengthConstants.ID_STRING, Messages
                .getString("MetadataTypeLengthConstants.STRING_LENGTH"), dbTypeGroup);
        floatField = new StringFieldEditor(MetadataTypeLengthConstants.ID_FLOAT, Messages
                .getString("MetadataTypeLengthConstants.FLOAT_LENGTH"), dbTypeGroup);
        listField = new StringFieldEditor(MetadataTypeLengthConstants.ID_LIST, Messages
                .getString("MetadataTypeLengthConstants.LIST_LENGTH"), dbTypeGroup);
        datetimeField = new StringFieldEditor(MetadataTypeLengthConstants.PERL_DATETIME, Messages
                .getString("MetadataTypeLengthConstants.DATETIME_LENGTH"), dbTypeGroup);
        gridLayout = (GridLayout) dbTypeGroup.getLayout();
        gridLayout.numColumns = 4;
        dbTypeGroup.setLayout(gridLayout);
        addField(bigDecimalField);
        addField(booleanField);
        addField(byteField);
        addField(integerField);
        addField(bytearrayField);
        addField(longField);
        addField(characterField);
        addField(objectField);
        addField(dateField);
        addField(shortField);
        addField(doubleField);
        addField(stringField);
        addField(floatField);
        addField(listField);
        addField(datetimeField);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.FieldEditorPreferencePage#performOk()
     */
    @Override
    public boolean performOk() {
        getPreferenceStore().setValue(MetadataTypeLengthConstants.VALUE_DEFAULT_TYPE, comboValueTypeField.getFieldValue());
        getPreferenceStore().setValue(MetadataTypeLengthConstants.VALUE_DEFAULT_LENGTH, valueLengthField.getStringValue());
        if (LanguageManager.getCurrentLanguage() == ECodeLanguage.JAVA) {
            getPreferenceStore().setValue(MetadataTypeLengthConstants.FIELD_DEFAULT_TYPE, comboFieldTypeField.getFieldValue());
        } else {
            getPreferenceStore().setValue(MetadataTypeLengthConstants.PERL_FIELD_DEFAULT_TYPE,
                    comboFieldTypeField.getFieldValue());
        }
        getPreferenceStore().setValue(MetadataTypeLengthConstants.FIELD_DEFAULT_LENGTH, fieldLengthField.getStringValue());
        getPreferenceStore().setValue(MetadataTypeLengthConstants.ID_BOOLEAN, booleanField.getStringValue());
        getPreferenceStore().setValue(MetadataTypeLengthConstants.ID_BIGDECIMAL, bigDecimalField.getStringValue());
        getPreferenceStore().setValue(MetadataTypeLengthConstants.ID_BYTE, byteField.getStringValue());
        getPreferenceStore().setValue(MetadataTypeLengthConstants.ID_INTEGER, integerField.getStringValue());
        getPreferenceStore().setValue(MetadataTypeLengthConstants.ID_BYTEARRAY, bytearrayField.getStringValue());
        getPreferenceStore().setValue(MetadataTypeLengthConstants.ID_LONG, longField.getStringValue());
        getPreferenceStore().setValue(MetadataTypeLengthConstants.ID_CHARACTER, characterField.getStringValue());
        getPreferenceStore().setValue(MetadataTypeLengthConstants.ID_OBJECT, objectField.getStringValue());
        getPreferenceStore().setValue(MetadataTypeLengthConstants.ID_DATE, dateField.getStringValue());
        getPreferenceStore().setValue(MetadataTypeLengthConstants.ID_SHORT, shortField.getStringValue());
        getPreferenceStore().setValue(MetadataTypeLengthConstants.ID_DOUBLE, doubleField.getStringValue());
        getPreferenceStore().setValue(MetadataTypeLengthConstants.ID_STRING, stringField.getStringValue());
        getPreferenceStore().setValue(MetadataTypeLengthConstants.ID_FLOAT, floatField.getStringValue());
        getPreferenceStore().setValue(MetadataTypeLengthConstants.ID_LIST, listField.getStringValue());
        getPreferenceStore().setValue(MetadataTypeLengthConstants.PERL_DATE, dateField.getStringValue());
        getPreferenceStore().setValue(MetadataTypeLengthConstants.PERL_BOOLEAN, booleanField.getStringValue());
        getPreferenceStore().setValue(MetadataTypeLengthConstants.PERL_INT, integerField.getStringValue());
        getPreferenceStore().setValue(MetadataTypeLengthConstants.PERL_DECIMAL, bigDecimalField.getStringValue());
        getPreferenceStore().setValue(MetadataTypeLengthConstants.PERL_STRING, stringField.getStringValue());
        getPreferenceStore().setValue(MetadataTypeLengthConstants.PERL_DATETIME, datetimeField.getStringValue());
        return super.performOk();
    }
}
