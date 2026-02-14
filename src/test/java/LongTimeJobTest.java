import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LongTimeJobTest {

    @Test
    public void testLongTimeJob() throws InterruptedException {
        // Создание задачи
        JsonPath response = RestAssured
                .given()
                .when()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        //response.prettyPrint();
        String token = response.get("token");
        Integer secondsToWait = response.get("seconds");

        //еще один запрос со значением token, проверяем статус выполнения задачи с заполненным token
        Map<String,String> params = new HashMap<>();
        params.put("token", token);
        response = RestAssured
                .given()
                .queryParams(params)
                .when()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        //response.prettyPrint();
        String status = response.get("status");
        if (Objects.equals(status, "Job is NOT ready")) {
            System.out.print("Статус ответа до окончания выполнения задачи корректный: ");
            System.out.println(status);
        }
        else{
            System.out.print("Статус ответа до окончания выполнения задачи не корректный");
        }

        // засыпаем на нужное количество секунд + 1 (на всякий случай?)
        Thread.sleep((secondsToWait + 1) * 1000);
        response = RestAssured
                .given()
                .queryParams(params)
                .when()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        //response.prettyPrint();
        // проверяем статус выполнения задачи после истечения времени ее выполнения
        status = response.get("status");
        if (Objects.equals(status, "Job is ready")) {
            System.out.print("Статус ответа после выполнения задачи корректный: ");
            System.out.println(status);
        }
        else{
            System.out.print("Статус ответа после выполнения задачи не корректный");
        }
        String result = response.get("result");
        if(response.get("result") != null){
            System.out.print("Поле result заполнено: ");
            System.out.println(result);
        }

    }

}



