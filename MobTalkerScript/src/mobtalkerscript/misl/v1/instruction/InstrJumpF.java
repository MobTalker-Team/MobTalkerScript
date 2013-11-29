package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

public class InstrJumpF extends AbstractMislInstruction
{
    @Override
    void setNext( MislInstruction next )
    {}
    
    // ========================================
    
    @Override
    protected void doExecute( MislFrame frame, ScriptContext context )
    {
        MislValue top = frame.pop();
        
        if ( top.isNativeFunction() )
        {
            try
            {
                top.asNativeFunction().call( context.getCurrentScope() );
            }
            catch ( ScriptRuntimeException ex )
            {
                throw new ScriptRuntimeException( "%s (at %s:%s)",
                                                  ex.getMessage(),
                                                  frame.getSourceName(),
                                                  frame.getSourceLine() );
            }
        }
        else if ( top.isFunction() )
        {
            context.leaveFunctionScope();
            context.enterFunctionScope();
            
            MislInstruction instr = top.asFunction().getInstruction();
            _next = instr;
        }
        else
        {
            throw new ScriptRuntimeException( "expected function, got %s (at %s:%s)",
                                              top.getTypeName(),
                                              frame.getSourceName(),
                                              frame.getSourceLine() );
        }
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "JUMP_F";
    }
}