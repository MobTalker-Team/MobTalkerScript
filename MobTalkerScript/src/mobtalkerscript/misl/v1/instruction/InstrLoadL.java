package mobtalkerscript.misl.v1.instruction;

// package mobtalker.script.misl.v1.instruction;
//
//import mobtalker.script.misl.v1.*;
//import mobtalker.script.misl.v1.value.*;
//
//public class InstrLoadL extends AbstractFrameInstruction
//{
//    
//    private final int _index;
//    
//    // ========================================
//    
//    public InstrLoadL(int index)
//    {
//        _index = index;
//    }
//    
//    // ========================================
//    
//    @Override
//    protected void doExecute(MislFrame frame, ScriptContext context)
//    {
//        MislValue value = frame.getLocal(_index);
//        frame.getStack().push(value);
//    }
//    
//    // ========================================
//    
//    @Override
//    public String toString()
//    {
//        return "load_l " + _index;
//    }
//    
//}
