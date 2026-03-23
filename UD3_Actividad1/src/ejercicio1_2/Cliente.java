package ejercicio1_2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {
            // Creamos socket para conectarnos con el servidor
            Socket cliente = new Socket();
            // Direccion + puerto del servidor
            InetSocketAddress direccionServidor = new InetSocketAddress("localhost", 2000);

            cliente.connect(direccionServidor);
            System.out.println("CONECTADO AL SERVIDOR");

            // Obtenemos los streams del socket para poder enviar y recibir informacion
            InputStream entrada = cliente.getInputStream();
            OutputStream salida = cliente.getOutputStream();

            System.out.println("Opcion [1-HORA | 2-FECHA]");
            String texto = scan.nextLine();

            // Envia mensaje al servidor
            salida.write(texto.getBytes());

            // Recibe mensaje del servidor
            byte[] mensaje = new byte[100];
            entrada.read(mensaje);

            String rpta = new String(mensaje).trim();
            System.out.println("PETICION RECIBIDA: " + rpta);

            // Cuando terminemos la comunicacioon, cerramos TODO
            salida.close();
            entrada.close();
            cliente.close();

        } catch (IOException e) {
            System.out.println("ERROR al conectarse con el servidor");
            e.printStackTrace();
        }

        scan.close();
    }
}
