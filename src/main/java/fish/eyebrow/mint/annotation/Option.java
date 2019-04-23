package fish.eyebrow.mint.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Option {

    String option();


    boolean requiresParam() default false;
}
