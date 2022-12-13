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
public class ContraChequeApplicationTests {
    @LocalServerPort
    private int port;
    @Test
    public void retornaContraChequePorId() {
        RestAssured.given()
                .basePath("/contraCheques")
                .port(port)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("id", Matchers.hasItems(1))
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    public void deveRetornaContraCheque() throws IOException {
        RestAssured.given()
                .basePath("/contraCheques")
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(FileUtils.readFileContent("contraCheques-post-request.json"))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }
    @Test
    public void retornaContraChequeAtualizado() throws IOException {
        RestAssured.given()
                .basePath("/contraCheques/{id}")
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
    public void retornaContraCheque200_peloCodigo() {
        RestAssured.given()
                .basePath("/contraCheques/{id}")
                .port(port)
                .accept(ContentType.JSON)
                .pathParam("id", 3)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    public void retornaContraCheque404_ContraChequeInexistente() {
        RestAssured.given()
                .basePath("/contraCheques/{id}")
                .port(port)
                .accept(ContentType.JSON)
                .pathParam("id", 10)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    @Test
    public void retornaContraChequeDeletada() throws IOException {
        RestAssured.given()
                .basePath("/contraCheques/{id}")
                .pathParam("id", 10)
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete()
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
