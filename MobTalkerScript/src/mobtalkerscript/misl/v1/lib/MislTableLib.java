package mobtalkerscript.misl.v1.lib;

import mobtalkerscript.*;
import mobtalkerscript.misl.v1.*;
import mobtalkerscript.misl.v1.value.*;

public class MislTableLib implements IMislLibrary
{
    
    @Override
    public void loadInto(IBindings env)
    {
        MislTable t = new MislTable(3);
        t.set("Insert", new Insert());
        t.set("Remove", new Remove());
        t.set("Size", new Size());
        t.set("Random", new Random());
        
        env.set("table", t);
    }
    
    // ========================================
    
    private static final class Insert extends MislVarArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue... args)
        {
            if (args.length < 2)
            {
                throw new ScriptRuntimeException("Expected at least 2 arguments, got %s", args.length);
            }
            
            MislTable table = args[0].asTable();
            MislValue where;
            MislValue what;
            
            if (args.length == 2)
            {
                where = table.getNextIndex();
                what = args[1];
            }
            else
            {
                where = args[1];
                what = args[2];
            }
            
            return table.set(where, what);
        }
    }
    
    // ========================================
    
    private static final class Remove extends MislVarArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue... args)
        {
            if (args.length < 1)
            {
                throw new ScriptRuntimeException("Expected at least 1 argument");
            }
            
            MislTable table = args[0].asTable();
            MislValue removed;
            
            if (args.length == 1)
            {
                removed = table.remove();
            }
            else
            {
                removed = table.remove(args[1]);
            }
            
            return removed;
        }
    }
    
    // ========================================
    
    private static final class Size extends MislOneArgFunction
    {
        @Override
        public MislValue call(IBindings env, MislValue arg1)
        {
            return arg1.asTable().getSize();
        }
    }
    
    // ========================================
    
    private static final class Random extends MislVarArgFunction
    {
        private static final java.util.Random rnd = new java.util.Random();
        
        @Override
        public MislValue call(IBindings env, MislValue... args)
        {
            if ((args == null) || (args.length == 0))
            {
                return NIL;
            }
            
            if (args.length == 1)
            {
                if (args[0].isTable())
                {
                    return args[0].asTable().getRandomElement(rnd);
                }
                else
                {
                    return args[0];
                }
            }
            else
            {
                return args[rnd.nextInt(args.length)];
            }
        }
    }
    
}
