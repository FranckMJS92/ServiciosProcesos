import java.io.File;
import java.io.IOException;

public class Ejercicio06 {
    public static void main(String[] args) {
        ProcessBuilder pb1 = new ProcessBuilder("java", "Triangulo", "5");
        ProcessBuilder pb2 = new ProcessBuilder("java", "Triangulo", "7");
        ProcessBuilder pb3 = new ProcessBuilder("java", "Triangulo", "9");
        try {

            pb1.directory(new File("bin"));
            pb2.directory(new File("bin"));
            pb3.directory(new File("bin"));

            pb1.redirectOutput(new File("triangulo5.txt"));
            pb2.redirectOutput(new File("triangulo7.txt"));
            pb3.redirectOutput(new File("triangulo9.txt"));

            pb1.redirectError(new File("errorTriangulo5.txt"));
            pb2.redirectError(new File("errorTriangulo7.txt"));
            pb3.redirectError(new File("errorTriangulo9.txt"));

            pb1.start();
            pb2.start();
            pb3.start();
        } catch (IOException e) {
            // TODO: handle exception
        }

    }
}
