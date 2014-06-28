package mobtalkerscript.v2.instruction;

import static mobtalkerscript.v2.value.MtsValue.*;
import mobtalkerscript.v2.value.*;

public final class FrameValue
{
    private MtsValue _value;
    
    // ========================================
    
    public FrameValue()
    {
        this( NIL );
    }
    
    public FrameValue( MtsValue value )
    {
        _value = value;
    }
    
    // ========================================
    
    public MtsValue get()
    {
        return _value;
    }
    
    public void set( MtsValue value )
    {
        _value = value;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return _value.toString();
    }
}
