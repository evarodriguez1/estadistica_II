import java.util.Scanner;
import java.math.BigInteger;

public class Binomial {

    static final String ROJO = "\u001B[31m";
    static final String VERDE = "\u001B[32m";
    static final String AZUL = "\u001B[34m";
    static final String RESET = "\u001B[0m";

    public static void distribucionBinomial() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Usted eligió trabajar con la" + AZUL + " distribución binomial.\n" + RESET +
                "Esta distribución se usa cuando hay dos resultados posibles (como éxito o fracaso) y un número fijo de intentos.");

        int ensayos = 0;
        while (true) {
            try {
                System.out.print("\nPor favor, ingrese el número total de ensayos (n > 0): ");
                ensayos = Integer.parseInt(scanner.nextLine().trim());
                if (ensayos > 0) {
                    break;
                } else {
                    System.out.println(ROJO + "El número debe ser natural, es decir, mayor que cero." + RESET);
                }

            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Debe ingresar un número entero válido." + RESET);
            }
        }

        double probExitos = 0;
        while (true) {
            try {
                System.out.print("\nIngrese la probabilidad de " + VERDE + "éxito" + RESET + " (entre 0 y 1): ");
                probExitos = scanner.nextDouble();
                scanner.nextLine();
                if (probExitos >= 0 && probExitos <= 1) {
                    break;
                } else {
                    System.out.println(ROJO + "Ingrese una probabilidad válida comprendida entre 0 y 1." + RESET);
                }
            } catch (Exception e) {
                System.out.println(ROJO + "Debe ingresar una probabilidad válida." + RESET);
                scanner.nextLine();
            }
        }

        System.out.println("\nSeleccione el tipo de cálculo que desea realizar:");
        System.out.println("1. Probabilidad exacta (P(X = k))");
        System.out.println("2. Probabilidad acumulada hasta un valor máximo (P(X ≤ k))");
        System.out.println("3. Probabilidad desde un mínimo (P(X ≥ k))");
        System.out.println("4. Probabilidad entre un rango (P(a ≤ X ≤ b))");

        int opcion;
        while (true) {
            try {
                System.out.print("Ingrese una opción (1 a 4): ");
                opcion = Integer.parseInt(scanner.nextLine().trim());
                if (opcion >= 1 && opcion <= 4) break;
                else System.out.println(ROJO + "Debe ingresar un número entre 1 y 4." + RESET);
            } catch (Exception e) {
                System.out.println(ROJO + "Entrada inválida. Intente de nuevo." + RESET);
            }
        }

        int desde = 0, hasta = 0;

        try {
            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el valor de k: ");
                    desde = hasta = Integer.parseInt(scanner.nextLine().trim());
                    break;
                case 2:
                    System.out.print("Ingrese el valor máximo k: ");
                    hasta = Integer.parseInt(scanner.nextLine().trim());
                    desde = 0;
                    break;
                case 3:
                    System.out.print("Ingrese el valor mínimo k: ");
                    desde = Integer.parseInt(scanner.nextLine().trim());
                    hasta = ensayos;
                    break;
                case 4:
                    System.out.print("Ingrese el valor mínimo a: ");
                    desde = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Ingrese el valor máximo b: ");
                    hasta = Integer.parseInt(scanner.nextLine().trim());
                    break;
            }

            if (desde < 0 || hasta > ensayos || desde > hasta) {
                System.out.println(ROJO + "Rango inválido. Verifique los valores ingresados." + RESET);
                return;
            }

        } catch (NumberFormatException e) {
            System.out.println(ROJO + "Debe ingresar un número entero válido." + RESET);
            return;
        }

        // Cálculo acumulado
        double total = 0;
        for (int x = desde; x <= hasta; x++) {
            total += probabilidadBinomial(ensayos, x, probExitos);
        }

        System.out.printf(AZUL + "\nLa probabilidad de que X esté entre %d y %d es: %.5f\n" + RESET,
                desde, hasta, total);

        double esperanza = ensayos * probExitos;
        double varianza = ensayos * probExitos * (1 - probExitos);
        System.out.printf(AZUL + "Esperanza (media): %.4f\n" + RESET, esperanza);
        System.out.printf(AZUL + "Varianza: %.4f\n" + RESET, varianza);
    }

    public static BigInteger factorial(int num) {
        BigInteger resultado = BigInteger.ONE;
        for (int i = 2; i <= num; i++) {
            resultado = resultado.multiply(BigInteger.valueOf(i));
        }
        return resultado;
    }

    public static BigInteger combinatorio(int n, int k) {
        BigInteger numerador = factorial(n);
        BigInteger denominador = factorial(k).multiply(factorial(n - k));
        return numerador.divide(denominador);
    }

    public static double probabilidadBinomial(int n, int k, double p) {
        BigInteger combinatoria = combinatorio(n, k);
        return combinatoria.doubleValue() * Math.pow(p, k) * Math.pow(1 - p, n - k);
    }
}
