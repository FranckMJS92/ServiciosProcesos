package servidor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import modelo.IProductoDAO;
import modelo.ProductoDAO;
import utils.Utilidades;

/**
 * Servidor del sistema de gestión de productos.
 * <p>
 * Esta clase es responsable de iniciar y mantener el servidor que escucha
 * las conexiones entrantes de los clientes. Por cada cliente que se conecta,
 * se crea un nuevo hilo {@link ClienteHandler} para gestionar la comunicación.
 * </p>
 * <p>
 * Proyecto desarrollado para la asignatura de Programación de Servicios y
 * Procesos.
 * </p>
 * 
 * @author Francisco Lopez
 * @version 1.0
 */
public class Servidor {

    /**
     * Punto de entrada principal del servidor.
     * <p>
     * Inicializa el servidor socket en la dirección y puerto especificados en
     * {@link utils.Utilidades#DIRECCION} y {@link utils.Utilidades#PUERTO}.
     * Permanece en ejecución continua aceptando conexiones de clientes de forma
     * indefinida hasta que se detenga manualmente.
     * </p>
     * <p>
     * El servidor opera de forma concurrente, creando un hilo independiente
     * para cada cliente que se conecta mediante {@link ClienteHandler}.
     * </p>
     * 
     * @param args Argumentos de línea de comandos (no utilizados en esta
     *             aplicación)
     */
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