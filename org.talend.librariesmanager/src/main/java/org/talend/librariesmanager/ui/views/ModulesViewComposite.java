// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006-2007 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.librariesmanager.ui.views;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator.LAYOUT_MODE;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator.SORT;
import org.talend.commons.utils.data.bean.IBeanPropertyAccessors;
import org.talend.core.model.general.ModuleNeeded;
import org.talend.librariesmanager.i18n.Messages;
import org.talend.librariesmanager.model.ModulesNeededProvider;

/**
 * This is the composite filled in the ModulesView. So it implemented the inferface IModulesViewComposite. Know more see
 * interface IModulesViewComposite.
 * 
 * yzhang class global comment. Detailled comment <br/>
 * 
 * $Id: PerlModulesViewComposite.java PerlModulesViewComposite 2007-1-26 下�?�1�7:53:04 +0000 (下�?�1�7:53:04, 2007-1-26
 * 2007) yzhang $
 * 
 */
public class ModulesViewComposite extends Composite {

    protected static final String ID_STATUS = "status"; //$NON-NLS-1$

    private static TableViewerCreator tableViewerCreator;

    private static Logger log = Logger.getLogger(ModulesView.class);

    private IContextActivation ca;

    /**
     * Construct a new Perl modules view composite.
     * 
     * yzhang PerlModulesViewComposite constructor comment.
     * 
     * @param parent
     * @param style
     */
    public ModulesViewComposite(Composite parent) {
        super(parent, SWT.NONE);

        this.setLayout(new FormLayout());
        FormData formData = new FormData();

        Composite rightPartComposite = new Composite(this, SWT.NONE);
        formData = new FormData();
        formData.top = new FormAttachment(0);
        formData.left = new FormAttachment(0);
        formData.right = new FormAttachment(100);
        formData.bottom = new FormAttachment(100);
        rightPartComposite.setLayoutData(formData);
        rightPartComposite.setLayout(new FillLayout());

        tableViewerCreator = new TableViewerCreator(rightPartComposite);
        tableViewerCreator.setCheckboxInFirstColumn(false);
        tableViewerCreator.setColumnsResizableByDefault(true);
        tableViewerCreator.setLayoutMode(LAYOUT_MODE.FILL_HORIZONTAL);
        tableViewerCreator.createTable();

        TableViewerCreatorColumn column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setTitle(Messages.getString("PerlModulesViewComposite.Status.TitleText")); //$NON-NLS-1$
        column.setId(ID_STATUS);
        column.setSortable(true);
        column.setImageProvider(new StatusImageProvider());
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<ModuleNeeded, String>() {

            public String get(ModuleNeeded bean) {
                String str = null;
                switch (bean.getStatus()) {
                case INSTALLED:
                    str = Messages.getString("ModulesViewComposite.hint.installed"); //$NON-NLS-1$
                    break;
                case NOT_INSTALLED:
                    str = Messages.getString("ModulesViewComposite.hint.notInstalled"); //$NON-NLS-1$
                    break;
                default:
                    str = Messages.getString("ModulesViewComposite.hint.unknown"); //$NON-NLS-1$
                }
                return str;
            }

            public void set(ModuleNeeded bean, String value) {
            }
        });

        column.setWeight(3);
        column.setModifiable(false);

        column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setTitle(Messages.getString("PerlModulesViewComposite.Component.TitleText")); //$NON-NLS-1$
        column.setSortable(true);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<ModuleNeeded, String>() {

            public String get(ModuleNeeded bean) {
                return bean.getContext();
            }

            public void set(ModuleNeeded bean, String value) {
            }
        });

        column.setModifiable(false);
        column.setWeight(4);

        column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setTitle(Messages.getString("PerlModulesViewComposite.Module.TitleText")); //$NON-NLS-1$
        column.setSortable(true);
        tableViewerCreator.setDefaultSort(column, SORT.ASC);
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<ModuleNeeded, String>() {

            public String get(ModuleNeeded bean) {
                return bean.getModuleName();
            }

            public void set(ModuleNeeded bean, String value) {
            }
        });

        column.setModifiable(false);
        column.setWeight(6);

        column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setTitle(Messages.getString("PerlModulesViewComposite.Required.TitleText")); //$NON-NLS-1$

        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<ModuleNeeded, String>() {

            public String get(ModuleNeeded bean) {
                return bean.getInformationMsg();
            }

            public void set(ModuleNeeded bean, String value) {
            }
        });

        column.setModifiable(true);
        column.setWeight(10);

        column = new TableViewerCreatorColumn(tableViewerCreator);
        column.setTitle(Messages.getString("PerlModulesViewComposite.Required.Title")); //$NON-NLS-1$
        column.setImageProvider(new RequiredImageProvider());
        column.setSortable(true);
        column.setDisplayedValue(""); //$NON-NLS-1$
        column.setBeanPropertyAccessors(new IBeanPropertyAccessors<ModuleNeeded, String>() {

            public String get(ModuleNeeded bean) {
                return String.valueOf(bean.isRequired());
            }

            public void set(ModuleNeeded bean, String value) {
            }
        });

        column.setModifiable(false);
        column.setWeight(2);

        List<ModuleNeeded> modules = ModulesNeededProvider.getModulesNeeded();

        tableViewerCreator.init(filterHidenModule(modules));

        FocusListener fl = new FocusListener() {

            public void focusGained(FocusEvent e) {
                log.trace("Modules gain focus"); //$NON-NLS-1$
                IContextService contextService = (IContextService) PlatformUI.getWorkbench().getAdapter(IContextService.class);
                ca = contextService.activateContext("talend.modules"); //$NON-NLS-1$
            }

            public void focusLost(FocusEvent e) {
                log.trace("Modules lost focus"); //$NON-NLS-1$
                if (ca != null) {
                    IContextService contextService = (IContextService) PlatformUI.getWorkbench()
                            .getAdapter(IContextService.class);
                    contextService.deactivateContext(ca);
                }
            }
        };

        parent.addFocusListener(fl);
        rightPartComposite.addFocusListener(fl);
        tableViewerCreator.getTableViewer().getTable().addFocusListener(fl);
    }

    /**
     * DOC bqian Comment method "filterHidenModule".
     * 
     * @param modules
     * @return
     */
    private List filterHidenModule(List<ModuleNeeded> modules) {
        List<ModuleNeeded> list = new ArrayList<ModuleNeeded>();
        for (Iterator<ModuleNeeded> iter = modules.iterator(); iter.hasNext();) {
            ModuleNeeded module = iter.next();
            if (module.isShow()) {
                list.add(module);
            }
        }
        return list;
    }

    /*
     * Be called when the set focus of modules view was called.
     * 
     * @see org.eclipse.swt.widgets.Composite#setFocus()
     */
    public boolean setFocus() {
        return tableViewerCreator.getTableViewer().getTable().setFocus();
    }

    /*
     * Be called when the refresh of modules view was called.
     * 
     * @see org.talend.designer.codegen.perlmodule.ui.views.IModulesViewComposite#refresh()
     */
    public void refresh() {
        tableViewerCreator.getTableViewer().refresh();
    }

}
