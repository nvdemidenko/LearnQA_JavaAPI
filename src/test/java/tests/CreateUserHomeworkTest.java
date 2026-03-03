package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.DataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CreateUserHomeworkTest {
    @Test
    public void testCreateUserWithoutDogSymbol(){
        String email = "vinkotovexample.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAuth,400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Invalid email format");
    }

    @ParameterizedTest()
    @CsvSource(value = {
            "email", "username", "password", "firstName", "lastName"
    })
    public void testCreateUserWithoutAnyField(String fieldName) {
        Map<String, String> userData = new HashMap<>();
        userData.put("email", "vinkotov@example.com");
        userData.put("password", "123");
        userData.put("username", "learnqa");
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

        userData.remove(fieldName);
        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAuth,400);
        //System.out.println(responseCreateAuth.asString());
    }

    @ParameterizedTest()
    @CsvSource({"1", "251"})
    public void testCreateUserWithCustomNameLength(Integer fieldNameLength) {
        String email = DataGenerator.getRandomEmail();
        String login = generateRandomLogin(fieldNameLength);

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", "123");
        userData.put("username", login);
        userData.put("firstName", "learnqa");
        userData.put("lastName", "learnqa");

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAuth,400);
        //System.out.println(responseCreateAuth.asString());
    }

    private static String generateRandomLogin(Integer fieldNameLength) {
        // Алфавит возможных символов
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(fieldNameLength);
        for (int i = 0; i < fieldNameLength; ++i) {
            int c = random.nextInt(characters.length());
            sb.append(characters.charAt(c));
        }
        return sb.toString();
    }
}
