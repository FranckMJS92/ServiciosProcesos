package ejercicio4;

import java.util.concurrent.ThreadLocalRandom;

public class HiloLiebre implements Runnable {
    private int casillaFin;
    private int casillaActual;
    private Thread hilo;

    public HiloLiebre(int casillaFin) {
        this.casillaFin = casillaFin;
        this.casillaActual = 1;
        hilo = new Thread(this);
        hilo.start();
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Numero Aleatorio entre 0 y 100
            int aleatorio = ThreadLocalRandom.current().nextInt(0, 101);

            if (aleatorio < 40) {
                System.out.println("LIEBRE - Se ha dormido ... : " + casillaActual);
            } else if (aleatorio <= 60) {
                casillaActual += 9;
                System.out.println("LIEBRE - Avance rapido : " + casillaActual);
            } else if (aleatorio <= 80) {
                casillaActual -= 8;
                if (casillaActual < 1) {
                    casillaActual = 1;
                }
                System.out.println("LIEBRE - Resbalo : " + casillaActual);
            } else {
                casillaActual += 3;
                System.out.println("LIEBRE - Avance lento : " + casillaActual);
            }
        } while (casillaActual < casillaFin);

        System.out.println("LIEBRE HA FINALIZADO CARRERA");

        // variable ganador es estatica
        if (Carrera.ganador == null) {
            Carrera.ganador = "Liebre";
        }

    }

    // Se crea metodo join para clase con Runnable
    public void join() {
        try {
            this.hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
