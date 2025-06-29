import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        final String ROJO = "\u001B[31m";
        final String VERDE = "\u001B[32m";
        final String AZUL = "\u001B[34m";
        final String RESET = "\u001B[0m";

        System.out.println("=====================================");
        System.out.println(AZUL + "  Programa: Cálculo de Estadísticas         ");
        System.out.println("           y Probabilidades                 " + RESET);
        System.out.println("=====================================\n");

        while (true) {
            System.out.println("\nSeleccione el tipo de cálculo a realizar:");
            System.out.println("1. Cálculo de estadísticas");
            System.out.println("2. Cálculo de probabilidad");
            System.out.println("3. Salir del programa");
            System.out.print("Seleccione una opción (1, 2 o 3): ");
            String tipoInput = scanner.nextLine().trim();

            switch (tipoInput) {
                case "1":
                    Estadisticas.menuEstadistica();
                    break;

                case "2":
                    seleccionarProbabilidad(scanner);
                    break;

                case "3":
                    System.out.println(VERDE + "Gracias por usar el programa. ¡Hasta luego!" + RESET);
                    return;

                default:
                    System.out.println(ROJO + "Ingrese una opción válida (1, 2 o 3)" + RESET);
            }
        }
    }

    public static void seleccionarProbabilidad(Scanner scanner) {
        final String ROJO = "\u001B[31m";
        final String VERDE = "\u001B[32m";
        final String AZUL = "\u001B[34m";
        final String RESET = "\u001B[0m";

        boolean seguirEnProbabilidad = true;

        while (seguirEnProbabilidad) {
            System.out.println("\n¿Qué tipo de datos desea ingresar?");
            System.out.println("1. Discretos (valores enteros)");
            System.out.println("2. Continuos (valores con decimales)");
            System.out.println("3. Volver al menú principal");
            System.out.print("Opción: ");
            String opcionTipo = scanner.nextLine().trim();

            if (opcionTipo.equals("1")) {
                boolean seguirEnDiscretos = true;
                while (seguirEnDiscretos) {
                    System.out.println("\nSeleccione la distribución discreta:");
                    System.out.println("1. Distribución Binomial");
                    System.out.println("2. Distribución de Poisson");
                    System.out.println("3. Distribución Hipergeométrica");
                    System.out.println("4. Volver al paso anterior");
                    System.out.print("Opción: ");
                    String distribucion = scanner.nextLine().trim();

                    switch (distribucion) {
                        case "1":
                            repetirCalculo(scanner, "binomial");
                            break;
                        case "2":
                            repetirCalculo(scanner, "poisson");
                            break;
                        case "3":
                            repetirCalculo(scanner, "hipergeometrica");
                            break;
                        case "4":
                            seguirEnDiscretos = false;
                            break;
                        default:
                            System.out.println(ROJO + "Opción inválida. Ingrese 1 a 4." + RESET);
                    }
                }
            } else if (opcionTipo.equals("2")) {
                boolean seguirEnContinuos = true;
                while (seguirEnContinuos) {
                    System.out.println("\nSeleccione la distribución continua:");
                    System.out.println("1. Distribución Normal");
                    System.out.println("2. Volver al paso anterior");
                    System.out.print("Opción: ");
                    String opcionContinua = scanner.nextLine().trim();

                    switch (opcionContinua) {
                        case "1":
                            repetirCalculo(scanner, "normal");
                            break;
                        case "2":
                            seguirEnContinuos = false;
                            break;
                        default:
                            System.out.println(ROJO + "Opción inválida. Ingrese 1 o 2." + RESET);
                    }
                }
            } else if (opcionTipo.equals("3")) {
                seguirEnProbabilidad = false;
            } else {
                System.out.println(ROJO + "Opción inválida. Ingrese 1, 2 o 3." + RESET);
            }
        }
    }

    public static void repetirCalculo(Scanner scanner, String tipo) {
        final String VERDE = "\u001B[32m";
        final String RESET = "\u001B[0m";

        while (true) {
            switch (tipo) {
                case "binomial":
                    Binomial.distribucionBinomial();
                    break;
                case "poisson":
                    Poisson.distribucionPoisson();
                    break;
                case "hipergeometrica":
                    Hipergeometrica.distribucionHipergeometrica();
                    break;
                case "normal":
                    Gaussiana.distribucionNormal();
                    break;
            }

            System.out.print(VERDE + "\n¿Desea realizar otro cálculo con la misma distribución? (s/n): " + RESET);
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (!respuesta.equals("s")) {
                break;
            }
        }
    }
}
