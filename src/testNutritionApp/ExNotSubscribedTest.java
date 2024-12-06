package testNutritionApp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nutritionApp.ExNotSubscribed;

/**
 * Test class for the ExNotSubscribed exception to achieve 100% coverage.
 */
class ExNotSubscribedTest {

    @BeforeEach
    void setUp() throws Exception {
        // Setup before each test if necessary
    }

    @AfterEach
    void tearDown() throws Exception {
        // Cleanup after each test if necessary
    }

    /**
     * Test the parameterless constructor of ExNotSubscribed.
     * It should set the default message "This service is limited to subscribers only".
     */
    @Test
    void testDefaultConstructor() {
        ExNotSubscribed exception = new ExNotSubscribed();
        assertEquals("This service is limited to subscribers only", exception.getMessage(),
                "Default constructor should set message to 'This service is limited to subscribers only'");
    }

    /**
     * Test the constructor of ExNotSubscribed that accepts a custom message.
     * It should set the message to the provided string.
     */
    @Test
    void testParameterizedConstructor() {
        String customMessage = "Custom not subscribed message.";
        ExNotSubscribed exception = new ExNotSubscribed(customMessage);
        assertEquals(customMessage, exception.getMessage(),
                "Parameterized constructor should set message to the provided custom message.");
    }

    /**
     * Test that ExNotSubscribed can be thrown and caught with the default message.
     */
    @Test
    void testThrowDefaultConstructor() {
        try {
            throw new ExNotSubscribed();
        } catch (ExNotSubscribed e) {
            assertEquals("This service is limited to subscribers only", e.getMessage(),
                    "Caught exception should have message 'This service is limited to subscribers only'");
        }
    }

    /**
     * Test that ExNotSubscribed can be thrown and caught with a custom message.
     */
    @Test
    void testThrowParameterizedConstructor() {
        String customMessage = "Another custom not subscribed message.";
        try {
            throw new ExNotSubscribed(customMessage);
        } catch (ExNotSubscribed e) {
            assertEquals(customMessage, e.getMessage(),
                    "Caught exception should have the custom message provided.");
        }
    }

    /**
     * Test the inheritance hierarchy of ExNotSubscribed.
     * It should be an instance of Exception.
     */
    @Test
    void testExceptionInheritance() {
        ExNotSubscribed exception = new ExNotSubscribed();
        assertTrue(exception instanceof Exception, "ExNotSubscribed should inherit from Exception.");
    }

    /**
     * Test that ExNotSubscribed is not null after instantiation.
     */
    @Test
    void testExceptionNotNull() {
        ExNotSubscribed exception1 = new ExNotSubscribed();
        ExNotSubscribed exception2 = new ExNotSubscribed("Test message");
        assertNotNull(exception1, "Exception instantiated with default constructor should not be null.");
        assertNotNull(exception2, "Exception instantiated with parameterized constructor should not be null.");
    }

    /**
     * Test the stack trace information of ExNotSubscribed.
     * Ensures that the stack trace is captured correctly.
     */
    @Test
    void testStackTrace() {
        try {
            throw new ExNotSubscribed("Stack trace test.");
        } catch (ExNotSubscribed e) {
            StackTraceElement[] stackTrace = e.getStackTrace();
            assertTrue(stackTrace.length > 0, "Stack trace should contain at least one element.");
            assertEquals("testStackTrace", stackTrace[0].getMethodName(),
                    "First element in stack trace should be 'testStackTrace'.");
        }
    }
}
