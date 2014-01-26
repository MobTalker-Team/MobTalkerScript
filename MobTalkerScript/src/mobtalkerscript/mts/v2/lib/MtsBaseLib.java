package mobtalkerscript.mts.v2.lib;

import java.io.*;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

public class MtsBaseLib extends MtsLibrary
{
    public PrintStream out;
    
    // ========================================
    
    @Override
    public MtsValue bind( MtsString name, MtsValue env )
    {
        env.set( "_G", env );
        
        env.set( "assert", new Assert() );
        env.set( "print", new Print() );
        env.set( "error", new Error() );
        
        env.set( "typeof", new TypeOf() );
        env.set( "sizeof", new SizeOf() );
        
        env.set( "next", new Next() );
        env.set( "inext", new INext() );
        
        env.set( "getraw", new GetRaw() );
        env.set( "setraw", new SetRaw() );
        
        env.set( "toNumber", new ToNumber() );
        env.set( "toString", new ToString() );
        
        return env;
    }
    
    // ========================================
    
    private static final class Assert extends MtsTwoArgFunction
    {
        @Override
        public MtsValue call( MtsValue arg1, MtsValue arg2 )
        {
            if ( !MtsBoolean.isTrue( arg1 ) )
            {
                String msg = arg2.isNil() ? "assertion failed!" : arg2.toStringMts().toJava();
                throw new ScriptRuntimeException( msg );
            }
            
            return NIL;
        }
    }
    
    private final class Print extends MtsFunction
    {
        @Override
        public MtsValue call( MtsValue... args )
        {
            if ( args.length > 0 )
            {
                MtsString str = MtsString.concat( args );
                out.println( str.toJava() );
            }
            else
            {
                out.println();
            }
            
            return NIL;
        }
    }
    
    private final class Error extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( MtsValue arg1 )
        {
            throw new ScriptRuntimeException( arg1 );
        }
    }
    
    // ========================================
    
    private static final class TypeOf extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( MtsValue arg1 )
        {
            return valueOf( arg1.getTypeName() );
        }
    }
    
    private static final class SizeOf extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( MtsValue arg1 )
        {
            if ( arg1.isTable() )
            {
                return valueOf( arg1.asTable().listSize() );
            }
            else if ( arg1.isString() )
            {
                return arg1.asString().getLength();
            }
            else
            {
                throw new ScriptRuntimeException( "bad argument #1 to 'sizeof' (%s or %s expected, got %s)",
                                                  TYPENAME_TABLE,
                                                  TYPENAME_STRING,
                                                  arg1.getTypeName() );
            }
        }
    }
    
    // ========================================
    
    private static final class ToNumber extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( MtsValue arg1 )
        {
            if ( arg1.isNumber() )
                return arg1;
            if ( arg1.isString() )
                return MtsNumber.parse( arg1.asString() );
            if ( arg1.isBoolean() )
                return MtsNumber.parse( arg1.asBoolean() );
            
            return null;
        }
    }
    
    private static final class ToString extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( MtsValue arg1 )
        {
            if ( arg1.isString() )
                return arg1;
            
            return arg1.toStringMts();
        }
    }
    
    // ========================================
    
    private static final class Next extends MtsTwoArgFunction
    {
        @Override
        public MtsValue call( MtsValue arg1, MtsValue arg2 )
        {
            checkTable( arg1, "next", 1 );
            
            MtsTable.Entry next = arg1.asTable().getNext( arg2 );
            return next == null ? NIL : next.getKey();
        }
    }
    
    private static final class INext extends MtsTwoArgFunction
    {
        @Override
        public MtsValue call( MtsValue arg1, MtsValue arg2 )
        {
            checkTable( arg1, "inext", 1 );
            
            MtsTable.Entry next = arg1.asTable().getINext( arg2.asNumber() );
            return next == null ? NIL : next.getKey();
        }
    }
    
    // ========================================
    
    private static final class GetRaw extends MtsTwoArgFunction
    {
        @Override
        public MtsValue call( MtsValue arg1, MtsValue arg2 )
        {
            checkTable( arg1, "rawget", 1 );
            
            return arg1.asTable().getRaw( arg2 );
        }
    }
    
    private static final class SetRaw extends MtsThreeArgFunction
    {
        @Override
        public MtsValue call( MtsValue arg1, MtsValue arg2, MtsValue arg3 )
        {
            checkTable( arg1, "rawset", 1 );
            
            return arg1.asTable().setRaw( arg2, arg3 );
        }
    }
    
}
