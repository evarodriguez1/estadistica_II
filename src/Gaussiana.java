import java.util.Scanner;

public class Gaussiana {

    static final String ROJO = "\u001B[31m";
    static final String VERDE = "\u001B[32m";
    static final String AZUL = "\u001B[34m";
    static final String RESET = "\u001B[0m";

    public static void distribucionNormal() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Usted eligió trabajar con la" + AZUL + " distribución normal.\n" + RESET +
                "Esta distribución modela variables continuas que se agrupan en torno a un valor medio.");

        // 1) Lectura de la media muestral x̄
        double mediaMuestral;
        while (true) {
            System.out.print("\nIngrese la media muestral x̄: ");
            try {
                mediaMuestral = Double.parseDouble(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Entrada inválida. Debe ingresar un número." + RESET);
            }
        }

        // 2) Lectura de la varianza muestral s^2
        double varianzaMuestral;
        while (true) {
            System.out.print("\nIngrese la varianza muestral s^2 (>= 0): ");
            try {
                varianzaMuestral = Double.parseDouble(scanner.nextLine().trim());
                if (varianzaMuestral >= 0) break;
                System.out.println(ROJO + "La varianza no puede ser negativa." + RESET);
            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Entrada inválida. Debe ingresar un número." + RESET);
            }
        }

        // 3) Cálculo de desviación estándar s
        double desviacion = Math.sqrt(varianzaMuestral);

        // 4) Tipo de consulta
        System.out.println("\nSeleccione el tipo de probabilidad a calcular:");
        System.out.println("1) Valor puntual P(X = x)");
        System.out.println("2) Acumulada un valor");
        System.out.println("3) Acumulada dos valores");
        int opcion;
        while (true) {
            System.out.print("Opción (1-3): ");
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
                if (opcion >= 1 && opcion <= 3) break;
            } catch (NumberFormatException e) {}
            System.out.println(ROJO + "Opción inválida. Debe ser 1, 2 o 3." + RESET);
        }

        double x1, x2;
        boolean tipoMayor = false;
        switch (opcion) {
            case 1:
                // Puntual
                System.out.print("\nIngrese x para P(X = x): ");
                x1 = leerDoubleValido(scanner);
                mostrarProbabilidadPuntual(x1, mediaMuestral, desviacion);
                break;

            case 2:
                // Acumulada un valor: elegir ≤ o ≥
                System.out.println("\nSeleccione acumulación:");
                System.out.println("1) P(X ≤ x)");
                System.out.println("2) P(X ≥ x)");
                int sub;
                while (true) {
                    System.out.print("Sub-opción (1-2): ");
                    try {
                        sub = Integer.parseInt(scanner.nextLine().trim());
                        if (sub == 1 || sub == 2) break;
                    } catch (NumberFormatException e) {}
                    System.out.println(ROJO + "Elija 1 o 2." + RESET);
                }
                tipoMayor = (sub == 2);
                System.out.print("\nIngrese x para acumulada: ");
                x1 = leerDoubleValido(scanner);
                mostrarProbabilidadAcumulada(x1, mediaMuestral, desviacion, tipoMayor);
                break;

            case 3:
                // Acumulada dos valores
                System.out.print("\nIngrese valor mínimo a: ");
                x1 = leerDoubleValido(scanner);
                System.out.print("Ingrese valor máximo b: ");
                x2 = leerDoubleValido(scanner);
                mostrarProbabilidadEntre(x1, x2, mediaMuestral, desviacion);
                break;
        }
        scanner.nextLine();
    }

    // Método para leer double válido
    private static double leerDoubleValido(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Entrada inválida. Debe ingresar un número." + RESET);
            }
        }
    }

    // Mostrar probabilidad puntual usando densidad PDF
    private static void mostrarProbabilidadPuntual(double x, double media, double sigma) {
        double z = (x - media) / sigma;
        double densidad = Math.exp(-0.5 * z * z) / (sigma * Math.sqrt(2 * Math.PI));
        System.out.printf("\nZ = %.4f\n", z);
        System.out.printf("P(X = %.4f) ≈ %.4f\n", x, densidad);
    }

    // Mostrar probabilidad acumulada ≤ o ≥
    private static void mostrarProbabilidadAcumulada(double x, double media, double sigma, boolean mayor) {
        double z = (x - media) / sigma;
        double cdf = 0.5 * (1 + funcionError(z / Math.sqrt(2)));
        double prob = mayor ? (1 - cdf) : cdf;
        System.out.printf("\nZ = %.4f\n", z);
        if (mayor) {
            System.out.printf("P(X ≥ %.4f) = %.4f\n", x, prob);
        } else {
            System.out.printf("P(X ≤ %.4f) = %.4f\n", x, prob);
        }
    }

    // Mostrar probabilidad entre dos valores
    private static void mostrarProbabilidadEntre(double a, double b, double media, double sigma) {
        double z1 = (a - media) / sigma;
        double z2 = (b - media) / sigma;
        double cdf1 = 0.5 * (1 + funcionError(z1 / Math.sqrt(2)));
        double cdf2 = 0.5 * (1 + funcionError(z2 / Math.sqrt(2)));
        double prob = cdf2 - cdf1;
        System.out.printf("\nZ1 = %.4f, Z2 = %.4f\n", z1, z2);
        System.out.printf("P(%.4f ≤ X ≤ %.4f) = %.4f\n", a, b, prob);
    }

    // Cálculo de función de error
    public static double funcionError(double u) {
        double signo;
        if (u < 0) {
            signo = -1;
        } else {
            signo = 1;
        }
        u = Math.abs(u);

        double t = 1.0 / (1.0 + 0.3275911 * u);
        double[] coef = {0.254829592, -0.284496736, 1.421413741, -1.453152027, 1.061405429};
        double pol = coef[0];
        for (int i = 1; i < coef.length; i++) {
            pol = pol * t + coef[i];
        }
        return signo * (1 - pol * t * Math.exp(-u * u));
    }
}