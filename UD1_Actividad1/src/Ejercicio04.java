/*
*   EJERCICIO 4 – Crea un programa que se ejecute desde Linux que:
*   ▪ Define una constante que indique el tiempo máximo de ejecución de un proceso expresado en milisegundos
*   ▪ Ejecuta un proceso, si no ha terminado en el tiempo especificado por la constante, 
*   se finalizará el proceso y se mostrará un mensaje por pantalla.
*/

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Ejercicio04 {
    public static void main(String[] args) {
        final long TIME = 10000L;
        ProcessBuilder pb = new ProcessBuilder("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");

        try {
            // Inicio proceso
            Process p = pb.start();
            p.waitFor(TIME, TimeUnit.MILLISECONDS);

            if (p.isAlive()) {
                System.out.println("Proceso vivo, se finalizara!");
                p.destroy();
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Error, posible ruta/argumento erroneo ");
        }
    }
}
