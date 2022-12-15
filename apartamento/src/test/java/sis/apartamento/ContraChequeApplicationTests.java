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
class ContraChequeApplicationTests {
    @LocalServerPort
    private int port;

    @Test
    void deveRetornaContraCheque() throws IOException {
        RestAssured.given()
                .basePath("/v1/contraCheques")
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(FileUtils.readFileContent("contraCheques-post-request.json"))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    void retornaContraChequeAtualizado() throws IOException {
        RestAssured.given()
                .basePath("/v1/contraCheques/{id}")
                .pathParam("id", 2) /* Ñ entendi o parametroValue*/
                .port(port)
                .contentType(ContentType.JSON)
                .body(FileUtils.readFileContent("put-contraCheques.json"))
                .when()
                .put()
                .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    void retornaContraCheque200_peloCodigo() {
        RestAssured.given()
                .basePath("/v1/contraCheques/{id}")
                .port(port)
                .accept(ContentType.JSON)
                .pathParam("id", 3)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    void retornaContraCheque404_ContraChequeInexistente() {
        RestAssured.given()
                .basePath("/v1/contraCheques/{id}")
                .port(port)
                .accept(ContentType.JSON)
                .pathParam("id", 10)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
    @Test
    void retornaContraChequeDeletada() throws IOException {
        RestAssured.given()
                .basePath("/v1/contraCheques/{id}")
                .pathParam("id", 8)
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete()
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
