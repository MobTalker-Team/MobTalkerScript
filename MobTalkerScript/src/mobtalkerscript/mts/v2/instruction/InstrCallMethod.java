package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.value.*;

public final class InstrCallMethod extends MtsInstruction
{
    private final String _method;
    private final int _nArgs;
    private final int _nReturn;
    
    // ========================================
    
    public InstrCallMethod( String method, int nArgs, int nReturn )
    {
        _method = method;
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
        
        MtsValue result = method.call( new MtsVarArgs( args ) );
        
        if ( _nReturn == 0 )
            return;
        
        MtsVarArgs results = result.asVarArgs();
        for ( int i = 0; i < _nReturn; i++ )
        {
            frame.push( results.get( i ) );
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
