package net.mobtalker.mobtalkerscript.v2.value;

public abstract class MtsZeroArgFunction extends MtsJavaFunction
{
    @Override
    protected MtsValue invoke( MtsVarArgs args )
    {
        return invoke();
    }
    
    protected abstract MtsValue invoke();
}