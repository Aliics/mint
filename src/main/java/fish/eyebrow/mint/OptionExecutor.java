package fish.eyebrow.mint;

import java.lang.reflect.InvocationTargetException;

public interface OptionExecutor {

    Class<CalledOption> annotationType();


    void execute(final Object object, final String[] args) throws InvocationTargetException, IllegalAccessException;
}
