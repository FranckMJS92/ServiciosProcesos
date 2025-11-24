/*
EJERCICIO  4 - Crear un programa para que se ejecute por consola. Debe tener dos clases:
▪ Aleatorios: genera un número aleatorio del 0 al 10 y lo muestra por pantalla
▪ Ejercicio03: pide al usuario por pantalla que introduzca un texto, hasta que escriba “N”, 
cada vez que no escriba “N”, inicia un proceso lanzando el programa Aleatorios y muestra el número generado por pantalla. 
Ejemplo de ejecución:
Generar aleatorio (N para finalizar): a
Aleatorio: 8
Generar aleatorio (N para finalizar): s
Aleatorio: 6
Generar aleatorio (N para finalizar): n 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Ejercicio04 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String text = "";

        // Crear el proceso para ejecutar la clase Aleatorio
        ProcessBuilder pb = new ProcessBuilder("java", "Aleatorio");

        // Indicar directorio
        pb.directory(new File("bin"));

        // Bucle para ejecutor del proceso hasta stop por usuario
        while (!text.equals("N")) {

            System.out.print("Generar aleatorio ('N' para terminar) : ");
            text = scan.next();

            // Valida cadena antes de ejecutar proceso
            if (text.equals("N"))
                break;
            else {
                try {
                    Process p = pb.start();

                    InputStream is = p.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        System.out.println(linea);
                    }
                    br.close();
                } catch (IOException e) {
                    System.err.println("Error al ejecutar proceso");
                    e.printStackTrace();
                }
            }

        }

        System.out.println("Fin Programa");

        scan.close();
    }
}
