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
package org.talend.repository.ui.wizards;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.ui.image.ImageProvider;
import org.talend.commons.ui.swt.dialogs.RepositoryFolderSelectionDialog;
import org.talend.commons.utils.VersionUtils;
import org.talend.core.CorePlugin;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.i18n.Messages;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.JobletProcessItem;
import org.talend.core.model.properties.ProcessItem;
import org.talend.core.model.properties.Property;
import org.talend.core.model.properties.RoutineItem;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryObject;
import org.talend.core.ui.images.ECoreImage;
import org.talend.core.utils.KeywordsValidator;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.IRepositoryService;
import org.talend.repository.model.RepositoryConstants;
import org.talend.repository.ui.properties.StatusHelper;

/**
 * Wizard page contains common properties fields.<br/>
 * 
 * $Id: PropertiesWizardPage.java 914 2006-12-08 08:28:53 +0000 (星期五, 08 十二月 2006) bqian $
 * 
 */
public abstract class PropertiesWizardPage extends WizardPage {

    /** Name text. */
    protected Text nameText;

    public Text getNameText() {
        return this.nameText;
    }

    /** Purpose text. */
    protected Text purposeText;

    /** Comment text. */
    protected Text descriptionText;

    /** Author text. */
    protected Text authorText;

    /** Locker text. */
    protected Text lockerText;

    /** Version text. */
    protected Text versionText;

    /** Status text. */
    // protected Text statusText;
    private CCombo statusText;

    /** Version upgrade major button. */
    private Button versionMajorBtn;

    /** Version upgrade minor button. */
    private Button versionMinorBtn;

    private Text pathText;

    protected IStatus nameStatus;

    private IStatus purposeStatus;

    private IStatus commentStatus;

    private boolean nameModifiedByUser = false;

    private boolean update = false;

    protected Property property;

    private IPath destinationPath;

    private String path;

    private boolean readOnly;

    private StatusHelper statusHelper = null;

    private boolean editPath = true;

    private List<IRepositoryObject> listExistingObjects;

    private boolean retrieveNameFinished = false;

    private static final boolean NEED_CANCEL_BUTTON = true;

    protected PropertiesWizardPage(String pageName, Property property, IPath destinationPath) {
        this(pageName, property, destinationPath, false, true);
    }

    protected PropertiesWizardPage(String pageName, final Property property, IPath destinationPath, boolean readOnly,
            boolean editPath) {
        super(pageName);
        IRepositoryService service = (IRepositoryService) GlobalServiceRegister.getDefault().getService(IRepositoryService.class);
        statusHelper = new StatusHelper(service.getProxyRepositoryFactory());
        this.destinationPath = destinationPath;

        this.readOnly = readOnly;
        this.editPath = editPath;

        nameStatus = createOkStatus();
        purposeStatus = createOkStatus();
        commentStatus = createOkStatus();

        Job job = new Job("") { //$NON-NLS-1$

            @Override
            protected IStatus run(IProgressMonitor monitor) {
                ERepositoryObjectType type = ERepositoryObjectType.getItemType(property.getItem());
                try {
                    listExistingObjects = CorePlugin.getDefault().getProxyRepositoryFactory().getAll(type, true, false);
                } catch (PersistenceException e) {
                    return new org.eclipse.core.runtime.Status(IStatus.ERROR, "", 1, "", e); //$NON-NLS-1$ //$NON-NLS-2$
                }
                retrieveNameFinished = true;
                return Status.OK_STATUS;
            }
        };
        job.setUser(false);
        job.setPriority(Job.BUILD);
        job.schedule(); // start as soon as possible

        this.property = property;

    }

    protected boolean isReadOnly() {
        return readOnly;
    }

    /**
     * Getter for editPath.
     * 
     * @return the editPath
     */
    public boolean isEditPath() {
        return this.editPath;
    }

    /**
     * Sets the editPath.
     * 
     * @param editPath the editPath to set
     */
    public void setEditPath(boolean editPath) {
        this.editPath = editPath;
    }

    /**
     * Getter for isUpdate.
     * 
     * @return the isUpdate
     */
    public boolean isUpdate() {
        return this.update;
    }

    /**
     * Sets the isUpdate.
     * 
     * @param isUpdate the isUpdate to set
     */
    public void setUpdate(boolean update) {
        this.update = update;
    }

