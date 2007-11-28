// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.core.ui.snippet;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.talend.commons.exception.MessageBoxExceptionHandler;
import org.talend.core.model.properties.SnippetItem;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.snippets.SnippetManager;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.RepositoryNode.ENodeType;
import org.talend.repository.model.RepositoryNode.EProperties;

/**
 * DOC bqian class global comment. Detailled comment <br/>
 * 
 */
public class SnippetDropTargetListener implements TransferDropTargetListener {

    TextViewer viewer;

    public SnippetDropTargetListener(TextViewer viewer) {
        this.viewer = viewer;
    }

    public void dragEnter(DropTargetEvent event) {
        // TODO Auto-generated method stub

    }

    public void dragLeave(DropTargetEvent event) {
        // TODO Auto-generated method stub

    }

    public void dragOperationChanged(DropTargetEvent event) {
        // TODO Auto-generated method stub

    }

    public void dragOver(DropTargetEvent event) {
        RepositoryNode sourceNode = getSelection();
        ENodeType type = sourceNode.getType();
        if (type.equals(ENodeType.SIMPLE_FOLDER)) {
            event.detail = DND.DROP_NONE;
        }
    }

    private RepositoryNode getSelection() {
        LocalSelectionTransfer transfer = (LocalSelectionTransfer) getTransfer();
        IStructuredSelection selection = (IStructuredSelection) transfer.getSelection();
        RepositoryNode node = (RepositoryNode) selection.getFirstElement();
        return node;
    }

    public void drop(DropTargetEvent event) {
        RepositoryNode node = getSelection();
        if (node.getProperties(EProperties.CONTENT_TYPE).equals(ERepositoryObjectType.SNIPPETS)) {
            SnippetItem snippetItem = (SnippetItem) node.getObject().getProperty().getItem();
            Shell shell = viewer.getTextWidget().getShell();
            SnippetManager manager = new SnippetManager();

            String content = manager.applySnippet(snippetItem, shell);

            // String content = SnippetManager.SNIPPET_PREFFIX + snippetItem.getProperty().getLabel()
            // + SnippetManager.SNIPPET_SUFFIX;

            Point sel = viewer.getSelectedRange();
            IDocument document = viewer.getDocument();
            try {
                document.replace(sel.x, 0, content);
            } catch (BadLocationException ex) {
                MessageBoxExceptionHandler.process(ex);
            }
        }
    }

    public void dropAccept(DropTargetEvent event) {
        // TODO Auto-generated method stub

    }

    public Transfer getTransfer() {
        return LocalSelectionTransfer.getTransfer();
    }

    public boolean isEnabled(DropTargetEvent event) {
        return true;
    }
}
