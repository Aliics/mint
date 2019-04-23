package fish.eyebrow.mint;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import fish.eyebrow.mint.annotation.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OptionalDataException;

class ArgumentHandlerTestCase {

    private ArgumentHandler argumentHandler;

    @Option(option = "foobar", type = Boolean.class)
    private boolean booleanOptionTestEnabled;

    @Option(option = "fizzbuzz", type = String.class, requiresParam = true)
    private String stringOptionTestParam;


    @BeforeEach
    void setUp() {
        argumentHandler = new ArgumentHandler(this);
    }


    @Test
    void shouldSetBooleanToTrueWhenGivenAsOption() throws IOException, IllegalAccessException {
        final String[] fooBarEnabled = new String[] { "--foobar" };
        argumentHandler.enrichAnnotated(fooBarEnabled);

        assertThat(booleanOptionTestEnabled).isTrue();
    }


    @Test
    void shouldNotTouchBooleanOnlyOtherOptionsAreGiven() throws IOException, IllegalAccessException {
        final String[] fizzBuzzEnabled = new String[] { "--fizzbuzz", "foobar" };
        argumentHandler.enrichAnnotated(fizzBuzzEnabled);

        assertThat(booleanOptionTestEnabled).isFalse();
    }


    @Test
    void shouldNotTouchBooleanWhenNoOptionsAreGiven() throws IOException, IllegalAccessException {
        argumentHandler.enrichAnnotated();

        assertThat(booleanOptionTestEnabled).isFalse();
    }


    @Test
    void shouldSetStringToParamGivenWithOption() throws IOException, IllegalAccessException {
        final String[] fizzBuzzEnabled = new String[] { "--fizzbuzz", "foobar" };
        argumentHandler.enrichAnnotated(fizzBuzzEnabled);

        assertThat(stringOptionTestParam).isEqualTo("foobar");
    }


    @Test
    void shouldThrowErrorWhenOptionExpectsRequiresAParam() {
        try {
            final String[] fizzBuzzEnabled = new String[] { "--fizzbuzz" };
            argumentHandler.enrichAnnotated(fizzBuzzEnabled);
            throw new IOException();
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IOException.class);
        }
    }
}