import java.util.Scanner;

public class Gaussiana {

    static final String ROJO = "\u001B[31m";
    static final String AZUL = "\u001B[34m";
    static final String VERDE = "\u001B[32m";
    static final String RESET = "\u001B[0m";

    public static void distribucionNormal() {
        Scanner scanner = new Scanner(System.in);

        // --- MENSAJE INICIAL ---
        System.out.println("--- " + AZUL + "Cálculo de Probabilidad Normal (Gaussiana)" + RESET + " ---\n" +
                "Esta distribución es clave para modelar variables continuas que se agrupan alrededor de un valor central.\n" +
                "(Ej: la altura de las personas, errores de medición, presión arterial).");

        System.out.println("\n--- Define los Parámetros de tu Distribución ---");

        double media;
        while (true) {
            System.out.print("1. Introduce el " + VERDE + "promedio (media)" + RESET + " de los datos (μ o x̄): ");
            try {
                media = Double.parseDouble(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Entrada inválida. Debes ingresar un número (puede ser decimal)." + RESET);
            }
        }

        double varianza;
        while (true) {
            System.out.print("2. Introduce la " + VERDE + "varianza" + RESET + " de los datos (σ² o s² ≥ 0). La varianza mide la dispersión: ");
            try {
                varianza = Double.parseDouble(scanner.nextLine().trim());
                if (varianza >= 0) break;
                System.out.println(ROJO + "La varianza no puede ser un número negativo." + RESET);
            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Entrada inválida. Debes ingresar un número." + RESET);
            }
        }

        double desviacion = Math.sqrt(varianza);
        System.out.printf(AZUL + "↳ La desviación estándar (σ o s) calculada es: %.4f\n" + RESET, desviacion);

        // --- MENÚ DE OPCIONES ---
        System.out.println("\n3. Selecciona el tipo de probabilidad a calcular:");
        System.out.println("   1. Probabilidad de que un valor sea" + VERDE + " MENOR O IGUAL " + RESET + "que un punto, tal que P(X ≤ a) ('a lo sumo...').");
        System.out.println("   2. Probabilidad de que un valor sea" + VERDE + " MAYOR O IGUAL " + RESET + "que un punto, tal que P(X ≥ a) ('por lo menos...').");
        System.out.println("   3. Probabilidad de que un valor caiga" + VERDE + " DENTRO DE UN RANGO" + RESET + ", tal que P(a ≤ X ≤ b).");
        int opcion;

        while (true) {
            System.out.print("Elige una opción (1-3): ");
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
                if (opcion >= 1 && opcion <= 3) break;
                else System.out.println(ROJO + "Opción inválida. Debe ser un número entre 1 y 3." + RESET);
            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Entrada inválida. Por favor, introduce un número." + RESET);
            }
        }

        System.out.println(); // Espacio para legibilidad

        // --- LÓGICA DE CÁLCULO MEJORADA ---
        switch (opcion) {
            case 1: { // P(X ≤ a)
                System.out.print("   ↳ Ingresa el valor del punto (a) para P(X ≤ a): ");
                double a = leerDoubleValido(scanner);
                mostrarProbabilidadAcumulada(a, media, desviacion, false);
                break;
            }
            case 2: { // P(X ≥ a)
                System.out.print("   ↳ Ingresa el valor del punto (a) para P(X ≥ a): ");
                double a = leerDoubleValido(scanner);
                mostrarProbabilidadAcumulada(a, media, desviacion, true);
                break;
            }
            case 3: { // P(a ≤ X ≤ b)
                System.out.print("   ↳ Ingresa el valor mínimo del rango (a): ");
                double a = leerDoubleValido(scanner);
                System.out.print("   ↳ Ingresa el valor máximo del rango (b): ");
                double b = leerDoubleValido(scanner);
                mostrarProbabilidadEntre(a, b, media, desviacion);
                break;
            }
        }
    }

