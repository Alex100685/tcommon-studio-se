// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.core.runtime.services;

import org.talend.core.GlobalServiceRegister;
import org.talend.core.IService;
import org.talend.core.model.properties.RoutineItem;
import org.talend.core.model.routines.CodesJarInfo;

public interface IDesignerMavenService extends IService {

    String getCodesJarPackageByInnerCode(RoutineItem innerCodeItem);

    String getImportGAVPackageForCodesJar(CodesJarInfo info);

    void updateCodeJarMavenProject(CodesJarInfo info, boolean needReSync) throws Exception;

    String getLocalRepositoryPath() throws Exception;

    public static IDesignerMavenService get() {
        if (GlobalServiceRegister.getDefault().isServiceRegistered(IDesignerMavenService.class)) {
            return GlobalServiceRegister.getDefault().getService(IDesignerMavenService.class);
        }
        return null;
    }

}
