/* SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later */
package net.mobtalker.mobtalkerscript.util;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.notNull;

public final class ThrowableUtil
{
    public static RuntimeException throwUnchecked( Throwable t )
    {
        throw throwUnchecked( t, RuntimeException::new );
    }
    
    public static <T extends Throwable> RuntimeException throwUnchecked( T t, Function<? super T, ? extends RuntimeException> conversion )
    {
        notNull(t);
        notNull(conversion);
        if ( t instanceof RuntimeException )
        {
            throw (RuntimeException) t;
        }
        if ( t instanceof Error )
        {
            throw (Error) t;
        }
        throw conversion.apply( t );
    }
    
    // ========================================
    
    // private constructor to prevent instantiation
    private ThrowableUtil() {}
}
