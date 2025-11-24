/*
EJERCICIO 5 – Escribe un programa que se ejecute desde Windows que:
▪ Reciba una lista de comandos a ejecutar.
▪ Cree un proceso hijo por cada comando de manera concurrente.
▪ Espere a que todos los procesos hijos terminen.
▪ Al finalizar, muestre un mensaje indicando qué procesos terminaron correctamente y cuáles no.
*/

import java.io.IOException;
import java.util.ArrayList;

public class Ejercicio05 {
    public static void main(String[] args) {
        ArrayList<String> comandos = new ArrayList<>();
        comandos.add("notepad");
        comandos.add("calc");
        comandos.add("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");

        try {
            // ProcessBuilder pb1 = new ProcessBuilder("C:\\Program
            // Files\\Google\\Chrome\\Application\\chrome.exe");
            ProcessBuilder pb = new ProcessBuilder();
            Process p = null;
            for (byte i = 0; i < comandos.size(); i++) {
                pb = new ProcessBuilder(comandos.get(i));
                p = pb.start();
            }

            /* ProcessBuilder pb1 = new ProcessBuilder("cmd");
            ProcessBuilder pb2 = new ProcessBuilder("calc");
            ProcessBuilder pb3 = new ProcessBuilder("notepad");

            Process p1 = pb1.start();
            Process p2 = pb2.start();
            Process p3 = pb3.start();

            p1.waitFor();
            p2.waitFor();
            p3.waitFor(); */
            int finished = p.waitFor();

            if(finished == 0){
                System.out.println("Proceso termino correctamente");
            }
            
            
        } catch (IOException | InterruptedException e) {
            System.err.println("Error en la ejecucion");
        }
    }
}
