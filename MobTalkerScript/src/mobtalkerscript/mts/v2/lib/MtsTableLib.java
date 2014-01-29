package mobtalkerscript.mts.v2.lib;

import mobtalkerscript.mts.v2.value.*;

public final class MtsTableLib extends MtsLibrary
{
    private static final MtsJavaFunction Concat = new Concat();
    private static final MtsJavaFunction Count = new Count();
    private static final MtsJavaFunction Insert = new Insert();
    private static final MtsJavaFunction Remove = new Remove();
    
    @Override
    public MtsValue bind( MtsString name, MtsValue env )
    {
        MtsTable lib = new MtsTable( 1, 5 );
        
        bindFunction( lib, Concat );
        bindFunction( lib, Count );
        bindFunction( lib, Insert );
        bindFunction( lib, new Random() );
        bindFunction( lib, Remove );
        
        env.set( "Table", lib );
        return NIL;
    }
    
    // ========================================
    
    private static final class Concat extends MtsFourArgFunction
    {
        @Override
        public String getName()
        {
            return "Concat";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
            checkTable( arg1, 1 );
            
            if ( arg2.isNil() )
                arg2 = EMPTY_STRING;
            
            checkString( arg2, 2 );
            
            return arg1.asTable().concat( arg2.asString() );
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
        {
            checkTable( arg1, 1 );
            
            if ( arg2.isNil() )
                arg2 = EMPTY_STRING;
            
            checkString( arg2, 2 );
            checkNumber( arg3, 3 );
            
            return arg1.asTable().concat( arg2.asString(), arg3.asNumber() );
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2, MtsValue arg3, MtsValue arg4 )
        {
            checkTable( arg1, 1 );
            
            if ( arg2.isNil() )
                arg2 = EMPTY_STRING;
            
            checkString( arg2, 2 );
            checkNumber( arg3, 3 );
            checkNumber( arg4, 4 );
            
            return arg1.asTable().concat( arg2.asString(), arg3.asNumber(), arg4.asNumber() );
        }
    }
    
    // ========================================
    
    private static final class Count extends MtsOneArgFunction
    {
        @Override
        public String getName()
        {
            return "Count";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkTable( arg1, 1 );
            
            return valueOf( arg1.asTable().count() );
        }
    }
    
    // ========================================
    
    private static final class Insert extends MtsThreeArgFunction
    {
        @Override
        public String getName()
        {
            return "Insert";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
            checkTable( arg1, 1 );
            
            arg1.asTable().add( arg2 );
            return NIL;
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
        {
            checkTable( arg1, 1 );
            checkNumber( arg2, 2 );
            
            arg1.asTable().insert( arg2, arg3 );
            return NIL;
        }
    }
    
    // ========================================
    
    private static final class Random extends MtsJavaFunction
    {
        private final java.util.Random rnd = new java.util.Random();
        
        @Override
        public String getName()
        {
            return "Random";
        }
        
        @Override
        protected MtsValue invoke( MtsValue... args )
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
        public String getName()
        {
            return "Remove";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkTable( arg1, 1 );
            
            return arg1.asTable().removeLast();
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
            checkTable( arg1, 1 );
            
            return arg1.asTable().remove( arg2 );
        }
    }
}
