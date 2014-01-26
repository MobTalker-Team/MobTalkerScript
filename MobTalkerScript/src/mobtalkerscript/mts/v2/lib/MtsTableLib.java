package mobtalkerscript.mts.v2.lib;

import mobtalkerscript.mts.v2.value.*;

public class MtsTableLib extends MtsLibrary
{
    
    @Override
    public MtsValue bind( MtsString name, MtsValue env )
    {
        MtsTable lib = new MtsTable( 1, 5 );
        lib.set( "Concat", new Concat() );
        lib.set( "Count", new Count() );
        lib.set( "Insert", new Insert() );
        lib.set( "Random", new Random() );
        lib.set( "Remove", new Remove() );
        
        env.set( "table", lib );
        return NIL;
    }
    
    // ========================================
    
    private static final class Concat extends MtsFourArgFunction
    {
        @Override
        public MtsValue call( MtsValue arg1, MtsValue arg2 )
        {
            checkTable( arg1, "Concat", 1 );
            
            if ( arg2.isNil() )
                arg2 = EMPTY_STRING;
            
            checkString( arg2, "Concat", 2 );
            
            return arg1.asTable().concat( arg2.asString() );
        }
        
        @Override
        public MtsValue call( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
        {
            checkTable( arg1, "Concat", 1 );
            
            if ( arg2.isNil() )
                arg2 = EMPTY_STRING;
            
            checkString( arg2, "Concat", 2 );
            checkNumber( arg3, "Concat", 3 );
            
            return arg1.asTable().concat( arg2.asString(), arg3.asNumber() );
        }
        
        @Override
        public MtsValue call( MtsValue arg1, MtsValue arg2, MtsValue arg3, MtsValue arg4 )
        {
            checkTable( arg1, "Concat", 1 );
            
            if ( arg2.isNil() )
                arg2 = EMPTY_STRING;
            
            checkString( arg2, "Concat", 2 );
            checkNumber( arg3, "Concat", 3 );
            checkNumber( arg4, "Concat", 4 );
            
            return arg1.asTable().concat( arg2.asString(), arg3.asNumber(), arg4.asNumber() );
        }
    }
    
    // ========================================
    
    private static final class Count extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( MtsValue arg1 )
        {
            checkTable( arg1, "Count", 1 );
            return valueOf( arg1.asTable().count() );
        }
    }
    
    // ========================================
    
    private static final class Insert extends MtsThreeArgFunction
    {
        @Override
        public MtsValue call( MtsValue arg1, MtsValue arg2 )
        {
            checkTable( arg1, "Insert", 1 );
            arg1.asTable().add( arg2 );
            return NIL;
        }
        
        @Override
        public MtsValue call( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
        {
            checkTable( arg1, "Insert", 1 );
            checkNumber( arg2, "Insert", 2 );
            arg1.asTable().insert( arg2, arg3 );
            return NIL;
        }
    }
    
    // ========================================
    
    private static final class Random extends MtsFunction
    {
        private final java.util.Random rnd = new java.util.Random();
        
        @Override
        public MtsValue call( MtsValue... args )
        {
            if ( args.length < 1 )
                return NIL;
            
            if ( args.length > 1 )
                return args[rnd.nextInt( args.length )];
            
            if ( args[0].isTable() )
            {
                MtsTable t = args[0].asTable();
                
                if ( t.listSize() == 0 )
                    return NIL;
                
                MtsNumber k = valueOf( rnd.nextInt( t.listSize() ) );
                return t.get( k );
            }
            
            return args[0];
        }
    }
    
    // ========================================
    
    private static final class Remove extends MtsTwoArgFunction
    {
        @Override
        public MtsValue call( MtsValue arg1 )
        {
            checkTable( arg1, "Remove", 1 );
            return arg1.asTable().removeLast();
        }
        
        @Override
        public MtsValue call( MtsValue arg1, MtsValue arg2 )
        {
            checkTable( arg1, "Remove", 1 );
            return arg1.asTable().remove( arg2 );
        }
    }
    
}
