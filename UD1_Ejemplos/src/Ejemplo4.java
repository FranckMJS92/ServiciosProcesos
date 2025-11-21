import java.io.IOException;

public class Ejemplo4 {
    public static void main(String[] args) {
        try {
            ProcessBuilder proceso = new ProcessBuilder("notepad", "UD1_Ejemplos\\salida.txt");
            Process p = proceso.start();
        } catch (IOException e) {
            System.out.println("Error durante la ejecución del proceso");
            e.printStackTrace();
        }
    }
}
