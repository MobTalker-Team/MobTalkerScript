package mobtalkerscript.mts.v2.instruction;

import static mobtalkerscript.mts.v2.value.MtsValue.*;
import mobtalkerscript.mts.v2.value.*;

public final class FrameValue
{
    private MtsValue _value;
    
    public FrameValue()
    {
        this( NIL );
    }
    
    public FrameValue( MtsValue value )
    {
        _value = value;
    }
    
    public MtsValue get()
    {
        return _value;
    }
    
    public void set( MtsValue value )
    {
        _value = value;
    }
}
