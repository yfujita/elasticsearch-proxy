package com.github.yfujita.elasticsearch.proxy.settings;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProxySettings {
    public int port;
    public String esHost;
    public int esPort;
    public String esSchema;

    public static ProxySettings load(final String settingsPath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(readAll(settingsPath), ProxySettings.class);
    }

    public static String readAll(String path) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String string = reader.readLine();
            while (string != null){
                builder.append(string + System.getProperty("line.separator"));
                string = reader.readLine();
            }
        }
        return builder.toString();
    }
}
