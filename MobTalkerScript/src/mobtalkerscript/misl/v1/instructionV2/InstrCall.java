package mobtalkerscript.misl.v1.instructionV2;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

public final class InstrCall extends MislInstruction
{
    private final int _nArgs;
    private final int _nReturn;
    
    public InstrCall( int nArgs, int nReturn )
    {
        _nArgs = nArgs;
        _nReturn = nReturn;
    }
    
    @Override
    public void execute( MislFrame frame )
    {
        MislValue target = frame.pop();
        
        MislValue[] args = new MislValue[_nArgs];
        for ( int i = 0; i < _nArgs; i++ )
        {
            args[i] = frame.pop();
        }
        
        MislValue result = target.call( args );
        
        if ( _nReturn == 0 )
            return;
        
        MislArray results = result.asArray();
        for ( int i = 0; i < results.getSize(); i++ )
        {
            frame.push( results.get( i ) );
        }
    }
    
}
