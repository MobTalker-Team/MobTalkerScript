/*
 * Copyright (C) 2013-2014 Chimaine
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
package net.mobtalker.mobtalkerscript.v3.instruction;

import java.io.*;

import net.mobtalker.mobtalkerscript.v3.*;
import net.mobtalker.mobtalkerscript.v3.value.*;

public abstract class MtsInstruction
{
    public static MtsValue getMetaTag( MtsValue a, MtsValue b, MtsString key )
    {
        MtsValue tag = a.getMetaTag( key );
        return tag.isNil() ? b.getMetaTag( key ) : tag;
    }
    
    // ========================================
    
    protected MtsInstruction()
    {}
    
    // ========================================
    
    public abstract void execute( MtsFrame frame );
    
    /**
     * If this instruction causes the current frame to exit after its execution.
     */
    public boolean exits()
    {
        return false;
    }
    
    /**
     * The amount of elements this instruction adds (positive integer) or removes (negative integer) from the stack. Used to
     * calculate the maximum stack size.
     * <p>
     * If there are multiple possibilities, the highest possible positive integer is choosen.
     */
    public abstract int stackSizeChange();
    
    // ========================================
    
    @Override
    public abstract String toString();
    
    public String toString( MtsFunctionPrototype proto )
    {
        return toString();
    }
    
    // ========================================
    
    public abstract void writeTo( DataOutputStream stream ) throws IOException;
}
