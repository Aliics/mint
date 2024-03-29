package fish.eyebrow.mint;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;

class CalledOptionExecutorTestCase {

    private CalledOptionExecutor calledOptionExecutor;

    private TestClass testClass;


    @BeforeEach
    void setUp() {
        calledOptionExecutor = new CalledOptionExecutor();
        testClass = Mockito.mock(TestClass.class);
    }


    @Test
    void shouldInvokeAnnotatedMethodWhenAssociatedOptionIsGiven() throws InvocationTargetException, IllegalAccessException {
        final String[] args = { "--test-called" };
        calledOptionExecutor.execute(testClass, args);

        verify(testClass).annotatedMethod();
    }


    @Test
    void shouldInvokeAnnotatedMethodWhenAssociatedOptionIsGivenAmongstOthers() throws InvocationTargetException, IllegalAccessException {
        final String[] args = { "--bad-option", "--test-called", "--another-bad-option" };
        calledOptionExecutor.execute(testClass, args);

        verify(testClass).annotatedMethod();
    }


    @Test
    void shouldInvokeAnnotatedMethodWhenMarkedAsSingleCallButIsGivenMultipleTimes() throws InvocationTargetException, IllegalAccessException {
        final String[] args = { "--single-call-option", "--single-call-option" };
        calledOptionExecutor.execute(testClass, args);

        verify(testClass, times(1)).singleCallAnnotatedMethod();
    }


    @Test
    void shouldInvokeAnnotatedMethodsWithVaryingConditions() throws InvocationTargetException, IllegalAccessException {
        final String[] args = { "--single-call-option", "--single-call-option", "--test-called" };
        calledOptionExecutor.execute(testClass, args);

        verify(testClass).annotatedMethod();
        verify(testClass, times(1)).singleCallAnnotatedMethod();
    }


    @Test
    void shouldInvokeAnnotatedMethodWithParameter() throws InvocationTargetException, IllegalAccessException {
        final String[] args = { "--param-call-option", "foo" };
        calledOptionExecutor.execute(testClass, args);

        verify(testClass).paramCallAnnotatedMethod("foo");
    }


    @Test
    void shouldThrowTypeExceptionWhenMethodWithParamIsCalledWithInvalidOption() {
        final String[] args = { "--param-call-option-int", "foo" };

        assertThrows(IllegalArgumentException.class, () -> calledOptionExecutor.execute(testClass, args));
        verify(testClass, times(0)).paramCallAnnotatedMethodInt(anyInt());
    }


    private class TestClass {

        @CalledOption(option = "test-called")
        void annotatedMethod() {
        }


        @CalledOption(option = "single-call-option", singleCall = true)
        void singleCallAnnotatedMethod() {
        }


        @CalledOption(option = "param-call-option")
        void paramCallAnnotatedMethod(final String ignore) {
        }


        @CalledOption(option = "param-call-option-int")
        void paramCallAnnotatedMethodInt(final int ignore) {
        }
    }
}
