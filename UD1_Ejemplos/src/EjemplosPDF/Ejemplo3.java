/*
*   Ejemplo 3: lanzar un proceso con argumentos
 */

package EjemplosPDF;

import java.io.IOException;

public class Ejemplo3 {
    public static void main(String[] args) {
        try {
            ProcessBuilder proceso = new ProcessBuilder("notepad", "salida.txt");
            Process p = proceso.start();
        } catch (IOException e) {
            System.out.println("Error durante la ejecución del proceso");
            e.printStackTrace();
        }
    }
}
