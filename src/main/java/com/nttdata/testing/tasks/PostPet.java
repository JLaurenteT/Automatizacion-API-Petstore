package com.nttdata.testing.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class PostPet implements Task {

    private final String id, name, categoryName, status;

    public PostPet(String id, String name, String categoryName, String status) {
        this.id = id;
        this.name = name;
        this.categoryName = categoryName;
        this.status = status;
    }

    public static Performable withData(String id, String name, String categoryName, String status) {
        return instrumented(PostPet.class, id, name, categoryName, status);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String body = "{"
                + "\"id\":" + id + ","
                + "\"category\":{\"id\":1,\"name\":\"" + categoryName + "\"},"
                + "\"name\":\"" + name + "\","
                + "\"photoUrls\":[\"https://example.com/pet.jpg\"],"
                + "\"tags\":[{\"id\":1,\"name\":\"pet\"}],"
                + "\"status\":\"" + status + "\""
                + "}";

        actor.attemptsTo(
                Post.to("/pet")
                        .with(requestSpecification -> requestSpecification
                                .contentType(ContentType.JSON)
                                .header("Accept", "application/json")
                                .body(body)
                                .log().all())
        );

        SerenityRest.lastResponse().body().prettyPrint();
        if (SerenityRest.lastResponse().statusCode() == 200) {
            actor.remember("petId", SerenityRest.lastResponse().path("id").toString());
        }
    }
}
