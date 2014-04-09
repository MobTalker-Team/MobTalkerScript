package mobtalkerscript.mts.v2.lib;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

public final class MtsBaseLib extends MtsLibrary
{
    private static final MtsJavaFunction Assert = new Assert();
    private static final MtsJavaFunction Error = new Error();
    private static final MtsJavaFunction INext = new INext();
    private static final MtsJavaFunction Next = new Next();
    private static final MtsJavaFunction RawGet = new RawGet();
    private static final MtsJavaFunction RawSet = new RawSet();
    private static final MtsJavaFunction ToNumber = new ToNumber();
    private static final MtsJavaFunction ToString = new ToString();
    private static final MtsJavaFunction TypeOf = new TypeOf();
    
    // ========================================
    
    @Override
    public MtsValue bind( MtsString name, MtsValue env )
    {
        checkIfGlobals( env );
        
        env.set( "_G", env );
        
        bindFunction( env, Assert );
        bindFunction( env, Error );
        bindFunction( env, INext );
        bindFunction( env, Next );
        bindFunction( env, new Print( (MtsGlobals) env ) );
        bindFunction( env, RawGet );
        bindFunction( env, RawSet );
        bindFunction( env, ToNumber );
        bindFunction( env, ToString );
        bindFunction( env, TypeOf );
        
        return env;
    }
    
    // ========================================
    
    private static final class Assert extends MtsTwoArgFunction
    {
        @Override
        public String getName()
        {
            return "assert";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
            if ( !MtsBoolean.isTrue( arg1 ) )
            {
                String msg = arg2.isNil() ? "assertion failed!" : arg2.toStringMts().toJava();
                throw new ScriptRuntimeException( msg );
            }
            
            return NIL;
        }
    }
    
    private static final class Error extends MtsTwoArgFunction
    {
        @Override
        public String getName()
        {
            return "error";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            throw new ScriptRuntimeException( arg1.toStringMts().toJava() );
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
            throw new ScriptRuntimeException( (int) arg2.asNumber().toJava(), arg1.toStringMts().toJava() );
        }
    }
    
    // ========================================
    
    private static final class INext extends MtsTwoArgFunction
    {
        @Override
        public String getName()
        {
            return "inext";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
            checkTable( arg1, 1 );
            
            MtsTable.Entry next = arg1.asTable().getINext( arg2.asNumber() );
            return next == null ? NIL : next.getKey();
        }
    }
    
    private static final class Next extends MtsTwoArgFunction
    {
        @Override
        public String getName()
        {
            return "next";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
            checkTable( arg1, 1 );
            
            MtsTable.Entry next = arg1.asTable().getNext( arg2 );
            return next == null ? NIL : next.getKey();
        }
    }
    
    // ========================================
    
    private final class Print extends MtsJavaFunction
    {
        private final MtsGlobals _G;
        
        public Print( MtsGlobals g )
        {
            _G = g;
        }
        
        @Override
        public String getName()
        {
            return "print";
        }
        
        @Override
        protected MtsValue invoke( MtsValue... args )
        {
            if ( args.length > 0 )
            {
                MtsString str = MtsString.concat( args );
                _G.out.println( str.toJava() );
            }
            else
            {
                _G.out.println();
            }
            
            return NIL;
        }
    }
    
    // ========================================
    
    private static final class RawGet extends MtsTwoArgFunction
    {
        @Override
        public String getName()
        {
            return "rawget";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
            checkTable( arg1, 1 );
            
            return arg1.asTable().getRaw( arg2 );
        }
    }
    
    private static final class RawSet extends MtsThreeArgFunction
    {
        @Override
        public String getName()
        {
            return "rawset";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
        {
            checkTable( arg1, 1 );
            
            return arg1.asTable().setRaw( arg2, arg3 );
        }
    }
    
    // ========================================
    
    private static final class ToNumber extends MtsOneArgFunction
    {
        @Override
        public String getName()
        {
            return "toNumber";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            if ( arg1.isNumber() )
                return arg1;
            if ( arg1.isString() )
                return MtsNumber.parse( arg1.asString() );
            if ( arg1.isBoolean() )
                return MtsNumber.parse( arg1.asBoolean() );
            
            return NIL;
        }
    }
    
    private static final class ToString extends MtsOneArgFunction
    {
        @Override
        public String getName()
        {
            return "toString";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            return arg1.toStringMts();
        }
    }
    
    // ========================================
    
    private static final class TypeOf extends MtsOneArgFunction
    {
        @Override
        public String getName()
        {
            return "typeof";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            return valueOf( arg1.getType().getName() );
        }
    }
    
}
