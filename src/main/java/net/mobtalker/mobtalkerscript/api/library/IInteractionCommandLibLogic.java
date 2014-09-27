package net.mobtalker.mobtalkerscript.api.library;

import java.util.List;

public interface IInteractionCommandLibLogic
{
    
    void showText( String character, String text, boolean isLast );
    
    int showMenu( String caption, List<String> options );
    
    void showSprite( String group, String path, String screenPositon, int screenOffsetX, int screenOffsetY );
    
    void showScene( String path, String mode );
    
    void hideTexture( String group );
    
}
