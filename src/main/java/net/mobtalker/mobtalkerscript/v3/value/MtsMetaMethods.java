/*
 * Copyright (C) 2013-2015 Chimaine
 * 
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mobtalker.mobtalkerscript.v3.value;

public final class MtsMetaMethods
{
    public static final MtsString __metatable = MtsString.of( "__metatable" );
    public static final MtsString __index = MtsString.of( "__index" );
    public static final MtsString __newindex = MtsString.of( "__newindex" );
    public static final MtsString __call = MtsString.of( "__call" );
    public static final MtsString __len = MtsString.of( "__len" );
    public static final MtsString __unm = MtsString.of( "__unm" );
    public static final MtsString __add = MtsString.of( "__add" );
    public static final MtsString __sub = MtsString.of( "__sub" );
    public static final MtsString __mul = MtsString.of( "__mul" );
    public static final MtsString __div = MtsString.of( "__div" );
    public static final MtsString __mod = MtsString.of( "__mod" );
    public static final MtsString __pow = MtsString.of( "__pow" );
    public static final MtsString __concat = MtsString.of( "__concat" );
    public static final MtsString __eq = MtsString.of( "__eq" );
    public static final MtsString __lt = MtsString.of( "__lt" );
    public static final MtsString __lte = MtsString.of( "__lte" );
    public static final MtsString __tostring = MtsString.of( "__tostring" );
    
    // ========================================
    
    private MtsMetaMethods()
    {}
}