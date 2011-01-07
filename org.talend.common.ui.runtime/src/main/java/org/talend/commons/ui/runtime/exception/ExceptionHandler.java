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
package org.talend.commons.ui.runtime.exception;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.eclipse.swt.widgets.Shell;
import org.talend.commons.exception.BusinessException;
import org.talend.commons.exception.FatalException;
import org.talend.commons.exception.SystemException;
import org.talend.commons.ui.runtime.i18n.Messages;

/**
 * Implementation of exception handling strategy.<br/>
 * 
 * $Id: ExceptionHandler.java 7038 2007-11-15 14:05:48Z plegall $
 * 
 */
public final class ExceptionHandler {

    private static Logger log = Logger.getLogger(ExceptionHandler.class);

    /**
     * Empty constructor.
     */
    private ExceptionHandler() {
    }

    /**
     * Log message relative to ex param. Log level depends on exception type.
     * 
     * @param ex - exception to log
     */
    public static void process(Throwable ex) {
        Priority priority = getPriority(ex);
        process(ex, priority);
    }

    public static void log(String message) {
        log.log(Level.INFO, message);
    }

    public static void process(Throwable ex, Priority priority) {
        String message = ex.getMessage();

        log.log(priority, message, ex);

        if (priority == Level.FATAL) {
            MessageBoxExceptionHandler.showMessage(ex, new Shell());
        }
    }

    /**
     * Return priority corresponding to the exception implementation.
     * 
     * @param ex - the exception to evaluate priority
     * @return the priority corresponding to the exception implementation
     */
    protected static Priority getPriority(Throwable ex) {
        if (ex == null) {
            throw new IllegalArgumentException(Messages.getString("ExceptionHandler.Parameter.BeNull")); //$NON-NLS-1$
        }

        if (ex instanceof BusinessException) {
            return Level.INFO;
        } else if (ex instanceof FatalException) {
            return Level.FATAL;
        } else if (ex instanceof SystemException) {
            return Level.WARN;
        } else {
            return Level.ERROR;
        }
    }

    /**
     * bug 17654：import the xml file as the schema will throw error.
     * 
     * DOC yhch Comment method "processForSchemaImportXml".
     * 
     * @param ex
     */
    public static void processForSchemaImportXml(Throwable ex) {
        MessageBoxExceptionHandler.showMessageForSchemaImportXml(ex, new Shell());
    }
}
