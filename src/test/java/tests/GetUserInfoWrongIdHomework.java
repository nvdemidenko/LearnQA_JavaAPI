package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class GetUserInfoWrongIdHomework  extends BaseTestCase {
    @Test
    public void testGetUserDetailsAuthAsSameUser(){
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        //System.out.println(responseGetAuth.asString());
        Integer userId = responseGetAuth.jsonPath().getInt("user_id");
        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        Response responseUserData = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .get("https://playground.learnqa.ru/api/user/" + (userId + 1))
                .andReturn();

        String[] expectedFields = {"firstName", "lastName", "email"};
        for(String expectedField : expectedFields) {
            Assertions.assertJsonHasNotField(responseUserData, expectedField);
        }
        Assertions.assertJsonHasField(responseUserData, "username");
    }
}
