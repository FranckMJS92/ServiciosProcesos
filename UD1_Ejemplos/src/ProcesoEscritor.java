import java.io.PrintWriter;
import java.io.File;

/*  Recibe un texto como argumento
*   Lo guarda en un fichero texto
*/

public class ProcesoEscritor {
    public static void main(String[] args) {
        // Comprobar que se recibe el argumento
        if (args.length != 1) {
            System.out.println("Error no se ha recibido argumento");
            System.exit(1);
        }

        try {
            String mensaje = args[0];
            PrintWriter pw = new PrintWriter(new File("src//datosEscritor.txt"));
            pw.println(mensaje);
            pw.close(); //Debe cerrarse para que funcione bien
        } catch (Exception e) {
            System.err.println("Error de salida");
        }
    }
}
