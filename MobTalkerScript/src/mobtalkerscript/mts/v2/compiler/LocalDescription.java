package mobtalkerscript.mts.v2.compiler;

import mobtalkerscript.mts.v2.*;

public class LocalDescription extends VariableDescription
{
    private final int _start;
    private int _end;
    
    public LocalDescription( String name, int index, int start )
    {
        super( name, index );
        _start = start;
    }
    
    public int getStart()
    {
        return _start;
    }
    
    public int getEnd()
    {
        return _end;
    }
    
    public void setEnd( int end )
    {
        _end = end;
    }
}
