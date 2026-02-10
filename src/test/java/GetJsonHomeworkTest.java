import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

public class GetJsonHomeworkTest {

    @Test
    public void TestGetJsonHomework(){

       JsonPath response = RestAssured
               .when()
               .get("https://playground.learnqa.ru/api/get_json_homework")
               .then()
               .extract()
               .jsonPath();

        System.out.print("Второе сообщение: ");
        System.out.println(response.getString("messages[1].message"));
    }
}

