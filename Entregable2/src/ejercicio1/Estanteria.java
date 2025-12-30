package ejercicio1;

import java.util.LinkedList;
import java.util.Queue;

// Clase principal que representa la estantería compartida
public class Estanteria {
    private final Queue<String> comandas;
    private final int capacidadMaxima;

    public Estanteria(int capacidadMaxima) {
        this.comandas = new LinkedList<>();
        this.capacidadMaxima = capacidadMaxima;
    }

    // Método para que los camareros dejen comandas
    public synchronized void dejarComanda(String camarero, int numeroComanda, String comanda)
            throws InterruptedException {
        // Esperar si la estantería está llena
        while (comandas.size() >= capacidadMaxima) {
            wait();
        }

        // Añadir la comanda a la cola
        comandas.add(comanda);
        System.out.println(camarero + " deja comanda (" + comanda + ") -> Total en estantería: " + comandas.size());

        // Notificar a todos los hilos que esperan
        notifyAll();
    }

    // Método para que los cocineros recojan comandas
    public synchronized String recogerComanda(String cocinero) throws InterruptedException {
        // Esperar si la estantería está vacía
        while (comandas.isEmpty()) {
            wait();
        }

        // Recoger la comanda de la cola
        String comanda = comandas.poll();
        System.out.println(cocinero + " recoge comanda (" + comanda + ") -> Total en estantería: " + comandas.size());

        // Notificar a todos los hilos que esperan
        notifyAll();

        return comanda;
    }
}
