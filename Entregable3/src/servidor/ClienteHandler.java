package servidor;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import modelo.Producto;
import modelo.IProductoDAO;

/**
 * Manejador de clientes para el servidor.
 * <p>
 * Esta clase extiende {@link Thread} y se encarga de gestionar la comunicación
 * con un cliente específico. Cada instancia se ejecuta en un hilo
 * independiente,
 * permitiendo que el servidor atienda múltiples clientes de forma concurrente.
 * </p>
 * <p>
 * El manejador recibe peticiones del cliente a través de un socket, procesa
 * las operaciones solicitadas utilizando el DAO correspondiente y devuelve
 * los resultados al cliente.
 * </p>
 * <p>
 * Proyecto desarrollado para la asignatura de Programación de Servicios y
 * Procesos.
 * </p>
 * 
 * @author Francisco Lopez
 * @version 1.0
 */
public class ClienteHandler extends Thread {

    /** Socket de comunicación con el cliente */
    private Socket socketCliente;

    /** DAO para realizar operaciones sobre los productos */
    private IProductoDAO productoDAO;

    /** Flujo de salida para enviar objetos al cliente */
    private ObjectOutputStream out;

    /** Flujo de entrada para recibir objetos del cliente */
    private ObjectInputStream in;

    /**
     * Constructor del manejador de clientes.
     * <p>
     * Inicializa el socket y el DAO, e inicia automáticamente el hilo
     * que gestionará la comunicación con el cliente.
     * </p>
     * 
     * @param socketCliente Socket conectado al cliente
     * @param productoDAO   DAO para operaciones con productos
     */
    public ClienteHandler(Socket socketCliente, IProductoDAO productoDAO) {
        super();
        this.socketCliente = socketCliente;
        this.productoDAO = productoDAO;
        this.start();
    }

    /**
     * Hilo principal que procesa las peticiones del cliente.
     * <p>
     * Este método se ejecuta en un hilo independiente y permanece en un bucle
     * procesando las peticiones del cliente hasta que la conexión se cierra.
     * Las operaciones disponibles son:
     * </p>
     * <ul>
     * <li><b>1</b> - Listar todos los productos</li>
     * <li><b>2</b> - Buscar producto por ID</li>
     * <li><b>3</b> - Buscar productos por categoría</li>
     * <li><b>4</b> - Insertar nuevo producto</li>
     * <li><b>5</b> - Actualizar stock de un producto</li>
     * <li><b>6</b> - Eliminar producto por ID</li>
     * <li><b>7</b> - Cerrar conexión</li>
     * </ul>
     * <p>
     * El manejo de excepciones permite gestionar tanto cierres normales
     * ({@link EOFException}) como cierres abruptos ({@link SocketException})
     * del cliente, liberando correctamente los recursos.
     * </p>
     */
    @Override
    public void run() {
        try {
            // Inicializar streams
            out = new ObjectOutputStream(socketCliente.getOutputStream());
            in = new ObjectInputStream(socketCliente.getInputStream());
            boolean conectado = true;

            while (conectado) {
                try {
                    int opcion = in.readInt();
                    System.out.println("[INFO] Cliente " + socketCliente.getInetAddress() + " - Peticion: " + opcion);

                    switch (opcion) {
                        case 1:
                            // 1. Listar todos los productos
                            out.writeObject(productoDAO.obtenerTodos());
                            out.flush();
                            break;

                        case 2:
                            // 2. Busca por id
                            int idProducto = in.readInt();
                            out.writeObject(productoDAO.buscarPorId(idProducto));
                            out.flush();
                            break;

                        case 3:
                            // 3. Buscar por categoria
                            String categoria = in.readUTF();
                            out.writeObject(productoDAO.buscarPorCategoria(categoria));
                            out.flush();
                            break;

                        case 4:
                            // 4. Insertar nuevo producto
                            Producto p = (Producto) in.readObject();
                            out.writeBoolean(productoDAO.insertaProducto(p));
                            out.flush();
                            break;

                        case 5:
                            // 5. Actualizar stock de producto por Id
                            int idUpdate = in.readInt();
                            double stockUpdate = in.readDouble();
                            System.out.println(
                                    "[INFO] Actualizar producto con Id: " + idUpdate + " a stock: " + stockUpdate);
                            out.writeBoolean(productoDAO.actualizaStockProducto(idUpdate, stockUpdate));
                            out.flush();
                            break;

                        case 6:
                            // 6. Eliminar producto por id
                            int idDelete = in.readInt();
                            System.out.println("[INFO] Eliminar " + idDelete);
                            out.writeBoolean(productoDAO.eliminarProducto(idDelete));
                            out.flush();
                            break;

                        case 7:
                            // 7. Salir - cierre normal
                            conectado = false;
                            System.out.println("[INFO] Cliente " + socketCliente.getInetAddress() + " solicitó salir");
                            break;
                    }

                } catch (EOFException e) {
                    // El cliente cerró la conexión normalmente (cerró streams)
                    System.out.println(
                            "[INFO] Cliente " + socketCliente.getInetAddress() + " cerró la conexión normalmente");
                    break;
                } catch (SocketException e) {
                    // Conexión reseteada - cliente cerrado abruptamente
                    System.out.println("[WARN] Cliente " + socketCliente.getInetAddress()
                            + " se desconectó abruptamente: " + e.getMessage());
                    break;
                } catch (IOException e) {
                    // Otros errores de E/S
                    System.err.println("[ERROR] Error de E/S con cliente " + socketCliente.getInetAddress() + ": "
                            + e.getMessage());
                    break;
                } catch (ClassNotFoundException e) {
                    System.err.println("[ERROR] Error al deserializar objeto del cliente "
                            + socketCliente.getInetAddress() + ": " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("[ERROR] Error al inicializar streams para cliente: " + e.getMessage());
        } finally {
            cerrarConexion();
        }
    }

    /**
     * Cierra todos los recursos asociados al cliente.
     * <p>
     * Este método se ejecuta siempre, incluso si ocurre una excepción durante
     * la comunicación. Cierra en orden los flujos de entrada/salida y finalmente
     * el socket, liberando los recursos del sistema.
     * </p>
     */
    private void cerrarConexion() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (socketCliente != null && !socketCliente.isClosed()) {
                socketCliente.close();
                System.out.println("[INFO] Conexión cerrada con cliente: " + socketCliente.getInetAddress());
            }
        } catch (IOException e) {
            System.err.println("[ERROR] Error al cerrar recursos del cliente: " + e.getMessage());
        }
    }
}