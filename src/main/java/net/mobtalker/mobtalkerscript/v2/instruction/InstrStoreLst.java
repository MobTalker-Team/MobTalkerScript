package mobtalkerscript.v2.instruction;

import java.util.*;

import mobtalkerscript.v2.value.*;

import com.google.common.collect.*;

public class InstrStoreLst extends MtsInstruction
{
    private final int _count;
    
    // ========================================
    
    /* package */InstrStoreLst( int count )
    {
        _count = count;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        List<MtsValue> values = frame.pop( _count );
        MtsTable t = frame.pop().asTable();
        
        t.add( Lists.reverse( values ) );
    }
    
    @Override
    public int stackSizeChange()
    {
        return -1 - _count;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "STORELST " + _count;
    }
    
}
