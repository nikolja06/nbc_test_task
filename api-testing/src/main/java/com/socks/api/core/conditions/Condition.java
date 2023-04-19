package com.socks.api.core.conditions;

import io.restassured.response.Response;

public interface Condition {

    void check(Response response);
}
