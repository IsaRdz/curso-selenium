import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLOutput;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PruebasApisRefactorizacion {

    @Before
    public void setup(){

        RestAssured.baseURI="https://reqres.in";
        RestAssured.basePath="/api";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    public void loginTest2(){
        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"\n" +
                        "}")
                .post("/login")
                .then()
                .statusCode(200)
                .body("token",notNullValue());
    }

    @Test
    public void createUser(){
        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
                .post("/users")
                .then()
                .statusCode(201)
                .body("name",equalTo("morpheus"));
    }

    @Test
    public void listUser(){
        given()
                .contentType(ContentType.JSON)
                .param("page",2) //le indicamos el parametro de la url y su valor
                .get("/users")
                .then()
                .statusCode(200)
                .body("total",equalTo(12))
                .and()
                .body("data.id",hasItems(7,8,9,10,11,12));
    }

    @Test
    public void putUser(){
        JsonPath jsonPath = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
                .post("/users")
                .then()
                .extract().jsonPath();

        String idUser = jsonPath.get("id".toString());

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"job\": \"Tester\"\n}")
                .put("/users/"+idUser)
                .then()
                .statusCode(200)
                .and()
                .body("job",equalTo("Tester"));

    }

    @Test
    public void deleteUser(){
        JsonPath jsonPath = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
                .post("/users")
                .then()
                .extract().jsonPath();

        String userID = jsonPath.get("id".toString());

        RestAssured.given()
                .contentType(ContentType.JSON)
                .delete("/users"+userID)
                .then()
                .statusCode(204);

    }

}
