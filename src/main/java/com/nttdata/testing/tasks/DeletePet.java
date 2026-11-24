package com.nttdata.testing.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class DeletePet implements Task {

    public static Performable created() {
        return instrumented(DeletePet.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String petId = actor.recall("petId");

        actor.attemptsTo(
                Delete.from("/pet/" + petId)
                        .with(requestSpecification -> requestSpecification
                                .contentType(ContentType.JSON)
                                .header("Accept", "application/json")
                                .header("api_key", "special-key")
                                .log().all())
        );

        SerenityRest.lastResponse().body().prettyPrint();
    }
}
