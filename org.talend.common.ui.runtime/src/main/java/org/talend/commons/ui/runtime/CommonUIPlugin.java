// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.commons.ui.runtime;

import java.util.Properties;

import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.talend.commons.CommonsPlugin;
import org.talend.commons.exception.ExceptionService;
import org.talend.commons.ui.runtime.exception.ExceptionServiceImpl;

public class CommonUIPlugin implements BundleActivator {

    private static Boolean fullyHeadless = null;

    /*
     * (non-Javadoc)
     * 
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext context) throws Exception {
        Properties props = new Properties();
        context.registerService(ExceptionService.class.getName(), new ExceptionServiceImpl(), props);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext context) throws Exception {
    }

    /**
     * To check if there is GUI in the build of not, not especially means we're in commandline mode. (but for
     * commandline, this one will return true)<br>
     * <br>
     * To rename later to isHeadless later once the other function is renamed as well.
     * 
     * @return
     */
    public static boolean isFullyHeadless() {
        if (fullyHeadless != null) {
            return fullyHeadless;
        }
        fullyHeadless = CommonsPlugin.isHeadless();
        if (!CommonsPlugin.isHeadless()) {
            Bundle b = Platform.getBundle("org.eclipse.swt"); //$NON-NLS-1$
            if (b == null || (b.getState() != Bundle.ACTIVE && b.getState() != Bundle.RESOLVED)) {
                fullyHeadless = true;
                return fullyHeadless;
            }
            try {
                Display.getDefault();
            } catch (SWTError e) {
                fullyHeadless = true;
            }
        }

        return fullyHeadless;
    }

}
