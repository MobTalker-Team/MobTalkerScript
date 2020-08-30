/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.value;

import java.util.Iterator;
import java.util.ArrayList;

/* package */class EmptyVarargs extends MtsVarargs
{
    @Override
    public MtsVarargs subArgs( int start, int end )
    {
        return this;
    }
    
    @Override
    public MtsValue get( int i )
    {
        return MtsValue.Nil;
    }
    
    @Override
    public int count()
    {
        return 0;
    }
    
    @Override
    public boolean isEmpty()
    {
        return true;
    }
    
    @Override
    public MtsValue[] toArray()
    {
        return new MtsValue[0];
    }
    
    @Override
    public Iterator<MtsValue> iterator()
    {
        return new ArrayList<MtsValue>().iterator();
    }
}
