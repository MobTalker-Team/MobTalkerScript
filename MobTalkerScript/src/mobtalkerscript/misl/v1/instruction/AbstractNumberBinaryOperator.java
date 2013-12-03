package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.value.*;

/* package */abstract class AbstractNumberBinaryOperator extends AbstractBinaryOperator
{
    
    @Override
    protected final MislValue getResult( MislValue a, MislValue b )
    {
        return calculate( a.asNumber(), b.asNumber() );
    }
    
    protected abstract MislNumber calculate( MislNumber a, MislNumber b );
    
}
