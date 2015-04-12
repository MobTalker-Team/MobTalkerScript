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
package net.mobtalker.mobtalkerscript.api.library;

import net.mobtalker.mobtalkerscript.api.InventoryInfo;

public interface IPlayerLibLogic
{
    /**
     * @return <code>survival</code>, <code>adventure</code> or <code>creative</code>
     */
    String getGameMode();

    // ========================================

    /**
     * Total armor damage reduction
     */
    double getArmor();

    // ========================================

    /**
     * Current experience level
     */
    int getExperienceLevel();

    /**
     * Current experience progress on the current level
     */
    int getExperience();

    /**
     * Experience required to reach the next level
     */
    int getExperienceRequired();

    /**
     * Give the specific amount of experience
     */
    void giveExperience( int amount );

    /**
     * Tries to take that much experience
     */
    boolean takeExperience( int amount );

    // ========================================

    boolean isValidItem( String name );

    int getItemCount( String name, int meta );

    boolean takeItems( String name, int amount, int meta );

    boolean giveItems( String name, int amount, int meta );

    InventoryInfo getInventory();
}