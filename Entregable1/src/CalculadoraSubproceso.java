import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CalculadoraSubproceso {
    public static void main(String[] args) {
        File f = new File(args[0]);
        //File f = new File("datos_gen2.txt");

        int suma = 0;

        if (f.exists()) {
            try {
                FileReader fr = new FileReader(f); // Creamos el lector
                BufferedReader br = new BufferedReader(fr);
                String linea;
                while ((linea = br.readLine()) != null) {
                    // Calcula la suma
                    suma += Integer.parseInt(linea);
                }
                // Imprime la suma
                System.out.println(suma);
                br.close();// Cerrar BufferedReader y FileReader
            } catch (IOException e) {
                System.out.println("Erros al leer el fichero: " + e.getMessage());
            }
        }
    }
}
