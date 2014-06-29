package net.mobtalker.mobtalkerscript.v2.instruction;

import net.mobtalker.mobtalkerscript.v2.value.*;

public final class InstrTailcall extends MtsInstruction
{
    private final int _nArgs;
    private final int _nReturn;
    
    // ========================================
    
    /* package */InstrTailcall( int nArgs, int nReturn )
    {
        _nArgs = nArgs;
        _nReturn = nReturn;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        MtsValue[] args = new MtsValue[_nArgs];
        for ( int i = _nArgs - 1; i >= 0; i-- )
        {
            args[i] = frame.pop();
        }
        
        MtsValue target = frame.pop();
        
        frame.push( new MtsTailcall( target, MtsVarArgs.of( args ), _nReturn ) );
    }
    
    @Override
    public boolean exits()
    {
        return true;
    }
    
    @Override
    public int stackSizeChange()
    {
        return 1 - _nReturn;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "TAILCALL " + _nArgs + " " + _nReturn;
    }
}
