package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.util.*;

public class InstrYield extends MislInstruction
{
    @Override
    public void execute( Stack<MislFrame> frameStack, ScriptContext context )
    {}
    
    // ========================================
    
    @Override
    public boolean pausesExecution()
    {
        return true;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "YIELD";
    }
}