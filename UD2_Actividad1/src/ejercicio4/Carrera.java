package ejercicio4;

public class Carrera {

    public static String ganador = null;

    public static void main(String[] args) {
        HiloTortuga t = new HiloTortuga(20);
        HiloLiebre l = new HiloLiebre(20);

        t.start();
        l.join();

        System.out.println("\nHA TERMINADO LA CARRERA");
        System.out.println("GANADOR: " + ganador + "\n");
    }
}
