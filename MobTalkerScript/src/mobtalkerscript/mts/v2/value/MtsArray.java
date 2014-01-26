package mobtalkerscript.mts.v2.value;

import java.util.*;

/**
 * Lightweight version of {@link MtsTable}, indexable only by Java integers. Used as varargs.
 * <p>
 * Only for VM internal use, should never be exposed to the user!
 */
public class MtsArray extends MtsValue
{
    private final MtsValue[] _values;
    
    // ========================================
    
    /* package */MtsArray( MtsValue... values )
    {
        _values = values;
    }
    
    // ========================================
    
    /**
     * Returns the value at index <code>i</code> (zero based) or <code>nil</code>, if the index does not exist.
     */
    @Override
    public MtsValue get( int i )
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
    public MtsArray asArray()
    {
        return this;
    }
    
    @Override
    public MtsType getType()
    {
        return MtsType.VARARGS;
    }
    
    // ========================================
    
    @Override
    public int hashCode()
    {
        return _values.hashCode();
    }
    
    @Override
    public String toString()
    {
        return getType() + Arrays.toString( _values );
    }
}
