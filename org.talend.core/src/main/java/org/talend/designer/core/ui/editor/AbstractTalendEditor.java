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
package org.talend.designer.core.ui.editor;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.AbstractEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.gef.ui.rulers.RulerComposite;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IKeyBindingService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.MessageBoxExceptionHandler;
import org.talend.commons.utils.workbench.preferences.GlobalConstant;
import org.talend.core.CorePlugin;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.model.components.IComponent;
import org.talend.core.model.components.IComponentsFactory;
import org.talend.core.model.general.ILibrariesService;
import org.talend.core.model.process.EConnectionType;
import org.talend.core.model.process.Element;
import org.talend.core.model.process.INode2;
import org.talend.core.model.process.INodeConnector;
import org.talend.core.model.process.IProcess;
import org.talend.core.model.process.IProcess2;
import org.talend.core.model.properties.Property;
import org.talend.designer.core.ui.AbstractMultiPageTalendEditor;
import org.talend.designer.core.ui.action.TalendConnectionCreationTool;
import org.talend.designer.core.ui.editor.connections.IConnectionPart;
import org.talend.designer.core.ui.editor.nodes.INodeEditPart;
import org.talend.designer.core.ui.editor.process.IProcessPart;
import org.talend.designer.core.ui.views.properties.IComponentSettingsView;
import org.talend.designer.runprocess.ProcessorUtilities;
import org.talend.repository.editor.RepositoryEditorInput;
import org.talend.repository.job.deletion.IJobResourceProtection;
import org.talend.repository.job.deletion.JobResource;
import org.talend.repository.job.deletion.JobResourceManager;
import org.talend.repository.model.ComponentsFactoryProvider;
import org.talend.repository.model.IRepositoryService;
import org.talend.repository.model.RepositoryConstants;

/**
 * DOC qzhang class global comment. Detailled comment
 */
