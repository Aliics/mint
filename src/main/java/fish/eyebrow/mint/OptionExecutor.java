package fish.eyebrow.mint;

import java.lang.reflect.InvocationTargetException;

public interface OptionExecutor<T> {

    Class<T> annotationType();


    void execute(final Object object, final String[] args) throws InvocationTargetException, IllegalAccessException;
}
