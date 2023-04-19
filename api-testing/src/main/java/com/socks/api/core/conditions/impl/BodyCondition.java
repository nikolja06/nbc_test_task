package com.socks.api.core.conditions.impl;

import com.socks.api.core.conditions.Condition;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hamcrest.Matcher;

@ToString
@RequiredArgsConstructor
public class BodyCondition implements Condition {

    private final Matcher matcher;

    @Override
    public void check(Response response) {
        response.then().assertThat().body("", matcher);
    }
}
