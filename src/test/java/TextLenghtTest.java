import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextLenghtTest {

    @Test
    public void textLengthTest(){
        String someTestString = "Some text for test";
        assertEquals(true, someTestString.length() > 15, "Test failed");
    }
}
