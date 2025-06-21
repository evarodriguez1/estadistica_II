import java.math.BigInteger;
import java.util.Scanner;

public class Poisson {

    static final String ROJO = "\u001B[31m";
    static final String VERDE = "\u001B[32m";
    static final String AZUL = "\u001B[34m";
    static final String RESET = "\u001B[0m";

    public static void distribucionPoisson() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Usted eligió trabajar con la" + AZUL + " distribución de Poisson.\n" + RESET +
                "Esta distribución se usa para modelar la cantidad de veces que ocurre un evento\n" +
                "en un intervalo fijo de tiempo o espacio, cuando los eventos ocurren con una tasa constante.");

        // Solicitar lambda (λ)
        double lambda;
        while (true) {
            try {
                System.out.print("\nIngrese el valor esperado de ocurrencias (λ > 0): ");
                lambda = Double.parseDouble(scanner.nextLine().trim());

                if (lambda > 0) {
                    break;
                } else {
                    System.out.println(ROJO + "El valor de λ debe ser mayor que cero." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Debe ingresar un valor numérico válido para λ." + RESET);
            }
        }

        // Solicitar cantidad de eventos (x)
        int eventos;
        while (true) {
            try {
                System.out.print("\nIngrese la cantidad de veces que espera que el evento ocurra (x ≥ 0): ");
                eventos = Integer.parseInt(scanner.nextLine().trim());

                if (eventos >= 0) {
                    break;
                } else {
                    System.out.println(ROJO + "x debe ser mayor o igual a 0." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Debe ingresar un número entero válido para x." + RESET);
            }
        }

        // Entregar resultado en el main
        double resultado = probabilidadPoisson(lambda, eventos);

        System.out.printf("\nLa probabilidad de que ocurran exactamente %d eventos cuando λ = %.2f es: %.5f\n",
                eventos, lambda, resultado);
        scanner.nextLine();
    }

    // Factorial con BigInteger para evitar desbordes
    public static BigInteger factorial(int num) {
        BigInteger resultado = BigInteger.ONE;
        for (int i = 2; i <= num; i++) {
            resultado = resultado.multiply(BigInteger.valueOf(i));
        }
        return resultado;
    }

    // Fórmula de distribución de Poisson: P(x; λ) = (e^(-λ) * λ^x) / x!
    public static double probabilidadPoisson(double lambda, int eventos) {
        BigInteger factorialX = factorial(eventos);
        double numerador = Math.pow(Math.E, -lambda) * Math.pow(lambda, eventos);
        return numerador / factorialX.doubleValue();
    }
}
