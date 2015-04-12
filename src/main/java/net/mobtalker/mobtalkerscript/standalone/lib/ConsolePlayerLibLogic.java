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
package net.mobtalker.mobtalkerscript.standalone.lib;

import net.mobtalker.mobtalkerscript.api.InventoryInfo;
import net.mobtalker.mobtalkerscript.api.library.IPlayerLibLogic;
import net.mobtalker.mobtalkerscript.standalone.DummyPlayer;

public class ConsolePlayerLibLogic implements IPlayerLibLogic
{
    private final DummyPlayer _player;

    // ========================================

    public ConsolePlayerLibLogic( DummyPlayer player )
    {
        _player = player;
    }

    // ========================================

    @Override
    public String getGameMode()
    {
        return _player.getGameMode();
    }

    @Override
    public double getArmor()
    {
        // TODO
        return 0;
    }

    @Override
    public int getExperienceLevel()
    {
        // TODO
        return 0;
    }

    @Override
    public int getExperience()
    {
        // TODO
        return 0;
    }

    @Override
    public int getExperienceRequired()
    {
        // TODO
        return 0;
    }

    @Override
    public void giveExperience( int amount )
    {
        // TODO
    }

    @Override
    public boolean takeExperience( int amount )
    {
        // TODO
        return true;
    }

    @Override
    public boolean isValidItem( String name )
    {
        // TODO
        return true;
    }

    @Override
    public int getItemCount( String name, int meta )
    {
        // TODO
        return 64;
    }

    @Override
    public boolean takeItems( String name, int amount, int meta )
    {
        // TODO
        return true;
    }

    @Override
    public boolean giveItems( String name, int amount, int meta )
    {
        // TODO
        return true;
    }

    @Override
    public InventoryInfo getInventory()
    {
        // TODO
        return new InventoryInfo();
    }
}
