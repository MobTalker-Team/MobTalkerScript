package net.mobtalker.mobtalkerscript.api.library;

import static net.mobtalker.mobtalkerscript.v2.MtsCheck.*;
import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;

import java.util.*;

import net.mobtalker.mobtalkerscript.v2.value.MtsVarArgs;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

import com.google.common.collect.Lists;

public class GameCommandLib
{
    private IGameCommandLibLogic _logic;
    
    // ========================================
    
    public GameCommandLib( IGameCommandLibLogic logic )
    {
        _logic = logic;
    }
    
    // ========================================
    
    public IGameCommandLibLogic getLogic()
    {
        return _logic;
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsVarArgs execute( MtsVarArgs args )
    {
        String command = checkString( args, 0 );
        
        List<String> arguments;
        int nArguments = args.count() - 1;
        
        if ( nArguments > 0 )
        {
            arguments = Lists.newArrayListWithCapacity( nArguments );
            for ( int i = 1; i <= nArguments; i++ )
            {
                arguments.add( checkString( args.get( i ), i ) );
            }
        }
        else
        {
            arguments = Collections.emptyList();
        }
        
        int executions = _logic.execute( command, arguments );
        return MtsVarArgs.of( valueOf( executions > 0 ), valueOf( executions ) );
    }
}