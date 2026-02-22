import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeWorkCookieTest {

    @Test
    public void testCookie(){
        Response responseGetCookie = RestAssured
                .given()
                .post("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();

        //System.out.println(responseGetCookie.getCookies());
        Map<String, String> cookies = responseGetCookie.cookies();
        assertTrue(!cookies.isEmpty(), "Cookie set is empty");

        for (Map.Entry<String, String> cookie: cookies.entrySet()) {
            String cookieName = cookie.getKey();
            String cookieValue = cookie.getValue();
            assertTrue(StringUtils.isNotBlank(cookieName), "Cookie name is empty");
            assertTrue(StringUtils.isNotBlank(cookieValue), "Cookie value is empty");
            System.out.println("Cookie: " + cookieName);
            System.out.println("Value: " + cookieValue);
        }
    }
}

