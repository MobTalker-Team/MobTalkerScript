package mobtalkerscript.v2.value.userdata;

import java.lang.annotation.*;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface MtsNativeField
{
    String name() default "";
}
