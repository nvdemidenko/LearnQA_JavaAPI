import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeworkHeadersTest {

    @Test
    public void testHeaders(){
        Response responseGetHeaders = RestAssured
                .given()
                .post("https://playground.learnqa.ru/api/homework_header")
                .andReturn();

        Headers headers = responseGetHeaders.getHeaders();
        assertTrue(headers.size()>0, "Headers set is empty");

        for (Header header : headers) {
            String headerName = header.getName();
            String headerValue = header.getValue();
            assertTrue(StringUtils.isNotBlank(headerName), "Header name is empty");
            assertTrue(StringUtils.isNotBlank(headerValue), "Header value is empty");
            System.out.print("Header: " + headerName);
            System.out.println("    Value: " + headerValue);
        }
    }
}

