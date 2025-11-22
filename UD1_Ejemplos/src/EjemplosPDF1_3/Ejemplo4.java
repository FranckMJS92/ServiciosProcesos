/*
*   Ejemplo 4: Podemos estableces el directorio de trabajo para el proceso. 
*   El fichero prueba.txt lo buscara en D:/.
*/

package EjemplosPDF1_3;

import java.io.File;
import java.io.IOException;

public class Ejemplo4 {
    public static void main(String[] args) {
        try {
            ProcessBuilder proceso;
            proceso = new ProcessBuilder("C:/Windows/notepad.exe", "prueba.txt");
            proceso.directory(new File("D:/"));
            Process p = proceso.start();
        } catch (IOException e) {
            System.out.println("Error durante la ejecución del proceso");
            e.printStackTrace();
        }
    }
}
