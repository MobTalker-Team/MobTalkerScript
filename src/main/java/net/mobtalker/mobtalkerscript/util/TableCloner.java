package net.mobtalker.mobtalkerscript.util;

import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;

import java.util.Map;

import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.MtsTable.Entry;

import com.google.common.collect.Maps;

public class TableCloner
{
    private final Map<MtsTable, MtsTable> _cache;
    
    // ========================================
    
    {
        _cache = Maps.newIdentityHashMap();
    }
    
    // ========================================
    
    public MtsValue clone( MtsValue o )
    {
        if ( o.isTable() )
        {
            MtsTable t = o.asTable();
            
            MtsTable clone = _cache.get( t );
            
            if ( clone == null )
            {
                clone = new MtsTable( t.listSize(), t.tableSize() );
                _cache.put( t, clone );
            }
            
            MtsValue k = NIL;
            Entry entry;
            while ( ( entry = t.getNext( k ) ) != null )
            {
                clone.set( clone( k = entry.getKey() ), clone( entry.getValue() ) );
            }
            
            return clone;
        }
        else
        {
            return o;
        }
    }
}
