import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import javax.xml.stream.Location;
import java.util.HashMap;
import java.util.Map;

public class ShowHeadersTest {

    @Test
    public void TestStatusCodeRestAssured(){
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeader1","value1");
        headers.put("myHeader2","value2");

        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();

        response.prettyPrint();

        String locationHeader = response.getHeader("Location");
        System.out.println(locationHeader);
    }
}
