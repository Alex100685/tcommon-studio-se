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
package org.talend.repository.utils;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.utils.PasswordHelper;
import org.talend.core.language.ECodeLanguage;
import org.talend.core.model.general.Project;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.User;
import org.talend.repository.model.RepositoryConstants;

/**
 * ggu class global comment. Detailled comment
 */
public class ProjectHelper {

    public static Project createProject(String projectName, String projectDescription, String projectLanguage,
            String projectAuthor, String projectAuthorPass) {
        return createProject(projectName, projectDescription, projectLanguage, projectAuthor, projectAuthorPass, null, null);
    }

    public static Project createProject(String projectName, String projectDescription, String projectLanguage, User authoer) {
        Project newProject = createProject(projectName, projectDescription, projectLanguage);
        newProject.setAuthor(authoer); // set in project to record
        return newProject;
    }

    public static Project createProject(String projectName, String projectDescription, ECodeLanguage language, User authoer) {
        Project newProject = createProject(projectName, projectDescription, language);
        newProject.setAuthor(authoer); // set in project to record
        return newProject;
    }

    public static Project createProject(String projectName, String projectDescription, String projectLanguage,
            String projectAuthor, String projectAuthorPass, String projectAuthorFirstname, String projectAuthorLastname) {

        Project newProject = createProject(projectName, projectDescription, projectLanguage);
        User newUser = createUser(projectAuthor, projectAuthorPass, projectAuthorFirstname, projectAuthorLastname);

        newProject.setAuthor(newUser); // set in project to record

        return newProject;
    }

    private static Project createProject(String projectName, String projectDescription, ECodeLanguage language) {
        Project newProject = new Project();
        newProject.setLabel(projectName);
        newProject.setTechnicalLabel(Project.createTechnicalName(newProject.getLabel()));
        newProject.setLanguage(language);
        newProject.setDescription(projectDescription);

        return newProject;
    }

    private static Project createProject(String projectName, String projectDescription, String projectLanguage) {
        return createProject(projectName, projectDescription, ECodeLanguage.getCodeLanguage(projectLanguage));
    }

    public static User createUser(String projectAuthor, String projectAuthorPass, String projectAuthorFirstname,
            String projectAuthorLastname) {
        User newUser = PropertiesFactory.eINSTANCE.createUser();
        newUser.setLogin(projectAuthor);
        newUser.setFirstName(projectAuthorFirstname);
        newUser.setLastName(projectAuthorLastname);
        if (projectAuthorPass != null && !"".equals(projectAuthorPass)) { //$NON-NLS-1$
            try {
                newUser.setPassword(PasswordHelper.encryptPasswd(projectAuthorPass));
            } catch (NoSuchAlgorithmException e) {
                ExceptionHandler.process(e);
            }
        }

        return newUser;
    }

    public static String generateSandbocProjectName(String login) {

        if (login != null && Pattern.matches(RepositoryConstants.MAIL_PATTERN, login.trim())) {
            int at = login.indexOf('@');
            if (at > -1) {
                String mailName = login.substring(0, at);
                if (mailName.length() > 0) {
                    StringBuffer sb = new StringBuffer();
                    sb.append("Sandbox_"); //$NON-NLS-1$
                    sb.append(mailName);
                    sb.append("_project"); //$NON-NLS-1$
                    return sb.toString();
                }
            }
        }
        return null;
    }
}
