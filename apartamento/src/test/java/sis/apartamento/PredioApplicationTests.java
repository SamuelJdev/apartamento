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
class PredioApplicationTests {
    @LocalServerPort
    private int port;
    @Test
    void retornaTodosPrediosPorId() {
        RestAssured.given()
                .basePath("/predios")
                .port(port)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("numeroPredio", Matchers.hasItems("1A"))
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    void deveRetornaPredioSalvo() throws IOException {
        RestAssured.given()
                .basePath("/predios")
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(FileUtils.readFileContent("predio-post-request.json"))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    void retornaPredioAtualizado() throws IOException {
        RestAssured.given()
                .basePath("/predios/{id}")
                .pathParam("id", 2) /* Ñ entendi o parametroValue*/
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(FileUtils.readFileContent("put-predio.json"))
                .when()
                .put()
                .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    void retornaPredio200_peloCodigo() {
        RestAssured.given()
                .basePath("/predios/{id}")
                .port(port)
                .accept(ContentType.JSON)
                .pathParam("id", 2)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    void retornaPredio404_PredioInexistente() {
        RestAssured.given()
                .basePath("/predios/{id}")
                .port(port)
                .accept(ContentType.JSON)
                .pathParam("id", 6)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
    @Test
    void retornaPredioDeletada() throws IOException {
        RestAssured.given()
                .basePath("/predios/{id}")
                .pathParam("id", 5)
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete()
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
