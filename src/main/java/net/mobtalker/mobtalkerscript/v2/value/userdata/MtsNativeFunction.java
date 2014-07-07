package net.mobtalker.mobtalkerscript.v2.value.userdata;

import java.lang.annotation.*;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.METHOD )
public @interface MtsNativeFunction
{
    String name() default "";
}
