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
class ApartamentoApplicationTests {
    @LocalServerPort
    private int port;
    @Test
    void retornaTodosAptosPorId() {
        RestAssured.given()
                .basePath("/aptos")
                .port(port)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("numeroApto", Matchers.hasItems("1"))
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    void deveRetornaAptoSalvo() throws IOException {
        RestAssured.given()
                .basePath("/aptos")
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(FileUtils.readFileContent("apto-post-request.json"))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }
    @Test
    void retornaAptoAtualizado() throws IOException {
        RestAssured.given()
                .basePath("/aptos/{id}")
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
                .basePath("/aptos/{id}")
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
                .basePath("/aptos/{id}")
                .port(port)
                .accept(ContentType.JSON)
                .pathParam("id", 20)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    @Test
    void retornaAptosDeletada() throws IOException {
        RestAssured.given()
                .basePath("/aptos/{id}")
                .pathParam("id", 50)
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete()
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}