import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        while (true) {

            // 1) Tipo de cálculo: estadística o distribuciones

            while (true) {
                System.out.println("Seleccione el tipo de cálculo a realizar");
                System.out.println("1. Cálculo de estadísticas");
                System.out.println("1. Cálculo de probabilidad");
                System.out.print("Seleccione 1 o 2: ");
                String tipoInput = scanner.nextLine().trim();

                switch (tipoInput) {
                    case "1":
                        // Llamar al código viejo (Estadísticas)
                        break;
                    case "2":
                        // Pregunta si serán datos discretos o continuos
                        // Método con switch:
                        // case 1: Llama a las distribuciones discretas
                        // método de opciones de distribuciones discretas (binom, poisson, hipGeo):
                        // Llamar clase binomial (pedir los datos de binomial)
                        // Llamar clase poisson (pedir los datos de poisson)
                        // Llamar clase hipGeo
                        // case 2: Distribución normal.
                        // método de distribución normal (pedir datos de normal)
                }

            }
        }
    }
}
