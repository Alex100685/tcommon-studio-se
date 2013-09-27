// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.osgi.hook;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.ProtectionDomain;
import java.util.ArrayList;

import org.eclipse.osgi.baseadaptor.BaseData;
import org.eclipse.osgi.baseadaptor.bundlefile.BundleEntry;
import org.eclipse.osgi.baseadaptor.bundlefile.BundleFile;
import org.eclipse.osgi.baseadaptor.hooks.ClassLoadingHook;
import org.eclipse.osgi.baseadaptor.loader.BaseClassLoader;
import org.eclipse.osgi.baseadaptor.loader.ClasspathEntry;
import org.eclipse.osgi.baseadaptor.loader.ClasspathManager;
import org.eclipse.osgi.framework.adaptor.BundleProtectionDomain;
import org.eclipse.osgi.framework.adaptor.ClassLoaderDelegate;
import org.eclipse.osgi.framework.log.FrameworkLog;
import org.eclipse.osgi.framework.log.FrameworkLogEntry;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkEvent;
import org.talend.osgi.hook.notification.JarMissingObservable;

/**
 * search for the missing jar in the bundles and try to find them in the lib/java folder if not specified by the system
 * property "org.talend.lib.subfolder". created by sgandon on 9 sept. 2013 Detailled comment
 * 
 */
public class JarLoaderClassLoadingHook implements ClassLoadingHook {

    private File javaLibFolder;

    private final JarMissingObservable jarMissingObservable;

