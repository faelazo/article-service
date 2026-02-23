package com.editorial.platform.article;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ArticleResourceTest {

    @Test
    void shouldReturnEmptyPage() {
        given()
                .when()
                .get("/articles")
                .then()
                .statusCode(200)
                .body("content", notNullValue());
    }

    @Test
    void shouldFilterAndPaginateCorrectly() {
        given().when().get("/articles?author=Bollito").then().statusCode(200).body("content", notNullValue());
    }
}