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
package org.talend.designer.xmlmap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.talend.commons.ui.swt.cursor.CursorHelper;
import org.talend.core.model.process.AbstractExternalNode;
import org.talend.core.model.process.IComponentDocumentation;
import org.talend.core.model.process.IExternalData;
import org.talend.designer.core.model.utils.emf.talendfile.AbstractExternalData;
import org.talend.designer.xmlmap.model.emf.xmlmap.XmlMapData;
import org.talend.designer.xmlmap.ui.MapperUI;

/**
 * wchen class global comment. Detailled comment
 */
public class XmlMapComponent extends AbstractExternalNode {

    private XmlMapData emfMapData;

    private MapperUI mapperUi;

    public XmlMapComponent() {
    }

    public int open(Display display) {

        // TimeMeasure.start("Total open");
        // TimeMeasure.display = false;

        Shell parentShell = display.getActiveShell();
        mapperUi = new MapperUI(this);

        CursorHelper.changeCursor(parentShell, SWT.CURSOR_WAIT);

        Shell shell = null;
        try {
            mapperUi.prepareModel(getIODataComponents(), getMetadataList());
            shell = mapperUi.createWindow(display);

        } finally {
            parentShell.setCursor(null);
        }
        while (shell != null && !shell.isDisposed()) {
            try {
                if (!display.readAndDispatch()) {
                    display.sleep();
                }
            } catch (Throwable e) {

            }
        }

        return mapperUi.getMapperDialogResponse();
    }

    public void initialize() {
    }

    public int open(Composite parent) {
        return open(parent.getDisplay());
    }

    public void setExternalData(IExternalData persistentData) {
        // TODO Auto-generated method stub

    }

    public void renameInputConnection(String oldName, String newName) {
        // TODO Auto-generated method stub

    }

    public void renameOutputConnection(String oldName, String newName) {
        // TODO Auto-generated method stub

    }

    public IComponentDocumentation getComponentDocumentation(String componentName, String tempFolderPath) {
        // TODO Auto-generated method stub
        return null;
    }

    public IExternalData getTMapExternalData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void renameMetadataColumnName(String conectionName, String oldColumnName, String newColumnName) {
        // TODO Auto-generated method stub

    }

    @Override
    public void buildExternalData(AbstractExternalData abstractData) {
        if (abstractData instanceof XmlMapData) {
            this.emfMapData = (XmlMapData) abstractData;
        }
    }

    @Override
    public AbstractExternalData saveExternalData() {
        return this.emfMapData;
    }

    public XmlMapData getEmfMapData() {
        return emfMapData;
    }

    public void setEmfMapData(XmlMapData emfMapData) {
        this.emfMapData = emfMapData;
    }
}
