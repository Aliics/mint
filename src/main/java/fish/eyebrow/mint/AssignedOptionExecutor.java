package fish.eyebrow.mint;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AssignedOptionExecutor implements OptionExecutor<AssignedOption> {

    private final static Map<Class, Object> PRIMITIVE_DEFAULTS = new HashMap<Class, Object>() {
        {
            put(boolean.class, true);
            put(int.class, 1);
            put(double.class, 1.0);
            put(float.class, 1.0F);
            put(long.class, 1L);
            put(short.class, (short) 1);
            put(byte.class, (byte) 1);
            put(char.class, (char) 1);
        }
    };


    @Override
    public Class<AssignedOption> annotationType() {
        return AssignedOption.class;
    }


    @Override
    public void execute(final Object object, final String[] args) throws IllegalAccessException {
        final Field[] fields = object.getClass().getDeclaredFields();
        for (final Field field : fields) {
            final AssignedOption annotation = field.getAnnotation(annotationType());
            if (field.isAnnotationPresent(annotationType())) {
                assignAnnotated(object, args, field, annotation);
            }
        }
    }


    private void assignAnnotated(final Object object, final String[] args, final Field field, final AssignedOption annotation) throws IllegalAccessException {
        final String option = annotation.prefix() + annotation.option();
        for (int i = 0; i < args.length; i++) {
            final String arg = args[i];
            if (arg.equals(option)) {
                if (!annotation.variable()) {
                    assignWithPrimitiveDefaults(object, field);
                }
                else {
                    field.set(object, args[i + 1]);
                }
            }
        }
    }


    private void assignWithPrimitiveDefaults(final Object object, final Field field) throws IllegalAccessException {
        for (final Class primitiveDefault : PRIMITIVE_DEFAULTS.keySet()) {
            if (primitiveDefault.equals(field.getType())) {
                final Object defaultValue = PRIMITIVE_DEFAULTS.get(field.getType());
                field.set(object, defaultValue);
                break;
            }
        }
    }
}
