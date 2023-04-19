package com.socks.api.core.util;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ResourceHelper {

    @SneakyThrows
    public String getFileFromResource(String path) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {
            return new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining());
        } catch (Exception e) {
            throw e;
        }
    }
}
