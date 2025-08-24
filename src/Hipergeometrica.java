import java.util.Scanner;
import java.math.BigInteger;

public class Hipergeometrica {

    static final String ROJO = "\u001B[31m";
    static final String VERDE = "\u001B[32m";
    static final String AZUL = "\u001B[34m";
    static final String RESET = "\u001B[0m";

    public static void distribucionHipergeometrica() {
        Scanner scanner = new Scanner(System.in);

        // --- MENSAJE INICIAL ---
        System.out.println("--- " + AZUL + "Cálculo de Probabilidad Hipergeométrica" + RESET + " ---\n" +
                "Esta herramienta es ideal para calcular probabilidades cuando se extrae una muestra de un grupo" + ROJO + " y no se devuelven " + RESET + "los elementos sacados.\n" +
                "(Ej: Sacar 5 cartas de una baraja, seleccionar 10 personas de un grupo para un comité).");

        System.out.println("\n--- Define tu Escenario ---");
        int N;
        while (true) {
            N = pedirEntero(scanner, "1. ¿Cuántos elementos hay en el grupo total? (N > 0): ");
            if (N > 0) break;
            System.out.println(ROJO + "El tamaño total del grupo (N) debe ser mayor que cero." + RESET);
        }

        int K;
        while (true) {
            K = pedirEntero(scanner, "2. De ese total, ¿cuántos son considerados 'éxitos'? (K ≤ N) (Ej: N° de ases en la baraja): ");
            if (K >= 0 && K <= N) break; // K puede ser 0
            System.out.println(ROJO + "El número de 'éxitos' (K) no puede ser negativo ni mayor que el total del grupo (N)." + RESET);
        }

        int n;
        while (true) {
            n = pedirEntero(scanner, "3. ¿Cuántos elementos vas a extraer del grupo (muestra)? (n ≤ N): ");
            if (n >= 0 && n <= N) break; // n puede ser 0
            System.out.println(ROJO + "El tamaño de la muestra (n) no puede ser negativo ni mayor que el total del grupo (N)." + RESET);
        }

        // --- MENÚ DE OPCIONES ---
        System.out.println("\n4. Selecciona el tipo de cálculo a realizar:");
        System.out.println("   1. Probabilidad de obtener un número" + VERDE + " exacto " + RESET + "de éxitos, tal que P(X = k).");
        System.out.println("   2. Probabilidad de obtener" + VERDE + " como máximo " + RESET + "un número de éxitos, tal que P(X ≤ k) ('a lo sumo...').");
        System.out.println("   3. Probabilidad de obtener" + VERDE + " como mínimo " + RESET + "un número de éxitos, tal que P(X ≥ k) ('por lo menos...').");
        System.out.println("   4. Probabilidad de obtener un número de éxitos" + VERDE + " dentro de un rango " + RESET + ", tal que P(a ≤ X ≤ b).");


        int opcion;
        while (true) {
            opcion = pedirEntero(scanner, "Elige una opción (1-4): ");
            if (opcion >= 1 && opcion <= 4) break;
            System.out.println(ROJO + "Por favor, ingresa un número del 1 al 4." + RESET);
        }

        int desde = 0, hasta = 0;

        switch (opcion) {
            case 1:
                int k_exacto = pedirEntero(scanner, "   ↳ Ingresa el número exacto de éxitos que buscas en tu muestra (k): ");
                desde = hasta = k_exacto;
                break;
            case 2:
                hasta = pedirEntero(scanner, "   ↳ Ingresa el número máximo de éxitos posibles (k): ");
                desde = 0; // El mínimo posible de éxitos es siempre 0
                break;
            case 3:
                desde = pedirEntero(scanner, "   ↳ Ingresa el número mínimo de éxitos deseados (k): ");
                hasta = Math.min(n, K); // El máximo de éxitos posible es el tamaño de la muestra o el total de éxitos disponibles
                break;
            case 4:
                desde = pedirEntero(scanner, "   ↳ Valor mínimo del rango de éxitos (a): ");
                hasta = pedirEntero(scanner, "   ↳ Valor máximo del rango de éxitos (b): ");
                break;
        }

        if (desde > hasta) {
            System.out.println(ROJO + "Rango inválido. El valor mínimo (a) no puede ser mayor que el máximo (b)." + RESET);
            return;
        }
        if (hasta > n) {
            System.out.println(ROJO + "Rango inválido. No puedes obtener más éxitos ("+hasta+") que el tamaño de tu muestra ("+n+")." + RESET);
            return;
        }
        if (hasta > K) {
            System.out.println(ROJO + "Rango inválido. No puedes obtener más éxitos ("+hasta+") que el total de éxitos disponibles en el grupo ("+K+")." + RESET);
            return;
        }


        double total = 0;
        // El bucle calcula la probabilidad para cada k en el rango [desde, hasta]
        for (int k = desde; k <= hasta; k++) {
            // Condición de validez para el combinatorio:
            // k no puede ser > K (no puedes sacar más éxitos de los que hay)
            // n-k no puede ser > N-K (no puedes sacar más "fracasos" de los que hay)
            if (k <= K && (n - k) <= (N - K)) {
                total += calcularProbabilidadHipergeometrica(N, K, n, k);
            }
        }

        System.out.println("\n--- " + AZUL + "Resultado" + RESET + " ---");

        String textoResultado = "";
        switch(opcion) {
            case 1:
                textoResultado = String.format("La probabilidad de obtener %sexactamente %d%s éxitos en la muestra es:", VERDE, desde, AZUL);
                break;
            case 2:
                textoResultado = String.format("La probabilidad de obtener %scomo máximo %d%s éxitos en la muestra es:", VERDE, hasta, AZUL);
                break;
            case 3:
                textoResultado = String.format("La probabilidad de obtener %scomo mínimo %d%s éxitos en la muestra es:", VERDE, desde, AZUL);
                break;
            case 4:
                textoResultado = String.format("La probabilidad de obtener %sentre %d y %d%s éxitos en la muestra es:", VERDE, desde, hasta, AZUL);
                break;
        }

        System.out.printf(AZUL + "%s %.5f\n" + RESET, textoResultado, total);
        System.out.printf(AZUL + "↳ Esto representa un %.2f%% de probabilidad.\n" + RESET, total * 100);
    }

