package mobtalkerscript.mts.v2.instruction;

import mobtalkerscript.mts.v2.value.*;

public class InstrNewTable extends MtsInstruction
{
    
    @Override
    public void execute( MtsFrame frame )
    {
        frame.push( new MtsTable() );
    }
    
    @Override
    public int stackSizeChange()
    {
        return 1;
    }
    
    @Override
    public String toString()
    {
        return "NEWTABLE";
    }
}
