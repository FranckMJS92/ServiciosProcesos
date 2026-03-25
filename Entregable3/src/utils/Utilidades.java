package utils;

import java.util.List;
import java.util.Scanner;

import modelo.Producto;

/**
 * Clase de utilidades para la entrada/salida de datos y configuración del sistema.
 * <p>
 * Esta clase proporciona métodos auxiliares para:
 * <ul>
 *   <li>Leer datos del usuario con validación (enteros, decimales, texto)</li>
 *   <li>Mostrar listas de productos formateadas</li>
 *   <li>Almacenar las constantes de configuración del sistema (puerto y dirección)</li>
 * </ul>
 * </p>
 * <p>
 * Cada instancia de esta clase mantiene su propio objeto {@link Scanner} para
 * permitir que múltiples clientes (en diferentes hilos) lean de consola sin
 * interferencias.
 * </p>
 * <p>
 * Proyecto desarrollado para la asignatura de Programación de Servicios y Procesos.
 * </p>
 * 
 * @author Francisco Lopez
 * @version 1.0
 */
public class Utilidades {

    /** Puerto de conexión para el servidor */
    public static final int PUERTO = 5000;
    
    /** Dirección IP del servidor (localhost para conexión local) */
    public static final String DIRECCION = "localhost";

    /** Scanner para lectura de datos desde la consola */
    private final Scanner scan;

    /**
     * Constructor por defecto.
     * <p>
     * Inicializa el objeto {@link Scanner} para la lectura de datos
     * desde la entrada estándar (consola).
     * </p>
     */
    public Utilidades() {
        this.scan = new Scanner(System.in);
    }

    /**
     * Lee un número decimal (double) desde la consola con validación.
     * <p>
     * El método sigue solicitando el valor hasta que se introduce un
     * número válido y no negativo. Si el usuario introduce un valor
     * no numérico o negativo, muestra un mensaje de error y repite.
     * </p>
     * 
     * @param mensaje Mensaje que se muestra al usuario antes de leer el valor
     * @return El número decimal introducido por el usuario (>= 0)
     */
    public double leerDouble(String mensaje) {
        double num = 0;
        boolean error = false;
        do {
            error = false;
            try {
                System.out.print(mensaje);
                num = Double.parseDouble(scan.nextLine());
                if (num < 0) {
                    System.out.println("[ERROR] Número incorrecto");
                    error = true;
                }
            } catch (Exception e) {
                System.out.println("[ERROR] Número incorrrecto");
                error = true;
            }

        } while (error);
        return num;
    }

    /**
     * Lee un número entero (int) desde la consola con validación.
     * <p>
     * El método sigue solicitando el valor hasta que se introduce un
     * número entero válido. Si el usuario introduce un valor no numérico,
     * muestra un mensaje de error y repite.
     * </p>
     * 
     * @param mensaje Mensaje que se muestra al usuario antes de leer el valor
     * @return El número entero introducido por el usuario
     */
    public int leerEntero(String mensaje) {
        int num = 0;
        boolean error = false;
        do {
            error = false;
            try {
                System.out.print(mensaje);
                num = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                System.out.println("[ERROR] Número incorrecto");
                error = true;
            }

        } while (error);
        return num;
    }

    /**
     * Lee un número entero (int) dentro de un rango específico.
     * <p>
     * El método sigue solicitando el valor hasta que se introduce un
     * número entero válido que esté comprendido entre {@code min} y
     * {@code max} (ambos inclusive).
     * </p>
     * 
     * @param mensaje Mensaje que se muestra al usuario antes de leer el valor
     * @param min     Valor mínimo aceptado (inclusive)
     * @param max     Valor máximo aceptado (inclusive)
     * @return El número entero introducido por el usuario dentro del rango
     */
    public int leerEnteroRango(String mensaje, int min, int max) {
        int num = 0;
        boolean error = false;
        do {
            error = false;
            try {
                System.out.print(mensaje);
                num = Integer.parseInt(scan.nextLine());
                if (num < min || num > max) {
                    System.out.println("[ERROR] Valor incorrecto debe estar entre " + min + " y " + max);
                    error = true;
                }
            } catch (Exception e) {
                System.out.println("[ERROR] Número incorrrecto");
                error = true;
            }
        } while (error);
        return num;
    }

    /**
     * Lee una cadena de texto desde la consola con validación.
     * <p>
     * El método sigue solicitando el valor hasta que se introduce un
     * texto no vacío. Se eliminan los espacios en blanco al inicio y
     * final para la validación.
     * </p>
     * 
     * @param mensaje Mensaje que se muestra al usuario antes de leer el texto
     * @return La cadena de texto introducida por el usuario (no vacía)
     */
    public String leerTexto(String mensaje) {
        String texto = "";
        do {
            System.out.print(mensaje);
            texto = scan.nextLine();
            if (texto.trim().isEmpty()) {
                System.out.println("[ERROR] El texto no puede estar vacío");
            }
        } while (texto.isEmpty());
        return texto;
    }

    /**
     * Muestra una lista de productos por consola.
     * <p>
     * Si la lista está vacía, muestra un mensaje informativo.
     * En caso contrario, recorre la lista y muestra cada producto
     * utilizando su método {@link Producto#toString()}.
     * </p>
     * 
     * @param productos Lista de productos a mostrar (puede ser vacía, no {@code null})
     */
    public void mostrarListaProductos(List<Producto> productos) {
        if (productos.isEmpty()) {
            System.out.println("[INFO] No hay coincidencias");
        }
        for (Producto p : productos) {
            System.out.println(p);
        }
    }
}