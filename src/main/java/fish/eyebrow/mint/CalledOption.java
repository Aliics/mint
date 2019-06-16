package fish.eyebrow.mint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CalledOption {

    String option();


    String prefix() default "--";


    boolean singleCall() default false;
}
