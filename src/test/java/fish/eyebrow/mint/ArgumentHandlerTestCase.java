package fish.eyebrow.mint;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import fish.eyebrow.mint.annotation.Option;
import fish.eyebrow.mint.exception.InvalidAnnotationTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@SuppressWarnings("unused")
class ArgumentHandlerTestCase {

    private ArgumentHandler argumentHandler;

    @Option(option = "foobar")
    private boolean booleanOptionTestEnabled;

    @Option(option = "fizzbuzz", requiresParam = true)
    private String stringOptionTestParam;


    @BeforeEach
    void setUp() {
        argumentHandler = new ArgumentHandler(this);
    }


    @Test
    void shouldSetBooleanToTrueWhenGivenAsOption() throws IOException, IllegalAccessException, InvalidAnnotationTypeException {
        final String[] fooBarEnabled = new String[] { "--foobar" };
        argumentHandler.enrichAnnotated(fooBarEnabled);

        assertThat(booleanOptionTestEnabled).isTrue();
    }


    @Test
    void shouldNotTouchBooleanOnlyOtherOptionsAreGiven() throws IOException, IllegalAccessException, InvalidAnnotationTypeException {
        final String[] fizzBuzzEnabled = new String[] { "--fizzbuzz", "foobar" };
        argumentHandler.enrichAnnotated(fizzBuzzEnabled);

        assertThat(booleanOptionTestEnabled).isFalse();
    }


    @Test
    void shouldNotTouchBooleanWhenNoOptionsAreGiven() throws IOException, IllegalAccessException, InvalidAnnotationTypeException {
        argumentHandler.enrichAnnotated();

        assertThat(booleanOptionTestEnabled).isFalse();
    }


    @Test
    void shouldSetStringToParamGivenWithOption() throws IOException, IllegalAccessException, InvalidAnnotationTypeException {
        final String[] fizzBuzzEnabled = new String[] { "--fizzbuzz", "foobar" };
        argumentHandler.enrichAnnotated(fizzBuzzEnabled);

        assertThat(stringOptionTestParam).isEqualTo("foobar");
    }


    @Test
    void shouldThrowErrorWhenOptionExpectsRequiresAParam() throws InvalidAnnotationTypeException {
        try {
            final String[] fizzBuzzEnabled = new String[] { "--fizzbuzz" };
            argumentHandler.enrichAnnotated(fizzBuzzEnabled);
            throw new AssertionError();
        }
        catch (Exception e) {
            assertThat(e).isInstanceOf(IOException.class);
        }
    }


    @Test
    void shouldThrowErrorWhenOptionParamIsAnotherParam() throws InvalidAnnotationTypeException {
        try {
            final String[] fizzBuzzEnabled = new String[] { "--fizzbuzz", "--foobar" };
            argumentHandler.enrichAnnotated(fizzBuzzEnabled);
            throw new AssertionError();
        }
        catch (Exception e) {
            assertThat(e).isInstanceOf(IOException.class);
        }
    }


    @Test
    void shouldSetBothFieldsWhenGivenBothOptions() throws IOException, IllegalAccessException, InvalidAnnotationTypeException {
        final String[] fizzBuzzEnabled = new String[] { "--foobar", "--fizzbuzz", "foobar" };
        argumentHandler.enrichAnnotated(fizzBuzzEnabled);

        assertThat(booleanOptionTestEnabled).isTrue();
        assertThat(stringOptionTestParam).isEqualTo("foobar");
    }


    @Test
    void shouldSetBothFieldsWhenGivenBothOptionsAndRequiresParamFieldIsFirst() throws IOException, IllegalAccessException, InvalidAnnotationTypeException {
        final String[] fizzBuzzEnabled = new String[] { "--fizzbuzz", "foobar", "--foobar" };
        argumentHandler.enrichAnnotated(fizzBuzzEnabled);

        assertThat(booleanOptionTestEnabled).isTrue();
        assertThat(stringOptionTestParam).isEqualTo("foobar");
    }
}