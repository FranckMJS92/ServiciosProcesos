import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GeneradorNumeros {
    public static void main(String[] args) {

        //Validacion argumento
        if (args.length != 1) {
            System.err.println("Error: Debe recibir un parámetro con la cantidad de números");
            System.exit(1);
        }

        try {
            int cantidad = Integer.parseInt(args[0]);
            Random random = new Random();

            // Sacando PID del proceso (esto lo tuve que buscar...)
            long pid = ProcessHandle.current().pid();
            String nombreArchivo = "datos_" + pid + ".txt";

            // Escribir números en archivo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
                for (int i = 0; i < cantidad; i++) {
                    int aleatorio = random.nextInt(101);
                    writer.write(String.valueOf(aleatorio));
                    writer.newLine();
                }
            }

            // Confirmación escritura de archivo
            System.out.println("Archivo generado: " + nombreArchivo);

        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
            System.exit(1);
        }
    }
}
