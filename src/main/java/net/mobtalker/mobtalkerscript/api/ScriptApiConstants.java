package net.mobtalker.mobtalkerscript.api;

import net.mobtalker.mobtalkerscript.v3.value.MtsString;

public class ScriptApiConstants
{
    public static final MtsString KEY_ITEM_NAME = MtsString.of( "name" );
    public static final MtsString KEY_ITEM_COUNT = MtsString.of( "count" );
    public static final MtsString KEY_ITEM_META = MtsString.of( "meta" );
    
    public static final MtsString KEY_EFFECT_NAME = MtsString.of( "name" );
    public static final MtsString KEY_EFFECT_DURATION = MtsString.of( "duration" );
    public static final MtsString KEY_EFFECT_AMPLIFIER = MtsString.of( "amplifier" );
    
    public static final MtsString KEY_SCOREBOARD_TEAM_COLOR = MtsString.of( "color" );
    public static final MtsString KEY_SCOREBOARD_TEAM_FRIENDLYFIRE = MtsString.of( "friendlyFire" );
    public static final MtsString KEY_SCOREBOARD_TEAM_SEEFIRENDLYINVISIBLES = MtsString.of( "seeFriendlyInvisibles" );
    
    public static final MtsString KEY_VILLAGER_RECIPE_OFFER = MtsString.of( "offer" );
    public static final MtsString KEY_VILLAGER_RECIPE_PRICE = MtsString.of( "price" );
    
    public static final String ENTITYKEY_PLAYER = "player";
    public static final String ENTITYKEY_PREFIX_TYPE = "type:";
    
    // ========================================
    
    private ScriptApiConstants()
    {}
}
