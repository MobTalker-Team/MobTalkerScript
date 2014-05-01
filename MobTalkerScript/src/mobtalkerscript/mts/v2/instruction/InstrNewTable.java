package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.value.*;

import com.google.common.collect.*;

public class InstrNewTable extends MtsInstruction
{
    private final int _nListElements;
    private final int _nHashElements;
    
    // ========================================
    
    /* package */InstrNewTable( int listElements, int hashElements )
    {
        _nListElements = listElements;
        _nHashElements = hashElements;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsTable t = new MtsTable( Math.max( _nListElements, 16 ), Math.max( _nHashElements, 16 ) );
        
        // List elements
        t.add( Lists.reverse( frame.pop( _nListElements ) ) );
        
        // Key-Value pairs
        for ( int i = 0; i < _nHashElements; i++ )
        {
            MtsValue v = frame.pop();
            MtsValue k = frame.pop();
            t.set( k, v );
        }
        
        frame.push( t );
    }
    
    @Override
    public int stackSizeChange()
    {
        return 1 - _nListElements - ( _nHashElements * 2 );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "NEWTABLE " + _nListElements + " " + _nHashElements;
    }
}
