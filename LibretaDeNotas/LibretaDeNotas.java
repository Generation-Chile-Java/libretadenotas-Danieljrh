package LibretaDeNotas;

import java.util.*;

/**Crear la clase:
Crea la clase LibretaDeNotas para que pueda evaluar y almacenar las calificaciones de una lista de estudiantes.
Utiliza un HashMap para almacenar las calificaciones de los estudiantes, donde la llave es el nombre del estudiante y el valor es un ArrayList de notas.
Solicita al usuario que ingrese la cantidad de alumnos y la cantidad de notas por alumno.
Solicita el nombre de cada alumno y las notas correspondientes, almacenándolas en el HashMap.
Recorrer el HashMap y Evaluar Calificaciones:
Utiliza un bucle (por ejemplo, for o foreach) para recorrer el HashMap de calificaciones.
        Calcular Promedio, Nota Máxima y Mínima por Estudiante:
Después de evaluar las calificaciones, calcula y muestra para cada estudiante:
Promedio de Notas: Suma todas las calificaciones y divide por la cantidad total de notas.
Nota Máxima: Encuentra la calificación más alta en el ArrayList.
Nota Mínima: Encuentra la calificación más baja en el ArrayList.
Menú de Opciones:
Muestra un menú con las siguientes opciones:
        1. Mostrar el Promedio de Notas por Estudiante.
2. Mostrar si la Nota es Aprobatoria o Reprobatoria por Estudiante.
        3. Mostrar si la Nota está por Sobre o por Debajo del Promedio del Curso por Estudiante.
        0. Salir del Menú.
Utiliza un bucle para permitir al usuario seleccionar opciones hasta que ingrese 0 para salir.
Operaciones del Menú:
Opción 1: Mostrar el Promedio de Notas por Estudiante.
Muestra el promedio de notas por cada estudiante calculado previamente.
Opción 2: Mostrar si la Nota es Aprobatoria o Reprobatoria por Estudiante.
Solicita al usuario ingresar el nombre de un estudiante y una nota, luego verifica si es aprobatoria o reprobatoria según una nota de aprobación definida.
Opción 3: Mostrar si la Nota está por Sobre o por Debajo del Promedio del Curso por Estudiante.
Solicita al usuario ingresar el nombre de un estudiante y una nota, luego verifica si está por sobre o por debajo del promedio general.
        Validaciones:
Implementa validaciones para asegurar que las notas ingresadas estén en un rango válido y que la cantidad de alumnos sea un número positivo.*/




public class LibretaDeNotas {
    private final HashMap<String, ArrayList<Integer>> notasAlumnos = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public void ingresarNotas() {
        int cantidadAlumnos = leerNumero("Ingrese la cantidad de alumnos: ", 1, Integer.MAX_VALUE);
        int cantidadNotas = leerNumero("Ingrese la cantidad de notas por alumno: ", 1, Integer.MAX_VALUE);

        for (int i = 0; i < cantidadAlumnos; i++) {
            System.out.print("Ingrese el nombre del alumno #" + (i + 1) + ": ");
            String nombre = scanner.nextLine();

            ArrayList<Integer> notas = new ArrayList<>();
            for (int j = 0; j < cantidadNotas; j++) {
                notas.add(leerNumero("Ingrese la nota " + (j + 1) + " de " + nombre + ": ", 0, 100));
            }
            notasAlumnos.put(nombre, notas);
        }
    }

    public void mostrarPromedios() {
        System.out.println("\nPromedio de notas por estudiante:");
        for (var entry : notasAlumnos.entrySet()) {
            double promedio = entry.getValue().stream().mapToInt(Integer::intValue).average().orElse(0.0);
            System.out.println(entry.getKey() + ": " + String.format("%.2f", promedio));
        }
    }

    public void verificarNotaAprobatoria() {
        String nombre = leerNombre("Ingrese el nombre del estudiante: ");
        int nota = leerNumero("Ingrese la nota a evaluar: ", 0, 100);
        System.out.println(nota >= 60 ? "Aprobado" : "Reprobado");
    }

    public void compararConPromedioCurso() {
        String nombre = leerNombre("Ingrese el nombre del estudiante: ");
        int nota = leerNumero("Ingrese la nota a evaluar: ", 0, 100);
        double promedioCurso = calcularPromedioCurso();

        System.out.println(nota > promedioCurso ? "Sobre el promedio del curso." : "Por debajo del promedio del curso.");
    }

    private double calcularPromedioCurso() {
        return notasAlumnos.values().stream()
                .flatMapToInt(notas -> notas.stream().mapToInt(Integer::intValue))
                .average().orElse(0.0);
    }

    private int leerNumero(String mensaje, int min, int max) {
        int numero;
        while (true) {
            System.out.print(mensaje);
            try {
                numero = Integer.parseInt(scanner.nextLine());
                if (numero >= min && numero <= max) break;
            } catch (NumberFormatException ignored) {}
            System.out.println("Entrada inválida. Ingrese un número entre " + min + " y " + max + ".");
        }
        return numero;
    }

    private String leerNombre(String mensaje) {
        String nombre;
        do {
            System.out.print(mensaje);
            nombre = scanner.nextLine();
        } while (!notasAlumnos.containsKey(nombre));
        return nombre;
    }

    public void mostrarMenu() {
        while (true) {
            System.out.println("\nMenú de Opciones:\n1. Promedio por Estudiante\n2. Verificar Nota\n3. Comparar con Promedio del Curso\n0. Salir");
            int opcion = leerNumero("Seleccione una opción: ", 0, 3);

            if (opcion == 0) break;
            switch (opcion) {
                case 1 -> mostrarPromedios();
                case 2 -> verificarNotaAprobatoria();
                case 3 -> compararConPromedioCurso();
            }
        }
    }

    public static void main(String[] args) {
        LibretaDeNotas libreta = new LibretaDeNotas();
        libreta.ingresarNotas();
        libreta.mostrarMenu();
    }
}