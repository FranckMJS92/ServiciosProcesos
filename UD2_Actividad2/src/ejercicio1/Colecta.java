package ejercicio1;

public class Colecta {
    private int recaudacion;
    private int numeroIngreso;
    private int maximoRecaudacion;

    public Colecta(int maximoRecaudacion) {
        this.recaudacion = 0;
        this.numeroIngreso = 0;
        this.maximoRecaudacion = maximoRecaudacion;
    }

    // Metodo usado por los hilos colectores
    synchronized public void addRecaudacion(int cantidad) {

        // Actualizamos la recaudacion y el numero de ingresos
        if (this.recaudacion + cantidad > this.maximoRecaudacion) {
            this.recaudacion = this.maximoRecaudacion;
        } else {
            this.recaudacion += cantidad;
        }
        numeroIngreso++;
    }

    synchronized public int getRecaudacion() {
        return recaudacion;
    }

    synchronized public int getNumeroIngreso() {
        return numeroIngreso;
    }

    public int getMaximoRecaudacion() {
        return maximoRecaudacion;
    }
}
