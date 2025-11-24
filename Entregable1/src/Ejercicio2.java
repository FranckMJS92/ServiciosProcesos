import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;

public class Ejercicio2 {
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("java", "GeneradorNumeros", "8");

        try {

            pb.directory(new File("bin"));

            // Obtener PID y crear nombre de archivo
            String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];

            pb.redirectOutput(new File("datos_" + pid + ".txt"));

            Process p = pb.start();
            
        } catch (IOException e) {
            System.err.println("Error en la ejecucion del comando");
            e.printStackTrace();
        }
    }
}
