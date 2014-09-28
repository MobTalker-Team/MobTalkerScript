package net.mobtalker.mobtalkerscript.api.library;

import static net.mobtalker.mobtalkerscript.api.ScriptApiCheck.*;
import static net.mobtalker.mobtalkerscript.v2.MtsCheck.*;
import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;
import net.mobtalker.mobtalkerscript.api.*;
import net.mobtalker.mobtalkerscript.v2.BadArgumentException;
import net.mobtalker.mobtalkerscript.v2.value.MtsValue;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

public class CreatureLib
{
    private ICreatureLibLogic _logic;
    
    // ========================================
    
    public CreatureLib( ICreatureLibLogic logic )
    {
        _logic = logic;
    }
    
    // ========================================
    
    public ICreatureLibLogic getLogic()
    {
        return _logic;
    }
    
    // ========================================
    
    @MtsNativeFunction
    public void setName( MtsValue argName )
    {
        _logic.setName( checkString( argName, 0 ) );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public void setEquipment( MtsValue argSlot, MtsValue argItemName, MtsValue argMeta )
    {
        String slotName = checkString( argSlot, 0 );
        EquipmentSlot slot = EquipmentSlot.forName( slotName );
        if ( slot == null )
            throw new BadArgumentException( 0, "'%s' is not a valid equipment slot", slotName );
        
        String itemName = checkString( argItemName, 1 );
        if ( !_logic.isValidItem( itemName ) )
            throw new BadArgumentException( 1, "unknown item name '%s'", itemName );
        
        int meta = checkIntegerWithMinimum( argMeta, 2, 0, 0 );
        
        _logic.setEquipment( slot, new ItemInfo( itemName, meta ) );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsValue getReaction( MtsValue argKey )
    {
        return valueOf( _logic.getReaction( checkString( argKey, 0 ) ).getName() );
    }
    
    @MtsNativeFunction
    public void setReaction( MtsValue argKey, MtsValue argReaction )
    {
        _logic.setReaction( checkString( argKey, 0 ),
                            checkReaction( argReaction, 1 ) );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public void follow( MtsValue argKey )
    {
        _logic.follow( checkString( argKey, 0 ) );
    }
    
    @MtsNativeFunction
    public void stopFollowing()
    {
        _logic.stopFollowing();
    }
}
