/*
 * Copyright (C) 2013-2014 Chimaine
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mobtalker.mobtalkerscript.v2.compiler;

/**
 * Stores information about a compiling loop block, e.g. references to different instructions.
 */
public class LoopState
{
    private final int _firstInstrIndex;
    private final PendingJumpCollection _breaks;
    
    // ========================================
    
    public LoopState( int firstInstrIndex )
    {
        _firstInstrIndex = firstInstrIndex;
        _breaks = new PendingJumpCollection();
    }
    
    // ========================================
    
    public int firstIndex()
    {
        return _firstInstrIndex;
    }
    
    public void addBreak( PendingJump jump )
    {
        _breaks.add( jump );
    }
    
    public void setBreakTarget( int target )
    {
        _breaks.setTarget( target );
    }
}
