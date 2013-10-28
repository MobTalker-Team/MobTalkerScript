package mobtalkerscript.misl.v1.value;

import mobtalkerscript.misl.v1.*;

public abstract class MislZeroArgFunction extends MislNativeFunction
{
    @Override
    public MislValue call(IBindings env, MislValue... args)
    {
        return call(env);
    }
}