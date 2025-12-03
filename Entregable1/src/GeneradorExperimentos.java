import java.io.BufferedWriter;
import java.io.FileWriter;

public class GeneradorExperimentos {
    public static void main(String[] args) {

        // Validacion argumento
        if (args.length != 1) {
            System.err.println("Error: Debe recibir un parámetro con la cantidad de experimentos");
            System.exit(1);
        }

        int cantidad = Integer.parseInt(args[0]);
        // Nombre del archivo con el PID
        String nombreArchivo = "experimento_proceso_" + ProcessHandle.current().pid() + ".txt";

        // Escribir números en archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (int i = 1; i <= cantidad; i++) {
                writer.write("Experimento-" + i);
                writer.newLine();
            }

            
            // Confirmación escritura de archivo
            System.out.println("Archivo generado: " + nombreArchivo);

        } catch (Exception e) {
            System.err.println("Error en la escritura");
            System.exit(2);
        }

    }
}