    /**
     * Getter for nameModifiedByUser.
     * 
     * @return the nameModifiedByUser
     */
    public boolean isNameModifiedByUser() {
        return this.nameModifiedByUser;
    }

    /**
     * Sets the nameModifiedByUser.
     * 
     * @param nameModifiedByUser the nameModifiedByUser to set
     */
    public void setNameModifiedByUser(boolean nameModifiedByUser) {
        this.nameModifiedByUser = nameModifiedByUser;
    }

    /**
     * Getter for destinationPath.
     * 
     * @return the destinationPath
     */
    public IPath getDestinationPath() {
        return new Path(pathText.getText());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        GridData data;

        // Name
        Label nameLab = new Label(parent, SWT.NONE);
        nameLab.setText(Messages.getString("PropertiesWizardPage.Name")); //$NON-NLS-1$

        nameText = new Text(parent, SWT.BORDER);
        nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        nameText.setEditable(!readOnly);

        // Purpose
        Label purposeLab = new Label(parent, SWT.NONE);
        purposeLab.setText(Messages.getString("PropertiesWizardPage.Purpose")); //$NON-NLS-1$

        purposeText = new Text(parent, SWT.BORDER);
        purposeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        purposeText.setEditable(!readOnly);

        // Description
        Label descriptionLab = new Label(parent, SWT.NONE);
        descriptionLab.setText(Messages.getString("PropertiesWizardPage.Description")); //$NON-NLS-1$
        descriptionLab.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

        descriptionText = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        data = new GridData(GridData.FILL_HORIZONTAL);
        data.heightHint = 60;
        descriptionText.setLayoutData(data);
        descriptionText.setEditable(!readOnly);

        // Author
        Label authorLab = new Label(parent, SWT.NONE);
        authorLab.setText(Messages.getString("PropertiesWizardPage.Author")); //$NON-NLS-1$

        authorText = new Text(parent, SWT.BORDER);
        authorText.setEnabled(false);
        authorText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        // Locker
        Label lockerLab = new Label(parent, SWT.NONE);
        lockerLab.setText(Messages.getString("PropertiesWizardPage.Locker")); //$NON-NLS-1$

        lockerText = new Text(parent, SWT.BORDER);
        lockerText.setEnabled(false);
        lockerText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        // Version
        Label versionLab = new Label(parent, SWT.NONE);
        versionLab.setText(Messages.getString("PropertiesWizardPage.Version")); //$NON-NLS-1$

        Composite versionContainer = new Composite(parent, SWT.NONE);
        versionContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        GridLayout versionLayout = new GridLayout(3, false);
        versionLayout.marginHeight = 0;
        versionLayout.marginWidth = 0;
        versionLayout.horizontalSpacing = 0;
        versionContainer.setLayout(versionLayout);

        versionText = new Text(versionContainer, SWT.BORDER);
        versionText.setEnabled(false);
        versionText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        versionMajorBtn = new Button(versionContainer, SWT.PUSH);
        versionMajorBtn.setText(Messages.getString("PropertiesWizardPage.Version.Major")); //$NON-NLS-1$
        versionMajorBtn.setEnabled(!readOnly);

        versionMinorBtn = new Button(versionContainer, SWT.PUSH);
        versionMinorBtn.setText(Messages.getString("PropertiesWizardPage.Version.Minor")); //$NON-NLS-1$
        versionMinorBtn.setEnabled(!readOnly);

        // Status
        Label statusLab = new Label(parent, SWT.NONE);
        statusLab.setText(Messages.getString("PropertiesWizardPage.Status")); //$NON-NLS-1$

        statusText = new CCombo(parent, SWT.BORDER);
        List<org.talend.core.model.properties.Status> statusList;
        try {
            if (property != null) {
                statusList = statusHelper.getStatusList(property);
                statusText.setItems(toArray(statusList));
                statusText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
                statusText.setEditable(!readOnly);
                statusText.setEnabled(!readOnly);
            }
        } catch (PersistenceException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            ExceptionHandler.process(e);
        }

        // Path:
        Label pathLab = new Label(parent, SWT.NONE);
        pathLab.setText(Messages.getString("PropertiesWizardPage.Path")); //$NON-NLS-1$

        Composite pathContainer = new Composite(parent, SWT.NONE);
        pathContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        GridLayout pathLayout = new GridLayout(2, false);
        pathLayout.marginHeight = 0;
        pathLayout.marginWidth = 0;
        pathLayout.horizontalSpacing = 0;
        pathContainer.setLayout(pathLayout);

        pathText = new Text(pathContainer, SWT.BORDER);
        pathText.setEnabled(false);
        pathText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        if (editPath) {
            Button button = new Button(pathContainer, SWT.PUSH);
            button.setText(Messages.getString("PropertiesWizardPage.Select")); //$NON-NLS-1$

            button.addSelectionListener(new SelectionAdapter() {

                @Override
                public void widgetSelected(SelectionEvent e) {
                    openFolderSelectionDialog(NEED_CANCEL_BUTTON);
                }
            });

            if (destinationPath == null) {
                openFolderSelectionDialog(!NEED_CANCEL_BUTTON);
            }

        }

    }

