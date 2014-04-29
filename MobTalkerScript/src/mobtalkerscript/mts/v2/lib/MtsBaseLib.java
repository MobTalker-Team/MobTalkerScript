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
        env.set( "_VERSION", valueOf( "2.0" ) );
        
        bindFunction( env, "assert", Assert );
        bindFunction( env, "error", Error );
        bindFunction( env, "next", Next );
        bindFunction( env, "inext", INext );
        bindFunction( env, "print", new Print( (MtsGlobals) env ) );
        bindFunction( env, "rawget", RawGet );
        bindFunction( env, "rawset", RawSet );
        bindFunction( env, "typeof", TypeOf );
        
        bindFunction( env, "toNumber", ToNumber );
        bindFunction( env, "toString", ToString );
        
        return env;
    }
    
    // ========================================
    
    private static final class Assert extends MtsTwoArgFunction
    {
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
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
            checkTable( arg1, 1 );
            
            MtsTable.Entry next = arg1.asTable().getNext( arg2 );
            return next == null ? NIL : new MtsVarArgs( next.getKey(), next.getValue() );
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
        protected MtsValue invoke( MtsVarArgs args )
        {
            if ( args.count() > 0 )
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
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
            checkTable( arg1, 1 );
            
            return arg1.asTable().getRaw( arg2 );
        }
    }
    
    private static final class RawSet extends MtsThreeArgFunction
    {
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
        protected MtsValue invoke( MtsValue arg1 )
        {
            return arg1.toStringMts();
        }
    }
    
    // ========================================
    
    private static final class TypeOf extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            return valueOf( arg1.getType().getName() );
        }
    }
    
}
