/*
*   Ejemplo 2: lanzar un proceso. Con el método waitFor mantiene nuestro programa en
*   ejecución en espera de que el proceso se cierre
 */

package EjemplosPDF;

import java.io.IOException;

public class Ejemplo2 {
    public static void main(String[] args) {
        try {
            ProcessBuilder proceso = new ProcessBuilder("C:/Windows/notepad.exe");
            // Creamos e inciamos el proceso
            Process p = proceso.start();
            // Se mantiene el programa en ejecución en espera de que el proceso se cierre
            p.waitFor();
            System.out.println("Ha finalizado el proceso");
        } catch (IOException e) {
            System.out.println("Error durante la ejecución del proceso");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Proceso interrumpido");
            e.printStackTrace();
        }
    }
}
