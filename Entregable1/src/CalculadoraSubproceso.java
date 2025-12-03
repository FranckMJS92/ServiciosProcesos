import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CalculadoraSubproceso {
    public static void main(String[] args) {

        // Validacion argumento
        if (args.length != 1) {
            System.err.println("Error: Debe recibir el nombre del archivo como parámetro");
            System.exit(1);
        }

        File f = new File(args[0]);
        int suma = 0;

        // Validacion nombre archivo
        if (!f.exists()) {
            System.err.println("Error: El archivo no existe");
            System.exit(1);
        }

        try (FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr)) {

            String linea;
            while ((linea = br.readLine()) != null) {
                suma += Integer.parseInt(linea.trim());
            }

            System.out.println(suma);

        } catch (IOException e) {
            System.err.println("Error al leer el fichero: " + e.getMessage());
            System.exit(1);
        }
    }
}