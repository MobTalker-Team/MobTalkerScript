package net.mobtalker.mobtalkerscript.api.library;

import static net.mobtalker.mobtalkerscript.v2.MtsCheck.*;
import static net.mobtalker.mobtalkerscript.v2.value.MtsValue.*;

import java.util.List;

import net.mobtalker.mobtalkerscript.v2.ScriptRuntimeException;
import net.mobtalker.mobtalkerscript.v2.value.*;
import net.mobtalker.mobtalkerscript.v2.value.userdata.MtsNativeFunction;

import com.google.common.collect.Lists;

public class InteractionCommandLib
{
    private IInteractionCommandLibLogic _logic;
    
    // ========================================
    
    public InteractionCommandLib( IInteractionCommandLibLogic logic )
    {
        _logic = logic;
    }
    
    // ========================================
    
    public IInteractionCommandLibLogic getLogic()
    {
        return _logic;
    }
    
    // ========================================
    
    @MtsNativeFunction
    public void showText( MtsValue argName, MtsValue argText, MtsValue argIsLast )
    {
        _logic.showText( checkString( argName, 0, "" ),
                         checkString( argText, 1 ),
                         isTrue( argIsLast ) );
    }
    
    @MtsNativeFunction
    public MtsValue showMenu( MtsVarArgs args )
    {
        String caption = checkString( args, 0, "" );
        MtsValue arg1 = args.get( 1 );
        
        if ( arg1.isNil() )
            throw new ScriptRuntimeException( "must provide at least one option" );
        
        if ( arg1.isTable() )
        {
            MtsTable argOptions = arg1.asTable();
            
            List<String> options = Lists.newArrayListWithCapacity( argOptions.listSize() );
            for ( MtsValue arg : argOptions.listView() )
            {
                options.add( checkString( arg ) );
            }
            
            int choice = _logic.showMenu( caption, options );
            return argOptions.get( valueOf( choice ) );
        }
        else
        {
            List<String> options = Lists.newArrayListWithCapacity( args.count() - 1 );
            for ( int i = 1; i < args.count(); i++ )
            {
                options.add( checkString( args, i ) );
            }
            
            return valueOf( _logic.showMenu( caption, options ) );
        }
    }
    
    @MtsNativeFunction
    public void showSprite( MtsValue argGroup,
                            MtsValue argSubPath,
                            MtsValue argPosition,
                            MtsValue argOffsetX,
                            MtsValue argOffsetY )
    {
        _logic.showSprite( checkString( argGroup, 0 ),
                           checkString( argSubPath, 1 ),
                           checkString( argPosition, 2, "bottom" ),
                           checkInteger( argOffsetX, 3, 0 ),
                           checkInteger( argOffsetY, 4, 0 ) );
    }
    
    @MtsNativeFunction
    public void showScene( MtsValue argPath, MtsValue argMode )
    {
        _logic.showScene( checkString( argPath, 0 ),
                          checkString( argMode, 1, "fill" ) );
    }
    
    @MtsNativeFunction
    public void hideTexture( MtsValue argGroup )
    {
        _logic.hideTexture( checkString( argGroup, 0 ) );
    }
}
