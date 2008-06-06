package org.talend.librariesmanager.prefs;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = PreferencesUtilities.getPreferenceStore();
        store.setDefault(PreferencesUtilities.EXTERNAL_LIB_PATH, Platform.getInstallLocation().getURL().getFile()
                + "lib");
        store.setDefault(PreferencesUtilities.EXTERNAL_LIB_PATH_MODE_SINGLE, true);
    }

}
