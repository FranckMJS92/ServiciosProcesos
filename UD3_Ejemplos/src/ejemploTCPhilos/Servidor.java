package ejemploTCPhilos;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {

        try {
            // Creamos objeto compartido
            GestionPersonas personas = new GestionPersonas();

            // Creamos objeto para el servidor Socket
            ServerSocket servidor;
            servidor = new ServerSocket();
            // Direccion + puerto
            InetSocketAddress direccion = new InetSocketAddress("localhost", 2000);
            // Vincular Direccion + puerto al servidor
            servidor.bind(direccion);

            System.out.println("SERVIDOR ARRANCADO.....");
            // Bucle para aceptar peticiones simultaneas de cliente
            while (true) {
                // Por cada cliente obtenemos el socket
                Socket socketCliente = servidor.accept();
                System.out.println("CLIENTE CONECTADO.....");
                // Para que se puedan ejecutar simultaneamente, creamos el hilo
                new HiloCliente(socketCliente, personas);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
