package fish.eyebrow.mint;

import fish.eyebrow.mint.annotation.Option;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ArgumentHandler {

    private static final String OPTION_PREFIX = "--";

    private final Object instance;


    public ArgumentHandler(final Object instance) {
        this.instance = instance;
    }


    public void enrichAnnotated(final String... args) {
        final Field[] declaredFields = instance.getClass().getDeclaredFields();

        for (final Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Option.class)) {
                final Option option = declaredField.getAnnotation(Option.class);
                final String optionWithPrefix = OPTION_PREFIX + option.option();

                final boolean containsOption = Arrays.asList(args).contains(optionWithPrefix);
                if (containsOption) {
                    declaredField.setAccessible(true);
                    try {
                        declaredField.set(instance, true);
                    }
                    catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    break;
                }
            }
        }
    }
}
