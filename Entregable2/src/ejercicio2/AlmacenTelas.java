package ejercicio2;

import java.util.Random;

class AlmacenTelas {
    private int rollos;
    private final int capacidadMaxima;
    private final Random random;

    public AlmacenTelas(int capacidadMaxima) {
        this.rollos = 0;
        this.capacidadMaxima = capacidadMaxima;
        this.random = new Random();
    }

    // Método sincronizado para depositar rollos de tela
    public synchronized void depositarRollo(String suministrador) throws InterruptedException {
        // Esperar si el almacén está lleno
        while (rollos >= capacidadMaxima) {
            wait();
        }

        // Simular tiempo de depositar (100-300 ms)
        int tiempoDepositar = 100 + random.nextInt(201);
        Thread.sleep(tiempoDepositar);

        // Depositar el rollo
        rollos++;
        System.out.println(suministrador + " deposita rollo de tela -> Total rollos en almacén: " + rollos);

        // Notificar a todos los hilos que esperan
        notifyAll();
    }

    // Método sincronizado para retirar 2 rollos (para una camiseta)
    public synchronized void retirarDosRollos(String costurero) throws InterruptedException {
        // Esperar si no hay al menos 2 rollos
        while (rollos < 2) {
            wait();
        }

        // Retirar 2 rollos
        rollos -= 2;

        // Notificar a todos los hilos que esperan
        notifyAll();
    }

    // Método para obtener la cantidad actual de rollos
    public synchronized int getRollos() {
        return rollos;
    }
}
