package ejemplo_UDP1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

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

            // Para enviar respuesta al ciente, necesito la direccion IP y el puerto
            // por el que el cliente ha enviado el mensaje
            InetAddress direccionCliente = datagramaRecibido.getAddress();
            int puertoCliente = datagramaRecibido.getPort();

            // Enviar datagrama
            String textoEnviar = "Tamaño : " + textoRecibo.length();
            DatagramPacket datagramaEnviar = new DatagramPacket(textoEnviar.getBytes(), textoEnviar.length() + 1,
                    direccionCliente,
                    puertoCliente);
            datagramSocket.send(datagramaEnviar);

            datagramSocket.close();

        } catch (IOException i) {
            System.out.println("ERROR al arrancar el servidor");
            i.printStackTrace();
        }
    }
}
