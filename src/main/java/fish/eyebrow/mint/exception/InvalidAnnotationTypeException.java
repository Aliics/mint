package fish.eyebrow.mint.exception;

import java.lang.reflect.Field;

public class InvalidAnnotationTypeException extends Throwable {
    public InvalidAnnotationTypeException(final Field field) {
        super(String.format("An invalid data type was assigned on annotated field: %s", field));
    }
}
