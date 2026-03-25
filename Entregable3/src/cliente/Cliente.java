package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import modelo.Producto;
import utils.Utilidades;

/**
 * Cliente del sistema de gestión de productos.
 * <p>
 * Esta clase implementa el cliente que se conecta al servidor para gestionar
 * productos. Proporciona un menú interactivo que permite al usuario realizar
 * operaciones como listar, buscar, insertar, actualizar y eliminar productos.
 * </p>
 * <p>
 * La comunicación con el servidor se realiza mediante sockets, enviando y
 * recibiendo objetos serializados a través de flujos {@link ObjectOutputStream}
 * y {@link ObjectInputStream}.
 * </p>
 * <p>
 * Proyecto desarrollado para la asignatura de Programación de Servicios y
 * Procesos.
 * </p>
 * 
 * @author Francisco Lopez
 * @version 1.0
 */
public class Cliente {

    /** Socket para la comunicación con el servidor */
    private static Socket socket;

    /** Flujo de salida para enviar objetos al servidor */
    private static ObjectOutputStream out;

    /** Flujo de entrada para recibir objetos del servidor */
    private static ObjectInputStream in;

    /**
     * Muestra el menú principal de opciones en la consola.
     * <p>
     * Las opciones disponibles son:
     * <ul>
     * <li>1 - Listar todos los productos</li>
     * <li>2 - Buscar un producto por ID</li>
     * <li>3 - Buscar productos por categoría</li>
     * <li>4 - Insertar un nuevo producto</li>
     * <li>5 - Actualizar el stock de un producto</li>
     * <li>6 - Eliminar un producto por ID</li>
     * <li>7 - Salir del programa</li>
     * </ul>
     * </p>
     */
    public static void mostrarMenu() {
        System.out.println("\n=========== MENU ===========");
        System.out.println("1. Listar todos los productos.");
        System.out.println("2. Buscar un producto por ID.");
        System.out.println("3. Buscar productos por categoria.");
        System.out.println("4. Insertar producto");
        System.out.println("5. Actualizar stock por producto");
        System.out.println("6. Eliminar producto por Id");
        System.out.println("7. Salir.");
    }

    /**
     * Cierra la conexión con el servidor liberando todos los recursos.
     * <p>
     * Este método cierra en orden los flujos de entrada/salida y finalmente
     * el socket. Se invoca tanto al finalizar la ejecución normal como
     * en caso de cierre abrupto mediante el shutdown hook.
     * </p>
     */
    private static void cerrarConexion() {
        try {
            if (out != null)
                out.close();
            if (in != null)
                in.close();
            if (socket != null && !socket.isClosed())
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Punto de entrada principal del cliente.
     * <p>
     * Establece la conexión con el servidor en la dirección y puerto definidos
     * en {@link utils.Utilidades#DIRECCION} y {@link utils.Utilidades#PUERTO}.
     * Una vez conectado, muestra un menú interactivo y procesa las opciones
     * seleccionadas por el usuario enviando las peticiones correspondientes al
     * servidor.
     * </p>
     * <p>
     * Se implementa un {@link Runtime#addShutdownHook} para manejar el cierre
     * abrupto del programa (Ctrl+C), asegurando que la conexión se cierre
     * correctamente.
     * </p>
     * 
     * @param args Argumentos de línea de comandos (no utilizados en esta
     *             aplicación)
     */
    public static void main(String[] args) {

        // SHUTDOWN HOOK - Esto maneja el cierre abrupto (Ctrl+C)
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n[INFO] Cerrando conexión...");
            cerrarConexion();
        }));

        socket = new Socket();

        InetSocketAddress direccion = new InetSocketAddress(Utilidades.DIRECCION, Utilidades.PUERTO);
        int opcion = 0;

        try {
            socket.connect(direccion);

            System.out.println("CONECTADO AL SERVIDOR ....");
            Utilidades utils = new Utilidades();

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            do {
                mostrarMenu();

                opcion = utils.leerEnteroRango("\nIndica una opcion [1-7]: ", 1, 7);

                out.writeInt(opcion);

                out.flush();

                switch (opcion) {
                    case 1:
                        // 1. Listar todos los productos
                        @SuppressWarnings("unchecked")
                        List<Producto> productos = (List<Producto>) in.readObject();
                        System.out.println(); // Espacio
                        utils.mostrarListaProductos(productos);
                        break;

                    case 2:
                        // 2. Busca por id
                        int idProducto = utils.leerEntero("\nID del producto: ");
                        out.writeInt(idProducto);
                        out.flush();
                        Producto e = (Producto) in.readObject();
                        if (e != null) {
                            System.out.println("\nDATOS DEL PRODUCTO");
                            System.out.println(e);
                        } else {
                            System.out.println("[ERROR] No existe el producto con ID: " + idProducto);
                        }
                        break;

                    case 3:
                        // 3. Buscar por categoria
                        String categoria = utils.leerTexto("\nNombre de la categoria: ");
                        out.writeUTF(categoria);
                        out.flush();
                        @SuppressWarnings("unchecked")
                        List<Producto> productoCat = (List<Producto>) in.readObject();
                        System.out.println(); // Espacio
                        utils.mostrarListaProductos(productoCat);
                        break;

                    case 4:
                        // 4. Insertar nuevo producto
                        String nombre = utils.leerTexto("\nNombre: ");
                        String cat = utils.leerTexto("Categoria: ");
                        double precio = utils.leerDouble("Precio: ");
                        double stock = utils.leerDouble("Stock: ");
                        // Creamos el objeto
                        Producto nuevoProducto = new Producto(nombre, cat, precio, stock);
                        // Enviamos el objeto empleado
                        out.writeObject(nuevoProducto);
                        // Recibimos respuesta
                        if (in.readBoolean()) {
                            System.out.println("\n[INFO] Producto creado");
                        } else {
                            System.out.println("\n[INFO] No se ha podido crear el producto");
                        }
                        break;

                    case 5:
                        // 5. Actualizar stock de producto por Id
                        int idUpdate = utils.leerEntero("\nID del producto: ");
                        double stockUpdate = utils.leerDouble("Nuevo Stock: ");
                        out.writeInt(idUpdate);
                        out.writeDouble(stockUpdate);
                        out.flush();
                        if (in.readBoolean()) {
                            System.out.println("\n[INFO] Producto Actualizado");
                        } else {
                            System.out.println("\n[INFO] No se ha podido actualizar el producto");
                        }
                        break;

                    case 6:
                        // 6. Eliminar producto por id
                        int idDelete = utils.leerEntero("ID del producto: ");
                        out.writeInt(idDelete);
                        out.flush();
                        if (in.readBoolean()) {
                            System.out.println("\n[INFO] Producto eliminado");
                        } else {
                            System.out.println("\n[INFO] No se ha podido eliminar el producto");
                        }
                        break;

                    case 7:
                        System.out.println("\nCONEXION CLIENTE TERMINADA ....");
                        break;
                }

            } while (opcion != 7);

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("\n[ERROR] Error de comunicación con el servidor: " + e.getMessage());
        } finally {
            cerrarConexion();
        }
    }
}