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


    @Test
    void shouldAssignPrimitiveTypesToTheirDefaultOnValues() throws IllegalAccessException {
        final String[] args = {
                "--boolean-option",
                "--integer-option",
                "--double-option",
                "--float-option",
                "--long-option",
                "--short-option",
                "--byte-option",
                "--char-option"
        };
        assignedOptionExecutor.execute(testClass, args);

        assertThat(testClass.booleanOption).isTrue();
        assertThat(testClass.integerOption).isOne();
        assertThat(testClass.doubleOption).isOne();
        assertThat(testClass.floatOption).isOne();
        assertThat(testClass.longOption).isOne();
        assertThat(testClass.shortOption).isOne();
        assertThat(testClass.byteOption).isOne();
        assertThat(testClass.charOption).isEqualTo((char) 1);
    }


    @Test
    void shouldSetFieldValueToOptionWhenVariableFlagEnabled() throws IllegalAccessException {
        final String[] args = { "--variable-option", "foo" };
        assignedOptionExecutor.execute(testClass, args);

        assertThat(testClass.variableOption).isEqualTo("foo");
    }


    class TestClass {

        @AssignedOption(option = "variable-option", variable = true)
        String variableOption;

        @AssignedOption(option = "integer-option")
        int integerOption = 0;

        @AssignedOption(option = "double-option")
        double doubleOption = 0.0;

        @AssignedOption(option = "float-option")
        float floatOption = 0.0F;

        @AssignedOption(option = "long-option")
        long longOption = 0L;

        @AssignedOption(option = "short-option")
        short shortOption = 0;

        @AssignedOption(option = "byte-option")
        byte byteOption = 0;

        @AssignedOption(option = "char-option")
        char charOption = 0;

        @AssignedOption(option = "boolean-option")
        boolean booleanOption = false;
    }
}
