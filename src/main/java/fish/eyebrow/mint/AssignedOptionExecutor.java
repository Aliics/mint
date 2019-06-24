package fish.eyebrow.mint;

import java.lang.reflect.Field;

public class AssignedOptionExecutor implements OptionExecutor<AssignedOption> {

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
        for (final String arg : args) {
            if (arg.equals(option)) {
                field.setBoolean(object, true);
            }
        }
    }
}
