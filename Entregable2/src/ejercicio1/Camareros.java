package ejercicio1;

import java.util.Random;

// Clase que representa a un camarero
public class Camareros extends Thread {
    private String nombre;
    private Estanteria estanteria;
    private Random random;
    private int contadorComandas;

    public Camareros(String nombre, Estanteria estanteria) {
        super(nombre);
        this.estanteria = estanteria;
        this.random = new Random();
        this.contadorComandas = 0;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Generar una nueva comanda
                contadorComandas++;
                String comanda = nombre + " - Comanda " + contadorComandas;

                // Dejar la comanda en la estantería
                estanteria.dejarComanda(nombre, contadorComandas, comanda);

                // Tiempo aleatorio entre 200 y 400 ms
                int tiempoEspera = 200 + random.nextInt(201); // 200-400 ms
                Thread.sleep(tiempoEspera);
            }
        } catch (InterruptedException e) {
            System.out.println(nombre + " interrumpido.");
            e.printStackTrace();
        }
    }
}