    // Función de ayuda para leer un double, sin cambios en el texto.
    private static double leerDoubleValido(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Entrada inválida. Debe ingresar un número." + RESET);
            }
        }
    }

    // --- RESULTADOS MEJORADOS ---
    private static void mostrarProbabilidadAcumulada(double x, double media, double sigma, boolean esMayor) {
        if (sigma == 0) {
            // Caso especial: si no hay desviación, el resultado es 0 o 1 de forma determinista.
            System.out.println("\n--- " + AZUL + "Resultado" + RESET + " ---");
            if (esMayor) { // P(X >= x)
                System.out.println(AZUL + "Con desviación cero, la probabilidad es 1.0 (100%) si x <= media, y 0.0 (0%) si x > media." + RESET);
            } else { // P(X <= x)
                System.out.println(AZUL + "Con desviación cero, la probabilidad es 1.0 (100%) si x >= media, y 0.0 (0%) si x < media." + RESET);
            }
            return;
        }

        double z = (x - media) / sigma;
        double cdf = cdfHastings(z);
        double prob = esMayor ? (1 - cdf) : cdf;

        System.out.println("\n--- " + AZUL + "Resultado" + RESET + " ---");
        System.out.printf(AZUL + "El valor estandarizado (Z-score) es: %.4f\n" + RESET, z);
        System.out.println(AZUL + "↳" + RESET + " (Esto indica a cuántas desviaciones estándar de la media se encuentra tu valor).");

        if (esMayor) {
            System.out.printf(AZUL + "\nLa probabilidad P(X ≥ %.4f) es: %.5f\n" + RESET, x, prob);
        } else {
            System.out.printf(AZUL + "\nLa probabilidad P(X ≤ %.4f) es: %.5f\n" + RESET, x, prob);
        }
        System.out.printf(AZUL + "↳ Esto representa un %.2f%% de probabilidad.\n" + RESET, prob * 100);
    }

    private static void mostrarProbabilidadEntre(double a, double b, double media, double sigma) {
        if (a > b) { // Asegurar a <= b
            double temp = a; a = b; b = temp;
        }

        if (sigma == 0) {
            System.out.println("\n--- " + AZUL + "Resultado" + RESET + " ---");
            System.out.println(AZUL + "Con desviación cero, la probabilidad es 1.0 (100%) si el rango [a, b] contiene a la media, y 0.0 (0%) si no lo hace." + RESET);
            return;
        }

        double z1 = (a - media) / sigma;
        double z2 = (b - media) / sigma;
        double prob = cdfHastings(z2) - cdfHastings(z1);

        System.out.println("\n--- " + AZUL + "Resultado" + RESET + " ---");
        System.out.printf(AZUL + "Z-score para el límite inferior (a): %.4f\n" + RESET, z1);
        System.out.printf(AZUL + "Z-score para el límite superior (b): %.4f\n" + RESET, z2);

        System.out.printf(AZUL + "\nLa probabilidad P(%.4f ≤ X ≤ %.4f) es: %.5f\n" + RESET, a, b, prob);
        System.out.printf(AZUL + "↳ Esto representa un %.2f%% de probabilidad.\n" + RESET, prob * 100);
    }

    // --- Aproximación CDF Hastings (SIN CAMBIOS) ---
    private static double cdfHastings(double z) {
        boolean neg = z < 0;
        double absZ = Math.abs(z);
        double phi = Math.exp(-0.5 * absZ * absZ) / Math.sqrt(2 * Math.PI);
        double b0 = 0.2316419, b1 = 0.319381530, b2 = -0.356563782, b3 = 1.781477937, b4 = -1.821255978, b5 = 1.330274429;
        double t = 1.0 / (1.0 + b0 * absZ);
        double pol = (((((b5 * t + b4) * t + b3) * t + b2) * t + b1) * t);
        double approx = 1.0 - phi * pol;
        return neg ? (1.0 - approx) : approx;
    }
}