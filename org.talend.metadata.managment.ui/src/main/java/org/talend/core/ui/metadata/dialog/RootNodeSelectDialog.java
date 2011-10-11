package org.talend.core.ui.metadata.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.talend.repository.ui.wizards.metadata.connection.files.xml.FoxNodeComboViewProvider;
import org.talend.repository.ui.wizards.metadata.connection.files.xml.treeNode.FOXTreeNode;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class RootNodeSelectDialog extends Dialog {

    private ComboViewer rootComboViewer;

    private Combo rootCombo;

    private List<FOXTreeNode> nodes;

    private FOXTreeNode selectedNode;

    public RootNodeSelectDialog(Shell parentShell, List<FOXTreeNode> nodes) {
        super(parentShell);
        this.nodes = nodes;
    }

    protected Control createDialogArea(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        composite.setLayout(new GridLayout(2, false));

        final Label rootLabel = new Label(composite, SWT.NONE);
        rootLabel.setText("Root");

        rootComboViewer = new ComboViewer(composite, SWT.READ_ONLY);
        FoxNodeComboViewProvider comboProvider = new FoxNodeComboViewProvider();
        rootComboViewer.setLabelProvider(comboProvider);
        rootComboViewer.setContentProvider(comboProvider);
        rootCombo = rootComboViewer.getCombo();
        GridData comboData = new GridData(SWT.FILL, SWT.CENTER, true, false);
        rootCombo.setLayoutData(comboData);

        init();

        return composite;
    }

    private void init() {
        if (nodes != null && nodes.size() > 0) {
            rootComboViewer.setInput(nodes);
            rootCombo.select(0);
        }
    }

    protected void okPressed() {
        IStructuredSelection selection = ((IStructuredSelection) rootComboViewer.getSelection());
        Object obj = selection.getFirstElement();
        if (obj != null) {
            selectedNode = (FOXTreeNode) obj;
        }

        super.okPressed();
    }

    public FOXTreeNode getSelectedNode() {
        return this.selectedNode;
    }

}
