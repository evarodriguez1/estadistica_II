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

        System.out.println("Usted eligió trabajar con la" + AZUL + " distribución de Poisson.\n" + RESET +
                "Esta distribución se usa para modelar la cantidad de eventos que ocurren en un intervalo fijo de tiempo o espacio,\n" +
                "siempre que ocurran de forma independiente y a una tasa constante.");

        double lambda;
        while (true) {
            try {
                System.out.print("\nIngrese el valor esperado de ocurrencias (λ > 0): ");
                lambda = Double.parseDouble(scanner.nextLine().trim());
                if (lambda > 0) break;
                else System.out.println(ROJO + "λ debe ser mayor que cero." + RESET);
            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Entrada inválida. Intente con un número válido." + RESET);
            }
        }

        System.out.println(AZUL + "\nλ representa tanto la esperanza matemática como la varianza de la distribución." + RESET);
        System.out.printf("→ Esperanza (E[X]) = %.2f\n→ Varianza (Var[X]) = %.2f\n", lambda, lambda);

        System.out.println("\nSeleccione el tipo de cálculo:");
        System.out.println("1. P(X = x) → Probabilidad exacta");
        System.out.println("2. P(X ≤ x) → Probabilidad acumulada hasta x");
        System.out.println("3. P(X ≥ x) → Probabilidad desde x");
        System.out.println("4. P(a ≤ X ≤ b) → Probabilidad en un rango");
        System.out.print("Opción: ");
        String opcion = scanner.nextLine().trim();

        switch (opcion) {
            case "1" -> {
                int x = pedirX(scanner);
                double p = probabilidadPoisson(lambda, x);
                System.out.printf(AZUL + "\nLa probabilidad de que ocurran exactamente %d eventos es: %.10f\n" + RESET, x, p);
            }
            case "2" -> {
                int x = pedirX(scanner);
                double acumulada = 0;
                for (int i = 0; i <= x; i++) acumulada += probabilidadPoisson(lambda, i);
                System.out.printf(AZUL + "\nLa probabilidad de que ocurran hasta %d eventos (P(X ≤ %d)) es: %.10f\n" + RESET, x, x, acumulada);
            }
            case "3" -> {
                int x = pedirX(scanner);
                double acumulada = 0;
                for (int i = 0; i < x; i++) acumulada += probabilidadPoisson(lambda, i);
                System.out.printf(AZUL + "\nLa probabilidad de que ocurran %d o más eventos (P(X ≥ %d)) es: %.10f\n" + RESET, x, x, 1 - acumulada);
            }
            case "4" -> {
                int a = pedirLimite("mínimo", scanner);
                int b = pedirLimite("máximo", scanner);
                if (a > b) {
                    int temp = a;
                    a = b;
                    b = temp;
                }
                double total = 0;
                for (int i = a; i <= b; i++) total += probabilidadPoisson(lambda, i);
                System.out.printf(AZUL + "\nLa probabilidad de que ocurran entre %d y %d eventos (P(%d ≤ X ≤ %d)) es: %.10f\n" + RESET, a, b, a, b, total);
            }
            default -> System.out.println(ROJO + "Opción inválida." + RESET);
        }
    }

    public static int pedirX(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Ingrese la cantidad esperada de eventos (x ≥ 0): ");
                int x = Integer.parseInt(scanner.nextLine().trim());
                if (x >= 0) return x;
                else System.out.println(ROJO + "x debe ser mayor o igual a 0." + RESET);
            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Entrada inválida. Debe ser un número entero." + RESET);
            }
        }
    }

    public static int pedirLimite(String nombre, Scanner scanner) {
        while (true) {
            try {
                System.out.printf("Ingrese el valor %s del rango (entero ≥ 0): ", nombre);
                int x = Integer.parseInt(scanner.nextLine().trim());
                if (x >= 0) return x;
                else System.out.println(ROJO + "Debe ingresar un número entero mayor o igual a cero." + RESET);
            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Entrada inválida." + RESET);
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
        BigInteger resultado = BigInteger.ONE;
        for (int i = 2; i <= num; i++) {
            resultado = resultado.multiply(BigInteger.valueOf(i));
        }
        return resultado;
    }
}

