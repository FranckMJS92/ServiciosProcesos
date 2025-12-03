import java.io.*;
import java.util.*;

public class Principal1 {
    public static void main(String[] args) throws Exception {

        // Validar argumentos
        if (args.length < 2) {
            System.out.println("Debe especificar 2 argumentos: Primero -> # procesos | Segundo -> # experimentos");
            return;
        }

        int numProcesos = Integer.parseInt(args[0]);
        if (args.length - 1 != numProcesos) {
            System.out.println("Error: Debe especificar " + numProcesos + " valores de experimentos");
            return;
        }

        System.out.println("Generando experimentos desde " + numProcesos + " procesos.\n");

        // Lanzar procesos
        List<Process> procesos = new ArrayList<>();
        List<String> archivos = new ArrayList<>();

        for (int i = 0; i < numProcesos; i++) {
            int experimentos = Integer.parseInt(args[i + 1]);

            ProcessBuilder pb = new ProcessBuilder("java", "GeneradorExperimentos", String.valueOf(experimentos));
            pb.directory(new File("bin"));
            Process p = pb.start();
            procesos.add(p);

            // Capturar nombre del archivo de la salida
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.contains("experimento_proceso_") && linea.contains(".txt")) {
                            archivos.add(linea.substring(linea.lastIndexOf(" ") + 1));
                        }
            }
            reader.close();
        }

        // Esperar procesos
        for (Process p : procesos) {
            p.waitFor();
        }

        // Leer y mostrar resultados
        for (int i = 0; i < archivos.size(); i++) {
            System.out.println("--- Resultados del PROCESO " + (i + 1) + " ---");

            BufferedReader fileReader = new BufferedReader(new FileReader(archivos.get(i)));
            String linea;
            while ((linea = fileReader.readLine()) != null) {
                System.out.println(linea);
            }
            fileReader.close();
            System.out.println();
        }
    }
}