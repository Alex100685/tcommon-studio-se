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
package org.talend.core.ui.metadata.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.talend.commons.ui.swt.advanced.dataeditor.ExtendedToolbarView;
import org.talend.commons.ui.swt.extended.table.ExtendedTableModel;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.utils.data.bean.IBeanPropertyAccessors;
import org.talend.core.language.ECodeLanguage;
import org.talend.core.language.LanguageManager;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.editor.MetadataTableEditor;
import org.talend.core.model.metadata.types.TypesManager;
import org.talend.core.ui.proposal.JavaSimpleDateFormatProposalProvider;

/**
 * MetadataTableEditorView2 must be used.
 * 
 * $Id$
 * 
 */
public class MetadataTableEditorView extends AbstractMetadataTableEditorView<IMetadataColumn> {

    /**
     * DOC amaumont MetadataTableEditorView constructor comment.
     * 
     * @param parent
     * @param style
     * @param metadataTableEditor
     */
    public MetadataTableEditorView(Composite parent, int style, MetadataTableEditor metadataTableEditor) {
        super(parent, style, metadataTableEditor);
    }

    /**
     * DOC amaumont MetadataTableEditorView constructor comment.
     * 
     * @param parentComposite
     * @param mainCompositeStyle
     * @param extendedTableModel
     * @param readOnly
     * @param toolbarVisible
     */
    public MetadataTableEditorView(Composite parentComposite, int mainCompositeStyle,
            ExtendedTableModel<IMetadataColumn> extendedTableModel, boolean readOnly, boolean toolbarVisible) {
        super(parentComposite, mainCompositeStyle, extendedTableModel, readOnly, toolbarVisible, true);
    }

    /**
     * DOC amaumont MetadataTableEditorView constructor comment.
     * 
     * @param parentComposite
     * @param mainCompositeStyle
     * @param extendedTableModel
     * @param readOnly
     * @param toolbarVisible
     * @param labelVisible
     * @param initGraphicsComponents
     */
    public MetadataTableEditorView(Composite parentComposite, int mainCompositeStyle,
            ExtendedTableModel<IMetadataColumn> extendedTableModel, boolean readOnly, boolean toolbarVisible,
            boolean labelVisible, boolean initGraphicsComponents) {
        super(parentComposite, mainCompositeStyle, extendedTableModel, readOnly, toolbarVisible, labelVisible,
                initGraphicsComponents);
    }

    /**
     * DOC amaumont MetadataTableEditorView constructor comment.
     */
    public MetadataTableEditorView() {
        super();
    }

    /**
     * DOC amaumont MetadataTableEditorView constructor comment.
     * 
     * @param parentComposite
     * @param mainCompositeStyle
     * @param initGraphicsComponents
     */
    public MetadataTableEditorView(Composite parentComposite, int mainCompositeStyle) {
        super(parentComposite, mainCompositeStyle, false);
    }

    /**
     * DOC amaumont MetadataTableEditorView constructor comment.
     * 
     * @param parentComposite
     * @param mainCompositeStyle
     * @param extendedTableModel
     * @param readOnly
     * @param toolbarVisible
     * @param labelVisible
     */
    public MetadataTableEditorView(Composite parentComposite, int mainCompositeStyle,
            ExtendedTableModel<IMetadataColumn> extendedTableModel, boolean readOnly, boolean toolbarVisible, boolean labelVisible) {
        super(parentComposite, mainCompositeStyle, extendedTableModel, readOnly, toolbarVisible, labelVisible);
    }

    /**
     * DOC amaumont MetadataTableEditorView constructor comment.
     * 
     * @param parentComposite
     * @param mainCompositeStyle
     * @param extendedTableModel
     */
    public MetadataTableEditorView(Composite parentComposite, int mainCompositeStyle,
            ExtendedTableModel<IMetadataColumn> extendedTableModel) {
        super(parentComposite, mainCompositeStyle, extendedTableModel);
    }

    public MetadataTableEditor getMetadataTableEditor() {
        return (MetadataTableEditor) getExtendedTableModel();
    }

