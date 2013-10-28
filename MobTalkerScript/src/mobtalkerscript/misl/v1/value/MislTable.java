package mobtalkerscript.misl.v1.value;

import java.util.Map.Entry;
import java.util.*;

import mobtalkerscript.*;

import com.google.common.collect.*;

/**
 * A table is at its core an associative array. Values are stored and retrieved by keys, that are either {@link MislNumber
 * numbers} or {@link MislString strings}.
 * <p>
 * Keys are always ordered, numbers naturally and before strings, also naturally.
 * 
 * @author Chimaine
 * 
 */
public class MislTable extends MislValue
{
    
    private final TreeMap<MislValue, MislValue> _table;
    private boolean _readonly;
    
    // ========================================
    
    /**
     * Creates a new Table.
     * 
     * @param initialCapacity Currently ignored.
     */
    public MislTable(int initialCapacity)
    {
        _table = Maps.newTreeMap();
    }
    
    // ========================================
    
    public MislValue set(MislValue key, MislValue value)
    {
        if (_readonly)
        {
            throw new ScriptRuntimeException("%s is readonly", toString());
        }
        
        if (!(key.isNumber() || key.isString()))
        {
            throw new ScriptRuntimeException("Can only use numbers and strings as keys, got %s",
                                             key.getTypeName());
        }
        
        MislValue removed;
        
        if (value.isNil())
        {
            removed = _table.remove(key);
        }
        else
        {
            removed = _table.put(key, value);
        }
        
        return removed;
    }
    
    public MislValue set(String key, MislValue value)
    {
        return set(valueOf(key), value);
    }
    
    public MislValue remove(MislValue key)
    {
        return set(key, NIL);
    }
    
    public MislValue remove()
    {
        return remove(_table.lastKey());
    }
    
    public MislValue get(MislValue key)
    {
        if (!(key.isNumber() || key.isString()))
        {
            throw new ScriptRuntimeException("Can only use numbers and strings as keys, got %s",
                                             key.getTypeName());
        }
        
        MislValue result = _table.get(key);
        return result == null ? NIL : result;
    }
    
    public MislValue get(String key)
    {
        return get(valueOf(key));
    }
    
    public MislNumber getSize()
    {
        return valueOf(_table.size());
    }
    
    public MislNumber getNextIndex()
    {
        MislValue highestNum = _table.lowerKey(MislNumber.MAX_VALUE);
        
        if ((highestNum == null) || !highestNum.isNumber())
        {
            return valueOf(1.0D);
        }
        else
        {
            MislNumber nextIndex = highestNum.asNumber().incr();
            return valueOf(Math.floor(nextIndex.toJava()));
        }
    }
    
    public MislValue getRandomElement(Random rnd)
    {
        Object[] values = _table.values().toArray();
        Object value = values[rnd.nextInt(values.length)];
        
        return (MislValue) value;
    }
    
    // ========================================
    
    public Set<MislValue> getKeys()
    {
        return _table.keySet();
    }
    
    public Entry<MislValue, MislValue> getEntryAfter(MislValue key)
    {
        return _table.higherEntry(key);
    }
    
    public Set<Entry<MislValue, MislValue>> getEntries()
    {
        return _table.entrySet();
    }
    
    // ========================================
    
    public boolean getReadonly()
    {
        return _readonly;
    }
    
    public void setReadonly(boolean readonly)
    {
        _readonly = readonly;
    }
    
    // ========================================
    
    @Override
    public MislTable asTable()
    {
        return this;
    }
    
    // ========================================
    
    @Override
    public MislString toMtsString()
    {
        return valueOf(toString());
    }
    
    // ========================================
    
    @Override
    public boolean isTable()
    {
        return true;
    }
    
    @Override
    public String getTypeName()
    {
        return "table";
    }
    
    @Override
    public MislBoolean equal(MislValue x)
    {
        return valueOf(this == x);
    }
    
    // ========================================
    
    public SortedMap<MislValue, MislValue> toJava()
    {
        return Maps.newTreeMap(_table);
    }
    
    @Override
    public String toString()
    {
        return "table#" + Integer.toHexString(hashCode());
    }
    
    @Override
    public boolean equals(Object obj)
    {
        return this == obj;
    }
    
    @Override
    public int hashCode()
    {
        return _table.hashCode();
    }
    
}
