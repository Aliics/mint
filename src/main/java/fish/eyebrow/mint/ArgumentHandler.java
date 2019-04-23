package fish.eyebrow.mint;

import fish.eyebrow.mint.annotation.Option;

import java.io.OptionalDataException;
import java.lang.reflect.Field;
import java.util.Arrays;

public class ArgumentHandler {

    private static final String OPTION_PREFIX = "--";

    private final Object instance;


    public ArgumentHandler(final Object instance) {
        this.instance = instance;
    }


    public void enrichAnnotated(final String... args) throws OptionalDataException, IllegalAccessException {
        final Field[] declaredFields = instance.getClass().getDeclaredFields();

        for (int i = 0; i < declaredFields.length; i++) {
            final Field declaredField = declaredFields[i];
            if (declaredField.isAnnotationPresent(Option.class)) {
                final Option option = declaredField.getAnnotation(Option.class);
                final String optionWithPrefix = OPTION_PREFIX + option.option();
                final boolean requiresParam = option.requiresParam();

                final boolean containsOption = Arrays.asList(args).contains(optionWithPrefix);
                if (containsOption) {
                    declaredField.setAccessible(true);
                    setAnnotatedField(declaredField, requiresParam, declaredFields[i + 1]);

                    break;
                }
            }
        }
    }


    void setAnnotatedField(final Field declaredField, final boolean requiresParam, final Field optionParam) throws IllegalAccessException {
        // get declaredField type

        // check if declaredField need a param
        
        // set type based on field type
        declaredField.set(instance, true);
    }
}