package net.mobtalker.mobtalkerscript.v3.instruction;

import java.io.*;

import net.mobtalker.mobtalkerscript.v3.MtsFrame;

public class InstrVarargs extends MtsInstruction
{
    private final int _count;
    
    // ========================================
    
    /* package */InstrVarargs( int count )
    {
        assert count > 0;
        _count = count;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        if ( _count == -1 )
            frame.pushVarargs();
        else
            frame.pushVarargs( _count );
    }
    
    @Override
    public int stackSizeChange()
    {
        return _count;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "VARARGS " + _count;
    }
    
    @Override
    public void writeTo( DataOutputStream stream ) throws IOException
    {
        stream.writeByte( Instructions.VARARG );
        stream.writeByte( _count );
    }
}