    /**
     * yzhang PropertiesWizardPage class global comment. Detailled comment <br/>
     * 
     * $Id: talend.epf 1 2006-09-29 17:06:40Z nrousseau $
     * 
     */
    private class Folder {

        public static final String ROOT_FOLDER = FoldersContentProvider.DEFAULT;

        private String name;

        private List<Folder> children;

        private Folder parent;

        public Folder(String name) {
            this.name = name;
            children = new ArrayList<Folder>();
        }

        /**
         * Getter for parent.
         * 
         * @return the parent
         */
        public Folder getParent() {
            return this.parent;
        }

        /**
         * Sets the parent.
         * 
         * @param parent the parent to set
         */
        public void setParent(Folder parent) {
            this.parent = parent;
        }

        /**
         * Getter for name.
         * 
         * @return the name
         */
        public String getName() {
            return this.name;
        }

        /**
         * Getter for path.
         * 
         * @return the path
         */
        public String getPath() {
            if (parent == null) {
                return null;
            }
            return parent.getName() + "/" + name; //$NON-NLS-1$
        }

        /**
         * Sets the name.
         * 
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * yzhang Comment method "addChildFolder".
         * 
         * @param folder
         */
        public void addChildFolder(Folder folder) {
            folder.setParent(this);
            this.children.add(folder);
        }

        /**
         * yzhang Comment method "getChildren".
         * 
         * @return
         */
        public List<Folder> getChildren() {
            return this.children;
        }

    }

    /**
     * yzhang PropertiesWizardPage class global comment. Detailled comment <br/>
     * 
     * $Id: talend.epf 1 2006-09-29 17:06:40Z nrousseau $
     * 
     */
    private class FoldersLabelProvider extends LabelProvider {

        private RepositoryFolderSelectionDialog dialog;

