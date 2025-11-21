import java.io.IOException;

public class Ejemplo5 {
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "dir");
        try {
            pb.start();
        } catch (IOException e) {
            System.out.println("Error durante la ejecución del proceso");
            e.printStackTrace();
        }
    }
}
