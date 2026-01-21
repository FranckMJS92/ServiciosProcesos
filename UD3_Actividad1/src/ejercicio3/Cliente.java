package ejercicio3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {

        try {
            Scanner scan = new Scanner(System.in);

            DatagramSocket ds = new DatagramSocket();

            InetAddress destino = InetAddress.getByName("localhost");

            System.out.println("Opcion [1-HORA | 2-FECHA]");
            String peticion = scan.nextLine();

            // Creamos el datagrama para enviar al servidor
            DatagramPacket dEnviado = new DatagramPacket(peticion.getBytes(), peticion.length(), destino, 2000);

            // Enviamos datagrama
            ds.send(dEnviado);

            // recibimos datagrama del servidor
            byte mensaje[] = new byte[100];
            DatagramPacket dRecibido = new DatagramPacket(mensaje, 100);
            ds.receive(dRecibido);
            String recibido = new String(mensaje).trim();

            System.out.println(recibido);

            ds.close();
            scan.close();
        } catch (SocketException e) {
            System.out.println("ERROR al arrancar el servidor");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("ERROR al arrancar el servidor");
            e.printStackTrace();
        }
    }

}
