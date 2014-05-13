package mobtalkerscript.mts.v2.lib;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

public final class MtsBaseLib extends MtsGlobalLibrary
{
    private static final MtsJavaFunction Assert = new Assert();
    private static final MtsJavaFunction Error = new Error();
    private static final MtsJavaFunction INext = new INext();
    private static final MtsJavaFunction Next = new Next();
    private static final MtsJavaFunction PCall = new PCall();
    private static final MtsJavaFunction RawGet = new RawGet();
    private static final MtsJavaFunction RawSet = new RawSet();
    private static final MtsJavaFunction GetMetaTable = new GetMetaTable();
    private static final MtsJavaFunction SetMetaTable = new SetMetaTable();
    private static final MtsJavaFunction ToNumber = new ToNumber();
    private static final MtsJavaFunction ToString = new ToString();
    private static final MtsJavaFunction TypeOf = new TypeOf();
    
    // ========================================
    
    @Override
    protected void bind()
    {
        _G.set( "_G", _G, false );
        _G.set( "_VERSION", valueOf( "2.0.0-alpha" ), false );
        
        bindFunction( "assert", Assert );
        bindFunction( "error", Error );
        bindFunction( "next", Next );
        bindFunction( "inext", INext );
        bindFunction( "print", new Print() );
        bindFunction( "rawget", RawGet );
        bindFunction( "rawset", RawSet );
        bindFunction( "typeof", TypeOf );
        
        bindFunction( "getmetatable", GetMetaTable );
        bindFunction( "setmetatable", SetMetaTable );
        
        bindFunction( "pcall", PCall );
        
        bindFunction( "LoadString", new LoadString() );
        
        bindFunction( "ToNumber", ToNumber );
        bindFunction( "ToString", ToString );
    }
    
    // ========================================
    
    private static final class Assert extends MtsTwoArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
            if ( !MtsBoolean.isTrue( arg1 ) )
            {
                String msg = arg2.isNil() ? "assertion failed!" : arg2.toMtsString().asJavaString();
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
            throw new ScriptRuntimeException( arg1.toMtsString().asJavaString() );
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
            throw new ScriptRuntimeException( arg2.asNumber().asJavaInt(),
                                              arg1.toMtsString().asJavaString() );
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
            return next == null ? EMPTY_VARARGS : next.getKey();
        }
    }
    
    private static final class Next extends MtsTwoArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
            checkTable( arg1, 1 );
            
            MtsTable.Entry next = arg1.asTable().getNext( arg2 );
            return next == null ? EMPTY_VARARGS : new MtsVarArgs( next.getKey(), next.getValue() );
        }
    }
    
    // ========================================
    
    private final class Print extends MtsJavaFunction
    {
        @Override
        protected MtsValue invoke( MtsVarArgs args )
        {
            if ( args.count() > 0 )
            {
                MtsString str = MtsString.concat( args );
                getGlobals().out.println( str.asJavaString() );
            }
            else
            {
                getGlobals().out.println();
            }
            
            return EMPTY_VARARGS;
        }
    }
    
    // ========================================
    
    private static final class RawGet extends MtsTwoArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
            checkTable( arg1, 1 );
            
            return arg1.asTable().get( arg2, false );
        }
    }
    
    private static final class RawSet extends MtsThreeArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
        {
            checkTable( arg1, 1 );
            
            arg1.asTable().set( arg2, arg3, false );
            
            return EMPTY_VARARGS;
        }
    }
    
    // ========================================
    
    private static final class GetMetaTable extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg )
        {
            MtsTable t = checkTable( arg, 0 );
            return t.getMetaTable();
        }
    }
    
    private static final class SetMetaTable extends MtsTwoArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
            MtsTable t = checkTable( arg1, 0 );
            t.setMetaTable( arg2 );
            return arg1;
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
            
            return EMPTY_VARARGS;
        }
    }
    
    private static final class ToString extends MtsOneArgFunction
    {
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            return arg1.toMtsString();
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
    
    // ========================================
    
    private final class LoadString extends MtsJavaFunction
    {
        @Override
        protected MtsValue invoke( MtsVarArgs args )
        {
            checkString( args.get( 0 ), 0 );
            
            MtsFunctionPrototype p;
            try
            {
                p = getGlobals().loadString( args.get( 0 ).asString().asJavaString(), "string" );
            }
            catch ( Exception ex )
            {
                throw new ScriptRuntimeException( "Unable to load string: %s", ex.getMessage() );
            }
            
            MtsValue env = args.get( 1 );
            if ( env.isNil() )
            {
                env = getGlobals();
            }
            
            return new MtsClosure( p, env );
        }
    }
    
    // ========================================
    
    private static final class PCall extends MtsJavaFunction
    {
        @Override
        protected MtsValue invoke( MtsVarArgs args )
        {
            MtsValue f = args.get( 0 );
            MtsVarArgs callArgs = args.subArgs( 1 );
            
            MtsValue result;
            try
            {
                MtsValue callResults = f.call( callArgs );
                
                if ( callResults.isVarArgs() )
                    result = new MtsVarArgs( TRUE, callResults.asVarArgs() );
                else
                    result = new MtsVarArgs( TRUE, callResults );
            }
            catch ( ScriptRuntimeException ex )
            {
                result = new MtsVarArgs( FALSE, valueOf( ex.getMessage() ) );
            }
            
            return result;
        }
    }
    
}
