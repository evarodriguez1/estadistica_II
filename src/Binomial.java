import java.util.Scanner;
import java.math.BigInteger;

public class Binomial {

    static final String ROJO = "\u001B[31m";
    static final String VERDE = "\u001B[32m";
    static final String AZUL = "\u001B[34m";
    static final String RESET = "\u001B[0m";

    public static void distribucionBinomial(){

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
                scanner.nextLine();
            }
        }

        //¿agrego la funcionalidad para que calcule la probabilidad si es que la persona no la sabe o no tiene ganas?
        double probExitos = 0;
        while (true) {
            try {
                System.out.print("\nIngrese la probabilidad de " + VERDE + " éxito " + RESET + " (entre 0 y 1): ");
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

        int casosFav;
        while (true) {

            try{
                System.out.println("\nIngrese la cantidad de veces que espera que un éxito ocurra");
                casosFav = Integer.parseInt(scanner.nextLine().trim());
                if (casosFav <= ensayos && casosFav>=0) {
                    break;
                } else {
                    System.out.println(ROJO + "El número debe ser un número entero > o = a cero y < o = a la cantidad total de casos." + RESET);
                }
            }catch(NumberFormatException e){
                System.out.println(ROJO + "Debe ingresar un número entero válido." + RESET);
                scanner.nextLine();
            }

        }

        double resultado = probabilidadBinomial(ensayos, casosFav, probExitos);
        System.out.printf(AZUL + "\nLa probabilidad de obtener exactamente %d éxitos en %d ensayos es: %.5f\n" + RESET,
                casosFav, ensayos, resultado);
        scanner.nextLine();
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
        //no puedo usar la barra con biginterger, por eso utilizo divide
    }

    //Fórmula de probabilidad Binomial:  P(x; n, p) = C(n, x) * p^x * (1 - p)^(n - x)
    public static double probabilidadBinomial(int n, int k, double p) {
        BigInteger combinatoria = combinatorio(n, k);
        // Convierto combinatoria a double para no romper el cálculo
        return combinatoria.doubleValue() * Math.pow(p, k) * Math.pow(1 - p, n - k);
    }
}
