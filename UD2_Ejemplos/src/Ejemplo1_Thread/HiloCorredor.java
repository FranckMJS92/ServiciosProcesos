package Ejemplo1_Thread;

import java.util.Random;

public class HiloCorredor extends Thread {

    private static String ganador = null;

    public HiloCorredor(String nombre) {
        super(nombre);
    }

    // Sobresccribimos el metodo run
    @Override
    public void run() {
        Random rand = new Random();
        // para acceder al nombre no se puede acceder directamente hay que usar
        // getName()
        System.out.println("Empieza carrera " + this.getName());

        for (byte km = 0; km <= 5; km++) {
            System.out.println(this.getName() + " va por el km " + km);
            try {
                Thread.sleep(500 + rand.nextInt(2000));
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        System.out.println("Termina carrera " + this.getName());

        // Si nadie ha llegado a la meta
        if(ganador == null){
            ganador = this.getName();
        }
    }

    // MEtodo static para que el hilo principal pueda consultar el ganador
    public static String getGanador(){
        
        return ganador;
    }
}
