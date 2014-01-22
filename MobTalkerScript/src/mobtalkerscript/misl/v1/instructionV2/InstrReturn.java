package mobtalkerscript.misl.v1.instructionV2;

import static mobtalkerscript.misl.v1.value.MislValue.*;
import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

public class InstrReturn extends MislInstruction
{
    private final int _count;
    
    public InstrReturn( int count )
    {
        _count = count;
    }
    
    @Override
    public void execute( MislFrame frame )
    {
        if ( _count == 0 )
        {
            frame.push( EMPTY_ARRAY );
        }
        else
        {
            MislValue[] values = new MislValue[_count];
            for ( int i = _count - 1; i >= 0; i-- )
            {
                values[i] = frame.pop();
            }
            frame.push( arrayOf( values ) );
        }
        
        frame.exit();
    }
}
