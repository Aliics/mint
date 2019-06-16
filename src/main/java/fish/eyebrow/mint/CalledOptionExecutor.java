package fish.eyebrow.mint;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CalledOptionExecutor implements OptionExecutor {

    @Override
    public Class<CalledOption> annotationType() {
        return CalledOption.class;
    }


    @Override
    public void execute(final Object object, final String[] args) throws InvocationTargetException, IllegalAccessException {
        final Method[] methods = object.getClass().getDeclaredMethods();
        for (final Method method : methods) {
            final CalledOption annotation = method.getAnnotation(annotationType());
            if (annotation != null) {
                invokeCalledOptionMethod(object, args, method, annotation);
            }
        }
    }


    private void invokeCalledOptionMethod(final Object object, final String[] args, final Method method, final CalledOption annotation)
            throws IllegalAccessException, InvocationTargetException {
        final String option = annotation.prefix() + annotation.option();
        boolean alreadyCalled = false;

        for (final String arg : args) {
            if (option.equals(arg) && !alreadyCalled) {
                method.invoke(object);
                alreadyCalled = annotation.singleCall();
            }
        }
    }
}
