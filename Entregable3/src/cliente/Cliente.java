package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import modelo.Producto;
import utils.Utilidades;

public class Cliente {

    public static void mostrarMenu() {
        System.out.println("1. Listar todos los productos.");
        System.out.println("2. Buscar un producto por ID.");
        System.out.println("3. Buscar productos por categoria.");
        System.out.println("4. Insertar producto");
        System.out.println("5. Actualizar stock por producto");
        System.out.println("6. Eliminar producto por Id");
        System.out.println("7. Salir.");
    }

    public static void main(String[] args) {

        Socket socket = new Socket();

        InetSocketAddress direccion = new InetSocketAddress(Utilidades.DIRECCION, Utilidades.PUERTO);
        int opcion = 0;

        try {
            socket.connect(direccion);

            System.out.println("CONECTADO AL SERVIDOR ....");
            Utilidades utils = new Utilidades();

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            do {
                mostrarMenu();

                opcion = utils.leerEnteroRango("Indica una opcion [1-7]: ", 1, 7);

                out.writeInt(opcion);

                out.flush();

                switch (opcion) {
                    case 1:
                        // 1. Listar todos los productos
                        @SuppressWarnings("unchecked") List<Producto> productos = (List<Producto>) in.readObject();
                        utils.mostrarListaProductos(productos);
                        break;

                    case 2:
                        // 2. Busca por id
                        int idProducto = utils.leerEntero("ID del producto: ");
                        out.writeInt(idProducto);
                        out.flush();
                        Producto e = (Producto) in.readObject();
                        if (e != null) {
                            System.out.println("DATOS DEL PRODUCTO");
                            System.out.println(e);
                        } else {
                            System.out.println("[ERROR] No existe el producto con ID: " + idProducto);
                        }
                        break;

                    case 3:
                        // 3. Buscar por categoria
                        String categoria = utils.leerTexto("Nombre del departamento: ");
                        out.writeUTF(categoria);
                        out.flush();
                        @SuppressWarnings("unchecked") List<Producto> productoCat = (List<Producto>) in.readObject();
                        utils.mostrarListaProductos(productoCat);
                        break;

                    case 4:
                        // 4. Insertar nuevo producto
                        String nombre = utils.leerTexto("Nombre: ");
                        String cat = utils.leerTexto("Categoria: ");
                        double precio = utils.leerDouble("Precio: ");
                        double stock = utils.leerDouble("Stock: ");
                        // Creamos el objeto
                        Producto nuevoProducto = new Producto(nombre, cat, precio, stock);
                        // Enviamos el objeto empleado
                        out.writeObject(nuevoProducto);
                        // Recibimos respuesta
                        if (in.readBoolean()) {
                            System.out.println("[INFO] Producto creado");
                        } else {
                            System.out.println("[INFO] No se ha podido crear el producto");
                        }
                        break;

                    case 5:
                        // 5. Actualizar stock de producto por Id
                        int idUpdate = utils.leerEntero("ID del producto: ");
                        double stockUpdate = utils.leerDouble("Nuevo Stock: ");
                        out.writeInt(idUpdate);
                        out.writeDouble(stockUpdate);
                        out.flush();
                        if (in.readBoolean()) {
                            System.out.println("[INFO] Producto Actualizado");
                        } else {
                            System.out.println("[INFO] No se ha podido actualizar el producto");
                        }
                        break;

                    case 6:
                        // 6. Eliminar producto por id
                        int idDelete = utils.leerEntero("ID del producto: ");
                        out.writeInt(idDelete);
                        out.flush();
                        if (in.readBoolean()) {
                            System.out.println("[INFO] Producto eliminado");
                        } else {
                            System.out.println("[INFO] No se ha podido eliminar el producto");
                        }
                        break;

                    case 7:
                        System.out.println("CONEXION CLIENTE TERMINADA ....");
                        break;
                }

            } while (opcion != 7);

            socket.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
