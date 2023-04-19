package com.socks.api.core.conditions.impl;

import com.socks.api.core.conditions.Condition;
import com.socks.api.core.util.ResourceHelper;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.ToString;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;

@ToString
@RequiredArgsConstructor
public class SchemaValidationCondition implements Condition {

    private final String fileName;

    @SneakyThrows
    @Override
    public void check(Response response) {
        String strJson = response.body().prettyPrint();
        Object json = strJson.startsWith("{") ? new JSONObject(strJson) : new JSONArray(strJson);
        String strSchema = new ResourceHelper().getFileFromResource("schema/" + fileName + ".json");
        Schema schema = SchemaLoader.load(new JSONObject(strSchema));
        schema.validate(json);
    }
}
