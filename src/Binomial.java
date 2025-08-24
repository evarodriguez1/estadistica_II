import java.util.Scanner;
import java.math.BigInteger;

public class Binomial {

    static final String ROJO = "\u001B[31m";
    static final String VERDE = "\u001B[32m";
    static final String AZUL = "\u001B[34m";
    static final String RESET = "\u001B[0m";

    public static void distribucionBinomial() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("--- " + AZUL + "Cálculo de Probabilidad Binomial" + RESET + " ---\n" +
                "Esta herramienta es útil cuando un experimento con" + VERDE + " dos resultados posibles " + RESET +
                "(ej: 'cara o cruz') se repite varias veces.");

        int ensayos = 0;
        while (true) {
            try {
                System.out.print("\n1. ¿Cuántas veces se repite el experimento? (número total de ensayos n > 0): ");
                ensayos = Integer.parseInt(scanner.nextLine().trim());
                if (ensayos > 0) {
                    break;
                } else {
                    System.out.println(ROJO + "El número de ensayos debe ser mayor que cero." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Por favor, introduce un número entero." + RESET);
            }
        }

        double probExitos = 0;
        while (true) {
            try {
                System.out.print("2. ¿Cuál es la probabilidad de " + VERDE + "éxito" + RESET + "? (número de 0 a 1, utilice la coma): ");
                probExitos = scanner.nextDouble();
                scanner.nextLine();
                if (probExitos >= 0 && probExitos <= 1) {
                    break;
                } else {
                    System.out.println(ROJO + "La probabilidad debe ser un número entre 0 y 1. Por ejemplo, para un 75% de probabilidad, escribe 0.75." + RESET);
                }
            } catch (Exception e) {
                System.out.println(ROJO + "Formato incorrecto. Por favor, introduce un número válido" + RESET);
                scanner.nextLine();
            }
        }

        // --- MENÚ DE OPCIONES ---
        System.out.println("\n3. Seleccione el tipo de cálculo a realizar:");
        System.out.println("   1. Obtener un número" + VERDE + " exacto " + RESET + "de éxitos, tal que P(X = k)");
        System.out.println("   2. Obtener" + VERDE + " como máximo " + RESET + "un número de éxitos, tal que P(X ≤ k) ('Por lo menos ocurra tal cosa').");
        System.out.println("   3. Obtener" + VERDE + " como mínimo " + RESET + "un número de éxitos, tal que P(X ≥ k) ('A lo sumo ocurra tal cosa').");
        System.out.println("   4. Obtener un número de éxitos" + VERDE + " dentro de un rango " + RESET + "específico, tal que (P(a ≤ X ≤ b))");

        int opcion;
        while (true) {
            try {
                System.out.print("Elige una opción (1-4): ");
                opcion = Integer.parseInt(scanner.nextLine().trim());
                if (opcion >= 1 && opcion <= 4) break;
                else System.out.println(ROJO + "Por favor, ingresa un número del 1 al 4." + RESET);
            } catch (Exception e) {
                System.out.println(ROJO + "Opción no válida. Inténtalo de nuevo." + RESET);
            }
        }

        int desde = 0, hasta = 0;

        try {
            switch (opcion) {
                case 1:
                    System.out.print("   ↳ Ingresa el número exacto de éxitos que buscas (k): ");
                    desde = hasta = Integer.parseInt(scanner.nextLine().trim());
                    break;
                case 2:
                    System.out.print("   ↳ Ingresa el número máximo de éxitos (k): ");
                    hasta = Integer.parseInt(scanner.nextLine().trim());
                    desde = 0;
                    break;
                case 3:
                    System.out.print("   ↳ Ingresa el número mínimo de éxitos (k): ");
                    desde = Integer.parseInt(scanner.nextLine().trim());
                    hasta = ensayos;
                    break;
                case 4:
                    System.out.print("   ↳ Valor mínimo del rango de éxitos (a): ");
                    desde = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("   ↳ Valor máximo del rango de éxitos (b): ");
                    hasta = Integer.parseInt(scanner.nextLine().trim());
                    break;
            }

            if (desde < 0 || hasta > ensayos || desde > hasta) {
                // Original: "Rango inválido. Verifique los valores ingresados."
                System.out.println(ROJO + "Los valores de éxito no son válidos. Deben estar entre 0 y el número total de repeticiones ("+ ensayos +")." + RESET);
                return;
            }

        } catch (NumberFormatException e) {
            // Original: "Debe ingresar un número entero válido."
            System.out.println(ROJO + "Por favor, introduce un número entero." + RESET);
            return;
        }

        // Cálculo acumulado (sin cambios)
        double total = 0;
        for (int x = desde; x <= hasta; x++) {
            total += probabilidadBinomial(ensayos, x, probExitos);
        }

        // --- RESULTADOS ---
        System.out.println("\n--- " + AZUL + "Resultados" + RESET + " ---");
        String textoResultado;
        if (opcion == 1) {
            textoResultado = String.format("La probabilidad de obtener %s %d éxitos es:", VERDE + "exactamente" + AZUL, desde);
        } else if (opcion == 2) {
            textoResultado = String.format("La probabilidad de obtener %s %d éxitos (o menos) es:", VERDE + "como máximo" + AZUL, hasta);
        } else if (opcion == 3) {
            textoResultado = String.format("La probabilidad de obtener %s %d éxitos (o más) es:", VERDE + "como mínimo" + AZUL, desde);
        } else {
            textoResultado = String.format("La probabilidad de obtener %s %d y %d éxitos es:", VERDE + "entre" + AZUL, desde, hasta);
        }
        System.out.printf(AZUL + "%s %.5f\n" + RESET, textoResultado, total);

        double esperanza = ensayos * probExitos;
        double varianza = ensayos * probExitos * (1 - probExitos);
        System.out.println(AZUL + "\n--- Otros datos de interés ---" + RESET);
        System.out.printf(AZUL + "Valor Esperado o Esperanza (Media): %.4f\n" + RESET, esperanza);
        System.out.println(AZUL + "↳" + RESET + " En promedio, es el número de éxitos que podrías esperar.");
        System.out.printf(AZUL + "Varianza: %.4f\n" + RESET, varianza);
        System.out.println(AZUL + "↳" + RESET + " Mide qué tan dispersos estarán los resultados respecto al promedio.");
    }

    public static BigInteger factorial(int num) {
        BigInteger resultado = BigInteger.ONE;
        for (int i = 2; i <= num; i++) {
            resultado = resultado.multiply(BigInteger.valueOf(i));
        }
        return resultado;
    }

    public static BigInteger combinatorio(int n, int k) {
        if (k < 0 || k > n) {
            return BigInteger.ZERO; // Combinatorio de k fuera de rango es 0
        }
        BigInteger numerador = factorial(n);
        BigInteger denominador = factorial(k).multiply(factorial(n - k));
        return numerador.divide(denominador);
    }

    public static double probabilidadBinomial(int n, int k, double p) {
        if (k < 0 || k > n) {
            return 0; // La probabilidad es 0 si k está fuera del rango posible [0, n]
        }
        BigInteger combinatoria = combinatorio(n, k);
        return combinatoria.doubleValue() * Math.pow(p, k) * Math.pow(1 - p, n - k);
    }
}