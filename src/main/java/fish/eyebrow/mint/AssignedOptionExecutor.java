package fish.eyebrow.mint;

import java.lang.reflect.InvocationTargetException;

public class AssignedOptionExecutor implements OptionExecutor<AssignedOption> {

    @Override
    public Class<AssignedOption> annotationType() {
        return null;
    }


    @Override
    public void execute(final Object object, final String[] args) throws InvocationTargetException, IllegalAccessException {

    }
}
