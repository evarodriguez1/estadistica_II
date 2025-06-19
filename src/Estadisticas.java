import java.util.*;
import java.util.stream.Collectors;

//prueba
public class Estadisticas {

    public static final String ROJO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AZUL = "\u001B[34m";
    public static final String RESET = "\u001B[0m";

    public static String tipoDato = "";

    public static List<Double> ingresarNumeros(Scanner scanner) {
        List<Double> numeros = new ArrayList<>();
        boolean valido = false;

        while (!valido) {
            System.out.println("Ingrese una lista de números separados por coma o espacio:");
            String entrada = scanner.nextLine();
            entrada = entrada.replace(",", " ");
            String[] partes = entrada.trim().split("\\s+");

            try {
                for (String parte : partes) {
                    double valor = Double.parseDouble(parte);
                    if (tipoDato.equals("Discretos") && valor % 1 != 0) {
                        throw new NumberFormatException("Los datos discretos deben ser enteros.");
                    }
                    numeros.add(valor);
                }
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println(ROJO + "Error: " + e.getMessage() + RESET);
                numeros.clear();
            }
        }
        return numeros;
    }

    public static void mostrarMenu(List<Double> numeros, Scanner scanner) {
        while (true) {
            System.out.println("\n¿Qué deseas calcular?");
            System.out.println("1. Medidas de posición");
            System.out.println("2. Medidas de dispersión");
            System.out.println("3. Frecuencias (todas)");
            System.out.println("4. Todas las anteriores");
            System.out.println("5. Salir");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    calcularMedidasPosicion(numeros);
                    break;
                case "2":
                    calcularMedidasDispersion(numeros);
                    break;
                case "3":
                    mostrarFrecuencias(numeros);
                    break;
                case "4":
                    calcularMedidasPosicion(numeros);
                    calcularMedidasDispersion(numeros);
                    mostrarFrecuencias(numeros);
                    break;
                case "5":
                    System.out.println("¡Hasta luego!");
                    return;
                default:
                    System.out.println(ROJO + "Opción inválida." + RESET);
            }
        }
    }

    public static void calcularMedidasPosicion(List<Double> numeros) {
        System.out.println(AZUL + "\nMedidas de posición:" + RESET);
        System.out.println("Media: " + media(numeros));
        System.out.println("Mediana (Q2): " + mediana(numeros));

        List<Double> modas = moda(numeros);
        if (modas.isEmpty()) {
            System.out.println("Moda: No hay moda");
        } else {
            System.out.println("Moda: " + modas);
        }

        System.out.println("Q1: " + cuartil(numeros, 1));
        System.out.println("Q3: " + cuartil(numeros, 3));
    }

    public static void calcularMedidasDispersion(List<Double> numeros) {
        System.out.println(VERDE + "\nMedidas de dispersión:" + RESET);
        System.out.println("Rango: " + rango(numeros));
        System.out.println("Varianza: " + varianza(numeros));
        System.out.println("Desviación estándar: " + desviacionEstandar(numeros));
        System.out.println("Rango intercuartílico: " + (cuartil(numeros, 3) - cuartil(numeros, 1)));
        System.out.println("Coeficiente de variación: " + coeficienteVariacion(numeros) + "%");
    }

    public static void mostrarFrecuencias(List<Double> numeros) {
        System.out.println(AZUL + "\nFrecuencias estadísticas:" + RESET);

        Map<Double, Long> frecuenciaAbs = numeros.stream()
                .collect(Collectors.groupingBy(n -> n, TreeMap::new, Collectors.counting()));

        int total = numeros.size();
        long acumulada = 0;
        double acumuladaRelativa = 0;

        System.out.printf("%-10s %-6s %-6s %-10s %-10s\n", "Valor", "f", "F", "fr (%)", "Fr (%)");
        System.out.println("--------------------------------------------------");

        for (Map.Entry<Double, Long> entry : frecuenciaAbs.entrySet()) {
            double valor = entry.getKey();
            long f = entry.getValue();
            acumulada += f;
            double fr = (f * 100.0) / total;
            acumuladaRelativa += fr;

            System.out.printf("%-10.2f %-6d %-6d %-10.2f %-10.2f\n", valor, f, acumulada, fr, acumuladaRelativa);
        }
    }

    public static double media(List<Double> nums) {
        return nums.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }

    public static double mediana(List<Double> nums) {
        int n = nums.size();
        if (n % 2 == 0) {
            return (nums.get(n / 2 - 1) + nums.get(n / 2)) / 2.0;
        } else {
            return nums.get(n / 2);
        }
    }

    public static List<Double> moda(List<Double> nums) {
        Map<Double, Long> frecuencia = nums.stream()
                .collect(Collectors.groupingBy(n -> n, Collectors.counting()));

        long max = frecuencia.values().stream().max(Long::compare).orElse(0L);

        if (max <= 1) return List.of(); // no hay moda

        return frecuencia.entrySet().stream()
                .filter(e -> e.getValue() == max)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public static double cuartil(List<Double> nums, int cuartil) {
        int n = nums.size();
        double pos = (cuartil / 4.0) * (n + 1);
        int index = (int) pos - 1;
        if (index < 0) return nums.get(0);
        if (index >= n - 1) return nums.get(n - 1);
        double frac = pos - Math.floor(pos);
        return nums.get(index) + frac * (nums.get(index + 1) - nums.get(index));
    }

    public static double rango(List<Double> nums) {
        return nums.get(nums.size() - 1) - nums.get(0);
    }

    public static double varianza(List<Double> nums) {
        double media = media(nums);
        return nums.stream().mapToDouble(n -> Math.pow(n - media, 2)).sum() / nums.size();
    }

    public static double desviacionEstandar(List<Double> nums) {
        return Math.sqrt(varianza(nums));
    }

    public static double coeficienteVariacion(List<Double> nums) {
        return (desviacionEstandar(nums) / media(nums)) * 100;
    }
}


// Separacion de temas
// Marti:
// Binomial
// Poisson

// Eva:
// Hipergeométrica
// Todo y salir, salir

// Luca:
// Distribución normal
// Coeficiente Curtois
