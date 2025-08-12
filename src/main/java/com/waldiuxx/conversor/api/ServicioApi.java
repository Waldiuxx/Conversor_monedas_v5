package com.waldiuxx.conversor.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.net.http.*;
import java.util.Properties;

public class ServicioApi {
    private static final String LLAVE_API;
    private static final String URL_API;

    static {
        try {
            InputStream input = ServicioApi.class.getClassLoader().getResourceAsStream("config.properties");
            if (input == null) {
                throw new RuntimeException("Archivo config.properties no encontrado. Debe estar en src/main/resources/");
            }

            Properties prop = new Properties();
            prop.load(input);

            LLAVE_API = prop.getProperty("llave.api");
            URL_API = prop.getProperty("url.api");

            if (LLAVE_API == null || URL_API == null) {
                throw new RuntimeException("config.properties debe contener 'llave.api' y 'url.api'");
            }

            input.close();
        } catch (Exception e) {
            throw new RuntimeException("Error cargando configuración: " + e.getMessage(), e);
        }
    }

    public static JsonObject obtenerTasas(String monedaBase) throws Exception {
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(java.net.URI.create(URL_API + LLAVE_API + "/latest/" + monedaBase))
                .GET()
                .build();

        HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
        return JsonParser.parseString(respuesta.body()).getAsJsonObject();
    }
}