    public void setMetadataTableEditor(MetadataTableEditor metadataTableEditor) {
        setExtendedTableModel(metadataTableEditor);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.commons.ui.swt.advanced.dataeditor.AbstractDataTableEditorView#setTableViewerCreatorOptions(org.talend.commons.ui.swt.tableviewer.TableViewerCreator)
     */
    @Override
    protected void setTableViewerCreatorOptions(TableViewerCreator<IMetadataColumn> newTableViewerCreator) {
        super.setTableViewerCreatorOptions(newTableViewerCreator);
        newTableViewerCreator.setFirstVisibleColumnIsSelection(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.metadata.editor.AbstractMetadataTableEditorView#getNullableAccessor()
     */
    @Override
    protected IBeanPropertyAccessors getNullableAccessor() {
        return new IBeanPropertyAccessors<IMetadataColumn, Boolean>() {

            public Boolean get(IMetadataColumn bean) {
                return bean.isNullable() ? Boolean.TRUE : Boolean.FALSE;
            }

            public void set(IMetadataColumn bean, Boolean value) {
                bean.setNullable(value);
            }

        };
    }

    @Override
    protected IBeanPropertyAccessors<IMetadataColumn, String> getCommentAccessor() {
        return new IBeanPropertyAccessors<IMetadataColumn, String>() {

            public String get(IMetadataColumn bean) {
                return bean.getComment();
            }

            public void set(IMetadataColumn bean, String value) {
                bean.setComment(value);
            }

        };
    }

    @Override
    protected IBeanPropertyAccessors<IMetadataColumn, String> getDefaultValueAccessor() {
        return new IBeanPropertyAccessors<IMetadataColumn, String>() {

            public String get(IMetadataColumn bean) {
                return bean.getDefault();
            }

            public void set(IMetadataColumn bean, String value) {
                bean.setDefault(value);
            }

        };
    }

    @Override
    protected IBeanPropertyAccessors<IMetadataColumn, Integer> getPrecisionAccessor() {
        return new IBeanPropertyAccessors<IMetadataColumn, Integer>() {

            public Integer get(IMetadataColumn bean) {
                // String dbmsId = getCurrentDbms();
                // if (dbmsId != null) {
                // Dbms dbms = MetadataTalendType.getDbms(dbmsId);
                // List<DbDefaultLengthAndPrecision> dlpList = dbms.getDefaultLengthPrecision();
                // for (DbDefaultLengthAndPrecision dlp : dlpList) {
                // if (dlp.getDbTypeName().equals(bean.getType()))
                // if (bean.getPrecision() == null)
                // bean.setPrecision(dlp.getDefaultPrecision());
                // }
                // }
                return bean.getPrecision();
            }

            public void set(IMetadataColumn bean, Integer value) {
                bean.setPrecision(value);
            }

        };
    }

    @Override
    protected IBeanPropertyAccessors<IMetadataColumn, Integer> getLengthAccessor() {
        return new IBeanPropertyAccessors<IMetadataColumn, Integer>() {

            public Integer get(IMetadataColumn bean) {
                // String dbmsId = getCurrentDbms();
                // if (dbmsId != null) {
                // Dbms dbms = MetadataTalendType.getDbms(dbmsId);
                // List<DbDefaultLengthAndPrecision> dlpList = dbms.getDefaultLengthPrecision();
                // for (DbDefaultLengthAndPrecision dlp : dlpList) {
                // if (dlp.getDbTypeName().equals(bean.getType()))
                // if (bean.getLength() == null)
                // bean.setLength(dlp.getDefaultLength());
                // }
                // }
                return bean.getLength();
            }

            public void set(IMetadataColumn bean, Integer value) {
                bean.setLength(value);
            }
        };
    }

    @Override
    protected IBeanPropertyAccessors<IMetadataColumn, String> getPatternAccessor() {
        return new IBeanPropertyAccessors<IMetadataColumn, String>() {

            public String get(IMetadataColumn bean) {
                return bean.getPattern();
            }

            public void set(IMetadataColumn bean, String value) {
                bean.setPattern(value);
            }

        };
    }

    @Override
    protected IBeanPropertyAccessors<IMetadataColumn, Boolean> getKeyAccesor() {
        return new IBeanPropertyAccessors<IMetadataColumn, Boolean>() {

            public Boolean get(IMetadataColumn bean) {
                return new Boolean(bean.isKey());
            }

            public void set(IMetadataColumn bean, Boolean value) {
                bean.setKey(value);
            }

        };
    }

    @Override
    protected IBeanPropertyAccessors<IMetadataColumn, String> getLabelAccessor() {
        return new IBeanPropertyAccessors<IMetadataColumn, String>() {

            public String get(IMetadataColumn bean) {
                return bean.getLabel();
            }

            public void set(IMetadataColumn bean, String value) {
                if (dbTypeColumnWritable && bean.getLabel().equals(bean.getOriginalDbColumnName())) {
                    bean.setOriginalDbColumnName(value);
                }
                bean.setLabel(value);
            }

        };
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.metadata.editor.AbstractMetadataTableEditorView#validateColumnName(java.lang.String, int)
     */
    @Override
    protected String validateColumnName(String newValue, int beanPosition) {
        return getMetadataTableEditor().validateColumnName(newValue, beanPosition);
    }

    @Override
    protected IBeanPropertyAccessors<IMetadataColumn, String> getTalendTypeAccessor() {
        return new IBeanPropertyAccessors<IMetadataColumn, String>() {

            public String get(IMetadataColumn bean) {

                return bean.getTalendType();
            }

            public void set(IMetadataColumn bean, String value) {
                String oldTalendType = bean.getTalendType();
                bean.setTalendType(value);
                String dbms = getCurrentDbms();
                if (showDbTypeColumn && dbTypeColumnWritable && (dbms != null)) {
                    String oldDbType = bean.getType();
                    String oldDefaultDbType = null;
                    if ((oldDbType != null) && !oldDbType.equals("")) {
                        oldDefaultDbType = TypesManager.getDBTypeFromTalendType(dbms, oldTalendType);
                    }
                    if ((oldDbType == null) || oldDbType.equals(oldDefaultDbType) || oldDbType.equals("")) {
                        bean.setType(TypesManager.getDBTypeFromTalendType(dbms, value));
                    }
                }
                if (currentBeanHasJavaDateType(bean)) {
                    bean.setPattern(new JavaSimpleDateFormatProposalProvider().getProposals(null, 0)[0].getContent());
                }
            }
        };
    }

    @Override
    protected IBeanPropertyAccessors getDbTypeAccessor() {
        return new IBeanPropertyAccessors<IMetadataColumn, String>() {

            public String get(IMetadataColumn bean) {

                return bean.getType();
            }

            public void set(IMetadataColumn bean, String value) {
                // String dbmsId = getCurrentDbms();
                // if (dbmsId != null) {
                // Dbms dbms = MetadataTalendType.getDbms(dbmsId);
                // List<DbDefaultLengthAndPrecision> dlpList = dbms.getDefaultLengthPrecision();
                // for (DbDefaultLengthAndPrecision dlp : dlpList) {
                // if (dlp.getDbTypeName().equals(value)) {
                // bean.setLength(dlp.getDefaultLength());
                // bean.setPrecision(dlp.getDefaultPrecision());
                // }
                // }
                // }
                bean.setType(value);
            }

        };
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.commons.ui.swt.advanced.dataeditor.AbstractDataTableEditorView#initToolBar()
     */
    @Override
    protected ExtendedToolbarView initToolBar() {
        return new MetadataToolbarEditorView(getMainComposite(), SWT.NONE, this.getExtendedTableViewer(), this.getCurrentDbms());
    }

    public MetadataToolbarEditorView getToolBar() {
        return (MetadataToolbarEditorView) getExtendedToolbar();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.metadata.editor.AbstractMetadataTableEditorView#configureDefaultColumn(org.talend.commons.ui.swt.tableviewer.TableViewerCreator)
     */
    @Override
    protected void configureDefaultColumn(TableViewerCreator<IMetadataColumn> tableViewerCreator) {
        if (LanguageManager.getCurrentLanguage() == ECodeLanguage.JAVA) {
            super.configureDefaultColumn(tableViewerCreator);
        }
    }

    @Override
    protected IBeanPropertyAccessors<IMetadataColumn, String> getDbColumnNameAccessor() {
        return new IBeanPropertyAccessors<IMetadataColumn, String>() {

            public String get(IMetadataColumn bean) {
                return bean.getOriginalDbColumnName();
            }

            public void set(IMetadataColumn bean, String value) {
                bean.setOriginalDbColumnName(value);
            }

        };
    }
}
