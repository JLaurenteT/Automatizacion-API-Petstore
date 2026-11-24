package com.nttdata.testing.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ResponseListSize implements Question<Integer> {

    public static Question<Integer> count() {
        return new ResponseListSize();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        return SerenityRest.lastResponse().jsonPath().getList("$").size();
    }
}
