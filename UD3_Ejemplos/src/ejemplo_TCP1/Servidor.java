package ejemplo_TCP1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        try {
            // Creamos objeto para el servidor socket
            ServerSocket servidor = new ServerSocket();
            // Direccion + puerto
            InetSocketAddress direccion = new InetSocketAddress("localhost", 2000);
            // Vincular Direccion + puerto al servidor
            servidor.bind(direccion);

            System.out.println("SERVIDOR ARRANCADO ... ");

            Socket socketCliente = servidor.accept();
            System.out.println("CLIENTE CONECTADO ... ");

            // Obtenemos los streams del socket para poder enviar y recibir informacion
            InputStream entrada = socketCliente.getInputStream();
            OutputStream salida = socketCliente.getOutputStream();

            // Leemos la peticion que nos envia el cliente
            // Declara una variable donde guardamos mensaje del cliente
            byte[] mensaje = new byte[100];
            // Leemos la petifcion que nos envia el cliente y lo guardamos en mensaje
            entrada.read(mensaje);
            String texto = new String(mensaje).trim();
            System.out.println("PETICION RECIBIDA: " + texto);

            // Enviamos respuesta al cliente
            String respuesta = "Tamaño: " + texto.length();
            salida.write(respuesta.getBytes());

            // Cuando terminemos la comunicacioon, cerramos TODO
            salida.close();
            entrada.close();
            socketCliente.close();
            servidor.close();

        } catch (IOException e) {
            System.out.println("[ERROR] Al iniciar servidor");
            e.printStackTrace();
        }
    }

}
