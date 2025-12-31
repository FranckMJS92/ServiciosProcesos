package ejercicio2;

import java.util.Random;

public class Clientes extends Thread {
    private AlmacenCamisetas almacenCamisetas;
    private Random random;
    private int camisetasCompradas;

    public Clientes(String nombre, AlmacenCamisetas almacenCamisetas) {
        super(nombre);
        this.almacenCamisetas = almacenCamisetas;
        this.random = new Random();
        this.camisetasCompradas = 0;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Retirar una camiseta (esto esperará si no hay camisetas)
                almacenCamisetas.retirarCamiseta(getName());
                camisetasCompradas++;

                // Tiempo aleatorio entre compras
                int tiempoEntreCompras = random.nextInt(301); // 0-300 ms adicionales
                Thread.sleep(tiempoEntreCompras);
            }
        } catch (InterruptedException e) {
            System.out.println("\n" + getName() + " interrumpido. Camisetas compradas: " + camisetasCompradas);
            Thread.currentThread().interrupt();
        }
    }
}
