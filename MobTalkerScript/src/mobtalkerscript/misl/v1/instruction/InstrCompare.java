package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.value.*;

public class InstrCompare extends AbstractBinaryOperator
{
    
    @Override
    protected MislValue getResult( MislValue a, MislValue b )
    {
        return a.equalsMts( b );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "COMPARE";
    }
}
