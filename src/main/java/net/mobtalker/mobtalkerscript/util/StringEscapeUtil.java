/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.util;

import org.apache.commons.lang3.StringUtils;

public class StringEscapeUtil
{
    private static final String[] targets = new String[] { "\\\"", "\\n", "\\t" };
    private static final String[] replacements = new String[] { "\"", "\n", "\t" };
    
    public static String unescape( String s )
    {
        return StringUtils.replaceEachRepeatedly( s, targets, replacements );
    }
}
