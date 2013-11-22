package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

/* package */abstract class AbstractBinaryOperator extends AbstractStackInstruction
{
    
    @Override
    protected void doExecute( Stack<MislValue> stack, ScriptContext context )
    {
        MislValue b = stack.pop();
        MislValue a = stack.pop();
        
        MislValue result = getResult( a, b );
        
        stack.push( result );
    }
    
    protected abstract MislValue getResult( MislValue a, MislValue b );
    
}
