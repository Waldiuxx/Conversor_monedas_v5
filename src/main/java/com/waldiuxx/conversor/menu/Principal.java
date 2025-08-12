package com.waldiuxx.conversor.menu;

import com.waldiuxx.conversor.servicio.ServicioConversor;
import com.waldiuxx.conversor.modelo.Moneda;
import java.util.Scanner;
import java.util.List;

public class Principal {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            int opcion = leerOpcion(1, 2);

            switch (opcion) {
                case 1 -> realizarConversion();
                case 2 -> salir = true;
            }
        }
        cerrarAplicacion();
    }

    private static void mostrarMenu() {
        System.out.println("\n=== CONVERSOR DE MONEDAS WALDIUXX ===");
        System.out.println("1. Realizar conversión");
        System.out.println("2. Salir");
        System.out.print("Elija una opción: ");
    }

    private static void realizarConversion() {
        List<Moneda> monedas = ServicioConversor.getMonedasSoportadas();
        mostrarMonedasDisponibles(monedas);

        Moneda origen = seleccionarMoneda(monedas, "origen");
        Moneda destino = seleccionarMoneda(monedas, "destino");

        validarMonedasDiferentes(origen, destino);

        double cantidad = leerCantidad(origen);
        convertirMoneda(origen, destino, cantidad);
    }

    private static void mostrarMonedasDisponibles(List<Moneda> monedas) {
        System.out.println("\nMonedas disponibles:");
        for (int i = 0; i < monedas.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, monedas.get(i));
        }
    }

    private static Moneda seleccionarMoneda(List<Moneda> monedas, String tipo) {
        System.out.printf("\nElija moneda de %s (número): ", tipo);
        int opcion = leerOpcion(1, monedas.size());
        return monedas.get(opcion - 1);
    }

    private static void validarMonedasDiferentes(Moneda origen, Moneda destino) {
        if (origen.equals(destino)) {
            System.out.println("¡Error! Las monedas deben ser diferentes.");
            realizarConversion(); // Vuelve a empezar el proceso
        }
    }

    private static double leerCantidad(Moneda moneda) {
        System.out.printf("Ingrese cantidad en %s: ", moneda.codigo());
        return leerDouble();
    }

    private static void convertirMoneda(Moneda origen, Moneda destino, double cantidad) {
        try {
            double resultado = ServicioConversor.convertir(cantidad, origen.codigo(), destino.codigo());
            mostrarResultado(cantidad, origen, resultado, destino);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void mostrarResultado(double cantidad, Moneda origen, double resultado, Moneda destino) {
        System.out.printf("\n>> RESULTADO: %.2f %s = %.2f %s%n",
                cantidad, origen.codigo(), resultado, destino.codigo());
    }

    private static int leerOpcion(int min, int max) {
        while (true) {
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                if (opcion >= min && opcion <= max) return opcion;
                System.out.printf("Ingrese un número entre %d y %d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.print("¡Entrada inválida! Ingrese un número: ");
            }
        }
    }

    private static double leerDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("¡Cantidad inválida! Ingrese un número: ");
            }
        }
    }

    private static void cerrarAplicacion() {
        System.out.println("¡Gracias por usar el conversor!");
        scanner.close();
    }
}
