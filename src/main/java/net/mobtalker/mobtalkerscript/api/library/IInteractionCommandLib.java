package net.mobtalker.mobtalkerscript.api.library;

import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

public interface IInteractionCommandLib
{
    @MtsNativeFunction
    void showText( MtsValue argTalker, MtsValue argText, MtsValue argIsLast );
    
    @MtsNativeFunction
    MtsValue showMenu( MtsVarArgs args );
    
    @MtsNativeFunction
    void showSprite( MtsValue argGroup, MtsValue argSubPath, MtsValue argPosition, MtsValue argOffsetX, MtsValue argOffsetY );
    
    @MtsNativeFunction
    void showScene( MtsValue argPath, MtsValue argMode );
    
    @MtsNativeFunction
    void hideTexture( MtsValue argGroup );
}