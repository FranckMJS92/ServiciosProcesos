package Ejemplo1_Thread;

public class Principal {
    public static void main(String[] args) {
        // Creamos los hilos
        HiloCorredor hilo1 = new HiloCorredor("Pepe");
        HiloCorredor hilo2 = new HiloCorredor("Juan");

        // Iniciamos los hilos
        hilo1.start(); // run o start funciona igual - usamos run si lo hemos sobreescrito en la clase
        hilo2.start();

        // Esperar a que los hilos terminen
        try {
            hilo1.join();
            hilo2.join();
        } catch (InterruptedException e) {
            System.out.println("Hilo interrumpido");
            e.printStackTrace();
        }

        System.out.println("*** FIN DE CARRERA ***");

        // Mostrar el ganador
        System.out.println("-> Ganador " + HiloCorredor.getGanador());
    }
}
