import java.io.IOException;

public class EjemploProcessBuilder {
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
                "github.com");

        try {
            Process p = pb.start();

            System.out.println("Esta vivo " + p.isAlive());
            System.out.println(p.pid());

            // Espera proceso acabe para acabar el programa
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error");
        }
    }
}
