/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.util;

import java.util.HashMap;
import java.util.Map;

import net.mobtalker.mobtalkerscript.v3.value.*;

public class TableCloner
{
    private final Map<MtsTable, MtsTable> _cache;
    
    // ========================================
    
    {
        _cache = new HashMap<>();
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
                clone = new MtsTable( t );
                _cache.put( t, clone );
            }
            
            return clone;
        }
        else
        {
            return o;
        }
    }
}
