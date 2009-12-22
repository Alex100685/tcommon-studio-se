// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.core.prefs;

import java.io.File;
import java.util.Locale;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.update.core.SiteManager;
import org.eclipse.update.internal.scheduler.SchedulerStartup;
import org.epic.core.preferences.PerlMainPreferencePage;
import org.epic.perleditor.PerlEditorPlugin;
import org.talend.commons.CommonsPlugin;
import org.talend.commons.ui.swt.colorstyledtext.ColorManager;
import org.talend.core.CorePlugin;
import org.talend.core.prefs.GeneralParametersProvider.GeneralParameters;
import org.talend.core.prefs.ui.MetadataTypeLengthConstants;

/**
 * Intializer of core preferences. <br/>
 * 
 * $Id$
 * 
 */
public class CorePreferenceInitializer extends AbstractPreferenceInitializer {

    public static final String PERL_EMBEDDED_INTERPRETER_DIRECTORY = GeneralParametersProvider
            .getString(GeneralParameters.DEFAULT_PERL_INTERPRETER_EMBEDDED_SUFFIX_WIN32);

    private static final String JAVA_LINUX_INTERPRETER_PATH = GeneralParametersProvider
            .getString(GeneralParameters.DEFAULT_JAVA_INTERPRETER_SUFFIX_LINUX);

    private static final String JAVA_WIN32_INTERPRETER = GeneralParametersProvider
            .getString(GeneralParameters.DEFAULT_JAVA_INTERPRETER_SUFFIX_WIN32);

    private static final String PERL_LINUX_INTERPRETER_PATH = GeneralParametersProvider
            .getString(GeneralParameters.DEFAULT_PERL_INTERPRETER_LINUX);

    public static final String PERL_WIN32_INTERPRETER_PATH = CorePlugin.getDefault().getResourceService().getResourcesPath()
            + PERL_EMBEDDED_INTERPRETER_DIRECTORY;

    /**
     * Construct a new CorePreferenceInitializer.
     */
    public CorePreferenceInitializer() {
        super();
    }

