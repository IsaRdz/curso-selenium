package stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import metodos.Parametro_url;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class StepDefinitions_login {

    public String myJson;
    public Parametro_url url;
    Response resp;

    @Given("Tengo un json con datos para el logueo de cuenta {string} y {string}")
    public void tengo_json_datos_usuario_ingreso(String email, String password){
        myJson = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}";
    }
    @When("^Hago la peticion POST para loguear un usuario al endpoint (.+)$")
    public void hago_peticion_post_login(String endpoint){
        url = new Parametro_url();
        resp= given().log().all().body(myJson)
                .contentType(ContentType.JSON)
                .when().baseUri(url.urlBase).post(endpoint)
                .then().extract().response();

        System.out.println("CODIGO ESTADO: " + resp.statusCode());
    }
    @Then("^Espero que la respuesta del servicio contenga codigo de estado (\\d+)$")
    public void espero_respuesta_contenga_codEdtado(int code){
        Assert.assertEquals(resp.statusCode(), code);
    }
    @And("Espero que el cuerpo de la respuesta contenga el token no nulo")
    public void espero_cuerpo_respuesta_token_no_nulo(){
        Assert.assertNotNull(resp.jsonPath().get("token"));
    }

}
