/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.value.userdata;

import java.lang.reflect.Method;

public class AnnotatedMethod
{
    private final Method _method;
    private final String _name;
    
    // ========================================
    
    public AnnotatedMethod( Method method, String name )
    {
        _method = method;
        _name = name;
    }
    
    // ========================================
    
    public Method getMethod()
    {
        return _method;
    }
    
    public String getName()
    {
        return _name;
    }
}
