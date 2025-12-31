package ejercicio2;

import java.util.Random;

class AlmacenCamisetas {
    private int camisetas;
    private int capacidadMaxima;
    private Random random;

    public AlmacenCamisetas(int capacidadMaxima) {
        this.camisetas = 0;
        this.capacidadMaxima = capacidadMaxima;
        this.random = new Random();
    }

    // Método sincronizado para depositar una camiseta
    public synchronized void depositarCamiseta(String costurero) throws InterruptedException {
        // Esperar si el almacén está lleno
        while (camisetas >= capacidadMaxima) {
            wait();
        }

        // Depositar la camiseta
        camisetas++;
        System.out.println(costurero + " confecciona camiseta -> Total camisetas en almacén: " + camisetas);

        // Notificar a todos los hilos que esperan
        notifyAll();
    }

    // Método sincronizado para retirar una camiseta
    public synchronized void retirarCamiseta(String cliente) throws InterruptedException {
        // Esperar si no hay camisetas disponibles
        while (camisetas <= 0) {
            wait();
        }

        // Simular tiempo de retirada/compra (300-600 ms)
        int tiempoCompra = 300 + random.nextInt(301);
        Thread.sleep(tiempoCompra);

        // Retirar la camiseta
        camisetas--;
        System.out.println(cliente + " retira camiseta -> Total camisetas en almacén: " + camisetas);

        // Notificar a todos los hilos que esperan
        notifyAll();
    }

    // Método para obtener la cantidad actual de camisetas
    public synchronized int getCamisetas() {
        return camisetas;
    }
}
