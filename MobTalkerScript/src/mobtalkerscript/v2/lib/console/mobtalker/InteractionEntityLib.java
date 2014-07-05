package mobtalkerscript.v2.lib.console.mobtalker;

import static mobtalkerscript.util.MtsCheck.*;
import mobtalkerscript.v2.lib.console.mobtalker.MobTalkerEntityLib.DummyEntity;
import mobtalkerscript.v2.value.*;
import mobtalkerscript.v2.value.userdata.*;

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
