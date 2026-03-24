package modelo;

import java.util.List;

public interface IProductoDAO {

    List<Producto> obtenerTodos();

    Producto buscarPorId(int idProducto);

    List<Producto> buscarPorCategoria(String categoria);

    boolean insertaProducto(Producto nuevoProducto);

    boolean actualizaStockProducto(int idProducto, double stockProducto);

    boolean eliminarProducto(int idProducto);

}