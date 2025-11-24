import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcesoLanzador {
    public static void main(String[] args) {
        ProcessBuilder pbEscritor = new ProcessBuilder("java", "ProcesoEjecutor", "hellloooouuu");

        pbEscritor.directory(new File("bin"));

        try {
            Process escritor = pbEscritor.start();

            InputStream is = escritor.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String linea;

            System.out.println("*** SALIDA ESTANDAR ***");
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
            br.close();

            InputStream es = escritor.getErrorStream();
            BufferedReader bre = new BufferedReader(new InputStreamReader(es));

            String lineaError;

            System.out.println("*** SALIDA ERROR ***");
            while ((lineaError = bre.readLine()) != null) {
                System.out.println(lineaError);
            }
            bre.close();



        } catch (IOException e) {
            System.err.println("Error");
            e.printStackTrace();
        }
    }
}
