package fish.eyebrow.mint;

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


    private class TestClass {

        @CalledOption(option = "test-called")
        void annotatedMethod() {
        }


        @CalledOption(option = "single-call-option", singleCall = true)
        void singleCallAnnotatedMethod() {
        }
    }
}
