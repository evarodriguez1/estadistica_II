import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("¿Qué tipo de datos ingresará?");
        System.out.println("1. Discretos (valores enteros)");
        System.out.println("2. Continuos (valores con decimales)");

        while (true) {
            String opcionTipo = scanner.nextLine();
            if (opcionTipo.equals("1")) {
                Estadisticas.tipoDato = "Discretos";
                break;
            } else if (opcionTipo.equals("2")) {
                Estadisticas.tipoDato = "Continuos";
                break;
            } else {
                System.out.println(Estadisticas.ROJO + "Opción inválida. Ingrese 1 o 2." + Estadisticas.RESET);
            }
        }

        List<Double> numeros = Estadisticas.ingresarNumeros(scanner);
        Collections.sort(numeros);
        System.out.println(Estadisticas.VERDE + "Lista ordenada: " + numeros + Estadisticas.RESET);
        System.out.println("Cantidad de números: " + numeros.size() + " y es " + (numeros.size() % 2 == 0 ? "par" : "impar"));
        System.out.println("Tipo de datos: " + Estadisticas.AZUL + Estadisticas.tipoDato + Estadisticas.RESET);

        // Acá se puede enganchar lo nuevo, luego de que termine el menú original o ver como reorganizar el menu
        //porque viene enganchado de u metodo mostrar menu cuac
        Estadisticas.mostrarMenu(numeros, scanner);
    }
}