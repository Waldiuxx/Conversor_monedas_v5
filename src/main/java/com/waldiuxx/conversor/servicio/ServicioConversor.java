package com.waldiuxx.conversor.servicio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.waldiuxx.conversor.api.ServicioApi;
import com.waldiuxx.conversor.modelo.Moneda;
import com.waldiuxx.conversor.modelo.RegistroConversion;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ServicioConversor {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Método para obtener las monedas soportadas (Solución 1)
    public static List<Moneda> getMonedasSoportadas() {
        return List.of(
                new Moneda("USD", "Dólar Estadounidense"),
                new Moneda("EUR", "Euro"),
                new Moneda("JPY", "Yen Japonés"),
                new Moneda("GBP", "Libra Esterlina"),
                new Moneda("COP", "Peso Colombiano"),
                new Moneda("MXN", "Peso Mexicano")
        );
    }

    public static double convertir(double cantidad, String deMoneda, String aMoneda) throws Exception {
        // 1. Obtener tasas de conversión desde la API
        JsonObject tasas = ServicioApi.obtenerTasas(deMoneda);

        // 2. Validar que la moneda destino exista en la respuesta
        if (!tasas.has("conversion_rates") || !tasas.getAsJsonObject("conversion_rates").has(aMoneda)) {
            throw new RuntimeException("❌ Moneda no soportada: " + aMoneda);
        }

        // 3. Calcular el resultado
        double tasa = tasas.getAsJsonObject("conversion_rates").get(aMoneda).getAsDouble();
        double resultado = cantidad * tasa;

        // 4. Guardar el registro en JSON
        guardarJsonIndividual(deMoneda, aMoneda, cantidad, resultado);

        return resultado;
    }

    private static void guardarJsonIndividual(String deMoneda, String aMoneda, double cantidad, double resultado) {
        String nombreArchivo = generarNombreArchivo();
        RegistroConversion registro = new RegistroConversion(deMoneda, aMoneda, cantidad, resultado);

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            gson.toJson(registro, writer);
            System.out.println("✓ Registro guardado en: " + nombreArchivo);
        } catch (Exception e) {
            System.err.println("❌ Error al guardar JSON: " + e.getMessage());
        }
    }

    private static String generarNombreArchivo() {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return "conversion_" + timestamp + ".json";
    }
}