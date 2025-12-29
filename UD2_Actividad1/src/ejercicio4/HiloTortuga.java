package ejercicio4;

import java.util.concurrent.ThreadLocalRandom;

public class HiloTortuga extends Thread {
    private int casillaFin;
    private int casillaActual;

    public HiloTortuga(int casillaFin) {
        this.casillaActual = 1;
        this.casillaFin = casillaFin;
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

            if (aleatorio < 50) {
                casillaActual += 3;
                System.out.println("TORTUGA - Avance rapido : " + casillaActual);
            } else if (aleatorio <= 70) {
                casillaActual -= 6;
                if (casillaActual < 1) {
                    casillaActual = 1;
                }
                System.out.println("TORTUGA - Resbalo : " + casillaActual);
            } else {
                casillaActual += 1;
                System.out.println("TORTUGA - Avance lento : " + casillaActual);
            }
        } while (casillaActual < casillaFin);

        System.out.println("TORTUGA HA FINALIZADO CARRERA");

        // variable ganador es estatica
        if (Carrera.ganador == null) {
            Carrera.ganador = "Tortuga";
        }
    }

}
