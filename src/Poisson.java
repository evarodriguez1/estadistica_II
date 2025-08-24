import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Scanner;

public class Poisson {

    static final String ROJO = "\u001B[31m";
    static final String VERDE = "\u001B[32m";
    static final String AZUL = "\u001B[34m";
    static final String RESET = "\u001B[0m";

    public static void distribucionPoisson() {
        Scanner scanner = new Scanner(System.in);

        // --- MENSAJE INICIAL ---
        System.out.println("--- " + AZUL + "Cálculo de Probabilidad de Poisson" + RESET + " ---\n" +
                "Esta herramienta calcula la probabilidad de que un evento ocurra un número determinado de veces en un intervalo.\n" +
                "Se usa cuando los eventos son independientes y ocurren a un ritmo promedio constante.\n" +
                "(Ej: N° de llamadas a un call center en una hora)");

        double lambda;
        while (true) {
            try {
                System.out.print("\n1. Introduce el número" + VERDE + " promedio " + RESET + "de veces que ocurre el evento en el intervalo (λ > 0): ");
                lambda = Double.parseDouble(scanner.nextLine().trim());
                if (lambda > 0) break;
                else {
                    System.out.println(ROJO + "El promedio de ocurrencias (λ) debe ser un número positivo." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Entrada inválida. Por favor, introduce un número." + RESET);
            }
        }

        System.out.println(AZUL + "\n--- Datos Clave de la Distribución ---" + RESET);
        System.out.println("Una propiedad de Poisson es que la " + VERDE + "media (esperanza) y la varianza son iguales" + RESET + " al promedio ingresado.");
        System.out.printf("→ Valor Esperado E[X] = %.4f\n→ Varianza Var[X] = %.4f\n", lambda, lambda);


        // --- MENÚ DE OPCIONES ---
        System.out.println("\n2. Selecciona el tipo de cálculo a realizar:");
        System.out.println("   1. Probabilidad de un número" + VERDE + " exacto " + RESET + "de eventos, tal que P(X = x).");
        System.out.println("   2. Probabilidad de que ocurran" + VERDE + " como máximo " + RESET + "ciertos eventos, tal que P(X ≤ x) ('a lo sumo...').");
        System.out.println("   3. Probabilidad de que ocurran" + VERDE + " como mínimo " + RESET + "ciertos eventos, tal que P(X ≥ x) ('por lo menos...').");
        System.out.println("   4. Probabilidad de que ocurran" + VERDE + " dentro de un rango " + RESET + "de eventos, tal que P(a ≤ X ≤ b).");
        System.out.print("Elige una opción (1-4): ");
        String opcion = scanner.nextLine().trim();

        System.out.println();

        // --- LÓGICA DE CÁLCULO ---
        switch (opcion) {
            case "1" -> {
                int x = pedirValor("el número exacto de eventos a evaluar", scanner);
                double p = probabilidadPoisson(lambda, x);
                System.out.println("\n--- " + AZUL + "Resultado" + RESET + " ---");
                System.out.printf(AZUL + "La probabilidad de que ocurran %sexactamente %d%s eventos es: %.10f\n" + RESET, VERDE, x, AZUL, p);
            }
            case "2" -> {
                int x = pedirValor("el número máximo de eventos a evaluar", scanner);
                double acumulada = 0;
                for (int i = 0; i <= x; i++) acumulada += probabilidadPoisson(lambda, i);
                System.out.println("\n--- " + AZUL + "Resultado" + RESET + " ---");
                System.out.printf(AZUL + "La probabilidad de que ocurran %scomo máximo %d%s eventos (P(X ≤ %d)) es: %.10f\n" + RESET, VERDE, x, AZUL, x, acumulada);
            }
            case "3" -> {
                int x = pedirValor("el número mínimo de eventos a evaluar", scanner);
                // La probabilidad de P(X ≥ x) es 1 - P(X < x), que es 1 - P(X ≤ x-1)
                double acumuladaMenores = 0;
                for (int i = 0; i < x; i++) acumuladaMenores += probabilidadPoisson(lambda, i);
                System.out.println("\n--- " + AZUL + "Resultado" + RESET + " ---");
                System.out.printf(AZUL + "La probabilidad de que ocurran %scomo mínimo %d%s eventos (P(X ≥ %d)) es: %.10f\n" + RESET, VERDE, x, AZUL, x, 1 - acumuladaMenores);
            }
            case "4" -> {
                int a = pedirValor("el valor mínimo del rango (a)", scanner);
                int b = pedirValor("el valor máximo del rango (b)", scanner);
                if (a > b) { // Asegura que el rango sea válido
                    int temp = a; a = b; b = temp;
                }
                double total = 0;
                for (int i = a; i <= b; i++) total += probabilidadPoisson(lambda, i);
                System.out.println("\n--- " + AZUL + "Resultado" + RESET + " ---");
                System.out.printf(AZUL + "La probabilidad de que ocurran %sentre %d y %d%s eventos (P(%d ≤ X ≤ %d)) es: %.10f\n" + RESET, VERDE, a, b, AZUL, a, b, total);
            }
            default -> System.out.println(ROJO + "Opción no válida. Por favor, ejecuta de nuevo y elige un número del 1 al 4." + RESET);
        }
    }

    public static int pedirValor(String descripcion, Scanner scanner) {
        while (true) {
            try {
                System.out.printf("   ↳ Ingresa %s (x ≥ 0): ", descripcion);
                int x = Integer.parseInt(scanner.nextLine().trim());
                if (x >= 0) return x;
                else System.out.println(ROJO + "El número de eventos no puede ser negativo. Inténtalo de nuevo." + RESET);
            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Entrada inválida. Debes introducir un número entero." + RESET);
            }
        }
    }

    public static double probabilidadPoisson(double lambda, int x) {
        //define la precisión de los cálculos al usar el tipado Big
        MathContext mc = new MathContext(30);

        //se pasa lambda a BigDecimal para después elevarlo con la presición definida
        BigDecimal lambdaElevadoX = BigDecimal.valueOf(lambda).pow(x, mc);

        //calcula el factorial y lo convierte a bigdecimal
        BigDecimal factorial = new BigDecimal(factorial(x));

        //Calcula 𝑒^(−𝜆) usando Math.exp(-λ). Como es BigInt, la forma más estable es traer la librería.
        //expNegLambda = exponente lambda negativa
        BigDecimal expNegLambda = new BigDecimal(Math.exp(-lambda));

        //multiply es multiplicar en BigDecimal
        //fórmula distribución de Poisson = (λ^x * e^(-λ)) / x!
        BigDecimal result = lambdaElevadoX.multiply(expNegLambda, mc).divide(factorial, mc);

        //larga el resultado
        return result.doubleValue();
    }

    public static BigInteger factorial(int num) {
        if (num < 0) { // Manejo de factorial de negativos
            throw new IllegalArgumentException("El factorial no está definido para números negativos.");
        }
        BigInteger resultado = BigInteger.ONE;
        for (int i = 2; i <= num; i++) {
            resultado = resultado.multiply(BigInteger.valueOf(i));
        }
        return resultado;
    }
}