package mobtalkerscript.mts.v2.value;

public class MtsNil extends MtsValue
{
    private final MtsString stringValue;
    
    // ========================================
    
    /* package */MtsNil()
    {
        stringValue = MtsValue.valueOf( "nil" );
    }
    
    // ========================================
    
    @Override
    public boolean isNil()
    {
        return true;
    }
    
    @Override
    public MtsType getType()
    {
        return MtsType.NIL;
    }
    
    // ========================================
    
    @Override
    public MtsString toMtsString()
    {
        return stringValue;
    }
    
    @Override
    public MtsBoolean toMtsBoolean()
    {
        return FALSE;
    }
    
    public Object toJavaValue()
    {
        return null;
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "nil";
    }
    
    @Override
    public int hashCode()
    {
        return 0;
    }
    
    @Override
    public boolean equals( Object obj )
    {
        return obj == this;
    }
    
}
