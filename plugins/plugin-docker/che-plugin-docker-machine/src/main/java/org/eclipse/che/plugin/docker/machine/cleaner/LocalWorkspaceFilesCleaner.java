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
package org.eclipse.che.plugin.docker.machine.cleaner;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import org.eclipse.che.api.core.model.workspace.Workspace;
import org.eclipse.che.api.core.util.FileCleaner;
import org.eclipse.che.api.workspace.server.WorkspaceFilesCleaner;
import org.eclipse.che.plugin.docker.machine.local.node.provider.LocalWorkspaceFolderPathProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Local implementation of the {@link WorkspaceFilesCleaner}.
 *
 * @author Alexander Andrienko
 */
@Singleton
public class LocalWorkspaceFilesCleaner implements WorkspaceFilesCleaner {

    private static final Logger LOG = LoggerFactory.getLogger(LocalWorkspaceFilesCleaner.class);

    private final LocalWorkspaceFolderPathProvider workspaceFolderPathProvider;

    @Inject(optional = true)
    @Named("host.projects.root")
    private String hostProjectsFolder;

    @Inject
    public LocalWorkspaceFilesCleaner(LocalWorkspaceFolderPathProvider workspaceFolderPathProvider) {
        this.workspaceFolderPathProvider = workspaceFolderPathProvider;
    }

    @Override
    public void clear(Workspace workspace) {
        try {
            String workspacePath = workspaceFolderPathProvider.getPathByName(workspace.getConfig().getName());
            File workspaceStorage = new File(workspacePath);
            if (!workspacePath.equals(hostProjectsFolder) && workspaceStorage.exists()) {
                FileCleaner.addFile(workspaceStorage);
            }
        } catch (IOException e) {
            LOG.error("Failed to clean up workspace folder for workspace with id: {}. Cause: {}.", workspace.getId(), e.getMessage());
        }
    }
}
