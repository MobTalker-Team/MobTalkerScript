package mobtalkerscript.misl.v1.instruction;

@Deprecated
public class InstrFrame /* extends MislInstruction */
{   
    
//    private int _nLocals;
//    
//    // ========================================
//    
//    public InstrFrame(int nLocals)
//    {
//        _nLocals = nLocals;
//    }
//    
//    // ========================================
//    
//    @Override
//    public void execute(Stack<MislFrame> frameStack, ScriptContext context)
//    {
//        MislFrame curFrame = frameStack.peek();
//        MislFrame newFrame = new MislFrame(_nLocals);
//        
//        Stack<MislValue> curStack = curFrame.getStack();
//        Stack<MislValue> newStack = newFrame.getStack();
//        
//        int argCount;
//        if (!curStack.isEmpty())
//        {
//            argCount = (int) curStack.pop().asNumber().toJava();
//        }
//        else
//        {
//            argCount = 0;
//        }
//        
//        if (curStack.count() != argCount)
//        {
//            throw new ScriptEngineException("Wrong number of values on the current stack! Expected %s, has %s",
//                                            argCount,
//                                            curStack.count());
//        }
//        
//        for (int i = 0; i < argCount; i++)
//        {
//            newStack.push(curStack.pop());
//        }
//        
//        frameStack.push(newFrame);
//    }
//    
//    // ========================================
//    
//    @Override
//    public String toString()
//    {
//        return "frame";
//    }
//    
}
