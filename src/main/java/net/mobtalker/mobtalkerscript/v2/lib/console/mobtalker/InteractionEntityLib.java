package net.mobtalker.mobtalkerscript.v2.lib.console.mobtalker;

import static net.mobtalker.mobtalkerscript.util.MtsCheck.*;
import net.mobtalker.mobtalkerscript.v2.lib.console.mobtalker.MobTalkerEntityLib.DummyEntity;
import net.mobtalker.mobtalkerscript.v2.value.MtsValue;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

public class InteractionEntityLib extends MobTalkerEntityLib<DummyEntity>
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
