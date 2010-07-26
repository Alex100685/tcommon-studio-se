// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.core.model.migration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.i18n.Messages;
import org.talend.core.model.general.Project;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.IRepositoryService;

/**
 */
public abstract class AbstractItemMigrationTask extends AbstractMigrationTask implements IProjectMigrationTask {

    private static Logger log = Logger.getLogger(AbstractItemMigrationTask.class);

    private Project project;

    public ExecutionResult execute(Project project) {
        setProject(project);
        IRepositoryService service = (IRepositoryService) GlobalServiceRegister.getDefault().getService(IRepositoryService.class);
        IProxyRepositoryFactory factory = service.getProxyRepositoryFactory();
        ExecutionResult executeFinal = null;
        List<IRepositoryViewObject> list = new ArrayList<IRepositoryViewObject>();
        try {
            for (ERepositoryObjectType curTyp : getTypes()) {
                list.addAll(factory.getAll(curTyp, true, true));
            }

            if (list.isEmpty()) {
                return ExecutionResult.NOTHING_TO_DO;
            }

            for (IRepositoryViewObject object : list) {
                ExecutionResult execute = null;
                Item item = object.getProperty().getItem();

                execute = execute(item);
                // reload the propoerty in case the item resources have changed
                factory.reload(object.getProperty());
                if (execute == ExecutionResult.FAILURE) {
                    log.warn(Messages.getString(
                            "AbstractItemMigrationTask.taskFailed", this.getName(), item.getProperty().getLabel())); //$NON-NLS-1$
                    executeFinal = ExecutionResult.FAILURE;
                }
                if (executeFinal != ExecutionResult.FAILURE) {
                    executeFinal = execute;
                }
            }

            return executeFinal;
        } catch (PersistenceException e) {
            ExceptionHandler.process(e);
            return ExecutionResult.FAILURE;
        }
    }

    public ExecutionResult execute(Project project, Item item) {
        if (!getTypes().contains(ERepositoryObjectType.getItemType(item))) {
            return ExecutionResult.NOTHING_TO_DO;
        }
        setProject(project);
        return execute(item);
    }

    public abstract ExecutionResult execute(Item item);

    public List<ERepositoryObjectType> getTypes() {
        return Arrays.asList(ERepositoryObjectType.values());
    }

    public final boolean isApplicableOnItems() {
        return true;
    }

    /**
     * Getter for project.
     * 
     * @return the project
     */
    public Project getProject() {
        return this.project;
    }

    /**
     * Sets the project.
     * 
     * @param project the project to set
     */
    public void setProject(Project project) {
        this.project = project;
    }

}