        /**
         * yzhang PropertiesWizardPage.FoldersLabelProvider constructor comment.
         */
        public FoldersLabelProvider(RepositoryFolderSelectionDialog dialog) {
            this.dialog = dialog;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
         */
        public Image getImage(Object element) {
            ECoreImage image = (dialog.getExpandedState(element) ? ECoreImage.FOLDER_OPEN_ICON : ECoreImage.FOLDER_CLOSE_ICON);
            return ImageProvider.getImage(image);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
         */
        public String getText(Object element) {
            return ((Folder) element).getName();
        }

    }

    private Folder formdFolderTree(List<String> paths) {

        Folder root = new Folder(Folder.ROOT_FOLDER);
        for (String path : paths) {
            String[] splitedPaths = path.split("/"); //$NON-NLS-1$
            Folder lastFolder = null;
            for (int i = 0; i < splitedPaths.length; i++) {
                String folderLabel = splitedPaths[i];
                Folder existFolder = findFolder(root, folderLabel);
                if (existFolder == null) {
                    if (i == 0) {
                        lastFolder = new Folder(folderLabel);
                        root.addChildFolder(lastFolder);
                    } else {
                        Folder newFolder = new Folder(folderLabel);
                        lastFolder.addChildFolder(newFolder);
                        lastFolder = newFolder;
                    }
                } else {
                    lastFolder = existFolder;
                }

            }
        }

        return root;
    }

    /**
     * yzhang Comment method "findFolder".
     * 
     * @param folder
     * @param name
     * @return
     */
    private Folder findFolder(Folder folder, String name) {

        Folder toRreturn = null;

        if (folder.getName().equals(name)) {
            return folder;
        }
        for (Folder f : folder.getChildren()) {
            toRreturn = findFolder(f, name);
        }
        return toRreturn;
    }

    /**
     * Provides all user folders for a given type.<br/>
     * 
     * $Id: talend.epf 1 2006-09-29 17:06:40 +0000 (ven., 29 sept. 2006) nrousseau $
     * 
     */
    private class FoldersContentProvider implements ITreeContentProvider {

        private static final String DEFAULT = "(default)"; //$NON-NLS-1$

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
         */
        public Object[] getElements(Object inputElement) {
            ERepositoryObjectType type = (ERepositoryObjectType) inputElement;
            IProxyRepositoryFactory factory = ((IRepositoryService) GlobalServiceRegister.getDefault().getService(
                    IRepositoryService.class)).getProxyRepositoryFactory();
            try {
                List<String> folders = factory.getFolders(type);
                Folder root = formdFolderTree(folders);

                return new Folder[] { root };
            } catch (PersistenceException e) {
                ExceptionHandler.process(e);
                return new String[0];
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         */
        public void dispose() {
            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
         * java.lang.Object, java.lang.Object)
         */
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
         */
        public Object[] getChildren(Object parentElement) {
            return ((Folder) parentElement).getChildren().toArray();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
         */
        public Object getParent(Object element) {
            // TODO Auto-generated method stub
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
         */
        public boolean hasChildren(Object element) {
            return ((Folder) element).getChildren().size() > 0;
        }

    }

    private void openFolderSelectionDialog(boolean needCancelButton) {

        // ListDialog dlg = new ListDialog(getShell());
        // dlg.setInput(getRepositoryObjectType());
        // dlg.setContentProvider(new FoldersContentProvider());
        // dlg.setLabelProvider(new LabelProvider());
        //        dlg.setTitle(Messages.getString("PropertiesWizardPage.SelectfolderTitle")); //$NON-NLS-1$
        //        dlg.setMessage(Messages.getString("PropertiesWizardPage.SelectfolderMessage")); //$NON-NLS-1$
        // if (!needCancelButton) {
        // dlg.setAddCancelButton(false);
        // }
        //        String defaultValue = (pathText.getText().equals("") ? FoldersContentProvider.DEFAULT : pathText.getText()); //$NON-NLS-1$
        // dlg.setInitialSelections(new String[] { defaultValue });
        //
        // if (dlg.open() == Window.OK) {
        // String string = (String) dlg.getResult()[0];
        // if (string.equals(FoldersContentProvider.DEFAULT)) {
        //                pathText.setText(""); //$NON-NLS-1$
        // } else {
        // pathText.setText(string);
        // this.path = string;
        // }
        // }

        RepositoryFolderSelectionDialog dialog = new RepositoryFolderSelectionDialog(getShell());
        dialog.setInput(getRepositoryObjectType());
        dialog.setContentProvider(new FoldersContentProvider());
        dialog.setLabelProvider(new FoldersLabelProvider(dialog));
        dialog.setTreeListener(new ITreeViewerListener() {

            public void treeCollapsed(TreeExpansionEvent event) {
                setItemImage(event, true);
            }

            public void treeExpanded(TreeExpansionEvent event) {
                setItemImage(event, false);
            }

            /**
             * yzhang Comment method "setItemImage".
             * 
             * @param event
             */
            private void setItemImage(TreeExpansionEvent event, boolean isCollpased) {
                Tree tree = ((TreeViewer) event.getSource()).getTree();
                Object element = event.getElement();
                TreeItem item = getTreeObject(tree, element);
                if (isCollpased) {
                    item.setImage(ImageProvider.getImage(ECoreImage.FOLDER_CLOSE_ICON));
                } else {
                    item.setImage(ImageProvider.getImage(ECoreImage.FOLDER_OPEN_ICON));
                }
            }
        });
        dialog.setTitle(Messages.getString("PropertiesWizardPage.SelectfolderTitle")); //$NON-NLS-1$
        dialog.setMessage(Messages.getString("PropertiesWizardPage.SelectfolderMessage")); //$NON-NLS-1$
        if (dialog.open() == Window.OK) {
            if (dialog.getResult().length > 0) {
                Folder folder = (Folder) dialog.getResult()[0];
                String pathString = folder.getPath();
                if (pathString == null) {
                    pathText.setText(""); //$NON-NLS-1$
                } else {
                    pathText.setText(pathString);
                    this.path = pathString;
                }
            }
        }

    }

    /**
     * yzhang Comment method "getTreeObject".
     * 
     * @param item
     * @param element
     * @return
     */
    private TreeItem getTreeObject(TreeItem item, Object element) {
        if (element.equals(item.getData())) {
            return item;
        }
        for (TreeItem treeItem : item.getItems()) {
            TreeItem toReturn = getTreeObject(treeItem, element);
            if (toReturn != null) {
                return toReturn;
            }
        }
        return null;
    }

    /**
     * yzhang Comment method "getTreeObject".
     * 
     * @param tree
     * @param objectToFind
     * @return
     */
    private TreeItem getTreeObject(Tree tree, Object objectToFind) {
        for (TreeItem item : tree.getItems()) {
            TreeItem toReturn = getTreeObject(item, objectToFind);
            if (toReturn != null) {
                return toReturn;
            }
        }
        return null;
    }

    public String[] toArray(List<org.talend.core.model.properties.Status> status) {
        String[] res = new String[status.size()];
        int i = 0;
        for (org.talend.core.model.properties.Status s : status) {
            res[i++] = s.getLabel();
        }
        return res;
    }

    protected void updateContent() {
        if (property != null) {
            nameText.setText(StringUtils.trimToEmpty(property.getLabel()));
            purposeText.setText(StringUtils.trimToEmpty(property.getPurpose()));
            descriptionText.setText(StringUtils.trimToEmpty(property.getDescription()));
            authorText.setText(StringUtils.trimToEmpty(property.getAuthor().getLogin()));
            lockerText.setText(""); //$NON-NLS-1$
            try {
                lockerText.setText(property.getItem().getState().getLocker().getLogin());
            } catch (Exception e) {
                // ignore null pointer exceptions
            }
            versionText.setText(property.getVersion());
            statusText.setText(statusHelper.getStatusLabel(property.getStatusCode()));
            if (destinationPath != null) {
                pathText.setText(destinationPath.toString());
            }
        }

        evaluateFields();
    }

    protected void addListeners() {
        nameText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                // if (!update) {
                if (nameText.getText().length() == 0) {
                    nameModifiedByUser = false;
                } else {
                    nameModifiedByUser = true;
                }
                // }
                evaluateTextField();
            }
        });

        purposeText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                if (purposeText.getText().length() == 0) {
                    purposeStatus = createStatus(IStatus.WARNING, Messages.getString("PropertiesWizardPage.EmptyPurposeWarning")); //$NON-NLS-1$
                } else {
                    purposeStatus = createOkStatus();
                }
                property.setPurpose(StringUtils.trimToNull(purposeText.getText()));
                updatePageStatus();
            }
        });

