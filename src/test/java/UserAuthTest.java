import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.http.Headers;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserAuthTest {

    @Test
    public void testAuthUser(){
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responceGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        Map<String, String> cookies = responceGetAuth.getCookies();
        Headers headers = responceGetAuth.getHeaders();
        Integer userIdInAuth = responceGetAuth.jsonPath().getInt("user_id");

        assertEquals(200, responceGetAuth.getStatusCode(), "Unexpected status code");
        assertTrue(cookies.containsKey("auth_sid"),"Response does not have 'auth_id' cookie");
        assertTrue(headers.hasHeaderWithName("x-csrf-token"), "Response does not have 'x-csrf-token' header");
        assertTrue(responceGetAuth.jsonPath().getInt("user_id") > 0, "User_id should be greater than 0");

        JsonPath responceCheckAuth = RestAssured
                .given()
                .header("x-csrf-token", responceGetAuth.getHeader("x-csrf-token"))
                .cookie("auth_sid", responceGetAuth.getCookie("auth_sid"))
                .get("https://playground.learnqa.ru/api/user/auth")
                .jsonPath();

        Integer userIdOnCheck = responceCheckAuth.getInt("user_id");
        assertTrue(userIdOnCheck > 0, "Unexpected user_id: " + userIdOnCheck);

        assertEquals(
                userIdOnCheck,
                userIdInAuth,
                "User id from auth request does not equals user_id from check auth request"
        );
    }
}
