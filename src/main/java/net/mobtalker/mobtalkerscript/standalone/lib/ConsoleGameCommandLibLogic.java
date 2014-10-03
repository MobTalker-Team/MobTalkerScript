package net.mobtalker.mobtalkerscript.standalone.lib;

import java.util.List;

import joptsimple.internal.Strings;
import net.mobtalker.mobtalkerscript.api.library.IGameCommandLibLogic;
import net.mobtalker.mobtalkerscript.v2.MtsGlobals;

public class ConsoleGameCommandLibLogic implements IGameCommandLibLogic
{
    private final MtsGlobals _G;
    
    // ========================================
    
    public ConsoleGameCommandLibLogic( MtsGlobals g )
    {
        _G = g;
    }
    
    // ========================================

    @Override
    public int execute( String command, List<String> args )
    {
        _G.out.println( "Executing command '" + command + "' with arguments [" + Strings.join( args, "," ) + "]" );
        return 1;
    }
}
