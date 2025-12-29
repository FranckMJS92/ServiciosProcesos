package ejercicio2;

public class Colecta {
    private int recaudacion;
    private int numeroIngreso;
    private int numeroRetiradas;
    private int maximoRecaudacion;

    public Colecta(int maximoRecaudacion) {
        this.recaudacion = 0;
        this.numeroIngreso = 0;
        this.numeroRetiradas = 0;
        this.maximoRecaudacion = maximoRecaudacion;
    }

    // Metodo usado por los hilos recolectores
    synchronized public void addRecaudacion(int cantidad) {

        // Comprobar que no s ha llegado al limite
        while (this.recaudacion + cantidad > this.maximoRecaudacion) {
            System.out.println("No se puede ingresar " + cantidad + " tenemos " + recaudacion);
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("[ERROR] en addRecaudacion");
                e.printStackTrace();
            }
        }

        // Actualizamos la recaudacion y el numero de ingresos
        this.recaudacion += cantidad;
        numeroIngreso++;

        // Notify para los hilos consumidores que esten a la espera
        notifyAll();
    }

    // Metodo usado por los hilos consumidores
    synchronized public void sacarRecaudacion(int cantidad) {

        // Comprobar que hay dinero suficiente para sacar la cantidad
        while (recaudacion - cantidad < 0) {
            System.out.println("No se puede retirar " + cantidad + " tenemos " + recaudacion);
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("[ERROR] en sacarRecaudacion");
                e.printStackTrace();
            }
        }

        // Actualizamos la recaudacion y el numero de retiros
        this.recaudacion -= cantidad;
        numeroRetiradas++;

        // Notify para los hilos recolectores que esten a la espera
        notifyAll();
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
