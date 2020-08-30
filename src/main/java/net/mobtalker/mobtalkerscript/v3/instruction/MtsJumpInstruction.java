/*
 * SPDX-FileCopyrightText: 2013-2020 Chimaine, MobTalkerScript contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package net.mobtalker.mobtalkerscript.v3.instruction;

import net.mobtalker.mobtalkerscript.v3.MtsFrame;

public abstract class MtsJumpInstruction extends MtsInstruction
{
    private int _distance;
    
    // ========================================
    
    protected MtsJumpInstruction()
    {
        this( 0 );
    }
    
    protected MtsJumpInstruction( int distance )
    {
        _distance = distance;
    }
    
    // ========================================
    
    @Override
    public void execute( MtsFrame frame )
    {
        frame.setInstructionPointer( ( frame.getInstructionPointer() + getDistance() ) - 1 );
    }
    
    // ========================================
    
    public int getDistance()
    {
        return _distance;
    }
    
    public MtsJumpInstruction setDistance( int distance )
    {
        assert distance != 0 : "target can not be self (infinite loop)";
        
        _distance = distance;
        return this;
    }
    
    public MtsJumpInstruction setTarget( int sourceIndex, int targetIndex )
    {
        return setDistance( targetIndex - sourceIndex );
    }
}
