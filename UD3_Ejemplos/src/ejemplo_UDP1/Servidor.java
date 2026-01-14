package ejemplo_UDP1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class Servidor {
    public static void main(String[] args) {

        try {
            // Direccion + puerto
            InetSocketAddress direccionServidor = new InetSocketAddress("localhost", 2000);
            DatagramSocket datagramSocket = new DatagramSocket(direccionServidor);

            // Recibimos datagrama
            byte[] mensajeRecibido = new byte[100];
            DatagramPacket datagramaRecibido = new DatagramPacket(mensajeRecibido, 100);
            datagramSocket.receive(datagramaRecibido);
            String textoRecibo = new String(mensajeRecibido).trim();

            // PAra enviar respuesta al ciente, necesito la direccion IP y el puerto
            // por el que el cliente ha enviado el mensaje
            InetAddress direccionCliente = datagramaRecibido.getAddress();
            int puertoCliente = datagramaRecibido.getPort();

            // Enviar datagrama
            String textoEnviar = "Tamaño: " + textoRecibo;

        } catch (SocketException e) {
            System.out.println("ERROR al arrancar el servidor");
            e.printStackTrace();
        } catch (IOException i) {
            System.out.println("ERROR al arrancar el servidor");
            i.printStackTrace();
        }
    }
}
