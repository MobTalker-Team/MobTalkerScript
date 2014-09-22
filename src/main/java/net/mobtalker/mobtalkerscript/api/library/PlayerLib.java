package net.mobtalker.mobtalkerscript.api.library;

import static net.mobtalker.mobtalkerscript.v2.MtsCheck.*;
import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;
import net.mobtalker.mobtalkerscript.v2.BadArgumentException;
import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

public final class PlayerLib
{
    private final IPlayerLibLogic _logic;
    
    // ========================================
    
    public PlayerLib( IPlayerLibLogic logic )
    {
        _logic = logic;
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsString getGameMode()
    {
        return valueOf( _logic.getGameMode() );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsNumber getArmor()
    {
        return valueOf( _logic.getArmor() );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsVarArgs getExperience()
    {
        return MtsVarArgs.of( valueOf( _logic.getExperienceLevel() ),
                              valueOf( _logic.getExperience() ),
                              valueOf( _logic.getExperienceRequired() ) );
    }
    
    @MtsNativeFunction
    public void giveExperience( MtsValue argAmount )
    {
        _logic.giveExperience( checkInteger( argAmount, 0 ) );
    }
    
    // ========================================
    
    @MtsNativeFunction
    public MtsNumber getItemCount( MtsValue argItemName, MtsValue argItemMeta )
    {
        return valueOf( _logic.getItemCount( checkString( argItemName, 0 ),
                                             checkInteger( argItemMeta, 1, -1 ) ) );
    }
    
    @MtsNativeFunction
    public MtsBoolean takeItems( MtsValue argItemName, MtsValue argItemCount, MtsValue argItemMeta )
    {
        int amount = checkInteger( argItemCount, 1, 1 );
        if ( amount <= 0 )
            throw new BadArgumentException( "amount must be greater than zero (was '%s')", amount );
        
        return valueOf( _logic.takeItems( checkString( argItemName, 0 ),
                                          amount,
                                          checkInteger( argItemMeta, 2, -1 ) ) );
    }
    
    @MtsNativeFunction
    public MtsBoolean giveItems( MtsValue argItemName, MtsValue argItemCount, MtsValue argItemMeta )
    {
        int amount = checkInteger( argItemCount, 1, 1 );
        if ( amount <= 0 )
            throw new BadArgumentException( "amount must be greater than zero (was '%s')", amount );
        
        return valueOf( _logic.giveItems( checkString( argItemName, 0 ),
                                          amount,
                                          checkInteger( argItemMeta, 2 ) ) );
    }
    
    @MtsNativeFunction
    public MtsTable getInventory()
    {
        InventoryInfo inventory = _logic.getInventory();
        
        MtsTable t = new MtsTable( inventory.count(), 0 );
        for ( ItemInfo item : inventory.getItems() )
        {
            MtsTable info = new MtsTable( 0, 3 );
            info.set( "name", valueOf( item.getName() ) );
            info.set( "meta", valueOf( item.getMeta() ) );
            info.set( "count", valueOf( inventory.getAmount( item ) ) );
            t.add( info );
        }
        
        return t;
    }
}
