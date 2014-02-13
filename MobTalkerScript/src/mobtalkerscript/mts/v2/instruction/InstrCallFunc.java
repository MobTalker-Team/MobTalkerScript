package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.value.*;

public final class InstrCallFunc extends MtsInstruction
{
    private final int _nArgs;
    private final int _nReturn;
    
    // ========================================
    
    public InstrCallFunc( int nArgs, int nReturn )
    {
        _nArgs = nArgs;
        _nReturn = nReturn;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue target = frame.pop();
        
        MtsValue[] args = new MtsValue[_nArgs];
        for ( int i = 0; i < _nArgs; i++ )
        {
            args[i] = frame.pop();
        }
        
        MtsValue result = target.call( args );
        
        if ( _nReturn == 0 )
            return;
        
        MtsArray results = result.asArray();
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
        return "CALLF " + _nArgs + " " + _nReturn;
    }
}
