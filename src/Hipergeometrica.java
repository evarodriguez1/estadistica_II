import java.util.Scanner;
import java.math.BigInteger;

public class Hipergeometrica {

    static final String ROJO = "\u001B[31m";
    static final String VERDE = "\u001B[32m";
    static final String AZUL = "\u001B[34m";
    static final String RESET = "\u001B[0m";

    public static void distribucionHipergeometrica() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Usted eligió trabajar con la" + AZUL + " distribución hipergeométrica.\n" + RESET +
                "Esta se usa cuando se saca una muestra sin reposición de una población finita que contiene elementos exitosos y fallidos.\n");

        int N;
        while (true) {
            N = pedirEntero(scanner, "Ingrese el tamaño total de la población (N): ");
            if (N > 0) break;
            System.out.println(ROJO + "❌ N debe ser mayor a cero." + RESET);
        }

        int K;
        while (true) {
            K = pedirEntero(scanner, "Ingrese la cantidad total de elementos exitosos en la población (K): ");
            if (K <= N) break;
            System.out.println(ROJO + "❌ K no puede ser mayor que N." + RESET);
        }

        int n;
        while (true) {
            n = pedirEntero(scanner, "Ingrese el tamaño de la muestra tomada sin reemplazo (n): ");
            if (n <= N) break;
            System.out.println(ROJO + "❌ n no puede ser mayor que N." + RESET);
        }

        //opciones de rango
        System.out.println("\nSeleccione el tipo de cálculo que desea realizar:");
        System.out.println("1. Probabilidad exacta (P(X = k))");
        System.out.println("2. Probabilidad acumulada hasta un valor máximo (P(X ≤ k))");
        System.out.println("3. Probabilidad desde un mínimo (P(X ≥ k))");
        System.out.println("4. Probabilidad entre un rango (P(a ≤ X ≤ b))");

        int opcion;
        while (true) {
            opcion = pedirEntero(scanner, "Ingrese una opción (1 a 4): ");
            if (opcion >= 1 && opcion <= 4) break;
            System.out.println(ROJO + "Debe ingresar un número entre 1 y 4." + RESET);
        }

        int desde = 0, hasta = 0;

        try {
            switch (opcion) {
                case 1: // chance de conseguir exactamente x éxitos
                    int x = pedirEntero(scanner, "Ingrese el valor de x (éxitos deseados): ");
                    desde = hasta = x;
                    break;
                case 2: // chance de conseguir como mucho x éxitos
                    hasta = pedirEntero(scanner, "Ingrese el valor máximo de éxitos (k): ");
                    desde = 0;
                    break;
                case 3: // chance de conseguir como mínimo x éxitos
                    desde = pedirEntero(scanner, "Ingrese el valor mínimo de éxitos (k): ");
                    hasta = Math.min(n, K); // no puede haber más éxitos que los disponibles
                    break;
                case 4: // chances entre dos valores (x) de éxitos
                    desde = pedirEntero(scanner, "Ingrese el valor mínimo de éxitos (a): ");
                    hasta = pedirEntero(scanner, "Ingrese el valor máximo de éxitos (b): ");
                    break;
            }

            if (desde < 0 || hasta > n || desde > hasta) {
                System.out.println(ROJO + "❌ Rango inválido. Verifique los valores ingresados." + RESET);
                return;
            }

        } catch (NumberFormatException e) {
            System.out.println(ROJO + "❌ Entrada inválida. Debe ingresar un número entero." + RESET);
            return;
        }

        double total = 0;
        for (int i = desde; i <= hasta; i++) {
            if (i <= K && (n - i) <= (N - K)) {
                total += calcularProbabilidadHipergeometrica(N, K, n, i);
            }
        }

        System.out.printf(VERDE + "\nLa probabilidad de que X esté entre %d y %d es: %.5f\n" + RESET, desde, hasta, total);
        System.out.printf(VERDE + "Eso representa un %.2f%% de probabilidad.\n" + RESET, total * 100);
    }

    private static int pedirEntero(Scanner scanner, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                int valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor >= 0) return valor;
                System.out.println(ROJO + "Ingrese un entero mayor o igual a cero." + RESET);
            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Debe ingresar un número entero válido." + RESET);
            }
        }
    }

    public static double calcularProbabilidadHipergeometrica(int N, int K, int n, int x) {
        BigInteger exitos = combinatorio(K, x);
        BigInteger fracasos = combinatorio(N - K, n - x);
        BigInteger total = combinatorio(N, n);

        return exitos.multiply(fracasos).doubleValue() / total.doubleValue();
    }

    public static BigInteger factorial(int num) {
        BigInteger resultado = BigInteger.ONE;
        for (int i = 2; i <= num; i++) {
            resultado = resultado.multiply(BigInteger.valueOf(i));
        }
        return resultado;
    }

    public static BigInteger combinatorio(int n, int k) {
        if (k > n) return BigInteger.ZERO;
        BigInteger numerador = factorial(n);
        BigInteger denominador = factorial(k).multiply(factorial(n - k));
        return numerador.divide(denominador);
    }
}
