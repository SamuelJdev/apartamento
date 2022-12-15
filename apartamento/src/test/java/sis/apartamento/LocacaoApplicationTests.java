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
class LocacaoApplicationTests {
    @LocalServerPort
    private int port;

    @Test
    void deveRetornaLocacaoPost200() throws IOException {
        RestAssured.given()
                .basePath("/v1/locacoes")
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(FileUtils.readFileContent("locacoes-post-request.json"))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornaAptoOcupadoException() throws IOException {
        RestAssured.given()
                .basePath("/v1/locacoes")
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(FileUtils.readFileContent("locacoes-ocupada-post-request.json"))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    void retornaLocacaoAtualizado() throws IOException {
        RestAssured.given()
                .basePath("/v1/locacoes/{id}")
                .pathParam("id", 1)
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(FileUtils.readFileContent("put-locacoes.json"))
                .when()
                .put()
                .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    void retornaLocacao200_peloCodigo() {
        RestAssured.given()
                .basePath("/v1/locacoes/{id}")
                .port(port)
                .accept(ContentType.JSON)
                .pathParam("id", 1)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    void retornaLocacao404_LocacaoInexistente() {
        RestAssured.given()
                .basePath("/v1/locacoes/{id}")
                .port(port)
                .accept(ContentType.JSON)
                .pathParam("id", 19)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
    @Test
    void retornaLocacaoDeletada() throws IOException {
        RestAssured.given()
                .basePath("/v1/locacoes/{id}")
                .pathParam("id", 7)
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete()
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}