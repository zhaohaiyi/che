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
package org.eclipse.che.ide.editor.macro;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the {@link EditorCurrentFileRelativePathProvider}
 *
 * @author Vlad Zhukovskyi
 */
@RunWith(MockitoJUnitRunner.class)
public class EditorCurrentFileRelativePathProviderTest extends AbstractEditorMacroProviderTest {

    private EditorCurrentFileRelativePathProvider provider;

    @Override
    protected AbstractEditorMacroProvider getProvider() {
        return provider;
    }

    @Before
    public void init() throws Exception {
        provider = new EditorCurrentFileRelativePathProvider(editorAgent, promiseProvider);
    }

    @Test
    public void testGetKey() throws Exception {
        assertSame(provider.getKey(), EditorCurrentFileRelativePathProvider.KEY);
    }

    @Test
    public void getValue() throws Exception {
        initEditorWithTestFile();

        provider.getValue();

        verify(editorAgent).getActiveEditor();
        verify(promiseProvider).resolve(eq(FILE_PATH));
    }

    @Test
    public void getEmptyValue() throws Exception {
        provider.getValue();

        verify(editorAgent).getActiveEditor();
        verify(promiseProvider).resolve(eq(""));
    }

}