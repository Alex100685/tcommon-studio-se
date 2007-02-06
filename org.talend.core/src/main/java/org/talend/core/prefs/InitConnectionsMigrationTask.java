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
package org.talend.core.prefs;

import java.util.List;

import org.talend.core.CorePlugin;
import org.talend.core.i18n.Messages;
import org.talend.core.model.general.ConnectionBean;
import org.talend.core.model.migration.AbstractMigrationTask;
import org.talend.core.model.migration.IWorkspaceMigrationTask;

/**
 * DOC smallet class global comment. Detailled comment <br/>
 * 
 * $Id: talend.epf 1 2006-09-29 17:06:40 +0000 (ven., 29 sept. 2006) nrousseau $
 * 
 */
public class InitConnectionsMigrationTask extends AbstractMigrationTask implements IWorkspaceMigrationTask {

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.migration.IMigrationTask#execute()
     */
    public boolean execute() {
        PreferenceManipulator prefManipulator = new PreferenceManipulator(CorePlugin.getDefault().getPreferenceStore());

        List<ConnectionBean> toReturn = prefManipulator.readConnections();

        if (toReturn.isEmpty()) {
            if (prefManipulator.getLastUser() != null && prefManipulator.getLastUser().length() > 0) {
                ConnectionBean recup = ConnectionBean.getDefaultConnectionBean();
                recup.setUser(prefManipulator.getLastUser());
                recup.setComplete(true);
                toReturn.add(recup);

                prefManipulator.saveConnections(toReturn);

                prefManipulator.setLastUser(""); //$NON-NLS-1$
                prefManipulator.saveUsers(new String[] {});
            }
        }
        return true;
    }

}
