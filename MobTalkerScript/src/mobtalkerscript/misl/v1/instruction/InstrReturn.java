package mobtalkerscript.misl.v1.instruction;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;
import mobtalkerscript.util.*;

public class InstrReturn extends MislInstruction
{
    private final int _returnCount;
    
    // ========================================
    
    public InstrReturn( int returnCount )
    {
        _returnCount = returnCount;
    }
    
    // ========================================
    
    @Override
    public void execute( Stack<MislFrame> frameStack, ScriptContext context )
    {
        MislFrame oldFrame = frameStack.pop();
        
        _next = oldFrame.getReturnPointer();
        
        if ( !frameStack.isEmpty() )
        {
            MislFrame curFrame = frameStack.peek();
            
            for ( int i = 0; i < _returnCount; i++ )
            {
                MislValue value = oldFrame.pop();
                
                if ( i < oldFrame.getReturnCount() )
                {
                    curFrame.push( value );
                }
            }
            
            if ( !oldFrame.isEmpty() )
            {
                throw new ScriptEngineException( "Stack was not emptied: %s", oldFrame );
            }
            
            int missingCount = oldFrame.getReturnCount() - _returnCount;
            for ( int i = 0; i < missingCount; i++ )
            {
                curFrame.push( MislValue.NIL );
            }
        }
        
        context.leaveFunctionScope();
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return String.format( "%1$-10s %2$s", "RETURN", _returnCount );
    }
}