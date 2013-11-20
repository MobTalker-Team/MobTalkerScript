package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.value.*;

public class InstrConcat extends AbstractBinaryOperator
{
    
    @Override
    protected MislValue getResult( MislValue a, MislValue b )
    {
        return MislString.concat( a.toMtsString(), b.toMtsString() );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "CONCAT";
    }
    
}
