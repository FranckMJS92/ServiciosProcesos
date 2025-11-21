/*
Crea un programa Potencia.java que reciba dos argumentos enteros: 
la base y el exponente, que calcule la potencia (base^exponente) y muestre el resultado por pantalla
Crea un programa EjecutorPotencia.java que:
•	Lance dos procesos hijos de Potencia,
•	La salida de dos procesos debe ser enviada a los archivos potencia1.txt 
y potencia2.txt y los errores a errorPotencia1.txt, y errorPotencia2.txt
Al final, muestra un mensaje indicando que los archivos se han generado correctamente.
 */

import java.io.File;
import java.io.IOException;

public class Ejercicio05 {
    public static void main(String[] args) {
        ProcessBuilder pb1 = new ProcessBuilder("java", "Potencia", "s", "5");
        ProcessBuilder pb2 = new ProcessBuilder("java", "Potencia", "3", "5");

        try {
            // Establecemos directorio
            pb1.directory(new File("bin"));
            pb2.directory(new File("bin"));
            // Salida correcta
            pb1.redirectOutput(new File("potencia1.txt"));
            // Salida de error
            pb1.redirectError(new File("errorPotencia1.txt"));

            // Salida correcta
            pb2.redirectOutput(new File("potencia2.txt"));
            // Salida de error
            pb2.redirectError(new File("errorPotencia2.txt"));

            Process p1 = pb1.start();
            Process p2 = pb2.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
