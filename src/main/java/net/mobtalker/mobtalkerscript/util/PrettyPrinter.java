package net.mobtalker.mobtalkerscript.util;

import java.util.Set;

import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.MtsTable.Entry;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Sets;

public class PrettyPrinter
{
    private final Set<MtsValue> _cache;
    private final String _indent;
    
    // ========================================

    public PrettyPrinter()
    {
        this( "    " );
    }
    
    public PrettyPrinter( String indent )
    {
        _cache = Sets.newHashSet();
        _indent = indent;
    }
    
    // ========================================

    public String print( MtsValue v, String name )
    {
        StringBuilder s = new StringBuilder();
        print( v, name, s, "" );
        return s.toString();
    }
    
    private void print( MtsValue v, String name, StringBuilder s, String indent )
    {
        s.append( indent );

        if ( !StringUtils.isEmpty( name ) )
        {
            s.append( name ).append( " = " );
        }

        if ( v.isTable() )
        {
            MtsTable t = v.asTable();
            _cache.add( t );
            
            if ( t.isEmpty() )
            {
                s.append( "{};\n" );
            }
            else
            {
                s.append( "{\n" );
                tablePrint( t, s, indent + _indent );
                s.append( indent ).append( "};\n" );
            }
        }
        else
        {
            s.append( v ).append( ";\n" );
        }
    }
    
    private void tablePrint( MtsTable t, StringBuilder s, String indent )
    {
        for ( Entry entry : t.entryView() )
        {
            MtsValue k = entry.getKey();
            MtsValue v = entry.getValue();
            
            if ( v.isTable() && _cache.contains( v ) )
            {
                s.append( indent )
                 .append( '[' ).append( k ).append( "] = " )
                 .append( v ).append( ";\n" );
            }
            else
            {
                if ( v.isTable() )
                {
                    _cache.add( v );
                }

                print( v, "[" + k + "]", s, indent );
            }
        }
    }
}
