package mobtalkerscript.misl.v1.parser;

import mobtalkerscript.misl.v1.instruction.*;

public class ParsedFunction
{
    
    private final InstrLabel _label;
    private final int _argCount;
    
    public ParsedFunction(InstrLabel label, int argCount)
    {
        _label = label;
        _argCount = argCount;
    }
    
    public InstrLabel getLabel()
    {
        return _label;
    }
    
    public int getArgCount()
    {
        return _argCount;
    }
    
}
