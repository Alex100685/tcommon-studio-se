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
package org.talend.librariesmanager.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.common.util.EList;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.utils.workbench.extensions.ExtensionImplementationProvider;
import org.talend.commons.utils.workbench.extensions.ExtensionPointLimiterImpl;
import org.talend.commons.utils.workbench.extensions.IExtensionPointLimiter;
import org.talend.core.CorePlugin;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.language.ECodeLanguage;
import org.talend.core.language.LanguageManager;
import org.talend.core.model.components.IComponent;
import org.talend.core.model.components.IComponentsFactory;
import org.talend.core.model.general.ModuleNeeded;
import org.talend.core.model.general.ModuleNeeded.ELibraryInstallStatus;
import org.talend.core.model.process.IProcess2;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.RoutineItem;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryObject;
import org.talend.designer.core.model.utils.emf.component.IMPORTType;
import org.talend.librariesmanager.i18n.Messages;
import org.talend.repository.model.ComponentsFactoryProvider;
import org.talend.repository.model.ERepositoryStatus;
import org.talend.repository.model.IProxyRepositoryFactory;

/**
 * DOC smallet class global comment. Detailled comment <br/>
 * 
 * $Id: ModulesNeededProvider.java 1893 2007-02-07 11:33:35Z mhirt $
 * 
 */
public class ModulesNeededProvider {

    private static List<ModuleNeeded> componentImportNeedsList = new ArrayList<ModuleNeeded>();;

    private static List<ModuleNeeded> unUsedModules = new ArrayList<ModuleNeeded>();

