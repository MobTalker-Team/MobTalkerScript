package mobtalkerscript.mts.v2.value.userdata;

import java.lang.annotation.*;

@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.METHOD } )
public @interface MtsCallableMethod
{
    String name();
}
