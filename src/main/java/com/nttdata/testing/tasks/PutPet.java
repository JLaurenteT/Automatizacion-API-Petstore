package com.nttdata.testing.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class PutPet implements Task {

    private final String name, categoryName, status;

    public PutPet(String name, String categoryName, String status) {
        this.name = name;
        this.categoryName = categoryName;
        this.status = status;
    }

    public static Performable withData(String name, String categoryName, String status) {
        return instrumented(PutPet.class, name, categoryName, status);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String petId = actor.recall("petId");

        String body = "{"
                + "\"id\":" + petId + ","
                + "\"category\":{\"id\":1,\"name\":\"" + categoryName + "\"},"
                + "\"name\":\"" + name + "\","
                + "\"photoUrls\":[\"https://example.com/pet.jpg\"],"
                + "\"tags\":[{\"id\":1,\"name\":\"pet\"}],"
                + "\"status\":\"" + status + "\""
                + "}";

        actor.attemptsTo(
                Put.to("/pet")
                        .with(requestSpecification -> requestSpecification
                                .contentType(ContentType.JSON)
                                .header("Accept", "application/json")
                                .body(body)
                                .log().all())
        );

        SerenityRest.lastResponse().body().prettyPrint();
    }
}
