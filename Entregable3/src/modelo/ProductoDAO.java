package modelo;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO (Data Access Object) para la gestión de productos.
 * <p>
 * Esta clase implementa la interfaz {@link IProductoDAO} y proporciona
 * la lógica de acceso a datos para las operaciones CRUD sobre la tabla
 * de productos en la base de datos MySQL.
 * </p>
 * <p>
 * Todos los métodos están sincronizados ({@code synchronized}) para garantizar
 * la seguridad en entornos con múltiples hilos (varios clientes accediendo
 * simultáneamente a la base de datos).
 * </p>
 * <p>
 * Proyecto desarrollado para la asignatura de Programación de Servicios y
 * Procesos.
 * </p>
 * 
 * @author Francisco Lopez
 * @version 1.0
 */
public class ProductoDAO implements IProductoDAO {

    /**
     * {@inheritDoc}
     * <p>
     * Ejecuta una consulta SELECT para obtener todos los productos
     * almacenados en la base de datos.
     * </p>
     * 
     * @return Lista con todos los productos. Si no hay productos,
     *         devuelve una lista vacía.
     */
    @Override
    public synchronized List<Producto> obtenerTodos() {

        List<Producto> listaProductos = new ArrayList<Producto>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM productos";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                Producto e = mapearProducto(rs);

                listaProductos.add(e);
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return listaProductos;

    }

    /**
     * {@inheritDoc}
     * <p>
     * Ejecuta una consulta SELECT con filtro por ID utilizando un
     * {@link PreparedStatement} para prevenir inyección SQL.
     * </p>
     * 
     * @param idProducto Identificador único del producto a buscar
     * @return El producto encontrado, o {@code null} si no existe
     */
    @Override
    public synchronized Producto buscarPorId(int idProducto) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM productos WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idProducto);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Producto e = mapearProducto(rs);
                return e;
            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;

    }

    /**
     * {@inheritDoc}
     * <p>
     * Ejecuta una consulta SELECT con filtro por categoría utilizando un
     * {@link PreparedStatement} para prevenir inyección SQL.
     * </p>
     * 
     * @param categoria Nombre de la categoría a buscar
     * @return Lista de productos de la categoría especificada.
     *         Si no hay productos, devuelve una lista vacía.
     */
    @Override
    public synchronized List<Producto> buscarPorCategoria(String categoria) {

        List<Producto> listaProductos = new ArrayList<Producto>();

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM productos WHERE categoria=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, categoria);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Producto e = mapearProducto(rs);

                listaProductos.add(e);
            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return listaProductos;

    }

    /**
     * {@inheritDoc}
     * <p>
     * Ejecuta una sentencia INSERT para añadir un nuevo producto a la base de
     * datos.
     * El ID del producto es generado automáticamente por la base de datos.
     * </p>
     * 
     * @param nuevoProducto Objeto {@link Producto} con los datos a insertar
     * @return {@code true} si la inserción fue exitosa (al menos una fila
     *         afectada),
     *         {@code false} en caso contrario
     */
    @Override
    public synchronized boolean insertaProducto(Producto nuevoProducto) {
        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO productos (nombre, categoria, precio, stock) VALUES (?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nuevoProducto.getNombre());
            ps.setString(2, nuevoProducto.getCategoria());
            ps.setDouble(3, nuevoProducto.getPrecio());
            ps.setDouble(4, nuevoProducto.getStock());

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Ejecuta una sentencia UPDATE para modificar el stock de un producto
     * identificado por su ID.
     * </p>
     * 
     * @param idProducto    Identificador único del producto a actualizar
     * @param stockProducto Nuevo valor de stock para el producto
     * @return {@code true} si la actualización fue exitosa (al menos una fila
     *         afectada),
     *         {@code false} si el producto no existe o ocurrió un error
     */
    @Override
    public synchronized boolean actualizaStockProducto(int idProducto, double stockProducto) {
        try {

            Connection con = DBConnection.getConnection();

            String sql = "UPDATE productos SET stock=? WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setDouble(1, stockProducto);
            ps.setInt(2, idProducto);

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Ejecuta una sentencia DELETE para eliminar un producto de la base de datos
     * identificado por su ID.
     * </p>
     * 
     * @param idProducto Identificador único del producto a eliminar
     * @return {@code true} si la eliminación fue exitosa (al menos una fila
     *         afectada),
     *         {@code false} si el producto no existe o ocurrió un error
     */
    @Override
    public synchronized boolean eliminarProducto(int idProducto) {
        try {

            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM productos WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idProducto);

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    /**
     * Convierte una fila del resultado de la consulta en un objeto
     * {@link Producto}.
     * <p>
     * Este método privado extrae los valores de cada columna del {@link ResultSet}
     * y construye un objeto Producto con ellos. Se utiliza internamente por los
     * métodos de consulta para evitar duplicación de código.
     * </p>
     * 
     * @param rs {@link ResultSet} posicionado en la fila actual a procesar
     * @return Objeto {@link Producto} construido con los datos de la fila
     * @throws SQLException Si ocurre un error al acceder a los datos del ResultSet
     */
    private Producto mapearProducto(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        String categoria = rs.getString("categoria");
        double precio = rs.getDouble("precio");
        double stock = rs.getDouble("stock");

        Producto e = new Producto(id, nombre, categoria, precio, stock);
        return e;
    }
}