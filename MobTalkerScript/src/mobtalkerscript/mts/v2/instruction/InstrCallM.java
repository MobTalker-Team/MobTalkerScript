package mobtalkerscript.mts.v2.instruction;

import static mobtalkerscript.mts.v2.value.MtsValue.*;
import mobtalkerscript.mts.v2.value.*;

public final class InstrCallM extends MtsInstruction
{
    private final MtsString _method;
    private final int _nArgs;
    private final int _nReturn;
    
    // ========================================
    
    public InstrCallM( String method, int nArgs, int nReturn )
    {
        _method = valueOf( method );
        _nArgs = nArgs;
        _nReturn = nReturn;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue target = frame.pop();
        MtsValue method = target.get( _method );
        
        MtsValue[] args = new MtsValue[_nArgs + 1];
        for ( int i = _nArgs - 1; i > 0; i-- )
        {
            args[i] = frame.pop();
        }
        args[0] = target;
        
        MtsValue result = method.call( MtsVarArgs.of( args ) );
        
        if ( _nReturn == 0 )
            return;
        
        if ( result.isVarArgs() )
        {
            MtsVarArgs results = result.asVarArgs();
            for ( int i = 0; i < _nReturn; i++ )
            {
                frame.push( results.get( i ) );
            }
        }
        else
        {
            frame.push( result );
            for ( int i = 1; i < _nReturn; i++ )
            {
                frame.push( NIL );
            }
        }
    }
    
    @Override
    public int stackSizeChange()
    {
        return -( 1 + _nArgs ) + _nReturn;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "CALLM " + _method + " " + _nArgs + " " + _nReturn;
    }
}