    /**
     * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
     */
    @Override
    public void initializeDefaultPreferences() {

        IEclipsePreferences node = new DefaultScope().getNode(CorePlugin.getDefault().getBundle().getSymbolicName());

        // Building temporary files directory path
        IPath tempPath = new Path(System.getProperty("user.dir")).append("temp"); // NON-NLS-1$// NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-2$
        File tempFile = tempPath.toFile();
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        node.put(ITalendCorePrefConstants.FILE_PATH_TEMP, tempPath.toOSString());

        // TODO CCA : Change this default value
        String os = Platform.getOS();
        String javaPath = System.getProperty("java.home"); // NON-NLS-1$ //$NON-NLS-1$
        if (os.equals(Platform.OS_WIN32)) {
            String perlPath;
            perlPath = Platform.getInstallLocation().getURL().getFile().substring(1) + PERL_EMBEDDED_INTERPRETER_DIRECTORY;
            File perlEmbeddedExecFile = new File(perlPath);
            if (!perlEmbeddedExecFile.exists()) {
                perlPath = PERL_WIN32_INTERPRETER_PATH;

            }

            node.put(ITalendCorePrefConstants.PERL_INTERPRETER, perlPath.replace("/", "\\"));//$NON-NLS-1$//$NON-NLS-1$ //$NON-NLS-2$

            IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
            String prelExecutableValue = store.getString(ITalendCorePrefConstants.PERL_INTERPRETER);
            PerlEditorPlugin.getDefault().setExecutablePreference("\"" + prelExecutableValue + "\""); //$NON-NLS-1$ //$NON-NLS-2$
            PerlMainPreferencePage.refreshExecutableTextValue("\"" + prelExecutableValue + "\""); //$NON-NLS-1$ //$NON-NLS-2$

            node.put(ITalendCorePrefConstants.JAVA_INTERPRETER, javaPath + JAVA_WIN32_INTERPRETER);
        } else if (os.equals(Platform.OS_LINUX)) {
            node.put(ITalendCorePrefConstants.PERL_INTERPRETER, PERL_LINUX_INTERPRETER_PATH);
            node.put(ITalendCorePrefConstants.JAVA_INTERPRETER, javaPath + JAVA_LINUX_INTERPRETER_PATH);
        }

        // Sets default language
        node.put(ITalendCorePrefConstants.LANGUAGE_SELECTOR, Locale.getDefault().getLanguage());

        node.put(ITalendCorePrefConstants.PREVIEW_LIMIT, "50"); //$NON-NLS-1$

        node.putBoolean(ITalendCorePrefConstants.ALWAYS_WELCOME, true);

        // MHIRT : remove the automatic update check
        initializeUpdatePreference();

        // Initialize editors properties : line number shown
        final String perlEditorBundleName = "org.epic.perleditor"; // NON-NLS-1$ //$NON-NLS-1$
        final String editorsBundleName = "org.eclipse.ui.editors"; // NON-NLS-1$ //$NON-NLS-1$
        // AbstractDecoratedTextEditorPreferenceConstants.EDITOR_LINE_NUMBER_RULER = "lineNumberRuler"
        final String editorLineNumberRuler = "lineNumberRuler"; //$NON-NLS-1$

        if (!CommonsPlugin.isHeadless()) {
            IPreferenceStore store = new ScopedPreferenceStore(new InstanceScope(), perlEditorBundleName);
            store.setValue(editorLineNumberRuler, true);
            store = new ScopedPreferenceStore(new InstanceScope(), editorsBundleName);
            store.setValue(editorLineNumberRuler, true);

            // default colors for the ColorStyledText.
            ColorManager.initDefaultColors(CorePlugin.getDefault().getPreferenceStore());
        }
        String languageType = Locale.getDefault().getLanguage();
        CorePlugin.getDefault().getPreferenceStore().setValue(ITalendCorePrefConstants.LANGUAGE_SELECTOR, languageType);
        CorePlugin.getDefault().getPreferenceStore().setDefault(ITalendCorePrefConstants.SQL_ADD_QUOTE, false);
        CorePlugin.getDefault().getPreferenceStore().setDefault(ITalendCorePrefConstants.AS400_SQL_SEG, true);
        CorePlugin.getDefault().getPreferenceStore().setDefault(MetadataTypeLengthConstants.FIELD_DEFAULT_TYPE, "id_String"); //$NON-NLS-1$
        CorePlugin.getDefault().getPreferenceStore().setDefault(MetadataTypeLengthConstants.VALUE_DEFAULT_TYPE, "id_String"); //$NON-NLS-1$
        CorePlugin.getDefault().getPreferenceStore().setDefault(MetadataTypeLengthConstants.PERL_FIELD_DEFAULT_TYPE, "string"); //$NON-NLS-1$
        CorePlugin.getDefault().getPreferenceStore().setDefault(MetadataTypeLengthConstants.PERL_VALUE_DEFAULT_TYPE, "string"); //$NON-NLS-1$

        // As default, sets the choice which automatic generate corresponding documentation files if job is saved to
        // true:
        CorePlugin.getDefault().getPreferenceStore().setDefault(ITalendCorePrefConstants.DOC_GENERATION, false);
        CorePlugin.getDefault().getPreferenceStore().setDefault(ITalendCorePrefConstants.CONTEXT_GROUP_BY_SOURCE, true);
        CorePlugin.getDefault().getPreferenceStore().setDefault(ITalendCorePrefConstants.DOC_GENERATESOURCECODE, false);
        // CorePlugin.getDefault().getPreferenceStore().setDefault(ITalendCorePrefConstants.DOC_HIDEPASSWORDS, true);

        CorePlugin.getDefault().getPreferenceStore().setDefault(ITalendCorePrefConstants.COMMAND_STR,
                ITalendCorePrefConstants.DEFAULT_COMMAND_STR);

        //
        CorePlugin.getDefault().getPreferenceStore().setDefault(ITalendCorePrefConstants.SQL_ADD_WARNING, true);
    }

    // unused method : call remove for 2.3
    public void initializeUpdatePreference() {
        IEclipsePreferences nodeScheduler = new DefaultScope().getNode("org.eclipse.update.scheduler"); // NON-NLS-1$ //$NON-NLS-1$
        nodeScheduler.putBoolean(SchedulerStartup.P_ENABLED, false);
        nodeScheduler.put(SchedulerStartup.P_SCHEDULE, SchedulerStartup.VALUE_ON_SCHEDULE);
        nodeScheduler.putBoolean(SchedulerStartup.P_DOWNLOAD, false);
    }

    public static void setProxy(String proxyHost, String proxyPort) {
        SiteManager.setHttpProxyInfo(true, proxyHost, proxyPort);
    }

}
