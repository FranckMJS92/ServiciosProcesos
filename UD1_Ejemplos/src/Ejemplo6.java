import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Ejemplo6 {
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "dir");
        Process p = null;
        try {
            p = pb.start();
            InputStream is = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String linea;
            while ((linea = br.readLine()) != null) { // lee una linea
                System.out.println(linea);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error durante la ejecución del proceso");
            e.printStackTrace();
        }
    }
}