    /**
     * DOC sgandon JarLaoderClassLoadingHook constructor comment.
     * 
     * @param jarMissingObservable
     * 
     * @param hookRegistry
     */
    public JarLoaderClassLoadingHook(JarMissingObservable jarMissingObservable) {
        this.jarMissingObservable = jarMissingObservable;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.osgi.baseadaptor.hooks.ClassLoadingHook#processClass(java.lang.String, byte[],
     * org.eclipse.osgi.baseadaptor.loader.ClasspathEntry, org.eclipse.osgi.baseadaptor.bundlefile.BundleEntry,
     * org.eclipse.osgi.baseadaptor.loader.ClasspathManager)
     */
    @Override
    public byte[] processClass(String name, byte[] classbytes, ClasspathEntry classpathEntry, BundleEntry entry,
            ClasspathManager manager) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.osgi.baseadaptor.hooks.ClassLoadingHook#addClassPathEntry(java.util.ArrayList, java.lang.String,
     * org.eclipse.osgi.baseadaptor.loader.ClasspathManager, org.eclipse.osgi.baseadaptor.BaseData,
     * java.security.ProtectionDomain)
     */
    @Override
    public boolean addClassPathEntry(ArrayList cpEntries, String cp, ClasspathManager hostmanager, BaseData sourcedata,
            ProtectionDomain sourcedomain) {
        // check if jar exists
        BundleEntry entry = sourcedata.getBundleFile().getEntry(cp);
        String bundleSymbolicName = sourcedata.getSymbolicName();
        if (entry == null && bundleSymbolicName.startsWith("org.talend") && cp.endsWith(".jar")) {// the jar is not part of the bundle, let's try to look for it in the //$NON-NLS-1$ //$NON-NLS-2$
            // lib/java folder.
            try {
                File libJavaFolderFile = getLibJavaFolderFile();
                if (libJavaFolderFile != null) {
                    String jarName = new File(cp).getName();
                    File jarFile = new File(libJavaFolderFile, jarName);
                    if (jarFile.exists()) {
                        ClasspathEntry classPathEntry = createClassPathEntry(hostmanager, sourcedata, sourcedomain, jarFile);
                        cpEntries.add(classPathEntry);
                    } else {// jar file not found in the lib/java folder
                        // notify any observable to get a chance for the lib to be installed
                        jarMissingObservable.notifyObservers(new JarMissingObservable.JarMissingEvent(jarName,
                                bundleSymbolicName, libJavaFolderFile.getAbsolutePath()));
                        // let's do another check to see if the bundle was installed during the above notification
                        if (jarFile.exists()) {
                            ClasspathEntry classPathEntry = createClassPathEntry(hostmanager, sourcedata, sourcedomain, jarFile);
                            cpEntries.add(classPathEntry);
                        } else {// definetly ignors it, this will crash but the there is not much we can do excep
                                // logging it
                            FrameworkLog frameworkLog = sourcedata.getAdaptor().getFrameworkLog();
                            if (frameworkLog != null) {
                                Bundle bundle = sourcedata.getBundle();
                                String entryMessage = bundle.getSymbolicName() == null ? bundle.getLocation() : bundle
                                        .getSymbolicName();
                                frameworkLog.log(new FrameworkLogEntry(entryMessage, "one third party library file [" + jarFile
                                        + "] was not found, it is required for bundle [" + sourcedata.getSymbolicName()
                                        + "], please download it and place in [" + libJavaFolderFile + "]", 0, null, null));
                            }

                        }
                    }
                }// else //no lib folder found so ignor missing lib
            } catch (IOException e) {
                sourcedata.getAdaptor().getEventPublisher()
                        .publishFrameworkEvent(FrameworkEvent.ERROR, sourcedata.getBundle(), e);
            } catch (URISyntaxException e) {
                sourcedata.getAdaptor().getEventPublisher()
                        .publishFrameworkEvent(FrameworkEvent.ERROR, sourcedata.getBundle(), e);
            }
        }// else jar exist so let the usual entries to be used
        return false;
    }

    /**
     * creates the class path entry for the give file.
     * 
     * @param hostmanager
     * @param sourcedata
     * @param sourcedomain
     * @param jarFile, file must exist before calling this method, not check is done.
     * @return
     * @throws IOException, if an error occur while creating the bundle file
     */
    public ClasspathEntry createClassPathEntry(ClasspathManager hostmanager, BaseData sourcedata, ProtectionDomain sourcedomain,
            File jarFile) throws IOException {
        BundleFile externalJarBF = sourcedata.getAdaptor().createBundleFile(jarFile, sourcedata);
        ClasspathEntry classPathEntry = hostmanager.getBaseClassLoader().createClassPathEntry(externalJarBF, sourcedomain);
        // classPathEntry.setBaseData(sourcedata); // I am not sure whether this is required, if not set
        // then
        // getBaseData returns null.
        // one possible way of forcing the "set" is to use reflection but it is time consuming, so let's
        // wait to
        // see if it is really required.
        Object domain = classPathEntry.getDomain();
        if (domain instanceof BundleProtectionDomain) {
            ((BundleProtectionDomain) domain).setBundle(sourcedata.getBundle());
        }
        return classPathEntry;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.osgi.baseadaptor.hooks.ClassLoadingHook#findLibrary(org.eclipse.osgi.baseadaptor.BaseData,
     * java.lang.String)
     */
    @Override
    public String findLibrary(BaseData data, String libName) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.osgi.baseadaptor.hooks.ClassLoadingHook#getBundleClassLoaderParent()
     */
    @Override
    public ClassLoader getBundleClassLoaderParent() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.osgi.baseadaptor.hooks.ClassLoadingHook#createClassLoader(java.lang.ClassLoader,
     * org.eclipse.osgi.framework.adaptor.ClassLoaderDelegate,
     * org.eclipse.osgi.framework.adaptor.BundleProtectionDomain, org.eclipse.osgi.baseadaptor.BaseData,
     * java.lang.String[])
     */
    @Override
    public BaseClassLoader createClassLoader(ClassLoader parent, ClassLoaderDelegate delegate, BundleProtectionDomain domain,
            BaseData data, String[] bundleclasspath) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.osgi.baseadaptor.hooks.ClassLoadingHook#initializedClassLoader(org.eclipse.osgi.baseadaptor.loader
     * .BaseClassLoader, org.eclipse.osgi.baseadaptor.BaseData)
     */
    @Override
    public void initializedClassLoader(BaseClassLoader baseClassLoader, BaseData sourcedata) {
        // do nothing here
    }

    /**
     * return the folder where to find the missing libraries or null if none was defined
     * */
    protected File getLibJavaFolderFile() throws URISyntaxException {
        if (javaLibFolder == null) {
            String property = System.getProperty(TalendHookAdaptor.ORG_TALEND_EXTERNAL_LIB_FOLDER_SYS_PROP);
            if (property != null) {
                javaLibFolder = new File(property);
            }// else keep javaLibFolder null
        }
        return javaLibFolder;

    }

}
