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
        
        bindFunction( lib, "Concat", Concat );
        bindFunction( lib, "Count", Count );
        bindFunction( lib, "Insert", Insert );
        bindFunction( lib, "Random", new Random() );
        bindFunction( lib, "Remove", Remove );
        
        env.set( "table", lib );
        return NIL;
    }
    
    // ========================================
    
    private static final class Concat extends MtsFourArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
            checkTable( arg1, 1 );
            
            if ( arg2.isNil() )
                arg2 = EMPTY_STRING;
            
            checkString( arg2, 2 );
            
            return arg1.asTable().concatElements( arg2.asString() );
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
        {
            checkTable( arg1, 1 );
            
            if ( arg2.isNil() )
                arg2 = EMPTY_STRING;
            
            checkString( arg2, 2 );
            checkNumber( arg3, 3 );
            
            return arg1.asTable().concatElements( arg2.asString(), arg3.asNumber() );
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
            
            return arg1.asTable().concatElements( arg2.asString(), arg3.asNumber(), arg4.asNumber() );
        }
    }
    
    // ========================================
    
    private static final class Count extends MtsOneArgFunction
    {
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
        protected MtsValue invoke( MtsVarArgs args )
        {
            if ( args.isEmpty() )
                return NIL;
            
            if ( args.count() > 1 )
                return args.get( rnd.nextInt( args.count() ) );
            
            MtsValue arg1 = args.get( 0 );
            if ( arg1.isTable() )
            {
                MtsTable t = arg1.asTable();
                
                if ( t.listSize() == 0 )
                    return NIL;
                
                MtsNumber k = valueOf( rnd.nextInt( t.listSize() ) );
                return t.get( k );
            }
            
            return arg1;
        }
    }
    
    // ========================================
    
    private static final class Remove extends MtsTwoArgFunction
    {
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
