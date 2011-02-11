// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.rcp.intro.contentProvider;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.internal.intro.impl.model.util.ModelUtil;
import org.eclipse.ui.intro.config.IIntroContentProviderSite;
import org.eclipse.ui.intro.config.IIntroXHTMLContentProvider;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.core.CorePlugin;
import org.talend.core.model.general.Project;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.repository.ProjectManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * wchen class global comment. Detailled comment
 */
public class DynamicContentProvider implements IIntroXHTMLContentProvider {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.intro.config.IIntroXHTMLContentProvider#createContent(java.lang.String, org.w3c.dom.Element)
     */
    public void createContent(String id, Element parent) {
        // content for latest modified jobs and business models
        List<IRepositoryViewObject> latestItems = new ArrayList<IRepositoryViewObject>();
        Document dom = parent.getOwnerDocument();
        String url = "";
        if (ERepositoryObjectType.PROCESS.name().equals(id)) {
            latestItems = getLatestModifiedItems(ERepositoryObjectType.PROCESS, 8);
            url = "http://org.eclipse.ui.intro/runAction?pluginId=org.talend.designer.core&"
                    + "class=org.talend.designer.core.ui.action.EditProcess&"
                    + "id=org.talend.designer.core.actions.editprocess&nodeId=";
            if (latestItems.size() == 0) {

                parent.appendChild(dom.createElement("br"));
            }
        } else if (ERepositoryObjectType.BUSINESS_PROCESS.name().equals(id)) {
            latestItems = getLatestModifiedItems(ERepositoryObjectType.BUSINESS_PROCESS, 8);
            url = "http://org.eclipse.ui.intro/runAction?pluginId=org.talend.designer.business.diagram&"
                    + "class=org.talend.designer.business.diagram.custom.actions.OpenDiagramAction&"
                    + "id=org.talend.designer.business.diagram.Action2&nodeId=";
        } else if ("CUSTOMER_PAGE".equals(id)) {
            createOnlinePage(dom, parent);
        }

        for (IRepositoryViewObject object : latestItems) {
            Element hyperlink = dom.createElement("a");
            hyperlink.setAttribute("href", url + object.getId());
            hyperlink.setAttribute("title", "Modified at " + object.getModificationDate() + " by " + object.getAuthor() + "\n"
                    + "Created at " + object.getCreationDate() + " by" + object.getAuthor());
            hyperlink.appendChild(dom.createTextNode(object.getLabel() + " " + object.getVersion()));
            parent.appendChild(hyperlink);
            parent.appendChild(dom.createElement("br"));
        }
        // content for always welcome check box
        if ("ALWAYS_WELCOME".equals(id)) {
            IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
            boolean showIntroConfig = store.getBoolean(ITalendCorePrefConstants.ALWAYS_WELCOME);
            url = "location.href='http://org.eclipse.ui.intro/runAction?pluginId=org.talend.rcp&"
                    + "class=org.talend.rcp.intro.AlwaysWelcomeAction'";
            Element input = dom.createElement("input");
            input.setAttribute("type", "checkbox");
            if (!showIntroConfig) {
                input.setAttribute("checked", "checked");
            }
            input.setAttribute("onclick", url);

            input.appendChild(dom.createTextNode("Do not display again\u00a0"));
            parent.appendChild(input);
        }

    }

    private void createOnlinePage(Document dom, Element parent) {

        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL("http://www.talend.com/builtin_news/index.php");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setReadTimeout(1000);
            urlConnection.getInputStream();
            setDIVStyle(dom, true);
        } catch (Exception e) {
            setDIVStyle(dom, false);
            return;
        } finally {
            urlConnection.disconnect();
        }

        // online content

        Element tdElem = dom.createElement("td");
        tdElem.setAttribute("class", "separator");
        parent.appendChild(tdElem);

        Element div = dom.createElement("div");
        div.setAttribute("style", "overflow:auto;height:400px;width:260px;padding-left:20px;");
        tdElem.appendChild(div);

        Element spanElem = dom.createElement("span");
        spanElem.setAttribute("class", "style_1 style_2 style_3");
        spanElem.appendChild(dom.createTextNode("Talend news"));
        div.appendChild(spanElem);
        div.appendChild(dom.createElement("br"));

        Element iFrame = dom.createElement("iframe");
        iFrame.setAttribute("src", "http://www.talend.com/builtin_news/index.php");
        iFrame.setAttribute("frameborder", "0");
        iFrame.setAttribute("width", "240px");
        iFrame.setAttribute("height", "370px");
        iFrame.appendChild(dom.createTextNode(" "));
        div.appendChild(iFrame);
    }

    private List<IRepositoryViewObject> getLatestModifiedItems(ERepositoryObjectType type, int count) {
        List<IRepositoryViewObject> latestItems = new ArrayList<IRepositoryViewObject>();
        try {
            Project currentProject = ProjectManager.getInstance().getCurrentProject();
            if (currentProject != null) {
                List<IRepositoryViewObject> all = ProxyRepositoryFactory.getInstance().getAll(currentProject, type);
                IRepositoryViewObject[] data = new IRepositoryViewObject[all.size()];
                all.toArray(data);
                for (int i = 0; i < data.length && i < count; i++) {
                    for (int j = data.length - 1; j > i; j--) {
                        Date modificationDate = data[j].getModificationDate();
                        Date modificationDate2 = data[j - 1].getModificationDate();
                        if (modificationDate == null) {
                            modificationDate = data[j].getCreationDate();
                        }
                        if (modificationDate2 == null) {
                            modificationDate2 = data[j - 1].getCreationDate();
                        }
                        if (modificationDate.after(modificationDate2)) {
                            IRepositoryViewObject temp = data[j - 1];
                            data[j - 1] = data[j];
                            data[j] = temp;
                        }
                    }
                    latestItems.add(data[i]);
                }

            }
            return latestItems;
        } catch (PersistenceException e) {
            ExceptionHandler.process(e);
        }
        return latestItems;
    }

    private void setDIVStyle(Document dom, boolean online) {
        NodeList elementsByTagName = dom.getElementsByTagName("div");
        Node[] array = ModelUtil.getArray(elementsByTagName);
        Element leftDiv = null;
        Element rightDiv = null;
        for (int i = 0; i < array.length; i++) {
            Element node = (Element) array[i];
            if ("div_left_part".equals(node.getAttribute("id"))) {
                leftDiv = node;
            } else if ("div_right_part".equals(node.getAttribute("id"))) {
                rightDiv = node;
            }

        }

        if (leftDiv != null) {
            if (online) {
                leftDiv.setAttribute("class", "left_1");
            } else {
                leftDiv.setAttribute("class", "left");
            }
        }

        if (rightDiv != null) {
            if (online) {
                rightDiv.setAttribute("class", "right_1");
            } else {
                rightDiv.setAttribute("class", "right");
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.intro.config.IIntroContentProvider#createContent(java.lang.String, java.io.PrintWriter)
     */
    public void createContent(String id, PrintWriter out) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.intro.config.IIntroContentProvider#createContent(java.lang.String,
     * org.eclipse.swt.widgets.Composite, org.eclipse.ui.forms.widgets.FormToolkit)
     */
    public void createContent(String id, Composite parent, FormToolkit toolkit) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.intro.config.IIntroContentProvider#dispose()
     */
    public void dispose() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.intro.config.IIntroContentProvider#init(org.eclipse.ui.intro.config.IIntroContentProviderSite)
     */
    public void init(IIntroContentProviderSite site) {
    }

}
