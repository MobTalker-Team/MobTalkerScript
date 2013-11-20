// package mobtalker.script.misl.v1.instruction;
//
//import static mobtalker.script.misl.v1.value.MislValue.*;
//import mobtalker.script.misl.v1.value.*;
//
//public class InstrAnd extends AbstractBinaryOperator
//{
//    
//    @Override
//    protected MislValue getResult(MislValue a, MislValue b)
//    {
//        return (a == FALSE) || (a == NIL) ? a : b;
//    }
//    
//    // ========================================
//    
//    @Override
//    public String toString()
//    {
//        return "and";
//    }
//    
//}
package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrAnd extends AbstractStackInstruction
{
    
    private MislInstruction _b;
    private MislInstruction _cont;
    
    // ========================================
    
    public InstrAnd( MislInstruction cont )
    {
        _cont = cont;
    }
    
    // ========================================
    
    @Override
    void setNext( MislInstruction next )
    {
        super.setNext( next );
        _b = next;
    }
    
    // ========================================
    
    @Override
    protected void doExecute( Stack<MislValue> stack, ScriptContext context )
    {
        MislValue value = stack.peek();
        
        if ( MislBoolean.isTrue( value ) )
        {
            _next = _b;
            stack.pop();
        }
        else
        {
            _next = _cont;
        }
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "AND";
    }
}
