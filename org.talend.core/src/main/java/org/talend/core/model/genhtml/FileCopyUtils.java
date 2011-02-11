// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.core.model.genhtml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.talend.commons.ui.runtime.exception.ExceptionHandler;

/**
 * This class is used for copying file from one place to the other.
 * 
 * $Id: CopyFileUtils.java 2007-3-9,下午07:28:36 ftang $
 * 
 */
public class FileCopyUtils {

    /**
     * This method is used for coping file from one place to the other.
     * 
     * @param srcFilePath
     * @param destFilePath
     * @throws Exception
     */
    public static void copy(String srcFilePath, String destFilePath) {
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            byte[] bytearray = new byte[512];
            int len = 0;
            input = new FileInputStream(srcFilePath);
            output = new FileOutputStream(destFilePath);
            while ((len = input.read(bytearray)) != -1) {
                output.write(bytearray, 0, len);
            }

        } catch (Exception fe) {
            ExceptionHandler.process(fe);
        }

        finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                }
            }
        }
    }

    public static void copyFolder(String oldPath, String newPath) {

        try {
            (new File(newPath)).mkdirs();
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }

    }
}