    public static List<ModuleNeeded> getModulesNeeded() {
        // TimeMeasure.measureActive = true;
        // TimeMeasure.display = true;
        //        TimeMeasure.begin(Messages.getString("ModulesNeededProvider.0")); //$NON-NLS-1$

        /*
         * TimeMeasure.begin("ModulesNeededProvider.getModulesNeededForRoutines");
         * TimeMeasure.pause("ModulesNeededProvider.getModulesNeededForRoutines");
         * 
         * TimeMeasure.begin("ModulesNeededProvider.getModulesNeededForComponents");
         * TimeMeasure.pause("ModulesNeededProvider.getModulesNeededForComponents");
         * 
         * TimeMeasure.begin("ModulesNeededProvider.getModulesNeededForApplication");
         * TimeMeasure.pause("ModulesNeededProvider.getModulesNeededForApplication");
         * 
         * TimeMeasure.begin("ModulesNeededProvider.getModulesNeededForJobs");
         * TimeMeasure.pause("ModulesNeededProvider.getModulesNeededForJobs");
         */
        if (componentImportNeedsList.isEmpty()) {
            // TimeMeasure.step("ModulesNeededProvider.getModulesNeededForRoutines");
            componentImportNeedsList.addAll(getModulesNeededForRoutines());
            //            TimeMeasure.step(Messages.getString("ModulesNeededProvider.1"), "ModulesNeededProvider.getModulesNeededForRoutines"); //$NON-NLS-1$ //$NON-NLS-2$

            // TimeMeasure.begin("ModulesNeededProvider.getModulesNeededForApplication");
            componentImportNeedsList.addAll(getModulesNeededForApplication());
            //            TimeMeasure.step("ModulesNeededProvider.getAllMoudlesNeeded", "ModulesNeededProvider.getModulesNeededForApplication"); //$NON-NLS-1$ //$NON-NLS-2$

            // TimeMeasure.resume("ModulesNeededProvider.getModulesNeededForJobs");
            if (LanguageManager.getCurrentLanguage().equals(ECodeLanguage.JAVA)) {
                componentImportNeedsList.addAll(getModulesNeededForJobs());
            }
            //            TimeMeasure.step("ModulesNeededProvider.getAllMoudlesNeeded", "ModulesNeededProvider.getModulesNeededForJobs"); //$NON-NLS-1$ //$NON-NLS-2$

            // TimeMeasure.resume("ModulesNeededProvider.getModulesNeededForComponents");
            componentImportNeedsList.addAll(getModulesNeededForComponents());
            //            TimeMeasure.step("ModulesNeededProvider.getAllMoudlesNeeded", "ModulesNeededProvider.getModulesNeededForComponents"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        /*
         * TimeMeasure.end("ModulesNeededProvider.getModulesNeededForRoutines");
         * 
         * TimeMeasure.end("ModulesNeededProvider.getModulesNeededForComponents");
         * 
         * TimeMeasure.end("ModulesNeededProvider.getModulesNeededForApplication");
         * 
         * TimeMeasure.end("ModulesNeededProvider.getModulesNeededForJobs");
         */// TimeMeasure.measureActive = false;
        // TimeMeasure.display = false;
        //        TimeMeasure.end("ModulesNeededProvider.getAllMoudlesNeeded"); //$NON-NLS-1$
        return componentImportNeedsList;
    }

    public static List<String> getModulesNeededNames() {
        List<String> componentImportNeedsListNames = new ArrayList<String>();
        for (ModuleNeeded m : componentImportNeedsList) {
            componentImportNeedsListNames.add(m.getModuleName());
        }
        return componentImportNeedsListNames;
    }

    public static void reset() {
        componentImportNeedsList.clear();
    }

    /**
     *ftang Comment method "resetCurrentJobNeededModuleList".
     * 
     * @param process
     */
    public static void resetCurrentJobNeededModuleList(IProcess2 process) {
        // Step 1: remove all modules for current job;
        List<ModuleNeeded> moduleForCurrentJobList = new ArrayList<ModuleNeeded>(5);
        for (ModuleNeeded module : componentImportNeedsList) {
            if (module.getContext().equals("Job " + process.getProperty().getLabel())) { //$NON-NLS-1$
                moduleForCurrentJobList.add(module);
            }
        }
        componentImportNeedsList.removeAll(moduleForCurrentJobList);

        for (String neededLibrary : process.getNeededLibraries(false)) {
            boolean alreadyInImports = false;
            for (ModuleNeeded module : componentImportNeedsList) {
                if (module.getModuleName().equals(neededLibrary)) {
                    alreadyInImports = true;
                }
            }
            if (alreadyInImports) {
                continue;
            }

            // Step 2: re-add specific modules
            ModuleNeeded toAdd = new ModuleNeeded("Job " + process.getProperty().getLabel(), neededLibrary, //$NON-NLS-1$
                    "Required for the job " + process.getProperty().getLabel() + ".", true); //$NON-NLS-1$ //$NON-NLS-2$

            componentImportNeedsList.add(toAdd);

            // Step 3: remove added modules from unusedModule list
            ModuleNeeded unusedModule = null;
            for (ModuleNeeded module : unUsedModules) {
                if (module.getModuleName().equals(neededLibrary)) {
                    unusedModule = module;
                }
            }
            if (unusedModule != null) {
                unUsedModules.remove(unusedModule);
            }
        }
    }

    private static List<ModuleNeeded> getModulesNeededForComponents() {
        List<ModuleNeeded> importNeedsList = new ArrayList<ModuleNeeded>();
        IComponentsFactory compFac = ComponentsFactoryProvider.getInstance();
        List<IComponent> componentList = compFac.getComponents();
        for (IComponent component : componentList) {
            importNeedsList.addAll(component.getModulesNeeded());
        }
        return importNeedsList;
    }

    public static List<ModuleNeeded> getModulesNeededForJobs() {
        IProxyRepositoryFactory repositoryFactory = CorePlugin.getDefault().getRepositoryService().getProxyRepositoryFactory();

        List<ModuleNeeded> importNeedsList = new ArrayList<ModuleNeeded>();

        try {
            importNeedsList = repositoryFactory.getModulesNeededForJobs();
        } catch (PersistenceException e) {
            ExceptionHandler.process(e);
        }

        return importNeedsList;
    }

    public static List<ModuleNeeded> getModulesNeededForRoutines() {
        List<ModuleNeeded> importNeedsList = new ArrayList<ModuleNeeded>();
        IProxyRepositoryFactory repositoryFactory = CorePlugin.getDefault().getRepositoryService().getProxyRepositoryFactory();
        try {
            List<IRepositoryObject> routines = repositoryFactory.getAll(ERepositoryObjectType.ROUTINES, true);
            for (IRepositoryObject current : routines) {
                if (repositoryFactory.getStatus(current) != ERepositoryStatus.DELETED) {
                    Item item = current.getProperty().getItem();
                    RoutineItem routine = (RoutineItem) item;
                    EList imports = routine.getImports();
                    for (Object o : imports) {
                        IMPORTType currentImport = (IMPORTType) o;
                        // FIXME SML i18n
                        ModuleNeeded toAdd = new ModuleNeeded("Routine " + currentImport.getNAME(), currentImport.getMODULE(), //$NON-NLS-1$
                                currentImport.getMESSAGE(), currentImport.isREQUIRED());
                        // toAdd.setStatus(ELibraryInstallStatus.INSTALLED);
                        importNeedsList.add(toAdd);
                    }
                }
            }
        } catch (PersistenceException e) {
            ExceptionHandler.process(e);
        }
        return importNeedsList;
    }

    private static List<ModuleNeeded> getModulesNeededForApplication() {
        List<ModuleNeeded> importNeedsList = new ArrayList<ModuleNeeded>();

        IExtensionPointLimiter actionExtensionPoint = new ExtensionPointLimiterImpl(
                "org.talend.core.librariesNeeded", "libraryNeeded"); //$NON-NLS-1$ //$NON-NLS-2$
        List<IConfigurationElement> extension = ExtensionImplementationProvider.getInstanceV2(actionExtensionPoint);

        ECodeLanguage projectLanguage = ((RepositoryContext) CorePlugin.getContext().getProperty(Context.REPOSITORY_CONTEXT_KEY))
                .getProject().getLanguage();
        for (IConfigurationElement current : extension) {
            ECodeLanguage lang = ECodeLanguage.getCodeLanguage(current.getAttribute("language")); //$NON-NLS-1$
            if (lang == projectLanguage) {
                String context = current.getAttribute("context"); //$NON-NLS-1$
                String name = current.getAttribute("name"); //$NON-NLS-1$
                String message = current.getAttribute("message"); //$NON-NLS-1$
                boolean required = new Boolean(current.getAttribute("required")); //$NON-NLS-1$
                importNeedsList.add(new ModuleNeeded(context, name, message, required));
            }
        }

        return importNeedsList;
    }

    public static List<ModuleNeeded> getModulesNeeded(String componentName) {
        List<ModuleNeeded> toReturn = new ArrayList<ModuleNeeded>();
        for (ModuleNeeded current : getModulesNeeded()) {
            if (current.getContext().equals(componentName)) {
                toReturn.add(current);
            }
        }

        return toReturn;
    }

    /**
     * qiang.zhang Comment method "getImportModules".
     * 
     * @param name
     * @param context
     */
    public static void userAddImportModules(String context, String name, ELibraryInstallStatus status) {
        boolean required = true;
        String message = Messages.getString("ModulesNeededProvider.importModule"); //$NON-NLS-1$
        ModuleNeeded needed = new ModuleNeeded(context, name, message, required);
        needed.setStatus(status);
        componentImportNeedsList.add(needed);
    }

    public static void userAddUnusedModules(String context, String name) {
        boolean required = false;
        String message = Messages.getString("ModulesNeededProvider.unusedModule"); //$NON-NLS-1$
        ModuleNeeded needed = new ModuleNeeded(context, name, message, required);
        needed.setStatus(ELibraryInstallStatus.UNUSED);
        unUsedModules.add(needed);
    }

    public static void userRemoveUnusedModules(String url) {
        ModuleNeeded needed = null;
        for (ModuleNeeded module : unUsedModules) {
            if (module.getContext().equals(url)) {
                needed = module;
                break;
            }
        }
        if (needed != null) {
            unUsedModules.remove(needed);
        }
    }

    /**
     * Getter for unUsedModules.
     * 
     * @return the unUsedModules
     */
    public static List<ModuleNeeded> getUnUsedModules() {
        return unUsedModules;
    }

}
