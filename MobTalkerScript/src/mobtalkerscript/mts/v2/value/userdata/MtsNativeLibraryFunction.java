package mobtalkerscript.mts.v2.value.userdata;

import java.lang.annotation.*;

import mobtalkerscript.mts.v2.value.*;

/**
 * Annotates a Java method that is accessible as a {@link MtsFunction} and bound to a specific instance of the enclosing type.
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.METHOD )
public @interface MtsNativeLibraryFunction
{
    String name();
}
