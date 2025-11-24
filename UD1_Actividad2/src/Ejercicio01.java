/*
EJERCICIO 1 - Lanzar un proceso de Java que ejecute un comando incorrecto, 
obtenga el stream de entrada conectado con la salida de error del proceso y muéstrelo por pantalla.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Ejercicio01 {
    public static void main(String[] args) {

        ProcessBuilder pBuilder = new ProcessBuilder("cmd", "/C", "pong", "8.8.8.8");
        try {
            Process proceso = pBuilder.start();

            InputStream is = proceso.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            System.out.println("*** SALIDA ESTÁNDAR ****");
            String linea = br.readLine();
            while (linea != null) {
                System.out.println(linea);
                linea = br.readLine();
            }

            InputStream iError = proceso.getErrorStream();
            BufferedReader brError = new BufferedReader(new InputStreamReader(iError));
            System.out.println("*** SALIDA ERROR ****");
            String lineaError = brError.readLine();
            while (lineaError != null) {
                System.out.println(lineaError);
                lineaError = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
