package modelo;

import java.io.Serializable;

/**
 * Clase que representa un producto en el sistema.
 * <p>
 * Esta clase encapsula los datos de un producto, incluyendo su identificador,
 * nombre, categoría, precio y stock. Implementa {@link Serializable} para
 * poder ser transmitida a través de la red entre el cliente y el servidor.
 * </p>
 * <p>
 * Proyecto desarrollado para la asignatura de Programación de Servicios y
 * Procesos.
 * </p>
 * 
 * @author Francisco Lopez
 * @version 1.0
 */
public class Producto implements Serializable {

    /** Versión de serialización para mantener compatibilidad entre versiones */
    private static final long serialVersionUID = 1050366744287801879L;

    /** Identificador único del producto */
    private int id;

    /** Nombre del producto */
    private String nombre;

    /** Categoría a la que pertenece el producto */
    private String categoria;

    /** Precio del producto */
    private double precio;

    /** Cantidad disponible en inventario */
    private double stock;

    /**
     * Constructor para crear un producto sin ID.
     * <p>
     * Este constructor se utiliza cuando se va a insertar un nuevo producto
     * en la base de datos. El ID será generado automáticamente por la base
     * de datos.
     * </p>
     * 
     * @param nombre    Nombre del producto
     * @param categoria Categoría del producto
     * @param precio    Precio del producto
     * @param stock     Cantidad en stock del producto
     */
    public Producto(String nombre, String categoria, double precio, double stock) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    /**
     * Constructor para crear un producto con ID.
     * <p>
     * Este constructor se utiliza cuando se recupera un producto existente
     * de la base de datos, ya que incluye su identificador único.
     * </p>
     * 
     * @param id        Identificador único del producto
     * @param nombre    Nombre del producto
     * @param categoria Categoría del producto
     * @param precio    Precio del producto
     * @param stock     Cantidad en stock del producto
     */
    public Producto(int id, String nombre, String categoria, double precio, double stock) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    /**
     * Obtiene el identificador único del producto.
     * 
     * @return El ID del producto
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único del producto.
     * 
     * @param id Nuevo identificador del producto
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del producto.
     * 
     * @return El nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     * 
     * @param nombre Nuevo nombre del producto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la categoría del producto.
     * 
     * @return La categoría del producto
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoría del producto.
     * 
     * @param categoria Nueva categoría del producto
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Obtiene el precio del producto.
     * 
     * @return El precio del producto
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     * 
     * @param precio Nuevo precio del producto
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene la cantidad en stock del producto.
     * 
     * @return El stock del producto
     */
    public double getStock() {
        return stock;
    }

    /**
     * Establece la cantidad en stock del producto.
     * 
     * @param stock Nueva cantidad en stock
     */
    public void setStock(double stock) {
        this.stock = stock;
    }

    /**
     * Devuelve una representación en texto del producto.
     * <p>
     * Incluye todos los atributos del producto: id, nombre, categoría,
     * precio y stock.
     * </p>
     * 
     * @return Cadena con los datos del producto
     */
    @Override
    public String toString() {
        return "Producto [id=" + id + ", nombre=" + nombre + ", categoria=" + categoria + ", precio=" + precio
                + ", stock=" + stock + "]";
    }
}