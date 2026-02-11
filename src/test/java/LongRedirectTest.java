import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class LongRedirectTest {

    @Test
    public void TestLongRedirect(){
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeader1","value1");
        headers.put("myHeader2","value2");

        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        String locationHeader = response.getHeader("Location");
        System.out.println(locationHeader);
    }
}
