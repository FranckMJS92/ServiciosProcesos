package ejercicio2;

import java.util.Random;

public class Suministradores extends Thread {
    private AlmacenTelas almacenTelas;
    private Random random;

    public Suministradores(String nombre, AlmacenTelas almacenTelas) {
        super(nombre);
        this.almacenTelas = almacenTelas;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Depositar un rollo de tela
                almacenTelas.depositarRollo(getName());

                // Tiempo aleatorio entre depositar rollos
                int tiempoEspera = random.nextInt(201); // 0-200 ms
                Thread.sleep(tiempoEspera);
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " interrumpido.");
            Thread.currentThread().interrupt();
        }
    }
}
