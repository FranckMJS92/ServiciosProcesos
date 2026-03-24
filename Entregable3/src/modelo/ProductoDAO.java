package modelo;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO implements IProductoDAO {

    // 1. Listar todos los productos
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

    // 2. Busca por id
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

    // 3. Buscar por categoria
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

    // 4. Insertar nuevo producto
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

    // 5. Actualizar stock de producto por Id
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

    // 6. Eliminar producto por id
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