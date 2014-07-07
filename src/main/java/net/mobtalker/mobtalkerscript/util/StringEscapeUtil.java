package net.mobtalker.mobtalkerscript.util;

import org.apache.commons.lang3.*;

public class StringEscapeUtil
{
    private static final String[] targets = new String[] { "\\\"", "\\n", "\\t" };
    private static final String[] replacements = new String[] { "\"", "\n", "\t" };
    
    public static String unescape( String s )
    {
        return StringUtils.replaceEachRepeatedly( s, targets, replacements );
    }
}
