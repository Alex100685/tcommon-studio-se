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
package org.talend.commons.ui.image;

import org.talend.commons.ui.CommonUIPlugin;

/**
 * 
 * DOC smallet ImageProvider class global comment. Detailled comment <br/>
 * 
 * $Id: ImageProvider.java 418 2006-11-13 16:01:26 +0000 (lun., 13 nov. 2006) cantoine $
 * 
 */
public enum EImage implements IImage {

    DEFAULT_IMAGE,
    EMPTY("/icons/empty.gif"), //$NON-NLS-1$

    SAVE_ICON("/icons/save.png"), //$NON-NLS-1$
    COPY_ICON("/icons/copy.gif"), //$NON-NLS-1$
    PASTE_ICON("/icons/paste.gif"), //$NON-NLS-1$
    CUT_ICON("/icons/cut.png"), //$NON-NLS-1$
    DELETE_ICON("/icons/delete.gif"), //$NON-NLS-1$
    RESTORE_ICON("/icons/add.gif"), //$NON-NLS-1$
    ADD_ICON("/icons/add.gif"), //$NON-NLS-1$
    ADD_ALL_ICON("/icons/addall.gif"), //$NON-NLS-1$
    MINUS_ICON("/icons/delete.gif"), //$NON-NLS-1$
    REFRESH_ICON("/icons/refresh.gif"), //$NON-NLS-1$
    EDIT_ICON("/icons/write_obj.gif"), //$NON-NLS-1$
    READ_ICON("/icons/read_obj.gif"), //$NON-NLS-1$

    RESET_DBTYPES_ICON("/icons/reset_dbtypes.jpg"), //$NON-NLS-1$

    IMPORT_ICON("/icons/import.gif"), //$NON-NLS-1$
    EXPORT_ICON("/icons/export.gif"), //$NON-NLS-1$

    CHECKED_ICON("/icons/checked.gif"), //$NON-NLS-1$
    UNCHECKED_ICON("/icons/unchecked.gif"), //$NON-NLS-1$

    ERRORSIMPLEMESS_ICON("/icons/errorSimpleMess.gif"), //$NON-NLS-1$
    RIGHTPRESS_ICON("/icons/rightpressedarrow.gif"), //$NON-NLS-1$
    DOWNPRESS_ICON("/icons/downpressedarrow.gif"), //$NON-NLS-1$

    ERROR_ICON("/icons/error.gif"), //$NON-NLS-1$
    WARNING_ICON("/icons/warning.gif"), //$NON-NLS-1$
    INFORMATION_ICON("/icons/unknown.gif"), //$NON-NLS-1$
    ERROR_SMALL("/icons/error_small.gif"), //$NON-NLS-1$
    WARNING_SMALL("/icons/warning_small.gif"), //$NON-NLS-1$
    INFORMATION_SMALL("/icons/info_ovr.gif"), //$NON-NLS-1$
    OK("/icons/ok.png"), //$NON-NLS-1$

    PARALLEL_EXECUTION("/icons/parallelize.png"), //$NON-NLS-1$

    UP_ICON("/icons/up.gif"), //$NON-NLS-1$
    DOWN_ICON("/icons/down.gif"), //$NON-NLS-1$
    LEFT_ICON("/icons/left.gif"), //$NON-NLS-1$
    LEFTX_ICON("/icons/leftx.png"), //$NON-NLS-1$
    RIGHT_ICON("/icons/right.gif"), //$NON-NLS-1$
    RIGHTX_ICON("/icons/rightx.png"), //$NON-NLS-1$

    KEY_ICON("/icons/key.gif"), //$NON-NLS-1$
    HIERARCHY_ICON("/icons/hierarchicalLayout.gif"), //$NON-NLS-1$

    THREE_DOTS_ICON("/icons/dots_button.gif"), //$NON-NLS-1$

    PROPERTIES_WIZ("/icons/editpref_wiz.gif"), //$NON-NLS-1$

    TRACES_EXPAND("/icons/traces_expand.png"), //$NON-NLS-1$
    TRACES_COLLAPSE("/icons/traces_collapse.png"), //$NON-NLS-1$

    SUBJOB_EXPAND("/icons/subjob_expand.png"), //$NON-NLS-1$
    SUBJOB_COLLAPSE("/icons/subjob_collapse.png"), //$NON-NLS-1$

    COMPACT_VIEW("/icons/compact.png"), //$NON-NLS-1$
    NO_COMPACT_VIEW("/icons/noCompact.png"), //$NON-NLS-1$
    TABLE_VIEW("/icons/array_hot.png"), //$NON-NLS-1$
    NO_TABLE_VIEW("/icons/array.png"), //$NON-NLS-1$
    COMPOSITE_BACKGROUND("/icons/compositeBackground.jpg"), //$NON-NLS-1$

    LOCK_ICON("/icons/lock.gif"), //$NON-NLS-1$
    UNLOCK_ICON("/icons/unlock.gif"), //$NON-NLS-1$
    
    MERGE_ICON("/icons/synonym.gif");

    ;

    private String path;

    EImage() {
        this.path = "/icons/unknown.gif"; //$NON-NLS-1$
    }

    EImage(String path) {
        this.path = path;
    }

    /**
     * Getter for path.
     * 
     * @return the path
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Getter for clazz.
     * 
     * @return the clazz
     */
    public Class getLocation() {
        return CommonUIPlugin.class;
    }

}
