package fish.eyebrow.mint.annotation;

public @interface Option {

    String option();


    Class type() default Boolean.class;
}
