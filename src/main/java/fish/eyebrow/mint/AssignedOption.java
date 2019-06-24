package fish.eyebrow.mint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AssignedOption {

    String option();


    String prefix() default "--";


    boolean variable() default false;
}
