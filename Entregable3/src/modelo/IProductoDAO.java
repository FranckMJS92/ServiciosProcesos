package modelo;

import java.util.List;

/**
 * Interfaz que define las operaciones CRUD para la gestión de productos.
 * <p>
 * Esta interfaz establece el contrato que deben implementar las clases
 * que gestionan el acceso a datos de productos. Proporciona métodos para
 * realizar operaciones de consulta, inserción, actualización y eliminación
 * de productos en la base de datos.
 * </p>
 * <p>
 * El acrónimo DAO (Data Access Object) representa el patrón de diseño
 * utilizado para separar la lógica de negocio de la lógica de acceso a datos.
 * </p>
 * <p>
 * Proyecto desarrollado para la asignatura de Programación de Servicios y
 * Procesos.
 * </p>
 * 
 * @author Francisco Lopez
 * @version 1.0
 */
public interface IProductoDAO {

    /**
     * Obtiene todos los productos almacenados en la base de datos.
     * 
     * @return Lista con todos los productos. Si no hay productos,
     *         devuelve una lista vacía (no {@code null})
     */
    List<Producto> obtenerTodos();

    /**
     * Busca un producto por su identificador único.
     * 
     * @param idProducto Identificador único del producto a buscar
     * @return El producto encontrado, o {@code null} si no existe
     *         ningún producto con el ID especificado
     */
    Producto buscarPorId(int idProducto);

    /**
     * Busca todos los productos que pertenecen a una categoría específica.
     * 
     * @param categoria Nombre de la categoría a buscar
     * @return Lista de productos que pertenecen a la categoría especificada.
     *         Si no hay productos en esa categoría, devuelve una lista vacía
     *         (no {@code null})
     */
    List<Producto> buscarPorCategoria(String categoria);

    /**
     * Inserta un nuevo producto en la base de datos.
     * 
     * @param nuevoProducto Objeto {@link Producto} con los datos del
     *                      producto a insertar
     * @return {@code true} si el producto se insertó correctamente,
     *         {@code false} en caso contrario
     */
    boolean insertaProducto(Producto nuevoProducto);

    /**
     * Actualiza el stock de un producto existente.
     * 
     * @param idProducto    Identificador único del producto a actualizar
     * @param stockProducto Nuevo valor de stock para el producto
     * @return {@code true} si el stock se actualizó correctamente,
     *         {@code false} si el producto no existe o ocurrió un error
     */
    boolean actualizaStockProducto(int idProducto, double stockProducto);

    /**
     * Elimina un producto de la base de datos por su identificador.
     * 
     * @param idProducto Identificador único del producto a eliminar
     * @return {@code true} si el producto se eliminó correctamente,
     *         {@code false} si el producto no existe o ocurrió un error
     */
    boolean eliminarProducto(int idProducto);
}