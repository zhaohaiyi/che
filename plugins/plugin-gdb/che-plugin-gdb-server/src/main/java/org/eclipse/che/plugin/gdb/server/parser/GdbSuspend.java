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
package org.eclipse.che.plugin.gdb.server.parser;

import org.eclipse.che.api.debug.shared.model.Location;
import org.eclipse.che.api.debug.shared.model.impl.LocationImpl;
import org.eclipse.che.api.debugger.server.exceptions.DebuggerException;
import org.eclipse.che.plugin.gdb.server.exception.GdbParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Suspend command parser.
 *
 * @author Roman Nikitenko
 */
public class GdbSuspend {
    private static final Pattern GDB_INFO_LINE = Pattern.compile("Line ([0-9]*) of \"(.*)\"\\s.*");
    private static final Pattern GDB_LINE_OUT_OF_RANGE = Pattern.compile("Line number ([0-9]*) is out of range for \"(.*)\".*");

    private final Location location;

    public GdbSuspend(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    /**
     * Factory method.
     */
    public static GdbSuspend parse(GdbOutput gdbOutput) throws DebuggerException {
        String output = gdbOutput.getOutput();

        Matcher matcher = GDB_INFO_LINE.matcher(output);
        if (matcher.find()) {
            String lineNumber = matcher.group(1);
            String file = matcher.group(2);
            return new GdbSuspend(new LocationImpl(file, Integer.parseInt(lineNumber)));
        }

        matcher = GDB_LINE_OUT_OF_RANGE.matcher(output);
        if (matcher.find()) {
            throw new DebuggerException("Unable suspend debugger session." + output);
        }

        throw new GdbParseException(GdbSuspend.class, output);
    }
}
