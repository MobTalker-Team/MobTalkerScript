package mobtalkerscript.mts.v2.instruction;

import static mobtalkerscript.mts.v2.value.MtsValue.*;
import mobtalkerscript.mts.v2.value.*;

public final class InstrCallF extends MtsInstruction
{
    private final int _nArgs;
    private final int _nReturn;
    
    // ========================================
    
    public InstrCallF( int nArgs, int nReturn )
    {
        _nArgs = nArgs;
        _nReturn = nReturn;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue[] args = new MtsValue[_nArgs];
        for ( int i = _nArgs - 1; i >= 0; i-- )
        {
            args[i] = frame.pop();
        }
        
        MtsValue target = frame.pop();
        MtsValue result = target.call( new MtsVarArgs( args ) );
        
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
        return "CALLF " + _nArgs + " " + _nReturn;
    }
}
