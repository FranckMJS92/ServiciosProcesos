package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import modelo.Producto;
import modelo.IProductoDAO;

public class ClienteHandler extends Thread {

    private Socket socketCliente;
    private IProductoDAO productoDAO;

    public ClienteHandler(Socket socketCliente, IProductoDAO productoDAO) {
        super();
        this.socketCliente = socketCliente;
        this.productoDAO = productoDAO;
        this.start();
    }

    @Override
    public void run() {

        try {

            ObjectOutputStream out = new ObjectOutputStream(socketCliente.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socketCliente.getInputStream());
            boolean conectado = true;

            while (conectado) {
                int opcion = in.readInt();
                System.out.println("[INFO] Peticion: " + opcion);

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
                        System.out.println("[INFO] Actualizar producto con Id: " + idUpdate + " a stock: " + stockUpdate);
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
                        conectado = false;
                        out.close();
                        in.close();
                        socketCliente.close();
                        break;
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}