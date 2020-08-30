/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.value;

public abstract class MtsValueWithMetaTable extends MtsValue
{
    private MtsTable _metaTable;
    
    // ========================================
    
    @Override
    public boolean hasMetaTable()
    {
        return _metaTable != null;
    }
    
    @Override
    public MtsTable getMetaTable()
    {
        return _metaTable;
    }
    
    @Override
    public void setMetaTable( MtsValue t )
    {
        _metaTable = t.isTable() ? t.asTable() : null;
    }
    
    // ========================================
    
    @Override
    protected MtsValue getRaw( MtsValue key )
    {
        return Nil;
    }
}
