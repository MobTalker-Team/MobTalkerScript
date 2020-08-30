/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
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
