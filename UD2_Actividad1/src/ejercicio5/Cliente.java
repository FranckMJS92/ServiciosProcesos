package ejercicio5;

public class Cliente {
    private String nombre;
    private int[] productos;

    public Cliente(String nombre, int[] productos) {
        this.nombre = nombre;
        this.productos = productos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int[] getProductos() {
        return productos;
    }

    public void setProductos(int[] productos) {
        this.productos = productos;
    }

}
