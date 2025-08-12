package com.waldiuxx.conversor.modelo;

public record RegistroConversion(
        String monedaOrigen,
        String monedaDestino,
        double cantidad,
        double resultado
) {}