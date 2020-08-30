/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.value.userdata;

import java.lang.annotation.*;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.METHOD )
@Inherited
public @interface MtsNativeFunction
{
    String value() default "";
}
