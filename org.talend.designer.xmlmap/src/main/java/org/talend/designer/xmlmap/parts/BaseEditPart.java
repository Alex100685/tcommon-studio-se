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
package org.talend.designer.xmlmap.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

/**
 * wchen class global comment. Detailled comment
 */
public class BaseEditPart extends AbstractGraphicalEditPart implements Adapter {

    public void notifyChanged(Notification notification) {
        // TODO Auto-generated method stub
        System.out.println();
    }

    public Notifier getTarget() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setTarget(Notifier newTarget) {
    }

    public boolean isAdapterForType(Object type) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected IFigure createFigure() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void createEditPolicies() {
        // TODO Auto-generated method stub

    }

    @Override
    public void activate() {
        super.activate();
        ((EObject) getModel()).eAdapters().add(this);
    }

    @Override
    public void deactivate() {
        super.deactivate();
        ((EObject) getModel()).eAdapters().remove(this);
    }

}
