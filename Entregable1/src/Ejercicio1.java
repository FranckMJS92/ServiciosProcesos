import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class Ejercicio1 {
    public static void main(String[] args) {
        int sumaFinal = 0;
        // Instancia de ProcessBuilder para los procesos CalculadoraSubproceso
        ProcessBuilder cal1 = new ProcessBuilder("java", "CalculadoraSubproceso", "datos_gen1.txt");

        // Asignar directory para CalculadoraSubproceso
        cal1.directory(new File("bin"));

        try {

            cal1.redirectOutput(new File("prueba.txt"));
            cal1.redirectError(new File("pruebaerror.txt"));
            Process pc1 = cal1.start();

            
            OutputStream os1 = pc1.getOutputStream();
            InputStream is1 = pc1.getInputStream();

            BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
            String linea = br1.readLine();
            System.out.println("ARCHIVO1 ");
            if (linea != null) {
                // sumaFinal += Integer.parseInt(linea);
                System.out.println(linea);
            }
            br1.close();
/*
            InputStream iError = pc1.getErrorStream();
            BufferedReader brError = new BufferedReader(new InputStreamReader(iError));
            System.out.println("*** SALIDA ERROR ****");
            String lineaError = brError.readLine();
            while (lineaError != null) {
                System.out.println(lineaError);
                lineaError = brError.readLine();
            } */
            //brError.close();

            //System.out.println("La suma final es = " + sumaFinal);
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}
