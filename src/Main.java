import java.util.Scanner;

public class Main {
    // --- Constantes de color definidas a nivel de clase para ser usadas en todos los métodos ---
    static final String ROJO = "\u001B[31m";
    static final String VERDE = "\u001B[32m";
    static final String AZUL = "\u001B[34m";
    static final String RESET = "\u001B[0m";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // --- Banner de bienvenida ---
        System.out.println(AZUL + "==============================================" + RESET);
        System.out.println(AZUL + " BIENVENIDO A LA CALCULADORA ESTADÍSTICA" + RESET);
        System.out.println(AZUL + "==============================================" + RESET);
        System.out.println("Una herramienta para facilitar cálculos de estadística y probabilidad.");

        while (true) {
            System.out.println("\n--- " + VERDE + "Menú Principal" + RESET + " ---");
            System.out.println("¿Qué deseas hacer?");
            System.out.println("1. Realizar cálculos de" + AZUL + " Estadística Descriptiva" + RESET + " (media, varianza, etc.).");
            System.out.println("2. Realizar cálculos de" + AZUL + " Probabilidad" + RESET + " (Binomial, Poisson, etc.).");
            System.out.println("3. " + ROJO + "Salir" + RESET + " del programa.");
            System.out.print("Selecciona una opción (1, 2 o 3): ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    Estadisticas.menuEstadistica();
                    break;
                case "2":
                    // Llama al menú de probabilidades.
                    mostrarMenuProbabilidad(scanner);
                    break;
                case "3":
                    System.out.println(VERDE + "\n¡Gracias por usar la calculadora! Hasta pronto." + RESET);
                    return; // Termina el programa
                default:
                    System.out.println(ROJO + "Opción no válida. Por favor, ingresa 1, 2 o 3." + RESET);
            }
        }
    }

    /**
     * Muestra un menú APLANADO y descriptivo para las distribuciones de probabilidad.
     * Esto reemplaza la necesidad de anidar menús de "discreta" y "continua".
     */
    public static void mostrarMenuProbabilidad(Scanner scanner) {
        while (true) {
            System.out.println("\n--- " + VERDE + "Menú de Probabilidades" + RESET + " ---");
            System.out.println("Selecciona la distribución que se ajuste a tu problema:");
            System.out.println(AZUL + "1. Binomial:" + RESET + "          Para N repeticiones de un experimento con dos resultados (éxito/fracaso).");
            System.out.println(AZUL + "2. Poisson:" + RESET + "           Para la probabilidad de que ocurra un N° de eventos en un intervalo.");
            System.out.println(AZUL + "3. Hipergeométrica:" + RESET + "   Para extracciones de una muestra SIN devolver los elementos.");
            System.out.println(AZUL + "4. Normal (Gaussiana):" + RESET + "  Para variables continuas con datos agrupados en torno a una media.");
            System.out.println("5. " + ROJO + "Volver" + RESET + " al menú principal.");
            System.out.print("Elige una opción (1-5): ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    repetirCalculo(scanner, "Binomial");
                    break;
                case "2":
                    repetirCalculo(scanner, "Poisson");
                    break;
                case "3":
                    repetirCalculo(scanner, "Hipergeométrica");
                    break;
                case "4":
                    repetirCalculo(scanner, "Normal (Gaussiana)");
                    break;
                case "5":
                    return; // Sale de este menú y vuelve al principal
                default:
                    System.out.println(ROJO + "Opción no válida. Por favor, ingresa un número del 1 al 5." + RESET);
            }
        }
    }

    // Ejecuta el cálculo para la distribución seleccionada y pregunta si se desea repetir.

    public static void repetirCalculo(Scanner scanner, String tipoDistribucion) {
        while (true) {
            // Llama al método de cálculo correspondiente
            switch (tipoDistribucion) {
                case "Binomial":
                    Binomial.distribucionBinomial();
                    break;
                case "Poisson":
                    Poisson.distribucionPoisson();
                    break;
                case "Hipergeométrica":
                    Hipergeometrica.distribucionHipergeometrica();
                    break;
                case "Normal (Gaussiana)":
                    Gaussiana.distribucionNormal();
                    break;
            }

            System.out.printf(VERDE + "\n¿Deseas realizar otro cálculo con la distribución %s? (s/n): " + RESET, tipoDistribucion);
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (!respuesta.equals("s")) {
                break; // Sale del bucle de repetición y vuelve al menú de probabilidades
            }
        }
    }
}