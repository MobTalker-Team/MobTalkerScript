package mobtalkerscript.mts.v2.lib;

import mobtalkerscript.mts.v2.*;
import mobtalkerscript.mts.v2.value.*;

public final class MtsMathLib extends MtsLibrary
{
    private static final MtsNumber PI = valueOf( Math.PI );
    private static final MtsNumber E = valueOf( Math.E );
    
    private static final MtsJavaFunction Abs = new Abs();
    private static final MtsJavaFunction Ceil = new Ceil();
    private static final MtsJavaFunction Cos = new Cos();
    private static final MtsJavaFunction CosH = new CosH();
    private static final MtsJavaFunction Floor = new Floor();
    private static final MtsJavaFunction Log = new Log();
    private static final MtsJavaFunction Log10 = new Log10();
    private static final MtsJavaFunction Max = new Max();
    private static final MtsJavaFunction Min = new Min();
    private static final MtsJavaFunction Pow = new Pow();
    private static final MtsJavaFunction Round = new Round();
    private static final MtsJavaFunction Sin = new Sin();
    private static final MtsJavaFunction SinH = new SinH();
    private static final MtsJavaFunction Sign = new Sign();
    private static final MtsJavaFunction Sqrt = new Sqrt();
    private static final MtsJavaFunction Tan = new Tan();
    private static final MtsJavaFunction TanH = new TanH();
    private static final MtsJavaFunction ToDegrees = new ToDegrees();
    private static final MtsJavaFunction ToRadians = new ToRadians();
    
    // ========================================
    
    @Override
    public MtsValue bind( MtsString name, MtsValue env )
    {
        MtsTable math = new MtsTable( 0, 20 );
        
        math.setRaw( "PI", PI );
        math.setRaw( "E", E );
        
        bindFunction( math, Abs );
        bindFunction( math, Ceil );
        bindFunction( math, Cos );
        bindFunction( math, CosH );
        bindFunction( math, Floor );
        bindFunction( math, Log );
        bindFunction( math, Log10 );
        bindFunction( math, Max );
        bindFunction( math, Min );
        bindFunction( math, Pow );
        bindFunction( math, new Random() );
        bindFunction( math, Round );
        bindFunction( math, Sin );
        bindFunction( math, SinH );
        bindFunction( math, Sign );
        bindFunction( math, Sqrt );
        bindFunction( math, Tan );
        bindFunction( math, TanH );
        bindFunction( math, ToDegrees );
        bindFunction( math, ToRadians );
        
        env.set( "Math", math );
        
        return NIL;
    }
    
    // ========================================
    
    private static final class Abs extends MtsOneArgFunction
    {
        @Override
        public String getName()
        {
            return "Abs";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.abs( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class Ceil extends MtsOneArgFunction
    {
        @Override
        public String getName()
        {
            return "Ceil";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.ceil( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class Cos extends MtsOneArgFunction
    {
        @Override
        public String getName()
        {
            return "Cos";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.cos( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class CosH extends MtsOneArgFunction
    {
        @Override
        public String getName()
        {
            return "CosH";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.cosh( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class Floor extends MtsOneArgFunction
    {
        @Override
        public String getName()
        {
            return "Floor";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.floor( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class Log extends MtsOneArgFunction
    {
        @Override
        public String getName()
        {
            return "Log";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.log( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class Log10 extends MtsOneArgFunction
    {
        @Override
        public String getName()
        {
            return "Log10";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.log10( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class Max extends MtsJavaFunction
    {
        @Override
        public String getName()
        {
            return "Max";
        }
        
        @Override
        protected MtsValue invoke( MtsValue... args )
        {
            if ( args.length < 1 )
                throw new ScriptRuntimeException( "bad arguments to '%s' (got no value)", getName() );
            
            double result = args[0].asNumber().toJava();
            
            for ( int i = 1; i < args.length; i++ )
            {
                double n = args[i].asNumber().toJava();
                result = Math.max( result, n );
            }
            
            return valueOf( result );
        }
    }
    
    private static final class Min extends MtsJavaFunction
    {
        @Override
        public String getName()
        {
            return "Min";
        }
        
        @Override
        protected MtsValue invoke( MtsValue... args )
        {
            if ( args.length < 1 )
                throw new ScriptRuntimeException( "bad arguments to '%s' (got no value)", getName() );
            
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
        public String getName()
        {
            return "Pow";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
            checkNumber( arg1, 1 );
            checkNumber( arg2, 2 );
            
            return valueOf( Math.pow( arg1.asNumber().toJava(), arg2.asNumber().toJava() ) );
        }
    }
    
    private static final class Random extends MtsTwoArgFunction
    {
        @Override
        public String getName()
        {
            return "Random";
        }
        
        private final java.util.Random _rnd = new java.util.Random();
        
        @Override
        protected MtsValue invoke()
        {
            return valueOf( _rnd.nextDouble() );
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( _rnd.nextInt( (int) arg1.asNumber().toJava() ) );
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1, MtsValue arg2 )
        {
            checkNumber( arg1, 1 );
            checkNumber( arg2, 2 );
            
            int a = (int) arg1.asNumber().toJava();
            int b = (int) arg2.asNumber().toJava();
            
            return valueOf( a + _rnd.nextInt( b ) );
        }
    }
    
    private static final class Round extends MtsOneArgFunction
    {
        @Override
        public String getName()
        {
            return "Round";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.round( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class Sin extends MtsOneArgFunction
    {
        @Override
        public String getName()
        {
            return "Sin";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.sin( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class SinH extends MtsOneArgFunction
    {
        @Override
        public String getName()
        {
            return "SinH";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.sinh( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class Sign extends MtsOneArgFunction
    {
        @Override
        public String getName()
        {
            return "Sign";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.signum( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class Sqrt extends MtsOneArgFunction
    {
        @Override
        public String getName()
        {
            return "Sqrt";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.sqrt( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class Tan extends MtsOneArgFunction
    {
        @Override
        public String getName()
        {
            return "Tan";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.tan( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class TanH extends MtsOneArgFunction
    {
        @Override
        public String getName()
        {
            return "TanH";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.tanh( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class ToDegrees extends MtsOneArgFunction
    {
        @Override
        public String getName()
        {
            return "ToDegrees";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.toDegrees( arg1.asNumber().toJava() ) );
        }
    }
    
    private static final class ToRadians extends MtsOneArgFunction
    {
        @Override
        public String getName()
        {
            return "ToRadians";
        }
        
        @Override
        protected MtsValue invoke( MtsValue arg1 )
        {
            checkNumber( arg1, 1 );
            
            return valueOf( Math.toRadians( arg1.asNumber().toJava() ) );
        }
    }
}
