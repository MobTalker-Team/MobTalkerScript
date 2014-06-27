package mobtalkerscript.v2.lib;

import static mobtalkerscript.util.MtsCheck.*;
import static mobtalkerscript.v2.value.MtsValue.*;

import java.util.*;

import mobtalkerscript.v2.value.*;
import mobtalkerscript.v2.value.userdata.*;

public final class MtsTableLib
{
    @MtsNativeFunction
    public static MtsString concat( MtsValue arg1, MtsValue arg2, MtsValue arg3, MtsValue arg4 )
    {
        MtsTable table = checkTable( arg1, 0 );
        String sep = arg2.isNil() ? "" : checkString( arg2, 1 );
        
        if ( arg3.isNil() )
            return table.concatList( sep );
        
        int from = checkInteger( arg3, 2 );
        
        if ( arg4.isNil() )
            return table.concatList( sep, from );
        
        int to = checkInteger( arg4, 3 );
        
        return table.concatList( sep, from, to );
    }
    
    @MtsNativeFunction
    public static MtsNumber count( MtsValue arg1 )
    {
        return valueOf( checkTable( arg1, 0 ).count() );
    }
    
    @MtsNativeFunction
    public static void insert( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
    {
        MtsTable table = checkTable( arg1, 0 );
        
        if ( arg3.isNil() )
            table.add( arg2 );
        
        checkNumber( arg2, 1 );
        
        table.insert( arg2.asNumber(), arg3 );
    }
    
    private static final Random _rnd = new Random();
    
    @MtsNativeFunction
    public static MtsValue random( MtsVarArgs args )
    {
        if ( args.isEmpty() )
            return NIL;
        
        if ( args.count() > 1 )
            return args.get( _rnd.nextInt( args.count() ) );
        
        MtsValue arg1 = args.get( 0 );
        if ( arg1.isTable() )
        {
            MtsTable t = arg1.asTable();
            
            if ( t.listSize() == 0 )
                return NIL;
            
            MtsNumber k = valueOf( _rnd.nextInt( t.listSize() ) + 1 );
            return t.get( k );
        }
        
        return arg1;
    }
    
    // ========================================
    
    @MtsNativeFunction
    public static MtsValue remove( MtsValue arg1, MtsValue arg2 )
    {
        MtsTable table = checkTable( arg1, 0 );
        
        if ( arg2.isNil() )
            return table.removeLast();
        
        return table.remove( checkInteger( arg2, 1 ) );
    }
}
