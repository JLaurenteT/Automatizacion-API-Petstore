package com.nttdata.testing.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class GetPet implements Task {

    private final String endpoint;

    public GetPet(String endpoint) {
        this.endpoint = endpoint;
    }

    public static Performable fromEndpoint(String endpoint) {
        return instrumented(GetPet.class, endpoint);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource(endpoint)
                        .with(requestSpecification -> requestSpecification
                                .contentType(ContentType.JSON)
                                .header("Accept", "application/json")
                                .log().all())
        );

        SerenityRest.lastResponse().body().prettyPrint();
    }
}