public abstract class AbstractTalendEditor extends GraphicalEditorWithFlyoutPalette implements
        ITabbedPropertySheetPageContributor, IJobResourceProtection {

    protected static PaletteRoot paletteRoot;

    protected FigureCanvas getEditor() {
        return (FigureCanvas) getGraphicalViewer().getControl();
    }

    public void savePaletteState() {

    }

    /**
     * yzhang Comment method "updatePaletteContent".
     */
    public void updatePaletteContent() {

        components = ComponentsFactoryProvider.getInstance();

        List oldRoots = new ArrayList(paletteRoot.getChildren());
        for (Object obj : oldRoots) {
            if (obj instanceof PaletteGroup) {
                continue;
            }
            getPaletteRoot().remove((PaletteEntry) obj);
        }
        CorePlugin.getDefault().getDesignerCoreService().createPalette(components, paletteRoot);

    }

    protected AbstractMultiPageTalendEditor parent;

    protected boolean savePreviouslyNeeded;

    protected IProcess2 process;

    private RulerComposite rulerComp;

    protected IComponentsFactory components;

    protected Property property;

    protected boolean readOnly;

    public static final int GRID_SIZE = 32;

    private boolean dirtyState = false;

    protected JobResource currentJobResource;

    protected final String projectName;

    protected final Map<String, JobResource> protectedJobs;

    /** The verify key listener for activation code triggering. */
    public ActivationCodeTrigger fActivationCodeTrigger = new ActivationCodeTrigger();

    public AbstractTalendEditor() {
        this(false);

    }

    public AbstractTalendEditor(boolean readOnly) {

        ProcessorUtilities.addOpenEditor(this);

        // an EditDomain is a "session" of editing which contains things
        // like the CommandStack
        if (getTalendEditDomain() != null) {
            setEditDomain(getTalendEditDomain());
        } else {
            setEditDomain(new TalendEditDomain(this));
        }
        this.readOnly = readOnly;

        projectName = ((RepositoryContext) CorePlugin.getContext().getProperty(Context.REPOSITORY_CONTEXT_KEY)).getProject()
                .getLabel();
        currentJobResource = new JobResource();
        protectedJobs = new HashMap<String, JobResource>();
        initializeKeyBindingScopes();
    }

    @Override
    public void doSave(IProgressMonitor monitor) {
        monitor.beginTask("begin save job...", 100); //$NON-NLS-1$
        monitor.worked(10);

        try {
            if (getEditorInput() instanceof RepositoryEditorInput) {
                boolean saved = ((RepositoryEditorInput) getEditorInput()).saveProcess(new SubProgressMonitor(monitor, 80), null);
                if (!saved) {
                    monitor.setCanceled(true);
                    throw new InterruptedException();
                }
            }
            getCommandStack().markSaveLocation();
            setDirty(false);
            ((ILibrariesService) GlobalServiceRegister.getDefault().getService(ILibrariesService.class)).resetModulesNeeded();
            monitor.worked(10);

            savePreviewPictures();

        } catch (Exception e) {
            e.printStackTrace();
            monitor.setCanceled(true);
        } finally {
            monitor.done();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.ui.parts.GraphicalEditor#setFocus()
     */
    @Override
    public void setFocus() {
        IComponentSettingsView viewer = (IComponentSettingsView) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage().findView(IComponentSettingsView.ID);
        if (viewer != null) {
            IStructuredSelection selection = (IStructuredSelection) getViewer().getSelection();

            if (selection.size() == 1
                    && (selection.getFirstElement() instanceof INodeEditPart || selection.getFirstElement() instanceof IConnectionPart)) {

                viewer.setElement((Element) ((AbstractEditPart) selection.getFirstElement()).getModel());

            } else {
                viewer.cleanDisplay();
            }
        }

        super.setFocus();

        if (!readOnly) {
            // When gain focus, check read-only to disable read-only mode if process has been restore while opened :
            // 1. Enabled/disabled components :
            process.checkReadOnly();
            // 2. Set backgroung color :
            List children = getViewer().getRootEditPart().getChildren();
            if (!children.isEmpty()) {
                IProcessPart rep = (IProcessPart) children.get(0);
                rep.ajustReadOnly();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.ui.parts.GraphicalEditor#getGraphicalViewer()
     */
    @Override
    public GraphicalViewer getGraphicalViewer() {
        return super.getGraphicalViewer();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.ui.parts.GraphicalEditor#getSelectionActions()
     */
    @Override
    public List getSelectionActions() {
        return super.getSelectionActions();
    }

    /**
     * DOC qzhang Comment method "getTalendEditDomain".
     * 
     * @param abstractTalendEditor
     * @return
     */
    protected DefaultEditDomain getTalendEditDomain() {
        return null;
    }

    protected String[] fKeyBindingScopes;

    /**
     * Sets the key binding scopes for this editor.
     * 
     * @param scopes a non-empty array of key binding scope identifiers
     * @since 2.1
     */
    protected void setKeyBindingScopes(String[] scopes) {
        Assert.isTrue(scopes != null && scopes.length > 0);
        fKeyBindingScopes = scopes;
    }

    protected void setInput(final IEditorInput input) {
        super.setInput(input);

        try {
            if (input instanceof RepositoryEditorInput) {
                process = ((RepositoryEditorInput) input).getLoadedProcess();
                property = ((RepositoryEditorInput) input).getItem().getProperty();
            }
        } catch (Exception e) { // if there's an error, create a new diagram
            e.printStackTrace();
            process = CorePlugin.getDefault().getDesignerCoreService().newProcess(null);
        }
        currentJobResource.setJobName(process.getLabel());
        currentJobResource.setProjectName(projectName);

        JobResourceManager.getInstance().addProtection(this);

    }

    /*
     * @see ITextEditor#setAction(String, IAction)
     */
    public void setAction(String actionID, IAction action) {
        Assert.isNotNull(actionID);
        if (action == null) {
            action = getActionRegistry().getAction(actionID);
            if (action != null)
                fActivationCodeTrigger.unregisterActionFromKeyActivation(action);
        } else {
            // getActionRegistry().registerAction(action);
            fActivationCodeTrigger.registerActionForKeyActivation(action);
        }
    }

    /**
     * Initializes the key binding scopes of this editor.
     */
    protected void initializeKeyBindingScopes() {
        setKeyBindingScopes(new String[] { "org.eclipse.ui.textEditorScope" }); //$NON-NLS-1$
    }

    /**
     * Internal key verify listener for triggering action activation codes.
     */
    protected class ActivationCodeTrigger implements VerifyKeyListener {

        /** Indicates whether this trigger has been installed. */
        private boolean fIsInstalled = false;

        /**
         * The key binding service to use.
         * 
         * @since 2.0
         */
        private IKeyBindingService fKeyBindingService;

        /*
         * @see VerifyKeyListener#verifyKey(org.eclipse.swt.events.VerifyEvent)
         */
        public void verifyKey(VerifyEvent event) {

            // ActionActivationCode code = null;
            // int size = fActivationCodes.size();
            // for (int i = 0; i < size; i++) {
            // code = (ActionActivationCode) fActivationCodes.get(i);
            // if (code.matches(event)) {
            // IAction action = getAction(code.fActionId);
            // if (action != null) {
            //
            // if (action instanceof IUpdate)
            // ((IUpdate) action).update();
            //
            // if (!action.isEnabled() && action instanceof IReadOnlyDependent) {
            // IReadOnlyDependent dependent = (IReadOnlyDependent) action;
            // boolean writable = dependent.isEnabled(true);
            // if (writable) {
            // event.doit = false;
            // return;
            // }
            // } else if (action.isEnabled()) {
            // event.doit = false;
            // action.run();
            // return;
            // }
            // }
            // }
            // }
        }

        /**
         * Installs this trigger on the editor's text widget.
         * 
         * @since 2.0
         */
        public void install() {
            if (!fIsInstalled) {

                // if (fSourceViewer instanceof ITextViewerExtension) {
                // ITextViewerExtension e = (ITextViewerExtension) fSourceViewer;
                // e.prependVerifyKeyListener(this);
                // } else {
                // StyledText text = fSourceViewer.getTextWidget();
                // text.addVerifyKeyListener(this);
                // }

                fKeyBindingService = getEditorSite().getKeyBindingService();
                fIsInstalled = true;
            }
        }

        /**
         * Uninstalls this trigger from the editor's text widget.
         * 
         * @since 2.0
         */
        public void uninstall() {
            if (fIsInstalled) {

                // if (fSourceViewer instanceof ITextViewerExtension) {
                // ITextViewerExtension e = (ITextViewerExtension) fSourceViewer;
                // e.removeVerifyKeyListener(this);
                // } else if (fSourceViewer != null) {
                // StyledText text = fSourceViewer.getTextWidget();
                // if (text != null && !text.isDisposed())
                // text.removeVerifyKeyListener(fActivationCodeTrigger);
                // }

                fIsInstalled = false;
                fKeyBindingService = null;
            }
        }

        /**
         * Registers the given action for key activation.
         * 
         * @param action the action to be registered
         * @since 2.0
         */
        public void registerActionForKeyActivation(IAction action) {
            if (action.getActionDefinitionId() != null)
                fKeyBindingService.registerAction(action);
        }

        /**
         * The given action is no longer available for key activation
         * 
         * @param action the action to be unregistered
         * @since 2.0
         */
        public void unregisterActionFromKeyActivation(IAction action) {
            if (action.getActionDefinitionId() != null)
                fKeyBindingService.unregisterAction(action);
        }

        /**
         * Sets the key binding scopes for this editor.
         * 
         * @param keyBindingScopes the key binding scopes
         * @since 2.1
         */
        public void setScopes(String[] keyBindingScopes) {
            if (keyBindingScopes != null && keyBindingScopes.length > 0)
                fKeyBindingService.setScopes(keyBindingScopes);
        }
    }

    /**
     * Representation of action activation codes.
     */
    protected static class ActionActivationCode {

        /** The action id. */
        public String fActionId;

        /** The character. */
        public char fCharacter;

        /** The key code. */
        public int fKeyCode = -1;

        /** The state mask. */
        public int fStateMask = SWT.DEFAULT;

        /**
         * Creates a new action activation code for the given action id.
         * 
         * @param actionId the action id
         */
        public ActionActivationCode(String actionId) {
            fActionId = actionId;
        }

        /**
         * Returns <code>true</code> if this activation code matches the given verify event.
         * 
         * @param event the event to test for matching
         * @return whether this activation code matches <code>event</code>
         */
        public boolean matches(VerifyEvent event) {
            return (event.character == fCharacter && (fKeyCode == -1 || event.keyCode == fKeyCode) && (fStateMask == SWT.DEFAULT || event.stateMask == fStateMask));
        }
    }

    protected void configureGraphicalViewer() {
        super.configureGraphicalViewer();
        CorePlugin.getDefault().getDesignerCoreService().configureGEFEditor(this);
    }

    /**
     * Initializes the activation code trigger.
     * 
     * @since 2.1
     */
    public void initializeActivationCodeTrigger() {
        fActivationCodeTrigger.install();
        fActivationCodeTrigger.setScopes(fKeyBindingScopes);
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public IProcess2 getProcess() {
        return this.process;
    }

    public Property getProperty() {
        return this.property;
    }

    /**
     * Sets the property.
     * 
     * @param property the property to set
     */
    public void setProperty(Property property) {
        this.property = property;
    }

    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public List<String> getActions() {
        return getSelectionActions();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.ui.parts.GraphicalEditor#getActionRegistry()
     */
    public ActionRegistry getActionRegistry() {
        return super.getActionRegistry();
    }

    /**
     * Save the outline picture for this editor.
     * 
     * @param viewer
     */
    protected void saveOutlinePicture(ScrollingGraphicalViewer viewer) {
        GlobalConstant.generatingScreenShoot = true;
        LayerManager layerManager = (LayerManager) viewer.getEditPartRegistry().get(LayerManager.ID);
        // save image using swt
        // get root figure
        IFigure backgroundLayer = layerManager.getLayer(LayerConstants.GRID_LAYER);
        IFigure contentLayer = layerManager.getLayer(LayerConstants.PRINTABLE_LAYERS);

        // create image from root figure
        Image img = new Image(null, contentLayer.getSize().width, contentLayer.getSize().height);
        GC gc = new GC(img);
        Graphics graphics = new SWTGraphics(gc);
        Point point = contentLayer.getBounds().getTopLeft();
        graphics.translate(-point.x, -point.y);
        process.setPropertyValue(IProcess.SCREEN_OFFSET_X, String.valueOf(-point.x));
        process.setPropertyValue(IProcess.SCREEN_OFFSET_Y, String.valueOf(-point.y));
        backgroundLayer.paint(graphics);
        contentLayer.paint(graphics);
        graphics.dispose();
        gc.dispose();

        // save image to file
        ImageLoader il = new ImageLoader();
        il.data = new ImageData[] { img.getImageData() };

        IRepositoryService service = CorePlugin.getDefault().getRepositoryService();
        IPath filePath = service.getPathFileName(RepositoryConstants.IMG_DIRECTORY_OF_JOB_OUTLINE, "");
        String outlineFileName = process.getName();
        String outlineFileVersion = process.getVersion();
        filePath = filePath.append(outlineFileName + "_" + outlineFileVersion + ".jpg");

        il.save(filePath.toPortableString(), SWT.IMAGE_JPEG);

        service.getProxyRepositoryFactory().refreshJobPictureFolder();
        GlobalConstant.generatingScreenShoot = false;

    }

    @Override
    protected Control getGraphicalControl() {
        return rulerComp;
    }

    @Override
    protected void createGraphicalViewer(final Composite parent) {
        rulerComp = new RulerComposite(parent, SWT.NONE);
        super.createGraphicalViewer(rulerComp);
        rulerComp.setGraphicalViewer((ScrollingGraphicalViewer) getGraphicalViewer());
    }

    @Override
    public void commandStackChanged(final EventObject event) {
        if (isDirty()) {
            if (!this.savePreviouslyNeeded) {
                this.savePreviouslyNeeded = true;
                firePropertyChange(IEditorPart.PROP_DIRTY);
            }
        } else {
            savePreviouslyNeeded = false;
            firePropertyChange(IEditorPart.PROP_DIRTY);
        }
        super.commandStackChanged(event);
    }

    /**
     * This function is used only for the TabbedPropertySheetPage.
     * 
     * @return contributorId String
     */
    public String getContributorId() {
        return "org.talend.repository.views.repository"; //$NON-NLS-1$
    }

    /**
     * ftang Comment method "savePreviewPictures".
     */
    protected void savePreviewPictures() {
        Job job = new Job("Documentation node save in progress") {

            @Override
            protected IStatus run(IProgressMonitor monitor) {
                try {
                    saveOutlinePicture((ScrollingGraphicalViewer) getGraphicalViewer());
                } catch (Exception e) {
                    ExceptionHandler.process(e);
                    MessageBoxExceptionHandler.process(e);
                    return Status.CANCEL_STATUS;
                }
                return Status.OK_STATUS;
            }
        };

        job.setUser(true);
        job.setPriority(Job.INTERACTIVE);
        job.schedule();
    }

    protected RepositoryEditorInput getRepositoryEditorInput() {
        return (RepositoryEditorInput) getEditorInput();
    }

    @Override
    public void doSaveAs() {
        SaveAsDialog dialog = new SaveAsDialog(getSite().getWorkbenchWindow().getShell());
        dialog.setOriginalFile(((IFileEditorInput) getEditorInput()).getFile());
        dialog.open();
        IPath path = dialog.getResult();

        if (path == null) {
            return;
        }

        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IFile file = workspace.getRoot().getFile(path);

        WorkspaceModifyOperation op = new WorkspaceModifyOperation() {

            @Override
            public void execute(final IProgressMonitor monitor) throws CoreException {
                try {
                    process.saveXmlFile(file, getRepositoryEditorInput().isJobletItem());
                    file.refreshLocal(IResource.DEPTH_ONE, monitor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        try {
            new ProgressMonitorDialog(getSite().getWorkbenchWindow().getShell()).run(false, true, op);
            // setInput(new FileEditorInput((IFile) file));
            getCommandStack().markSaveLocation();
            setDirty(false);

            savePreviewPictures();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotoMarker(final IMarker marker) {
    }

    @Override
    public boolean isDirty() {
        // rely on the command stack to determine dirty flag
        return dirtyState || getCommandStack().isDirty();
    }

    public void setDirty(boolean dirty) {
        dirtyState = dirty;
        if (dirtyState) {
            firePropertyChange(IEditorPart.PROP_DIRTY);
        }
    }

    @Override
    public boolean isSaveAsAllowed() {
        // allow Save As
        return true;
    }

    public IComponent getComponent(String name) {
        if (components == null) {
            components = ComponentsFactoryProvider.getInstance();
        }
        return components.get(name);
    }

    /**
     * Outline view page. <br/>
     * 
     * $Id: TalendEditor.java 7516 2007-12-11 05:23:18Z ftang $
     * 
     */

    /**
     * This function will allow the use of the Delete in the Multipage Editor.
     */
    @Override
    public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
        super.selectionChanged(part, selection);
        if (this.equals(part)) { // Propagated from MyMultiPageEditor.
            updateActions(getSelectionActions());
        }
    }

    /**
     * Get the viewer in the editor.
     * 
     * @return
     */
    public GraphicalViewer getViewer() {
        return getGraphicalViewer();
    }

    public AbstractMultiPageTalendEditor getParent() {
        return this.parent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.job.deletion.IResourceProtection#getProtectedIds()
     */
    public String[] calculateProtectedIds() {
        Set<String> subJobs = process.getSubJobs(null);

        for (String protectedJobName : subJobs) {
            String jobName = protectedJobName;
            protectedJobName = "subjob_of_" + process.getLabel() + "_" + projectName + "_" + protectedJobName; //$NON-NLS-1$
            protectedJobs.put(protectedJobName, new JobResource(projectName, jobName));
        }
        String currentJobId = "talend_editor_" + projectName + "_" + process.getLabel(); //$NON-NLS-1$
        protectedJobs.put(currentJobId, currentJobResource);

        Set<String> set = protectedJobs.keySet();

        return set.toArray(new String[set.size()]); //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.job.deletion.IResourceProtection#getProjectedIds()
     */
    public String[] getProtectedIds() {
        Set<String> set = protectedJobs.keySet();
        return set.toArray(new String[set.size()]);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.job.deletion.IResourceProtection#getJobResource()
     */
    public JobResource getJobResource(String id) {
        return protectedJobs.get(id);
    }

    /**
     * Remove all the protected resources.
     * 
     * yzhang Comment method "resetJobResources".
     */
    public void resetJobResources() {
        String[] ids = getProtectedIds();
        for (String id : ids) {
            protectedJobs.remove(id);
        }
    }

    /**
     * Getter for currentJobResource.
     * 
     * @return the currentJobResource
     */
    public JobResource getCurrentJobResource() {
        return this.currentJobResource;
    }

    /**
     * bqian class global comment. Detailled comment <br/>
     * 
     * $Id: TalendEditor.java 7516 2007-12-11 05:23:18Z ftang $
     * 
     */
    protected class TalendEditDomain extends DefaultEditDomain {

        /**
         * DOC bqian TalendEditor.TalendEditDomain class global comment. Detailled comment <br/>
         * 
         * $Id: TalendEditor.java 7516 2007-12-11 05:23:18Z ftang $
         * 
         */
        class DragProcessor {

            int x;

            int y;

        }

        private DragProcessor processor = null;

        private boolean createConnection = false;

        private Point startPoint = null;

        /**
         * bqian TalendEditDomain constructor comment.
         * 
         * @param editorPart
         */
        public TalendEditDomain(IEditorPart editorPart) {
            super(editorPart);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.gef.EditDomain#mouseDown(org.eclipse.swt.events.MouseEvent, org.eclipse.gef.EditPartViewer)
         */
        @Override
        public void mouseDown(org.eclipse.swt.events.MouseEvent mouseEvent, EditPartViewer viewer) {
            // TalendEditorContextMenuProvider.setEnableContextMenu(true);
            createConnection = false;
            if (mouseEvent.button == 2) {
                getEditor().setCursor(Cursors.HAND);
                processor = new DragProcessor();
                processor.x = mouseEvent.x;
                processor.y = mouseEvent.y;
            } else if (mouseEvent.button == 3) {
                startPoint = new Point(mouseEvent.x, mouseEvent.y);
                createConnection = true;
                super.mouseDown(mouseEvent, viewer);
            } else {
                super.mouseDown(mouseEvent, viewer);
            }
        }

        public void updateViewport(int offX, int offY) {
            RootEditPart rep = getGraphicalViewer().getRootEditPart();

            if (rep instanceof ScalableFreeformRootEditPart) {
                ScalableFreeformRootEditPart root = (ScalableFreeformRootEditPart) rep;
                Viewport viewport = (Viewport) root.getFigure();
                Point viewOriginalPosition = viewport.getViewLocation();
                viewOriginalPosition.x -= offX;
                viewOriginalPosition.y -= offY;

                viewport.setViewLocation(viewOriginalPosition.x, viewOriginalPosition.y);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.gef.EditDomain#mouseDrag(org.eclipse.swt.events.MouseEvent, org.eclipse.gef.EditPartViewer)
         */
        @Override
        public void mouseDrag(org.eclipse.swt.events.MouseEvent mouseEvent, EditPartViewer viewer) {
            super.mouseDrag(mouseEvent, viewer);
            if (processor != null) {
                int offX = mouseEvent.x - processor.x;
                int offY = mouseEvent.y - processor.y;

                updateViewport(offX, offY);
                processor.x = mouseEvent.x;
                processor.y = mouseEvent.y;
            } else if (createConnection) {
                handleCreateDefaultConnection(new Point(mouseEvent.x, mouseEvent.y));
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.gef.EditDomain#mouseUp(org.eclipse.swt.events.MouseEvent, org.eclipse.gef.EditPartViewer)
         */
        @Override
        public void mouseUp(org.eclipse.swt.events.MouseEvent mouseEvent, EditPartViewer viewer) {
            createConnection = false;
            if (mouseEvent.button != 2) {
                super.mouseUp(mouseEvent, viewer);
            } else {
                getEditor().setCursor(null);
                processor = null;
            }
        }

        public void handleCreateDefaultConnection(Point currentLocation) {
            if (getActiveTool() instanceof TalendConnectionCreationTool) {
                // if a connection is already in creation, no need to create it again
                return;
            }

            if (getViewer().getSelectedEditParts().size() != 1) {
                return;
            }

            EditPart part = (EditPart) getViewer().getSelectedEditParts().get(0);
            if (!(part instanceof INodeEditPart)) {
                return;
            }

            IFigure figure = ((AbstractGraphicalEditPart) part).getFigure();

            if (!figure.getBounds().contains(startPoint)) {
                // if the start location of the drag is not in the component, ignore it
                return;
            }

            if (startPoint.getDistance2(currentLocation) < 30) {
                // need to move a minimum the mouse for the drag, if not enough, display the context menu
                return;
            }

            INode2 node = (INode2) part.getModel();

            final INodeConnector mainConnector;
            if (node.isELTComponent()) {
                mainConnector = node.getConnectorFromType(EConnectionType.TABLE);
            } else {
                mainConnector = node.getConnectorFromType(EConnectionType.FLOW_MAIN);
            }

            if (mainConnector == null) {
                return;
            }
            if (mainConnector.getMaxLinkOutput() != -1) {
                if (mainConnector.getCurLinkNbOutput() >= mainConnector.getMaxLinkOutput()) {
                    return;
                }
            }
            if (mainConnector.getMaxLinkOutput() == 0) {
                return;
            }

            figure.translateToAbsolute(startPoint);

            Canvas canvas = (Canvas) getViewer().getControl();
            Event event = new Event();
            event.button = 3;
            event.count = 0;
            event.detail = 0;
            event.end = 0;
            event.height = 0;
            event.keyCode = 0;
            event.start = 0;
            event.stateMask = 0;
            event.time = 9516624; // any old time... doesn't matter
            event.type = 3;
            event.widget = canvas;
            event.width = 0;
            event.x = startPoint.x + 3;
            event.y = startPoint.y + 3;
            /**
             * Set the connection tool to be the current tool
             */

            final List<Object> listArgs = new ArrayList<Object>();

            listArgs.add(null);
            listArgs.add(null);
            listArgs.add(null);

            TalendConnectionCreationTool myConnectTool = new TalendConnectionCreationTool(new CreationFactory() {

                public Object getNewObject() {
                    return listArgs;
                }

                public Object getObjectType() {
                    return mainConnector.getName();
                }
            }, false);
            myConnectTool.performConnectionStartWith(part);
            getViewer().getEditDomain().setActiveTool(myConnectTool);
            canvas.notifyListeners(3, event);
            // TalendEditorContextMenuProvider.setEnableContextMenu(false);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.ui.parts.GraphicalEditor#getCommandStack()
     */
    @Override
    public CommandStack getCommandStack() {
        // TODO Auto-generated method stub
        return super.getCommandStack();
    }

    /**
     * Comment method "getAction".
     * 
     * @param actionID
     * @return
     */
    public IAction getAction(String actionID) {
        return getActionRegistry().getAction(actionID);
    }

    /**
     * Sets the parent.
     * 
     * @param parent the parent to set
     */
    public void setParent(AbstractMultiPageTalendEditor parent) {
        this.parent = parent;
    }

    /**
     * DOC qzhang Comment method "getCommonKeyHandler".
     * 
     * @return
     */
    public KeyHandler getCommonKeyHandler() {
        return new KeyHandler();
    }

    // ------------------------------------------------------------------------
    // Abstract methods from GraphicalEditor

    @Override
    protected void initializeGraphicalViewer() {
        super.initializeGraphicalViewer();
        // this uses the PartFactory set in configureGraphicalViewer
        // to create an EditPart for the diagram and sets it as the
        // content for the viewer
        super.initializeGraphicalViewer();
        getGraphicalViewer().setContents(this.process);
        getGraphicalViewer().getControl().addMouseListener(new MouseAdapter() {

            /*
             * (non-Javadoc)
             * 
             * @see org.eclipse.swt.events.MouseAdapter#mouseUp(org.eclipse.swt.events.MouseEvent)
             */
            @Override
            public void mouseUp(MouseEvent e) {
                updateActions(getSelectionActions());
            }
        });
        getGraphicalViewer().addDropTargetListener(
                CorePlugin.getDefault().getDesignerCoreService().createTemplateTransferDropTargetListener(getGraphicalViewer()));
        getGraphicalViewer().addDropTargetListener(
                CorePlugin.getDefault().getDesignerCoreService().createEditorDropTargetListener(this));
    }

    // ------------------------------------------------------------------------
    // Abstract methods from EditorPart

}
