package net.mobtalker.mobtalkerscript.api.library;

import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;
import net.mobtalker.mobtalkerscript.v2.value.*;

public class ScriptApiConstants
{
    public static final MtsValue KEY_ITEM_NAME = valueOf( "name" );
    public static final MtsValue KEY_ITEM_COUNT = valueOf( "count" );
    public static final MtsValue KEY_ITEM_META = valueOf( "meta" );
    
    public static final MtsString KEY_EFFECT_NAME = valueOf( "name" );
    public static final MtsString KEY_EFFECT_DURATION = valueOf( "duration" );
    public static final MtsString KEY_EFFECT_AMPLIFIER = valueOf( "amplifier" );
    
    public static final String ENTITYKEY_PLAYER = "player";
    public static final String ENTITYKEY_PREFIX_TYPE = "type:";
    
    // ========================================
    
    private ScriptApiConstants()
    {}
}
