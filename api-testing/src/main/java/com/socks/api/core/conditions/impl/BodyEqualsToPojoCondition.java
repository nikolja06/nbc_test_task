package com.socks.api.core.conditions.impl;

import com.socks.api.core.conditions.Condition;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.testng.Assert;

@ToString
@RequiredArgsConstructor
public class BodyEqualsToPojoCondition implements Condition {

    private final Object pojo;

    @Override
    public void check(Response response) {
        Object actual = response.as(pojo.getClass());
        Assert.assertEquals(pojo, actual, "Verify objects are equals");
    }
}
