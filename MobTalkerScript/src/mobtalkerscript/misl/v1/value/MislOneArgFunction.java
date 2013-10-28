package mobtalkerscript.misl.v1.value;

import mobtalkerscript.misl.v1.*;

public abstract class MislOneArgFunction extends MislVarArgFunction
{
    @Override
    public MislValue call(IBindings env, MislValue... args)
    {
        switch (args.length)
        {
            case 1:
                return call(env, args[0]);
            default:
                return call(env);
        }
    }
    
    public abstract MislValue call(IBindings env, MislValue arg1);
}