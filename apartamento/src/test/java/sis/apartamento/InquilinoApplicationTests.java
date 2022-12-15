package sis.apartamento;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
    void deveRetornaInquilinos() throws IOException {
        RestAssured.given()
                .basePath("/v1/inquilinos")
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(FileUtils.readFileContent("inquilinos-post-request.json"))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    void retornaInquilinosAtualizado() throws IOException {
        RestAssured.given()
                .basePath("/v1/inquilinos/{id}")
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
                .basePath("/v1/inquilinos/{id}")
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
                .basePath("/v1/inquilinos/{id}")
                .port(port)
                .accept(ContentType.JSON)
                .pathParam("id", 5)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
    @Test
    void retornainquilinosDeletada() throws IOException {
        RestAssured.given()
                .basePath("/v1/inquilinos/{id}")
                .pathParam("id", 12)
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete()
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
