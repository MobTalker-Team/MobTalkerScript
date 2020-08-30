/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3;

import net.mobtalker.mobtalkerscript.v3.value.MtsType;

public class MtsLengthException extends MtsRuntimeException
{
    public MtsLengthException( MtsType a )
    {
        super( "attempt to get length of a %s value", a );
    }
}
