package org.example;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class RequestMethodsTest {
    @Test
    public void testGet() {
        given()
                .queryParam("foo1", "bar1")
                .queryParam("foo2", "bar2")

                .when()
                .get("https://postman-echo.com/get")

                .then()
                .statusCode(200)
                .body("args.foo1", equalTo("bar1"))
                .body("args.foo2", equalTo("bar2"));


    }

    @Test
    public void testPost(){
        String body = "{\"name\": \"Jura\"}";
        given()
                .contentType(ContentType.JSON)
                .body(body)

                .when()
                .post("https://postman-echo.com/post")

                .then()
                .statusCode(200)
                .body("data.name", equalTo("Jura"));



    }

    @Test
    public void testPut(){
        String body ="{\"status\": \"updated\"}";
        given()
                .contentType(ContentType.JSON)
                .body(body)

                .when()
                .put("https://postman-echo.com/put")

                .then()
                .statusCode(200)
                .body("data.status", equalTo("updated"));


    }

    @Test
    public void testPatch(){
        String body = "{\"patch\": \"done\"}";
        given()
                .contentType(ContentType.JSON)
                .body(body)

                .when()
                .patch("https://postman-echo.com/patch")

                .then()
                .statusCode(200)
                .body("data.patch", equalTo("done"));
    }

    @Test
    public void testDelete(){
        when()
                .delete("https://postman-echo.com/delete")

                .then()
                .statusCode(200)
                .body("url", equalTo("https://postman-echo.com/delete"));


    }

}


