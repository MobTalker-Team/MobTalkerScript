package mobtalkerscript.misl.v1.lib;

import mobtalkerscript.*;
import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

public class MislMathLib implements IMislLibrary
{
    
    // ========================================
    
    @Override
    public void loadInto(IBindings env)
    {
        MislTable math = new MislTable(20);
        
        math.set("pi", MislValue.valueOf(Math.PI));
        math.set("e", MislValue.valueOf(Math.E));
        
        math.set("Abs", new Abs());
        math.set("Ceil", new Ceil());
        math.set("Cos", new Cos());
        math.set("CosH", new CosH());
        math.set("Floor", new Floor());
        math.set("Log", new Log());
        math.set("Log10", new Log10());
        math.set("Max", new Max());
        math.set("Min", new Min());
        math.set("Pow", new Pow());
        math.set("Random", new Random());
        math.set("Round", new Round());
        math.set("Sin", new Sin());
        math.set("SinH", new SinH());
        math.set("Sign", new Sign());
        math.set("Sqrt", new Sqrt());
        math.set("Tan", new Tan());
        math.set("TanH", new TanH());
        math.set("ToDegrees", new ToDegrees());
        math.set("ToRadians", new ToRadians());
        
        math.setReadonly(true);
        
        env.set("math", math);
    }
    
    // ========================================
    
    private static final class Abs extends MislOneArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue arg1)
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.abs(a);
            
            return valueOf(result);
        }
    }
    
    private static final class Ceil extends MislOneArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue arg1)
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.ceil(a);
            
            return valueOf(result);
        }
    }
    
    private static final class Cos extends MislOneArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue arg1)
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.cos(a);
            
            return valueOf(result);
        }
    }
    
    private static final class CosH extends MislOneArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue arg1)
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.cosh(a);
            
            return valueOf(result);
        }
    }
    
    private static final class Floor extends MislOneArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue arg1)
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.floor(a);
            
            return valueOf(result);
        }
    }
    
    private static final class Log extends MislOneArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue arg1)
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.log(a);
            
            return valueOf(result);
        }
    }
    
    private static final class Log10 extends MislOneArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue arg1)
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.log10(a);
            
            return valueOf(result);
        }
    }
    
    private static final class Max extends MislVarArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue... args)
        {
            if (args.length < 1)
            {
                throw new ScriptRuntimeException("invalid argument count. usage: max(Number, ...)");
            }
            
            double result = args[0].asNumber().toJava();
            
            for (int i = 1; i < args.length; i++)
            {
                double n = args[i].asNumber().toJava();
                result = Math.max(result, n);
            }
            
            return valueOf(result);
        }
    }
    
    private static final class Min extends MislVarArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue... args)
        {
            if (args.length < 1)
            {
                throw new ScriptRuntimeException("invalid argument count. usage: min(Number, ...)");
            }
            
            double result = args[0].asNumber().toJava();
            
            for (int i = 1; i < args.length; i++)
            {
                double n = args[i].asNumber().toJava();
                result = Math.min(result, n);
            }
            
            return valueOf(result);
        }
    }
    
    private static final class Pow extends MislTwoArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue arg1, MislValue arg2)
        {
            double a = arg1.asNumber().toJava();
            double b = arg2.asNumber().toJava();
            
            double result = Math.pow(a, b);
            
            return valueOf(result);
        }
    }
    
    private static final class Random extends MislTwoArgFunction
    {
        
        private final java.util.Random _rnd = new java.util.Random();
        
        @Override
        public MislValue call(IBindings env)
        {
            return valueOf(_rnd.nextDouble());
        }
        
        @Override
        public MislValue call(IBindings env, MislValue arg1)
        {
            int a = (int) arg1.asNumber().toJava();
            
            return valueOf(_rnd.nextInt(a));
        }
        
        @Override
        public MislValue call(IBindings env, MislValue arg1, MislValue arg2)
        {
            int a = (int) arg1.asNumber().toJava();
            int b = (int) arg2.asNumber().toJava();
            
            return valueOf(a + _rnd.nextInt(b));
        }
    }
    
    private static final class Round extends MislOneArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue arg1)
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.round(a);
            
            return valueOf(result);
        }
    }
    
    private static final class Sin extends MislOneArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue arg1)
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.sin(a);
            
            return valueOf(result);
        }
    }
    
    private static final class SinH extends MislOneArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue arg1)
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.sinh(a);
            
            return valueOf(result);
        }
    }
    
    private static final class Sign extends MislOneArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue arg1)
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.signum(a);
            
            return valueOf(result);
        }
    }
    
    private static final class Sqrt extends MislOneArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue arg1)
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.sqrt(a);
            
            return valueOf(result);
        }
    }
    
    private static final class Tan extends MislOneArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue arg1)
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.tan(a);
            
            return valueOf(result);
        }
    }
    
    private static final class TanH extends MislOneArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue arg1)
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.tanh(a);
            
            return valueOf(result);
        }
    }
    
    private static final class ToDegrees extends MislOneArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue arg1)
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.toDegrees(a);
            
            return valueOf(result);
        }
    }
    
    private static final class ToRadians extends MislOneArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue arg1)
        {
            double a = arg1.asNumber().toJava();
            
            double result = Math.toRadians(a);
            
            return valueOf(result);
        }
    }
}
