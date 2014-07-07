package net.mobtalker.mobtalkerscript.v2.instruction;

import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;

public class InstrNot extends MtsInstruction
{
    @Override
    public void execute( MtsFrame frame )
    {
        frame.push( isTrue( frame.pop() ) ? FALSE : TRUE );
    }
    
    @Override
    public int stackSizeChange()
    {
        return 0;
    }
    
    @Override
    public String toString()
    {
        return "NOT";
    }
}
