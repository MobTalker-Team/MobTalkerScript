package mobtalkerscript.misl.v1.value;

public class MislString extends MislValue
{
    
    public static MislString concat(MislValue... values)
    {
        StringBuilder sb = new StringBuilder();
        for (MislValue value : values)
        {
            sb.append(value.toMtsString().toJava());
        }
        return valueOf(sb.toString());
    }
    
    // ========================================
    
    private final String _value;
    
    // ========================================
    
    MislString(String value)
    {
        _value = value;
    }
    
    // ========================================
    
    public MislString concat(MislValue x)
    {
        return valueOf(_value.concat(x.toMtsString().toJava()));
    }
    
    public MislNumber getLength()
    {
        return valueOf(_value.length());
    }
    
    // ========================================
    
    @Override
    public MislString asString()
    {
        return this;
    }
    
    // ========================================
    
    @Override
    public MislString toMtsString()
    {
        return this;
    }
    
    // ========================================
    
    @Override
    public boolean isString()
    {
        return true;
    }
    
    @Override
    public String getTypeName()
    {
        return "string";
    }
    
    @Override
    public MislBoolean equal(MislValue x)
    {
        return x.isString() ? valueOf(_value.equals(x.asString().toJava())) : FALSE;
    }
    
    // ========================================
    
    public String toJava()
    {
        return _value;
    }
    
    @Override
    public String toString()
    {
        return "\"" + _value + "\"";
    }
    
    @Override
    public int hashCode()
    {
        return _value.hashCode();
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof MislString)
        {
            return ((MislString) obj).toJava().equals(_value);
        }
        
        return false;
    }
    
    @Override
    public int compareTo(MislValue o)
    {
        if (o.isNumber())
        {
            return -1;
        }
        else if (!o.isString())
        {
            return 1;
        }
        
        MislString str = o.asString();
        return _value.compareTo(str._value);
    }
    
}
