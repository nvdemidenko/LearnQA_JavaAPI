import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JunitTest {

    @Test
    public void TestPassed(){
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/map")
                .andReturn();
        //response.prettyPrint();
        assertEquals(200, response.statusCode(), "Unexpected status code");
    }

    @Test
    public void TestFailed(){
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/map2")
                .andReturn();
        //response.prettyPrint();
        assertEquals(200, response.statusCode(), "Unexpected status code");
    }
}
