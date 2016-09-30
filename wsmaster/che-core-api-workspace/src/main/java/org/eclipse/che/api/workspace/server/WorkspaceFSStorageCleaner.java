/*******************************************************************************
 * Copyright (c) 2012-2016 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.api.workspace.server;

/**
 * This component removes workspace folder with user's projects after delete workspace operation.
 * (see more {@link WorkspaceService#delete}).
 *
 * @author Alexander Andrienko
 */
public interface WorkspaceFSStorageCleaner {

    /**
     * Removes workspace folder with projects by {@code workspaceId}. It can be potentially long time operation
     * (if workspace contains a lot of files or server is busy to done this task quickly), so it should be asynchronous operation.
     *
     * @param workspaceId
     *         unique workspaceId identifier
     */
    void clear(String workspaceId);
}
