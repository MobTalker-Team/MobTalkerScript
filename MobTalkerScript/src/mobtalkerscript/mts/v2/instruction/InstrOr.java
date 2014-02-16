package mobtalkerscript.mts.v2.instruction;

import static mobtalkerscript.mts.v2.value.MtsBoolean.*;

public final class InstrOr extends MtsJumpInstruction
{
    public InstrOr()
    {
        super();
    }
    
    public InstrOr( int offset )
    {
        super( offset );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        if ( isTrue( frame.peek() ) )
        {
            super.execute( frame );
        }
        else
        {
            frame.pop();
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
        return "OR " + getTarget();
    }
}
