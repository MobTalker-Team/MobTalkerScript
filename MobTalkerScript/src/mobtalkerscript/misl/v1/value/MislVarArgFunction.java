package mobtalkerscript.misl.v1.value;

import mobtalkerscript.misl.v1.*;

public abstract class MislVarArgFunction extends MislNativeFunction
{
    @Override
    public MislValue call( IBindings env )
    {
        return call( env, EMPTY_ARGS );
    }
}