/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.util;

import java.util.*;

import net.mobtalker.mobtalkerscript.v3.value.*;
import net.mobtalker.mobtalkerscript.v3.value.MtsTable.Entry;

import org.apache.commons.lang3.StringUtils;

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
        _cache = new HashSet<>();
        _indent = indent;
    }
    
    // ========================================
    
    public String print( MtsValue v, String name )
    {
        StringBuilder s = new StringBuilder();
        print( v, name, s, "" );
        return s.deleteCharAt( s.length() - 1 ).toString();
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
        else if ( v.isVarArgs() )
        {
            MtsVarargs args = v.asVarArgs();
            s.append( Arrays.toString( args.toArray() ) + "\n" );
        }
        else
        {
            s.append( v ).append( ";\n" );
        }
    }
    
    private void tablePrint( MtsTable t, StringBuilder s, String indent )
    {
        for ( Entry e : t )
        {
            tableEntryPrint( e.getKey(), e.getValue(), s, indent );
        }
    }
    
    private void tableEntryPrint( MtsValue k, MtsValue v, StringBuilder s, String indent )
    {
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
