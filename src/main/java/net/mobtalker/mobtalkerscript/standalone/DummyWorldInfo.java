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
package net.mobtalker.mobtalkerscript.standalone;

import net.mobtalker.mobtalkerscript.api.IWorldInfo;

public class DummyWorldInfo implements IWorldInfo
{
    private int _worldID = 0;
    private String _worldName = "Overworld";
    private String _worldType = "default";
    private int _worldTime = 6000;
    private boolean _isDaytime = true;
    private int _moonphase = 0;
    
    // ========================================
    
    @Override
    public int getID()
    {
        return _worldID;
    }
    
    @Override
    public String getName()
    {
        return _worldName;
    }
    
    @Override
    public String getType()
    {
        return _worldType;
    }
    
    @Override
    public long getTime()
    {
        return _worldTime;
    }
    
    @Override
    public boolean isDaytime()
    {
        return _isDaytime;
    }
    
    @Override
    public int getMoonphase()
    {
        return _moonphase;
    }
}
