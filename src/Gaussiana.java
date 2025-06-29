import java.util.Scanner;

public class Gaussiana {

    static final String ROJO = "\u001B[31m";
    static final String AZUL = "\u001B[34m";
    static final String RESET = "\u001B[0m";

    public static void distribucionNormal() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Usted eligió trabajar con la" + AZUL + " distribución normal. " + RESET +
                "Esta distribución modela variables continuas que se agrupan en torno a un valor medio.");

        // 1) Lectura de la media muestral x̄
        double mediaMuestral;
        while (true) {
            System.out.print("Ingrese la media muestral x̄: ");
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
            System.out.print("Ingrese la varianza muestral s^2 (>= 0): ");
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
        System.out.println("Seleccione el tipo de probabilidad a calcular:");
        System.out.println("1) Acumulada un valor P(X ≥ a) o P(X ≤ a)");
        System.out.println("2) Acumulada dos valores P(a ≤ X ≤ b)");
        int opcion;

        while (true) {
            System.out.print("Opción (1-2): ");
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
                if (opcion >= 1 && opcion <= 3) break;
            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Opción inválida. Debe ser 1 o 2." + RESET);
            }
        }

        double x1, x2;
        boolean tipoMayor;
        switch (opcion) {
            case 1:
                // Acumulada un valor: elegir ≤ o ≥
                System.out.println("Seleccione acumulación:");
                System.out.println("1) P(X ≤ x)");
                System.out.println("2) P(X ≥ x)");
                int sub;
                while (true) {
                    System.out.print("Sub-opción (1-2): ");
                    try {
                        sub = Integer.parseInt(scanner.nextLine().trim());
                        if (sub == 1 || sub == 2) break;
                    } catch (NumberFormatException e) {
                        System.out.println(ROJO + "Elija 1 o 2." + RESET);
                    }
                }
                tipoMayor = (sub == 2);
                System.out.print("Ingrese x para acumulada: ");
                x1 = leerDoubleValido(scanner);
                mostrarProbabilidadAcumulada(x1, mediaMuestral, desviacion, tipoMayor);
                break;

            case 2:
                // Acumulada dos valores
                System.out.print("Ingrese valor mínimo a: ");
                x1 = leerDoubleValido(scanner);
                System.out.print("Ingrese valor máximo b: ");
                x2 = leerDoubleValido(scanner);
                mostrarProbabilidadEntre(x1, x2, mediaMuestral, desviacion);
                break;
        }
        scanner.nextLine();
    }

    // Leer double válido
    private static double leerDoubleValido(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Entrada inválida. Debe ingresar un número." + RESET);
            }
        }
    }

    // Probabilidad acumulada ≤ o ≥ usando Hastings
    private static void mostrarProbabilidadAcumulada(double x, double media, double sigma, boolean mayor) {
        double z = (x - media) / sigma;
        double cdf = cdfHastings(z);
        double prob = mayor ? (1 - cdf) : cdf;
        System.out.printf("Z = %.4f", z);
        if (mayor) {
            System.out.printf("P(X ≥ %.4f) = %.4f", x, prob);
        } else {
            System.out.printf("P(X ≤ %.4f) = %.4f", x, prob);
        }
    }

    // Probabilidad entre dos valores
    private static void mostrarProbabilidadEntre(double a, double b, double media, double sigma) {
        double z1 = (a - media) / sigma;
        double z2 = (b - media) / sigma;
        double cdf1 = cdfHastings(z1);
        double cdf2 = cdfHastings(z2);
        double prob = cdf2 - cdf1;
        System.out.printf("Z1 = %.4f\nZ2 = %.4f\n", z1, z2);
        System.out.printf("P(%.4f ≤ X ≤ %.4f) = %.4f", a, b, prob);
    }

    /**
     * Aproximación CDF Normal estándar por Hastings (A&S 26.2.17)
     * Error máximo < 7.5e-8 en |z| < 4
     */
    private static double cdfHastings(double z) {
        // simetría
        boolean neg = z < 0;
        double absZ = Math.abs(z);
        // densidad φ(z)
        double phi = Math.exp(-0.5 * absZ * absZ) / Math.sqrt(2 * Math.PI);
        // coeficientes
        double b0 = 0.2316419;
        double b1 = 0.319381530;
        double b2 = -0.356563782;
        double b3 = 1.781477937;
        double b4 = -1.821255978;
        double b5 = 1.330274429;
        double t = 1.0 / (1.0 + b0 * absZ);
        // polinomio
        double pol = (((((b5 * t + b4) * t + b3) * t + b2) * t + b1) * t);
        double approx = 1.0 - phi * pol;
        return neg ? (1.0 - approx) : approx;
    }
}