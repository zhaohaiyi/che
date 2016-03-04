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
package org.eclipse.che.ide.reference;

import org.eclipse.che.api.workspace.shared.dto.ProjectConfigDto;
import org.eclipse.che.ide.api.app.AppContext;
import org.eclipse.che.ide.api.app.CurrentProject;
import org.eclipse.che.ide.api.project.node.HasStorablePath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class ShowReferencePresenterTest {

    private static final String PATH         = "path";
    private static final String PROJECT_TYPE = "type";

    //constructor mocks
    @Mock
    private ShowReferenceView view;
    @Mock
    private AppContext        appContext;

    //additional mocks
    @Mock
    private CurrentProject   currentProject;
    @Mock
    private ProjectConfigDto projectConfig;
    @Mock
    private FqnProvider      provider;
    @Mock
    private HasStorablePath  hasStorablePathNode;

    private ShowReferencePresenter presenter;

    @Before
    public void setUp() {
        Map<String, FqnProvider> providers = new HashMap<>();
        providers.put(PROJECT_TYPE, provider);

        presenter = new ShowReferencePresenter(view, providers, appContext);

        when(hasStorablePathNode.getStorablePath()).thenReturn(PATH);
        when(appContext.getCurrentProject()).thenReturn(currentProject);
        when(currentProject.getProjectConfig()).thenReturn(projectConfig);
        when(projectConfig.getType()).thenReturn(PROJECT_TYPE);
    }

    @Test
    public void pathShouldBeShownForNodeWhichDoesNotHaveFqn() {
        Map<String, FqnProvider> providers = new HashMap<>();
        presenter = new ShowReferencePresenter(view, providers, appContext);

        presenter.show(hasStorablePathNode);

        verify(provider, never()).getFqn(hasStorablePathNode);
        verify(view).show("", PATH);
    }

    @Test
    public void pathAndFqnShouldBeShownForNode() {
        when(provider.getFqn(hasStorablePathNode)).thenReturn("fqn");

        presenter.show(hasStorablePathNode);

        verify(provider).getFqn(hasStorablePathNode);
        verify(view).show("fqn", PATH);
    }
}