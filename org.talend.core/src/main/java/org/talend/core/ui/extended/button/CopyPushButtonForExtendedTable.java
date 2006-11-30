// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
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
package org.talend.core.ui.extended.button;

import java.util.Arrays;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.talend.commons.ui.command.ICommonCommand;
import org.talend.commons.ui.swt.advanced.dataeditor.commands.ExtendedTableCopyCommand;
import org.talend.commons.ui.swt.extended.table.AbstractExtendedTableViewer;


/**
 * DOC amaumont  class global comment. Detailled comment
 * <br/>
 *
 * $Id$
 *
 */
public class CopyPushButtonForExtendedTable extends CopyPushButton {

    
    /**
     * DOC amaumont SchemaTargetAddPushButton constructor comment.
     * @param parent
     * @param extendedControlViewer
     */
    public CopyPushButtonForExtendedTable(Composite parent, AbstractExtendedTableViewer extendedTableViewer) {
        super(parent, extendedTableViewer);
    }

    protected ICommonCommand getCommandToExecute() {
        AbstractExtendedTableViewer extendedTableViewer = (AbstractExtendedTableViewer) extendedControlViewer;
        TableViewer tableViewer = extendedTableViewer.getTableViewerCreator().getTableViewer();
        ISelection selection = tableViewer.getSelection();
        StructuredSelection structuredSelection = (StructuredSelection) selection;
        Object[] objects = structuredSelection.toArray();
        return new ExtendedTableCopyCommand(Arrays.asList(objects));
    }
    
}
