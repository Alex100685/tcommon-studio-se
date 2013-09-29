package org.talend.librariesmanager.ui.prefs;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.librariesmanager.ui.LibManagerUiPlugin;
import org.talend.librariesmanager.ui.i18n.Messages;

public class LibrariesMainPreferencesPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

    private DirectoryFieldEditor externalLibrariesPath;

    public LibrariesMainPreferencesPage() {
        super(GRID);
    }

    @Override
    protected void createFieldEditors() {

        externalLibrariesPath = new DirectoryFieldEditor(PreferencesUtilities.EXTERNAL_LIB_PATH,
                Messages.getString("MainPreferencesPage.externalLibPath"), getFieldEditorParent()); //$NON-NLS-1$
        addField(externalLibrariesPath);
    }

    @Override
    protected void initialize() {
        super.initialize();
        externalLibrariesPath.setEnabled(true, getFieldEditorParent());
    }

    @Override
    public void init(IWorkbench workbench) {
        setPreferenceStore(PreferencesUtilities.getPreferenceStore());

        // oldPath=PreferencesUtilities.getLibrariesPath();
    }

    @Override
    public boolean performOk() {
        boolean toReturn = super.performOk();

        // final String newPath = PreferencesUtilities.getLibrariesPath();
        if (toReturn) {// && !oldPath.equals(newPath)) {
            try {
                LibManagerUiPlugin.getDefault().getLibrariesService().syncLibraries();
            } catch (Exception e) {
                ExceptionHandler.process(e);
                return false;
            }
        }

        return toReturn;

    }

}
