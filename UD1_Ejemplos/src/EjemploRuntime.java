import java.io.IOException;

public class EjemploRuntime {

    public static void main(String[] args) {
        Runtime r = Runtime.getRuntime();
        try {
            // Objeto r Runtime r devuelve un objeto process
            Process p = r.exec("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
            // Espera proceso acabe para acabar el programa
            p.waitFor();
            // Obtener el valor con el que termina el proceso:
            // 0 (todo bien)
            // distinto de cero es codigo de error
            System.out.println(p.exitValue());

            System.out.println("FIN DEL PROGRAMA");
        } catch (IOException | InterruptedException e) {
            System.err.println("Error, posible ruta erronea");
        }
    }
}
