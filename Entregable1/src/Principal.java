import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Principal {

    public static void main(String[] args) {

        // ArrayList para procesos y archivos
        List<Process> procesos = new ArrayList<>();
        List<String> archivosGenerados = new ArrayList<>();

        try {
            System.out.println("=== INICIANDO GENERACIÓN DE NÚMEROS ===");

            // Paso 1: Lanzar dos procesos GeneradorNumeros
            for (int i = 0; i < 2; i++) {
                ProcessBuilder pb = new ProcessBuilder("java", "GeneradorNumeros", "5");

                pb.directory(new File("bin"));
                Process proceso = pb.start();
                procesos.add(proceso);

                // Leer la salida para obtener el nombre del archivo generado
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
                    String linea;
                    while ((linea = reader.readLine()) != null) {
                        if (linea.contains("datos_") && linea.contains(".txt")) {
                            // Extraer solo el nombre del archivo
                            archivosGenerados.add(linea.substring(linea.lastIndexOf(" ") + 1));
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error en ejecucion");
                }
            }

            // Esperar a que terminen los generadores
            System.out.println("Esperando a que terminen los procesos generadores...");
            for (Process proceso : procesos) {
                proceso.waitFor();
            }

            System.out.println("Archivos generados: " + archivosGenerados);
            procesos.clear(); 

            // Paso 2: Lanzar dos procesos CalculadoraSubproceso
            System.out.println("\n=== CALCULANDO SUMAS ===");
            List<Integer> sumas = new ArrayList<>();

            for (String archivo : archivosGenerados) {
                ProcessBuilder pb = new ProcessBuilder("java", "CalculadoraSubproceso", archivo);

                pb.directory(new File("bin"));
                Process proceso = pb.start();
                procesos.add(proceso);

                // Capturar la salida
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(proceso.getInputStream()))) {

                    String resultado = reader.readLine();
                    if (resultado != null) {
                        int suma = Integer.parseInt(resultado.trim());
                        sumas.add(suma);
                        System.out.println("Suma para " + archivo + ": " + suma);
                    }
                }

                // Leer errores si los hay
                try (BufferedReader errorReader = new BufferedReader(
                        new InputStreamReader(proceso.getErrorStream()))) {

                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        System.err.println("Error del proceso: " + errorLine);
                    }
                }
            }

            // Esperar a que terminen los calculadores
            for (Process proceso : procesos) {
                proceso.waitFor();
            }

            // Paso 3: Calcular y mostrar resultado final
            System.out.println("\n=== RESULTADO FINAL ===");
            int sumaTotal = 0;
            for (int suma : sumas) {
                sumaTotal += suma;
            }

            System.out.println("Suma total de ambos archivos: " + sumaTotal);

        } catch (Exception e) {
            System.err.println("Error en la ejecución principal: " + e.getMessage());
            e.printStackTrace();
        }
    }
}