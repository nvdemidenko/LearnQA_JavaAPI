import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class RedirectUntilCode200Test {

    @Test
    public void TestLongRedirect() {

        int statusCode = 0;
        int i = 0;
        String redirectUrl = "https://playground.learnqa.ru/api/long_redirect";
        while (redirectUrl != null) {
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .get(redirectUrl)
                    .andReturn();
            statusCode = response.getStatusCode();
            //System.out.println(statusCode);
            if (statusCode == 200) {
                break;
            }
            redirectUrl = response.getHeader("Location");
            i++;
        }
        System.out.print("Количество попыток: ");
        System.out.println(i);
    }
}
