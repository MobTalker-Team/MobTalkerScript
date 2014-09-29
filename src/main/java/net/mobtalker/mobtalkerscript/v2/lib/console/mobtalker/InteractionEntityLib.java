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
package net.mobtalker.mobtalkerscript.v2.lib.console.mobtalker;

import static net.mobtalker.mobtalkerscript.v2.MtsCheck.*;
import net.mobtalker.mobtalkerscript.v2.lib.console.mobtalker.AbstractConsoleEntityLib.DummyEntity;
import net.mobtalker.mobtalkerscript.v2.value.MtsValue;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

public class InteractionEntityLib extends AbstractConsoleEntityLib<DummyEntity>
{
    private final DummyEntity _entity;
    
    // ========================================
    
    public InteractionEntityLib()
    {
        _entity = new DummyEntity( "Creeper", 10.0F, 10.0F );
    }
    
    // ========================================
    
    @Override
    protected DummyEntity getEntity()
    {
        return _entity;
    }
    
    // ========================================
    
    @MtsNativeFunction
    public void setName( MtsValue arg1 )
    {
        String name = checkString( arg1, 0 );
        getEntity().setName( name );
    }
}
