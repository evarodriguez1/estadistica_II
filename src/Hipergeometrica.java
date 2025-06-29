import java.util.Scanner;
import java.math.BigInteger;

public class Hipergeometrica {

    static final String ROJO = "\u001B[31m";
    static final String VERDE = "\u001B[32m";
    static final String AZUL = "\u001B[34m";
    static final String RESET = "\u001B[0m";

    // Este método principal pide datos y calcula la probabilidad hipergeométrica
    public static void distribucionHipergeometrica() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Usted eligió trabajar con la" + AZUL + " distribución hipergeométrica.\n" + RESET +
                "Esta se usa cuando se saca una muestra sin reposición de una población finita que contiene ") ;
        System.out.println("una cantidad fija de elementos exitosos y fallidos (por ejemplo bolitas doradas o comunes).\n");

        // Supongamos que tenemos una bolsa con bolitas. Algunas son doradas (exitosas) y otras comunes (fallidas).
        //10 doradas y 40 comunes.
        //No repone lo que saca, así que la probabilidad cambia con cada intento. La hipergeométrica nos ayuda
        //a calcular qué tan probable es que saque, por ejemplo, 3 bolitas doradas en 5 intentos.

        // N: tamaño total de la población (debe ser > 0)
        int N;
        while (true) {
            N = pedirEntero(scanner, "Ingrese el tamaño total de la población (N): ");
            if (N > 0) break;
            System.out.println(ROJO + "❌ N debe ser mayor a cero." + RESET);
        }

// K: cantidad de éxitos en la población (bolitas doradas), debe ser <= N
        int K;
        while (true) {
            K = pedirEntero(scanner, "Ingrese la cantidad total de elementos exitosos en la población (K): ");
            if (K <= N) break;
            System.out.println(ROJO + "❌ K no puede ser mayor que N. No puede haber más éxitos que población total." + RESET);
        }

// n: tamaño de la muestra, debe ser <= N
        int n;
        while (true) {
            n = pedirEntero(scanner, "Ingrese el tamaño de la muestra tomada sin reemplazo (n): ");
            if (n <= N) break;
            System.out.println(ROJO + "❌ n no puede ser mayor que N. No podés extraer más elementos de los que hay." + RESET);
        }

// x: cantidad de éxitos deseados en la muestra. Debe ser <= K y <= n
        int x;
        while (true) {
            x = pedirEntero(scanner, "Ingrese la cantidad de éxitos que desea obtener en la muestra (x): ");
            if (x <= K && x <= n) break;

            System.out.println(ROJO + "❌ x no puede ser mayor que K ni mayor que n." + RESET);
            if (x > K)
                System.out.println("   ↳ x > K: No podés esperar más éxitos de los que hay disponibles en la población.");
            if (x > n)
                System.out.println("   ↳ x > n: No podés obtener más éxitos que la cantidad total de extracciones.");
        }
        double resultado = calcularProbabilidadHipergeometrica(N, K, n, x);
        System.out.printf(VERDE + "\nLa probabilidad de obtener exactamente %d éxitos en una muestra de %d es: %.5f\n" + RESET, x, n, resultado);
    }

    // Pide un entero por consola con validación
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

    // Calcula la probabilidad usando la fórmula hipergeométrica:
    // P(X = x) = [ C(K, x) * C(N - K, n - x) ] / C(N, n)
    public static double calcularProbabilidadHipergeometrica(int N, int K, int n, int x) {
        BigInteger exitos = combinatorio(K, x); // formas de elegir x éxitos de los K que hay
        BigInteger fracasos = combinatorio(N - K, n - x); // formas de elegir fracasos del resto
        BigInteger total = combinatorio(N, n); // todas las combinaciones posibles de extraer n de N

        return exitos.multiply(fracasos).doubleValue() / total.doubleValue();
    }

    // Factorial para BigInteger
    public static BigInteger factorial(int num) {
        BigInteger resultado = BigInteger.ONE;
        for (int i = 2; i <= num; i++) {
            resultado = resultado.multiply(BigInteger.valueOf(i));
        }
        return resultado;
    }

    // Combinatoria C(n, k), que se usa para contar cuántas formas hay de elegir k elementos de un grupo de n
    public static BigInteger combinatorio(int n, int k) {
        BigInteger numerador = factorial(n);
        BigInteger denominador = factorial(k).multiply(factorial(n - k));
        return numerador.divide(denominador);
    }
}

