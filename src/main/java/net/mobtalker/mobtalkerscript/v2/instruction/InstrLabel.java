package net.mobtalker.mobtalkerscript.v2.instruction;

public class InstrLabel extends MtsInstruction
{
    private final String _name;
    
    // ========================================
    
    public InstrLabel()
    {
        _name = null;
    }
    
    public InstrLabel( String name )
    {
        _name = name;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {}
    
    @Override
    public int stackSizeChange()
    {
        return 0;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return _name == null ? "LABEL" : ( "LABEL " + _name );
    }
    
}
