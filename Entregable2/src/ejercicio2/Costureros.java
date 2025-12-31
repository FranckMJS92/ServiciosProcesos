package ejercicio2;

import java.util.Random;

public class Costureros extends Thread {
    private final AlmacenTelas almacenTelas;
    private final AlmacenCamisetas almacenCamisetas;
    private final Random random;
    private int camisetasConfeccionadas;

    public Costureros(String nombre, AlmacenTelas almacenTelas, AlmacenCamisetas almacenCamisetas) {
        super(nombre);
        this.almacenTelas = almacenTelas;
        this.almacenCamisetas = almacenCamisetas;
        this.random = new Random();
        this.camisetasConfeccionadas = 0;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Retirar 2 rollos de tela (esto esperará si no hay suficientes)
                almacenTelas.retirarDosRollos(getName());

                // Simular tiempo de confección (200-400 ms)
                int tiempoConfeccion = 200 + random.nextInt(201);
                Thread.sleep(tiempoConfeccion);

                camisetasConfeccionadas++;

                // Depositar la camiseta terminada
                almacenCamisetas.depositarCamiseta(getName());
            }
        } catch (InterruptedException e) {
            System.out.println("\n" + getName() + " interrumpido. Camisetas confeccionadas: " + camisetasConfeccionadas);
            Thread.currentThread().interrupt();
        }
    }
}
