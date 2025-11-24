/*
EJERCICIO 3 - Crea un programa Java que reciba como argumentos varios comandos a ejecutar (al menos 2). Para cada comando:
•	Cree un proceso hijo que ejecute el comando.
•	Obtenga el stream de salida estándar y muestre por pantalla línea a línea la salida del proceso, 
precedida del nombre del comando.
•	Obtenga el stream de error y muestre por pantalla cualquier mensaje de error, precedido del nombre del comando.
El programa debe esperar a que todos los procesos hijos terminen antes de finalizar
Al final, muestre un resumen indicando qué comandos terminaron correctamente y cuáles produjeron errores.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Ejercicio03 {
    public static void main(String[] args) {
        /*
         * if (args.length != 3) {
         * System.err.println("Argumentos distintos de 3");
         * System.exit(1);
         * }
         */

        try {
            ProcessBuilder pb1 = new ProcessBuilder("cmd", "/C", "dir", "D:\\html5up-dimension");
            ProcessBuilder pb2 = new ProcessBuilder("cmd", "/C", "dir", "C:\\sge");
            ProcessBuilder pb3 = new ProcessBuilder("cmd", "/C", "dir", "/E");

            // Objetos Process
            Process p1 = pb1.start();
            Process p2 = pb2.start();
            Process p3 = pb3.start();

            // Guardar salida de proceso int
            int e1 = p1.waitFor();
            int e2 = p2.waitFor();
            int e3 = p3.waitFor();

            // Metodos para streams
            streamStandar(p1);
            streamError(p1, pb1);

            streamStandar(p2);
            streamError(p2, pb2);

            streamStandar(p3);
            streamError(p3, pb3);

            System.out.println("\n**** VALIDADOR DE SALIDA DE PROCESOS ****");
            System.out.println((e1 != 0) ? "Proceso 1 con error" : "Proceso 1 correcto");
            System.out.println((e2 != 0) ? "Proceso 2 con error" : "Proceso 2 correcto");
            System.out.println((e3 != 0) ? "Proceso 3 con error" : "Proceso 3 correcto");

        } catch (IOException | InterruptedException e) {
            System.err.println("Error al ejecutar comando");
            System.exit(2);
            e.printStackTrace();
        }
    }

    public static void streamStandar(Process p) {
        InputStream is = p.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        System.out.println("\n**** SALIDA ESTÁNDAR PROCESO ****");
        try {
            String linea = br.readLine();
            while (linea != null) {
                System.out.println(linea);
                linea = br.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error al ejecutar salida");
            e.printStackTrace();
        }
    }

    public static void streamError(Process p, ProcessBuilder pb) {
        try {
            InputStream iError = p.getErrorStream();
            BufferedReader brError = new BufferedReader(new InputStreamReader(iError));
            System.out.println("\n*** SALIDA ERROR PROCESO ****");
            String lineaError = brError.readLine();
            while (lineaError != null) {
                System.out.println(lineaError);
                lineaError = brError.readLine();
                System.out.println("Comando Erroneo: " + pb.command());
            }
        } catch (IOException e) {
            System.err.println("Error al ejecutar salida");
            e.printStackTrace();
        }
    }
}
