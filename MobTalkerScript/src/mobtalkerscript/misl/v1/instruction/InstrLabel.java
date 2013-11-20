package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.util.*;

public class InstrLabel extends MislInstruction
{
    private final String _name;
    
    // ========================================
    
    public InstrLabel( String name )
    {
        _name = name;
    }
    
    // ========================================
    
    @Override
    public void execute( Stack<MislFrame> frameStack, ScriptContext context )
    {}
    
    // ========================================
    
    public String getName()
    {
        return _name;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return String.format( "%1$-10s %2$s", "LABEL", _name );
    }
}