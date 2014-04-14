package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.value.*;

/**
 * Jump instruction that only jumps if the top of the stack is not true.
 */
public final class InstrGForLoop extends MtsJumpInstruction
{
    private final int _index;
    private final int _values;
    
    // ========================================
    
    public InstrGForLoop( int index, int values )
    {
        super();
        _index = index;
        _values = values;
    }
    
    public InstrGForLoop( int index, int values, int offset )
    {
        super( offset );
        _index = index;
        _values = values;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue iterFunc = frame.getLocal( _index ).get();
        MtsValue state = frame.getLocal( _index + 1 ).get();
        
        FrameValue indexVar = frame.getLocal( _index + 2 );
        MtsValue index = indexVar.get();
        
        MtsVarArgs results = iterFunc.call( state, index ).asVarArgs();
        
        MtsValue nextIndex = results.get( 0 );
        if ( !nextIndex.isNil() )
        {
            indexVar.set( nextIndex );
            for ( int i = 0; i < _values; i++ )
            {
                frame.getLocal( _values + i ).set( results.get( i ) );
            }
        }
        else
        {
            super.execute( frame );
        }
    }
    
    @Override
    public int stackSizeChange()
    {
        return 0;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return String.format( "GFORLOOP %s %s", _index, getDistance() );
    }
}
