package com.editorial.platform.article;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import jakarta.persistence.EntityManager;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ArticleResourceTest {

    @Inject
    EntityManager em;

    @BeforeEach
    @Transactional
    void cleanDatabase() {
        em.createQuery("delete from Article").executeUpdate();
    }

    void createArticle(int amount){
        for (int i = 0; i < amount; i++){
            String json = String.format("""
            {
                "title": "Clean Architecture %d",
                "author": "Robert Martin"
            }
            """, i);

            given()
                    .contentType("application/json")
                    .body(json)
                    .when()
                    .post("/articles")
                    .then()
                    .statusCode(201);
        }
    }

    @Test
    void shouldReturnEmptyPageInitially() {
        when()
                .get("/articles")
                .then()
                .statusCode(200)
                .body("content.size()", equalTo(0));
    }

    @Test
    void shouldCreateArticleAndThenRetrieveIt() {
        String json = """
        {
            "title": "Clean Architecture",
            "author": "Robert Martin"
        }
        """;

        // Create article
        given()
                .contentType("application/json")
                .body(json)
                .when()
                .post("/articles")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("title", equalTo("Clean Architecture"))
                .body("author", equalTo("Robert Martin"));

        // Retrieve list and verify it contains 1 element
        when()
                .get("/articles")
                .then()
                .statusCode(200)
                .body("totalElements", equalTo(1))
                .body("content.size()", equalTo(1));
    }

    @Test
    void shouldReturn400WhenTitleIsNull() {
        String json = """
        {
            "author": "Someone"
        }
        """;

        given()
                .contentType("application/json")
                .body(json)
                .when()
                .post("/articles")
                .then()
                .statusCode(400);
    }

    @Test
    void shouldFilterbyAuthor(){
        String json1 = """
        {
            "title": "Clean Architecture",
            "author": "Robert Martin"
        }
        """;
        String json2 = """
        {
            "title": "Clean Architecture",
            "author": "Rafael Martin"
        }
        """;
        given().contentType("application/json").body(json1).when().post("/articles").then().statusCode(201);
        given().contentType("application/json").body(json2).when().post("/articles").then().statusCode(201);

        when().get("/articles?author=Robert Martin").then()
                .statusCode(200)
                .body("totalElements", equalTo(1))
                .body("content.size()", equalTo(1))
                .body("content[0].author", equalTo("Robert Martin"));
    }

    @Test
    void shouldPaginateFirstPageCorrectly() {

        createArticle(15);

        given().when().get("/articles?page=0&size=10").then().statusCode(200).body("totalElements", equalTo(15)).body("content.size()", equalTo(10)).body("totalPages", equalTo(2));
    }


    @Test
    void shouldPaginateSecondPageCorrectly() {

        createArticle(15);

        given().when().get("/articles?page=1&size=10").then().statusCode(200).body("totalElements", equalTo(15)).body("content.size()", equalTo(5)).body("totalPages", equalTo(2))
                .body("page", equalTo(1)).body("content[0].title", equalTo("Clean Architecture 10"));;


    }
/*
    @Test
    void shouldFailWhenTitleIsNull() {
        given()
                .contentType("application/json")
                .body("""
              {
                  "author": "Rafael"
              }
              """)
                .when()
                .post("/articles")
                .then()
                .statusCode(500);
    }*/
}