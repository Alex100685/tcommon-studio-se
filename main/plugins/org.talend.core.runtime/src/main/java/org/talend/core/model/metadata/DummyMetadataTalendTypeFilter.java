// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.core.model.metadata;

import java.util.ArrayList;
import java.util.List;

/**
 * created by rdubois on 30 juil. 2015 Detailled comment
 *
 */
public class DummyMetadataTalendTypeFilter extends MetadataTalendTypeFilter {

    @Override
    protected List<String> getUnsupportedTypes() {
        return new ArrayList<>();
    }

}
