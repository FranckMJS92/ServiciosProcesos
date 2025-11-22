/*
*   Ejemplo 1: ejecutar la aplicación Notepad
 */

package EjemplosPDF1_3;

import java.io.IOException;

public class Ejemplo1 {
    Runtime r = Runtime.getRuntime();
    String comando = "NOTEPAD";
Process p;
try
    {
        p = r.exec(comando);
    }catch(
    IOException e)
    {
        System.out.println("Error en el comando: " + comando);
        e.printStackTrace();
    }
}
