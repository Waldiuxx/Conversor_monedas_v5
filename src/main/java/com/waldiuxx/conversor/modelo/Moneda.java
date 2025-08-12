package com.waldiuxx.conversor.modelo;

public record Moneda(String codigo, String nombre) {
    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
}