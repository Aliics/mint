package fish.eyebrow.mint;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CalledOptionExecutor implements OptionExecutor<CalledOption> {

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

        for (int i = 0; i < args.length; i++) {
            final String arg = args[i];
            if (option.equals(arg) && !alreadyCalled) {
                if (method.getParameterCount() == 0) {
                    method.invoke(object);
                }
                else {
                    method.invoke(object, args[i + 1]);
                }

                alreadyCalled = annotation.singleCall();
            }
        }
    }
}
