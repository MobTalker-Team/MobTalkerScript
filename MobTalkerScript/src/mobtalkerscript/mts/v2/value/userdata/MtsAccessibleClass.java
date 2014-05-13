package mobtalkerscript.mts.v2.value.userdata;

import java.lang.annotation.*;

@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.TYPE } )
public @interface MtsAccessibleClass
{
    String name();
}
