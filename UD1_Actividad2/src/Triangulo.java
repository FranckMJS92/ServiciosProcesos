import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.text.DateFormatter;

public class Triangulo {
    public static void main(String[] args) {
        // Obtener fecha y hora actual
        LocalDateTime now = LocalDateTime.now();
        if (args.length != 1) {
            System.err.println("Error se debe recibir 1 argumento");
        }

        try {
            byte base = Byte.parseByte(args[0]);

            // Formatear la fecha y hora
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String fechaFormateada = now.format(formatter);

            System.out.println("Fecha Inicio: " + fechaFormateada + "\n");
            for (byte i = base; i >= 1; i--) {
                for (byte j = 1; j <= i; j++) {
                    System.out.print(j + " ");
                }
                System.out.println();
            }

            System.out.println("\nFecha Fin: " + fechaFormateada);

        } catch (Exception e) {
            System.err.println("La base debe ser un numero entero");
        }

    }
}
