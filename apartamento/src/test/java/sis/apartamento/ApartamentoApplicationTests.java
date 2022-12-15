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
class ApartamentoApplicationTests {
    @LocalServerPort
    private int port;

    @Test
    void deveRetornaAptoSalvo() throws IOException {
        RestAssured.given()
                .basePath("/v1/aptos")
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(FileUtils.readFileContent("apto-post-request.json"))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    void retornaAptoAtualizado() throws IOException {
        RestAssured.given()
                .basePath("/v1/aptos/{id}")
                .pathParam("id", 2) /* Ñ entendi o parametroValue*/
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(FileUtils.readFileContent("put-aptos.json"))
                .when()
                .put()
                .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    void retornaAptos200_peloCodigo() {
        RestAssured.given()
                .basePath("/v1/aptos/{id}")
                .port(port)
                .accept(ContentType.JSON)
                .pathParam("id", 2)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    void retornaApto404_AptoInexistente() {
        RestAssured.given()
                .basePath("/v1/aptos/{id}")
                .port(port)
                .accept(ContentType.JSON)
                .pathParam("id", 20)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
    @Test
    void retornaAptosDeletado() throws IOException {
        RestAssured.given()
                .basePath("/v1/aptos/{id}")
                .pathParam("id", 11)
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete()
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}