package ejemplo_UDP1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(System.in);

            DatagramSocket datagramSocket = new DatagramSocket();

            InetAddress direccionServidor = InetAddress.getByName("localhost");
            System.out.println("Dime una frase: ");
            String textoEnviar = scan.nextLine();

            // Enviamos datagrama
            DatagramPacket datagramaEnviar = new DatagramPacket(textoEnviar.getBytes(), textoEnviar.length(), direccionServidor, 2000);
            datagramSocket.send(datagramaEnviar);

            // Recibimos datagrama
            byte[] mensajeRecibido = new byte[100];
            DatagramPacket datagramaRecibido = new DatagramPacket(mensajeRecibido, 100);
            datagramSocket.receive(datagramaRecibido);
            String textoRecibido = new String(mensajeRecibido).trim();

            System.out.println(textoRecibido);

            datagramSocket.close();
            scan.close();

        } catch (IOException i) {
            System.out.println("ERROR al arrancar el servidor");
            i.printStackTrace();
        }
    }
}
