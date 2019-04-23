package fish.eyebrow.mint;

import fish.eyebrow.mint.annotation.Option;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;

public class ArgumentHandler {

    private static final String OPTION_PREFIX = "--";

    private static final Set<Class> ACCEPTED_CLASSES = Set.of(String.class, Boolean.class, boolean.class);

    private final Object instance;


    public ArgumentHandler(final Object instance) {
        this.instance = instance;
    }


    public void enrichAnnotated(final String... args) throws IOException, IllegalAccessException {
        final Field[] declaredFields = instance.getClass().getDeclaredFields();

        for (final Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Option.class)) {
                final Option option = declaredField.getAnnotation(Option.class);
                final String optionWithPrefix = OPTION_PREFIX + option.option();
                final boolean requiresParam = option.requiresParam();

                final int optionIndex = Arrays.asList(args).indexOf(optionWithPrefix);
                if (optionIndex != -1) {
                    declaredField.setAccessible(true);
                    final String optionParam = optionIndex < args.length - 1 ? args[optionIndex + 1] : null;
                    setAnnotatedField(declaredField, requiresParam, optionParam);
                }
            }
        }
    }


    private void setAnnotatedField(final Field declaredField, final boolean requiresParam, final String optionParam) throws IllegalAccessException, IOException {
        final Class fieldType = declaredField.getType();

        if (ACCEPTED_CLASSES.contains(fieldType)) {
            if (requiresParam) {
                if (optionParam != null) {
                    if (!optionParam.substring(0, 2).equals(OPTION_PREFIX)) {
                        declaredField.set(instance, optionParam);
                        return;
                    }
                }

                throw new IOException("Expected option parameter.");
            } else {
                declaredField.set(instance, true);
            }
        }
    }
}