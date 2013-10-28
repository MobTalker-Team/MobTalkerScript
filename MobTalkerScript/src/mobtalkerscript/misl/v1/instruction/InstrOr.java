// package mobtalker.script.misl.v1.instruction;
//
//import static mobtalker.script.misl.v1.value.MislValue.*;
//import mobtalker.script.misl.v1.value.*;
//
//public class InstrOr extends AbstractBinaryOperator
//{
//    
//    @Override
//    protected MislValue getResult(MislValue a, MislValue b)
//    {
//        return (a != FALSE) && (a != NIL) ? a : b;
//    }
//    
//    private boolean test(boolean a, boolean b)
//    {
//        if (a && b)
//        {
//            return true;
//        }
//        else
//        {
//            return false;
//        }
//    }
//    
//    // ========================================
//    
//    @Override
//    public String toString()
//    {
//        return "or";
//    }
//    
//}
package mobtalkerscript.misl.v1.instruction;

import static mobtalkerscript.misl.v1.value.MislValue.*;
import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrOr extends AbstractStackInstruction
{
    
    private MislInstruction _b;
    private MislInstruction _cont;
    
    // ========================================
    
    public InstrOr(MislInstruction cont)
    {
        _cont = cont;
    }
    
    // ========================================
    
    @Override
    void setNext(MislInstruction next)
    {
        super.setNext(next);
        _b = next;
    }
    
    // ========================================
    
    @Override
    protected void doExecute(Stack<MislValue> stack, ScriptContext context)
    {
        MislValue value = stack.peek();
        
        if ((value != FALSE) && (value != NIL))
        {
            _next = _cont;
        }
        else
        {
            _next = _b;
            stack.pop();
        }
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "or";
    }
    
}
