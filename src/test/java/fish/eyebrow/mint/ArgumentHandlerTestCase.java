package fish.eyebrow.mint;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import fish.eyebrow.mint.annotation.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArgumentHandlerTestCase {

    private ArgumentHandler argumentHandler;

    @Option(option = "foobar", type = Boolean.class)
    private boolean booleanOptionTestEnabled;


    @BeforeEach
    void setUp() {
        argumentHandler = new ArgumentHandler(this);
    }


    @Test
    void shouldSetBooleanToTrueWhenGivenAsOption() {
        final String[] fooBarEnabled = new String[] { "--foobar" };
        argumentHandler.enrichAnnotated(fooBarEnabled);

        assertThat(booleanOptionTestEnabled).isTrue();
    }


    @Test
    void shouldNotTouchBooleanOnlyOtherOptionsAreGiven() {
        final String[] fizzBuzzEnabled = new String[] { "--fizzbuzz" };
        argumentHandler.enrichAnnotated(fizzBuzzEnabled);

        assertThat(booleanOptionTestEnabled).isFalse();
    }


    @Test
    void shouldNotTouchBooleanWhenNoOptionsAreGiven() {
        argumentHandler.enrichAnnotated();

        assertThat(booleanOptionTestEnabled).isFalse();
    }
}