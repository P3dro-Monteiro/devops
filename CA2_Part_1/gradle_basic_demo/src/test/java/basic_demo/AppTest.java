package basic_demo;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    @Test public void testAppHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
        //Wrong Assert to Check If gradlew test command is running tests correctly
        //assertEquals(1, 0);
    }
}