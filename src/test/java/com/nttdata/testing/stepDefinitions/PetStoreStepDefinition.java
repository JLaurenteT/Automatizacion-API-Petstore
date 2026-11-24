package com.nttdata.testing.stepDefinitions;

import com.nttdata.testing.questions.ResponseCode;
import com.nttdata.testing.questions.ResponseField;
import com.nttdata.testing.questions.ResponseListSize;
import com.nttdata.testing.tasks.DeletePet;
import com.nttdata.testing.tasks.GetPet;
import com.nttdata.testing.tasks.PostPet;
import com.nttdata.testing.tasks.PutPet;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class PetStoreStepDefinition {

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("el {actor} establece el endpoint de petstore")
    public void elActorEstableceElEndpointDePetstore(Actor actor) {
        actor.whoCan(CallAnApi.at("https://petstore.swagger.io/v2"));
    }

    @When("el actor crea una mascota con id {string} nombre {string} categoria {string} y estado {string}")
    public void elActorCreaUnaMascotaConDatos(String id, String name, String category, String status) {
        theActorInTheSpotlight().attemptsTo(PostPet.withData(id, name, category, status));
    }

    @When("el actor consulta las mascotas con estado {string}")
    public void elActorConsultaLasMascotasConEstado(String status) {
        theActorInTheSpotlight().attemptsTo(GetPet.fromEndpoint("/pet/findByStatus?status=" + status));
    }

    @When("el actor consulta la mascota con id {string}")
    public void elActorConsultaLaMascotaConId(String petId) {
        theActorInTheSpotlight().attemptsTo(GetPet.fromEndpoint("/pet/" + petId));
    }

    @When("el actor actualiza la mascota con nombre {string} categoria {string} y estado {string}")
    public void elActorActualizaLaMascotaConDatos(String name, String category, String status) {
        theActorInTheSpotlight().attemptsTo(PutPet.withData(name, category, status));
    }

    @When("el actor elimina la mascota creada")
    public void elActorEliminaLaMascotaCreada() {
        theActorInTheSpotlight().attemptsTo(DeletePet.created());
    }

    @Then("el codigo de respuesta debe ser {int}")
    public void elCodigoDeRespuestaDebeSer(int responseCode) {
        theActorInTheSpotlight().should(
                seeThat("El codigo de respuesta", ResponseCode.getStatus(), equalTo(responseCode))
        );
    }

    @And("el campo {string} de la respuesta debe ser {string}")
    public void elCampoDeLaRespuestaDebeSer(String field, String expectedValue) {
        theActorInTheSpotlight().should(
                seeThat("El campo " + field, ResponseField.valueOf(field), equalTo(expectedValue))
        );
    }

    @And("el campo {string} de la respuesta debe coincidir con el id creado")
    public void elCampoDeLaRespuestaDebeCoincidirConElIdCreado(String field) {
        String petId = theActorInTheSpotlight().recall("petId");
        theActorInTheSpotlight().should(
                seeThat("El campo " + field, ResponseField.valueOf(field), equalTo(petId))
        );
    }

    @And("la lista de mascotas no debe estar vacia")
    public void laListaDeMascotasNoDebeEstarVacia() {
        theActorInTheSpotlight().should(
                seeThat("La cantidad de mascotas", ResponseListSize.count(), greaterThan(0))
        );
    }
}
