/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3;

import net.mobtalker.mobtalkerscript.v3.value.MtsType;

public class MtsComparisonException extends MtsRuntimeException
{
    public MtsComparisonException( MtsType a, MtsType b )
    {
        super( "attempt to compare %s with %s", a, b );
    }
}
