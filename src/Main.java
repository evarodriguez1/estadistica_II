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
            System.out.println("Seleccione el tipo de cálculo a realizar");
            System.out.println("1. Cálculo de estadísticas");
            System.out.println("2. Cálculo de probabilidad");
            System.out.print("Seleccione 1 o 2: ");
            String tipoInput = scanner.nextLine().trim();

            switch (tipoInput) {
                case "1":
                    // Llamada a la clase Estadisticas
                    Estadisticas.menuEstadistica();
                    break;

                case "2":
                    String tipoDato = "";
                    while (true) {
                        System.out.println("\n¿Qué tipo de datos ingresará?");
                        System.out.println("1. Discretos (valores enteros)");
                        System.out.println("2. Continuos (valores con decimales)");
                        String opcionTipo = scanner.nextLine();
                        if (opcionTipo.equals("1")) {
                            tipoDato = "Discretos";
                            break;
                        } else if (opcionTipo.equals("2")) {
                            tipoDato = "Continuos";
                            break;
                        } else {
                            System.out.println(ROJO + "Opción inválida. Ingrese 1 o 2." + RESET);
                        }
                    }

                    if (tipoDato.equals("Discretos")) {
                        System.out.println("\nSeleccione la distribución a calcular:");
                        System.out.println("1. Distribución Binomial");
                        System.out.println("2. Distribución de Poisson");
                        System.out.println("3. Distribución Hipergeométrica");
                        System.out.print("Opción: ");
                        String distribucion = scanner.nextLine();

                        switch (distribucion) {
                            case "1":
                                System.out.println("\n");
                                //se me rompe a la m con números grandes :(
                                Binomial.distribucionBinomial();
                                break;
                            case "2":
                                System.out.println("\n");
                                Poisson.distribucionPoisson();
                                break;
                            case "3":
                                System.out.println("jelou funcion no implementada"); //sacale esto a la m cuando lo tengas jsjsjsj
                                //Hipergeometrica.calcular(); poneeeeleeee
                                break;
                            default:
                                System.out.println(ROJO + "Opción inválida." + RESET);
                        }
                    } else if (tipoDato.equals("Continuos")) {
                        Gaussiana.distribucionNormal();
                    }
                    break;

                default:
                    System.out.println(ROJO + "Ingrese una opción válida (1 o 2)" + RESET);
            }
        }
    }
}