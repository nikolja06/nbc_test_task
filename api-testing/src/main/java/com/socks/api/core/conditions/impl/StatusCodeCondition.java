package com.socks.api.core.conditions.impl;

import com.socks.api.core.conditions.Condition;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class StatusCodeCondition implements Condition {
    private final int statusCode;

    @Override
    public void check(Response response) {
        response.then().assertThat().statusCode(statusCode);
    }
}
