package mobtalkerscript.misl.v1.lib;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

public class MislTableLib implements IMislLibrary
{
    
    @Override
    public void loadInto( IBindings env )
    {
        MislTable t = new MislTable( 0, 5 );
        t.setRaw( "Random", new Random() );
        
        t.setRaw( "Count", new Count() );
        t.setRaw( "Insert", new Insert() );
        t.setRaw( "Remove", new Remove() );
        t.setRaw( "Concat", new Concat() );
        
        env.set( "table", t );
    }
    
    // ========================================
    
    private static final class Random extends MislVarArgFunction
    {
        private static final java.util.Random rnd = new java.util.Random();
        
        @Override
        public MislValue call( IBindings env, MislValue... args )
        {
            if ( args.length < 1 )
                return NIL;
            
            if ( args.length > 1 )
                return args[rnd.nextInt( args.length )];
            
            if ( args[0].isTable() )
            {
                MislTable t = args[0].asTable();
                
                if ( t.listSize() == 0 )
                    return NIL;
                
                MislNumber k = valueOf( rnd.nextInt( t.listSize() ) );
                return t.get( k );
            }
            
            return args[0];
        }
    }
    
    // ========================================
    
    private static final class Count extends MislOneArgFunction
    {
        @Override
        public MislValue call( IBindings env, MislValue arg1 )
        {
            return valueOf( arg1.asTable().count() );
        }
    }
    
    // ========================================
    
    private static final class Insert extends MislThreeArgFunction
    {
        @Override
        public MislValue call( IBindings env, MislValue arg1, MislValue arg2 )
        {
            arg1.asTable().add( arg2 );
            return null;
        }
        
        @Override
        public MislValue call( IBindings env, MislValue arg1, MislValue arg2, MislValue arg3 )
        {
            arg1.asTable().insert( arg2, arg3 );
            return null;
        }
    }
    
    // ========================================
    
    private static final class Remove extends MislTwoArgFunction
    {
        @Override
        public MislValue call( IBindings env, MislValue arg1 )
        {
            return arg1.asTable().removeLast();
        }
        
        @Override
        public MislValue call( IBindings env, MislValue arg1, MislValue arg2 )
        {
            return arg1.asTable().remove( arg2 );
        }
    }
    
    // ========================================
    
    private static final class Concat extends MislFourArgFunction
    {
        @Override
        public MislValue call( IBindings env, MislValue arg1, MislValue arg2 )
        {
            return arg1.asTable().concat( arg2.asString() );
        }
        
        @Override
        public MislValue call( IBindings env, MislValue arg1, MislValue arg2, MislValue arg3 )
        {
            return arg1.asTable().concat( arg2.asString(), arg3.asNumber() );
        }
        
        @Override
        public MislValue call( IBindings env, MislValue arg1, MislValue arg2, MislValue arg3, MislValue arg4 )
        {
            return arg1.asTable().concat( arg2.asString(), arg3.asNumber(), arg4.asNumber() );
        }
    }
    
}
