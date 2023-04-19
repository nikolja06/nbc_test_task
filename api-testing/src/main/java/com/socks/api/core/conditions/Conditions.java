package com.socks.api.core.conditions;

import com.socks.api.core.conditions.impl.*;
import lombok.experimental.UtilityClass;
import org.hamcrest.Matcher;

@UtilityClass
public class Conditions {

    public StatusCodeCondition statusCode(int code) {
        return new StatusCodeCondition(code);
    }

    public BodyCondition body(Matcher matcher) {
        return new BodyCondition(matcher);
    }

    public BodyEqualsToPojoCondition bodyEqualsToPojo(Object pojo) {
        return new BodyEqualsToPojoCondition(pojo);
    }

    public SchemaValidationCondition schema(String fileName) {
        return new SchemaValidationCondition(fileName);
    }

    public BodyFieldCondition bodyField(String jsonPath, Matcher matcher) {
        return new BodyFieldCondition(jsonPath, matcher);
    }
}
