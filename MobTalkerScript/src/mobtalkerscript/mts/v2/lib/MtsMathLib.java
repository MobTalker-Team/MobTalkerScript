package mobtalkerscript.mts.v2.lib;

import mobtalkerscript.misl.v1.*;
import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

public class MtsMathLib implements IMislLibrary
{
    
    // ========================================
    
    @Override
    public void loadInto( IBindings env )
    {
        MtsTable math = new MtsTable( 0, 20 );
        
        math.setRaw( "PI", MtsValue.valueOf( Math.PI ) );
        math.setRaw( "E", MtsValue.valueOf( Math.E ) );
        
        math.setRaw( "Abs", new Abs() );
        math.setRaw( "Ceil", new Ceil() );
        math.setRaw( "Cos", new Cos() );
        math.setRaw( "CosH", new CosH() );
        math.setRaw( "Floor", new Floor() );
        math.setRaw( "Log", new Log() );
        math.setRaw( "Log10", new Log10() );
        math.setRaw( "Max", new Max() );
        math.setRaw( "Min", new Min() );
        math.setRaw( "Pow", new Pow() );
        math.setRaw( "Random", new Random() );
        math.setRaw( "Round", new Round() );
        math.setRaw( "Sin", new Sin() );
        math.setRaw( "SinH", new SinH() );
        math.setRaw( "Sign", new Sign() );
        math.setRaw( "Sqrt", new Sqrt() );
        math.setRaw( "Tan", new Tan() );
        math.setRaw( "TanH", new TanH() );
        math.setRaw( "ToDegrees", new ToDegrees() );
        math.setRaw( "ToRadians", new ToRadians() );
        
        env.set( "math", math );
    }
    
    // ========================================
    
    private static final class Abs extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( IBindings env, MtsValue arg1 )
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.abs( a );
            
            return valueOf( result );
        }
    }
    
    private static final class Ceil extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( IBindings env, MtsValue arg1 )
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.ceil( a );
            
            return valueOf( result );
        }
    }
    
    private static final class Cos extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( IBindings env, MtsValue arg1 )
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.cos( a );
            
            return valueOf( result );
        }
    }
    
    private static final class CosH extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( IBindings env, MtsValue arg1 )
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.cosh( a );
            
            return valueOf( result );
        }
    }
    
    private static final class Floor extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( IBindings env, MtsValue arg1 )
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.floor( a );
            
            return valueOf( result );
        }
    }
    
    private static final class Log extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( IBindings env, MtsValue arg1 )
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.log( a );
            
            return valueOf( result );
        }
    }
    
    private static final class Log10 extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( IBindings env, MtsValue arg1 )
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.log10( a );
            
            return valueOf( result );
        }
    }
    
    private static final class Max extends MislVarArgFunction
    {
        @Override
        public MtsValue call( IBindings env, MtsValue... args )
        {
            if ( args.length < 1 )
            {
                throw new ScriptRuntimeException( "invalid argument count. usage: max(Number, ...)" );
            }
            
            double result = args[0].asNumber().toJava();
            
            for ( int i = 1; i < args.length; i++ )
            {
                double n = args[i].asNumber().toJava();
                result = Math.max( result, n );
            }
            
            return valueOf( result );
        }
    }
    
    private static final class Min extends MislVarArgFunction
    {
        @Override
        public MtsValue call( IBindings env, MtsValue... args )
        {
            if ( args.length < 1 )
            {
                throw new ScriptRuntimeException( "invalid argument count. usage: min(Number, ...)" );
            }
            
            double result = args[0].asNumber().toJava();
            
            for ( int i = 1; i < args.length; i++ )
            {
                double n = args[i].asNumber().toJava();
                result = Math.min( result, n );
            }
            
            return valueOf( result );
        }
    }
    
    private static final class Pow extends MtsTwoArgFunction
    {
        @Override
        public MtsValue call( IBindings env, MtsValue arg1, MtsValue arg2 )
        {
            double a = arg1.asNumber().toJava();
            double b = arg2.asNumber().toJava();
            
            double result = Math.pow( a, b );
            
            return valueOf( result );
        }
    }
    
    private static final class Random extends MtsTwoArgFunction
    {
        
        private final java.util.Random _rnd = new java.util.Random();
        
        @Override
        public MtsValue call( IBindings env )
        {
            return valueOf( _rnd.nextDouble() );
        }
        
        @Override
        public MtsValue call( IBindings env, MtsValue arg1 )
        {
            int a = (int) arg1.asNumber().toJava();
            
            return valueOf( _rnd.nextInt( a ) );
        }
        
        @Override
        public MtsValue call( IBindings env, MtsValue arg1, MtsValue arg2 )
        {
            int a = (int) arg1.asNumber().toJava();
            int b = (int) arg2.asNumber().toJava();
            
            return valueOf( a + _rnd.nextInt( b ) );
        }
    }
    
    private static final class Round extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( IBindings env, MtsValue arg1 )
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.round( a );
            
            return valueOf( result );
        }
    }
    
    private static final class Sin extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( IBindings env, MtsValue arg1 )
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.sin( a );
            
            return valueOf( result );
        }
    }
    
    private static final class SinH extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( IBindings env, MtsValue arg1 )
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.sinh( a );
            
            return valueOf( result );
        }
    }
    
    private static final class Sign extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( IBindings env, MtsValue arg1 )
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.signum( a );
            
            return valueOf( result );
        }
    }
    
    private static final class Sqrt extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( IBindings env, MtsValue arg1 )
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.sqrt( a );
            
            return valueOf( result );
        }
    }
    
    private static final class Tan extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( IBindings env, MtsValue arg1 )
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.tan( a );
            
            return valueOf( result );
        }
    }
    
    private static final class TanH extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( IBindings env, MtsValue arg1 )
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.tanh( a );
            
            return valueOf( result );
        }
    }
    
    private static final class ToDegrees extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( IBindings env, MtsValue arg1 )
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.toDegrees( a );
            
            return valueOf( result );
        }
    }
    
    private static final class ToRadians extends MtsOneArgFunction
    {
        @Override
        public MtsValue call( IBindings env, MtsValue arg1 )
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.toRadians( a );
            
            return valueOf( result );
        }
    }
}
