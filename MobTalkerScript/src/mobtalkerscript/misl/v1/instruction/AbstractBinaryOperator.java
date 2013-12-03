package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

/* package */abstract class AbstractBinaryOperator extends AbstractMislInstruction
{
    
    @Override
    protected final void doExecute( MislFrame frame, ScriptContext context )
    {
        MislValue b = frame.pop();
        MislValue a = frame.pop();
        
        MislValue result = getResult( a, b );
        
        frame.push( result );
    }
    
    protected abstract MislValue getResult( MislValue a, MislValue b );
    
}
