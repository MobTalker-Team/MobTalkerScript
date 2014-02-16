package mobtalkerscript.mts.v2.instruction;

import static mobtalkerscript.mts.v2.value.MtsBoolean.*;

public final class InstrAnd extends MtsJumpInstruction
{
    public InstrAnd()
    {
        super();
    }
    
    public InstrAnd( int offset )
    {
        super( offset );
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        if ( isTrue( frame.peek() ) )
        {
            frame.pop();
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
        return "AND " + getTarget();
    }
}
