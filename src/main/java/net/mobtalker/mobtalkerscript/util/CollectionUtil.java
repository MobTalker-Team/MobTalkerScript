/* SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later */
package net.mobtalker.mobtalkerscript.util;

import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

import static org.apache.commons.lang3.Validate.notNull;

public final class CollectionUtil
{
    public static <E> void forEachReverse( List<E> list, Consumer<? super E> action )
    {
        notNull( list );
        notNull( action );
        ListIterator<E> it = list.listIterator( list.size() );
        while ( it.hasPrevious() )
        {
            action.accept( it.previous() );
        }
    }
    
    // ========================================
    
    // private constructor to prevent instantiation
    private CollectionUtil() {}
}
