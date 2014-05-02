package mobtalkerscript.mts.v2.instruction;

import static mobtalkerscript.mts.v2.value.MtsBoolean.*;

/**
 * Jump instruction that only jumps if the top of the stack is not true.
 */
public final class InstrTest extends MtsJumpInstruction
{
    /* package */InstrTest()
    {
        super();
    }
    
    /* package */InstrTest( int offset )
    {
        super( offset );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        if ( !isTrue( frame.pop() ) )
        {
            super.execute( frame );
        }
    }
    
    @Override
    public int stackSizeChange()
    {
        return -1;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "TEST " + getDistance();
    }
}
