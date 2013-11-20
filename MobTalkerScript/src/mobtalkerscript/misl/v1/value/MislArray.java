package mobtalkerscript.misl.v1.value;

/**
 * Lightweight version of {@link MislTable}, indexable only by Java integers. Used as varargs.
 * <p>
 * Only for VM internal use, should never be exposed to the user!
 */
public class MislArray extends MislValue
{
    private final MislValue[] _values;
    
    // ========================================
    
    /* package */MislArray( MislValue... values )
    {
        _values = values;
    }
    
    // ========================================
    
    /**
     * Returns the value at index <code>i</code> (zero based) or <code>nil</code>, if the index does not exist.
     */
    public MislValue get( int i )
    {
        return i < _values.length ? _values[i] : NIL;
    }
    
    public int getSize()
    {
        return _values.length;
    }
    
    // ========================================
    
    @Override
    public boolean isArray()
    {
        return true;
    }
    
    @Override
    public MislArray asArray()
    {
        return this;
    }
    
    // ========================================
    
    @Override
    public MislString toMtsString()
    {
        return valueOf( toString() );
    }
    
    @Override
    public String getTypeName()
    {
        return "array";
    }
    
    @Override
    public MislBoolean equal( MislValue x )
    {
        return valueOf( this == x );
    }
    
    // ========================================
    
    @Override
    public String toString()
    {
        return "array#" + Integer.toHexString( hashCode() );
    }
    
    @Override
    public int hashCode()
    {
        return _values.hashCode();
    }
    
}