        descriptionText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                if (descriptionText.getText().length() == 0) {
                    commentStatus = createStatus(IStatus.WARNING, Messages.getString("PropertiesWizardPage.EmptyDescWarning")); //$NON-NLS-1$
                } else {
                    commentStatus = createOkStatus();
                }
                property.setDescription(StringUtils.trimToNull(descriptionText.getText()));
                updatePageStatus();
            }
        });

        versionMajorBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                String version = property.getVersion();
                version = VersionUtils.upMajor(version);
                versionText.setText(version);
                property.setVersion(version);
                property.setCreationDate(new Date());
                updatePageStatus();
            }
        });

        versionMinorBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                String version = property.getVersion();
                version = VersionUtils.upMinor(version);
                versionText.setText(version);
                property.setVersion(version);
                property.setCreationDate(new Date());
                updatePageStatus();
            }
        });

        statusText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                property.setStatusCode(statusHelper.getStatusCode(statusText.getText()));
                updatePageStatus();
            }

        });
    }

    protected void evaluateFields() {
        evaluateTextField();

    }

    protected void evaluateTextField() {
        if (nameText.getText().length() == 0) {
            nameStatus = createStatus(IStatus.ERROR, Messages.getString("PropertiesWizardPage.NameEmptyError")); //$NON-NLS-1$
        } else if (!Pattern.matches(RepositoryConstants.getPattern(getRepositoryObjectType()), nameText.getText())
                || nameText.getText().trim().contains(" ")) { //$NON-NLS-1$
            nameStatus = createStatus(IStatus.ERROR, Messages.getString("PropertiesWizardPage.NameFormatError")); //$NON-NLS-1$
        } else if (isKeywords(nameText.getText()) || "java".equalsIgnoreCase(nameText.getText())) {//$NON-NLS-1$
            nameStatus = createStatus(IStatus.ERROR, Messages.getString("PropertiesWizardPage.KeywordsError")); //$NON-NLS-1$
        } else if (nameModifiedByUser) {
            if (retrieveNameFinished) {
                if (!isValid(nameText.getText())) {
                    nameStatus = createStatus(IStatus.ERROR, Messages.getString("PropertiesWizardPage.ItemExistsError")); //$NON-NLS-1$
                } else {
                    nameStatus = createOkStatus();
                }
            } else {
                nameStatus = createStatus(IStatus.WARNING, "Looking for current items name list"); //$NON-NLS-1$
            }
        } else {
            nameStatus = createOkStatus();
        }
        if (property != null && nameStatus.getSeverity() == IStatus.OK) {
            property.setLabel(StringUtils.trimToNull(nameText.getText()));
        }
        updatePageStatus();
    }

    protected IStatus[] getStatuses() {
        return new IStatus[] { nameStatus, purposeStatus, commentStatus };
    }

    protected static IStatus createOkStatus() {
        return new Status(IStatus.OK, CorePlugin.PLUGIN_ID, IStatus.OK, "", null); //$NON-NLS-1$
    }

    protected static IStatus createStatus(int severity, String message) {
        return new Status(severity, CorePlugin.PLUGIN_ID, IStatus.OK, message, null);
    }

    protected void updatePageStatus() {
        setMessage(findMostSevere());
        updatePageComplete();
    }

    protected void updatePageComplete() {
        setMessage(findMostSevere());
        setPageComplete(findMostSevere().getSeverity() != IStatus.ERROR);
    }

    protected IStatus findMostSevere() {
        IStatus[] statuses = getStatuses();
        IStatus severeStatus = statuses[0];
        for (IStatus status : statuses) {
            if (status.getSeverity() > severeStatus.getSeverity()) {
                severeStatus = status;
            }
        }
        return severeStatus;
    }

    protected void setMessage(IStatus status) {
        if (IStatus.ERROR == status.getSeverity()) {
            setErrorMessage(status.getMessage());
            // setMessage(""); //$NON-NLS-1$
        } else {
            if (StringUtils.isNotEmpty(status.getMessage())) {
                setMessage(status.getMessage(), status.getSeverity());
            } else {
                setMessage(getDescription());
            }
            setErrorMessage(null);
        }
    }

    @SuppressWarnings("unchecked")
    public boolean isValid(String itemName) {

        IRepositoryService service = (IRepositoryService) GlobalServiceRegister.getDefault().getService(IRepositoryService.class);
        IProxyRepositoryFactory repositoryFactory = service.getProxyRepositoryFactory();
        try {
            return repositoryFactory.isNameAvailable(property.getItem(), itemName, listExistingObjects);
        } catch (PersistenceException e) {
            ExceptionHandler.process(e);
            return false;
        }
    }

    public abstract ERepositoryObjectType getRepositoryObjectType();

    public IPath getPathForSaveAsGenericSchema() {
        if (this.path != null && path.length() > 0) {
            return new Path(path);
        }
        return null;

    }

    /**
     * 
     * DOC ggu Comment method "isKeywords".
     * 
     * @param itemName
     * @return
     */
    private boolean isKeywords(String itemName) {
        if (property != null) {
            Item item = property.getItem();
            // see bug 0004157: Using specific name for (main) tream
            if (item instanceof ProcessItem || item instanceof JobletProcessItem || item instanceof RoutineItem) {
                return KeywordsValidator.isKeyword(itemName);
            }
        }
        return false;
    }

    public Property getProperty() {
        return property;
    }

}
