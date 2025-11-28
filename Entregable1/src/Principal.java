import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Principal {
    public static void main(String[] args) {
        int sumaFinal = 0;
        // Instancia de ProcessBuilder para los procesos GeneradorNumeros
        ProcessBuilder gen1 = new ProcessBuilder("java", "GeneradorNumeros", "8");
        ProcessBuilder gen2 = new ProcessBuilder("java", "GeneradorNumeros", "5");

        try {

            // Asignar directory para GeneradorNumeros y la salida de fichero
            gen1.directory(new File("bin"));
            gen1.redirectOutput(new File("datos_gen1.txt"));

            gen2.directory(new File("bin"));
            gen2.redirectOutput(new File("datos_gen2.txt"));

            // Process start para ambos procesos
            Process pg1 = gen1.start();
            Process pg2 = gen2.start();

            // Instancia de ProcessBuilder para los procesos CalculadoraSubproceso
            ProcessBuilder cal1 = new ProcessBuilder("java", "CalculadoraSubproceso", "datos_gen1.txt");
            ProcessBuilder cal2 = new ProcessBuilder("java", "CalculadoraSubproceso", "datos_gen2.txt");

            // Asignar directory para CalculadoraSubproceso
            cal1.directory(new File("bin"));
            cal2.directory(new File("bin"));

            Process pc1 = cal1.start();
            Process pc2 = cal2.start();

            InputStream is1 = pc1.getInputStream();
            InputStream is2 = pc2.getInputStream();

            BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
            String linea = br1.readLine();
            if (linea != null) {
                sumaFinal += Integer.parseInt(linea);
                System.out.println(linea);
            }
            br1.close();

            BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
            if (linea != null) {
                sumaFinal += Integer.parseInt(linea);
            }
            br2.close();

            System.out.println("La suma final es = " + sumaFinal);

        } catch (IOException e) {
            System.err.println("Error en la ejecucion del comando");
            e.printStackTrace();
        }
    }
}
