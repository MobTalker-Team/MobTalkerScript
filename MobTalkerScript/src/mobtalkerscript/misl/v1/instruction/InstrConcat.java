package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

public class InstrConcat extends AbstractMislInstruction
{
    private final int _count;
    
    // ========================================
    
    public InstrConcat( int count )
    {
        _count = count;
    }
    
    // ========================================
    
    @Override
    protected void doExecute( MislFrame frame, ScriptContext context )
    {
        if ( _count == 2 )
        {
            MislString a = frame.pop().toMtsString();
            MislString b = frame.pop().toMtsString();
            
            frame.push( b.concat( a ) );
        }
        else if ( _count > 2 )
        {
            MislString[] strs = new MislString[_count];
            
            for ( int i = ( _count - 1 ); i >= 0; i-- )
            {
                strs[i] = frame.pop().toMtsString();
            }
            
            frame.push( MislString.concat( strs ) );
        }
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return String.format( "%1$-10s %2$s", "CONCAT", _count );
    }
}
