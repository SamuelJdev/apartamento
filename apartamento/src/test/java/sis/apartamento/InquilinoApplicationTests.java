package sis.apartamento;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sis.apartamento.util.FileUtils;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class InquilinoApplicationTests {
    @LocalServerPort
    private int port;
    @Test
    void retornaTodosInquilinoPorId() {
        RestAssured.given()
                .basePath("/inquilinos")
                .port(port)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("nome", Matchers.hasItems("Josue Souza"))
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    void deveRetornaInquilinos() throws IOException {
        RestAssured.given()
                .basePath("/inquilinos")
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(FileUtils.readFileContent("inquilinos-post-request.json"))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }
    @Test
    void retornaInquilinosAtualizado() throws IOException {
        RestAssured.given()
                .basePath("/inquilinos/{id}")
                .pathParam("id", 3) /* Ñ entendi o parametroValue*/
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(FileUtils.readFileContent("put-inquilinos.json"))
                .when()
                .put()
                .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    void retornaInquilinos200_peloCodigo() {
        RestAssured.given()
                .basePath("/inquilinos/{id}")
                .port(port)
                .accept(ContentType.JSON)
                .pathParam("id", 3)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    void retornaInquilinos404_InquilinosInexistente() {
        RestAssured.given()
                .basePath("/inquilinos/{id}")
                .port(port)
                .accept(ContentType.JSON)
                .pathParam("id", 5)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    @Test
    void retornainquilinosDeletada() throws IOException {
        RestAssured.given()
                .basePath("/inquilinos/{id}")
                .pathParam("id", 5)
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete()
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
