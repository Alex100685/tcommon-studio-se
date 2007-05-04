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
package org.talend.repository.ui.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.i18n.Messages;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.PropertiesPackage;
import org.talend.core.model.properties.Property;
import org.talend.core.model.properties.Status;
import org.talend.repository.model.IProxyRepositoryFactory;

/**
 * DOC smallet class global comment. Detailled comment <br/>
 * 
 * $Id: StatusHelper.java 914 2006-12-08 08:28:53 +0000 (星期五, 08 十二月 2006) bqian $
 * 
 */
public class StatusHelper {

    private Map<String, Status> code2status;

    private Map<String, String> label2code;

    private IProxyRepositoryFactory repositoryFactory;

    public StatusHelper(IProxyRepositoryFactory repositoryFactory) {
        super();
        this.repositoryFactory = repositoryFactory;
    }

    public String getStatusLabel(String statusCode) {
        if (statusCode == null) {
            return ""; //$NON-NLS-1$
        }
        Status status = code2status.get(statusCode);
        return status == null ? statusCode : status.getLabel();
    }

    public String getStatusCode(String label) {
        String text;
        text = label2code.get(label);
        return (text == null ? label : text);
    }

    public List<Status> getStatusList(Property property) throws PersistenceException {
        List<Status> status = null;
        Item item = property.getItem();
        if (item != null) {
            EClass propertyEClass = item.eClass();
            switch (propertyEClass.getClassifierID()) {
            case PropertiesPackage.CSV_FILE_CONNECTION_ITEM:
            case PropertiesPackage.DATABASE_CONNECTION_ITEM:
            case PropertiesPackage.DELIMITED_FILE_CONNECTION_ITEM:
            case PropertiesPackage.POSITIONAL_FILE_CONNECTION_ITEM:
            case PropertiesPackage.CONTEXT_ITEM:
            case PropertiesPackage.PROCESS_ITEM:
            case PropertiesPackage.ROUTINE_ITEM:
            case PropertiesPackage.REG_EX_FILE_CONNECTION_ITEM:
                status = repositoryFactory.getTechnicalStatus();
                break;
            case PropertiesPackage.BUSINESS_PROCESS_ITEM:
            case PropertiesPackage.DOCUMENTATION_ITEM:
                status = repositoryFactory.getDocumentationStatus();
                break;
            }
        }
        if (status == null) {
            status = new ArrayList<Status>();
        }
        toMaps(status);
        return status;
    }

    /**
     * DOC tguiu Comment method "asMap".
     * 
     * @param status
     * @return
     */
    private void toMaps(List<Status> status) {
        code2status = new HashMap<String, Status>();
        label2code = new HashMap<String, String>();
        for (Status s : status) {
            code2status.put(s.getCode(), s);
            label2code.put(s.getLabel(), s.getCode());
        }
    }
}
