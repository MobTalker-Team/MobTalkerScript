package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;

public class InstrLine extends AbstractFrameInstruction
{
    private final int _line;
    
    // ========================================
    
    public InstrLine(int line)
    {
        _line = line;
    }
    
    // ========================================
    
    @Override
    protected void doExecute(MislFrame frame, ScriptContext context)
    {
        frame.setCurrentLine(_line);
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "line " + _line;
    }
}
