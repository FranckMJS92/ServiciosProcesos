package servidor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import modelo.IProductoDAO;
import modelo.ProductoDAO;
import utils.Utilidades;

public class Servidor {

    public static void main(String[] args) {

        System.out.println("SERVIDOR ARRANCADO ....");

        ServerSocket servidor;
        IProductoDAO productoDAO = new ProductoDAO();

        try {

            servidor = new ServerSocket();

            InetSocketAddress direccion = new InetSocketAddress(Utilidades.DIRECCION, Utilidades.PUERTO);

            servidor.bind(direccion);

            while (true) {
                Socket socketCliente = servidor.accept();

                System.out.println("CLIENTE CONECTADO ....");

                new ClienteHandler(socketCliente, productoDAO);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}