    private static int pedirEntero(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Entrada inválida. Debes ingresar un número entero." + RESET);
            }
        }
    }

    public static double calcularProbabilidadHipergeometrica(int N, int K, int n, int x) {
        // La fórmula es: C(K, x) * C(N-K, n-x) / C(N, n)
        BigInteger exitos_combinatorio = combinatorio(K, x);
        BigInteger fracasos_combinatorio = combinatorio(N - K, n - x);
        BigInteger total_combinatorio = combinatorio(N, n);

        // Si el total de combinaciones es cero, la probabilidad es cero.
        if (total_combinatorio.equals(BigInteger.ZERO)) {
            return 0.0;
        }

        return exitos_combinatorio.multiply(fracasos_combinatorio).doubleValue() / total_combinatorio.doubleValue();
    }

    public static BigInteger factorial(int num) {
        if (num < 0) return BigInteger.ZERO;
        BigInteger resultado = BigInteger.ONE;
        for (int i = 2; i <= num; i++) {
            resultado = resultado.multiply(BigInteger.valueOf(i));
        }
        return resultado;
    }

    public static BigInteger combinatorio(int n, int k) {
        if (k < 0 || k > n) {
            return BigInteger.ZERO; // Condición lógica: es imposible elegir k de n si k > n o k < 0.
        }
        BigInteger numerador = factorial(n);
        BigInteger denominador = factorial(k).multiply(factorial(n - k));
        return numerador.divide(denominador);
    }
}