package ejercicio1;

import java.util.Random;

// Clase que representa a un cocinero
public class Cocineros extends Thread {
    private String nombre;
    private Estanteria estanteria;
    private Random random;

    public Cocineros(String nombre, Estanteria estanteria) {
        super(nombre);
        this.estanteria = estanteria;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Recoger una comanda de la estantería
                String comanda = estanteria.recogerComanda(nombre);

                // "Cocinar" la comanda (tiempo aleatorio entre 250 y 350 ms)
                int tiempoCocina = 250 + random.nextInt(101); // 250-350 ms
                Thread.sleep(tiempoCocina);

                // Aquí se podría procesar la comanda recibida
                System.out.println(nombre + " está cocinando: " + comanda);
            }
        } catch (InterruptedException e) {
            System.out.println(nombre + " interrumpido.");
            e.printStackTrace();
        }
    }
}
