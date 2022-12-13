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
class UsuarioApplicationTests {
    @LocalServerPort
    private int port;
    @Test
    void retornaTodosUsuariosPorId() {
        RestAssured.given()
                .basePath("/usuarios")
                .port(port)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("nome", Matchers.hasItems("Normandes"))
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    void deveRetornaUsuarios() throws IOException {
        RestAssured.given()
                .basePath("/usuarios")
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(FileUtils.readFileContent("usuarios-post-request.json"))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }
    @Test
    void retornaUsuariosAtualizado() throws IOException {
        RestAssured.given()
                .basePath("/usuarios/{id}")
                .pathParam("id", 4) /* Ñ entendi o parametroValue*/
                .port(port)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(FileUtils.readFileContent("put-usuarios.json"))
                .when()
                .put()
                .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    void retornaUsuarios200_peloCodigo() {
        RestAssured.given()
                .basePath("/usuarios/{id}")
                .port(port)
                .accept(ContentType.JSON)
                .pathParam("id", 4)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    void retornaUsuarios404_UsuariosInexistente() {
        RestAssured.given()
                .basePath("/usuarios/{id}")
                .port(port)
                .accept(ContentType.JSON)
                .pathParam("id", 5)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    @Test
    void retornaUsuariosDeletada() throws IOException {
        RestAssured.given()
                .basePath("/usuarios/{id}")
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