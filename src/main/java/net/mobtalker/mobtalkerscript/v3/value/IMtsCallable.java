/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.value;

@FunctionalInterface
public interface IMtsCallable
{
    MtsVarargs call( MtsVarargs args );
    
    // ========================================
    
    default MtsVarargs call()
    {
        return call( MtsVarargs.Empty );
    }
    
    default MtsVarargs call( MtsValue arg )
    {
        return call( MtsVarargs.of( arg ) );
    }
    
    default MtsVarargs call( MtsValue... args )
    {
        return call( MtsVarargs.of( args ) );
    }
}
