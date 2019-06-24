package fish.eyebrow.mint;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssignedOptionExecutorTestCase {

    private AssignedOptionExecutor assignedOptionExecutor;

    private TestClass testClass;


    @BeforeEach
    void setUp() {
        assignedOptionExecutor = new AssignedOptionExecutor();
        testClass = new TestClass();
    }


    @Test
    void shouldSetBooleanFieldToTrueWhenWantedOptionIsGiven() throws IllegalAccessException {
        final String[] args = { "--boolean-option" };
        assignedOptionExecutor.execute(testClass, args);

        assertThat(testClass.booleanOption).isTrue();
    }


    @Test
    void shouldNotTouchFieldsWhenOptionsGivenDoNotContainAnnotated() throws IllegalAccessException {
        final String[] args = { "--bad-boolean-option", "--what-is-this" };
        assignedOptionExecutor.execute(testClass, args);

        assertThat(testClass.booleanOption).isFalse();
    }


    @Test
    void shouldAssignFieldWhenOptionGivenIsAmongstBadOptions() throws IllegalAccessException {
        final String[] args = { "--bad-boolean-option", "--boolean-option", "--what-is-this" };
        assignedOptionExecutor.execute(testClass, args);

        assertThat(testClass.booleanOption).isTrue();
    }


    class TestClass {

        @AssignedOption(option = "boolean-option")
        boolean booleanOption = false;
    }
}
