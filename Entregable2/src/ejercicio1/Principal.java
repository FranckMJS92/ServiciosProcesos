package ejercicio1;

public class Principal {

    public static void main(String[] args) {
        // Crear la estantería compartida con capacidad para 10 comandas
        Estanteria estanteria = new Estanteria(10);

        // Crear los hilos de camareros
        Camareros camarero1 = new Camareros("Camarero 1", estanteria);
        Camareros camarero2 = new Camareros("Camarero 2", estanteria);

        // Crear los hilos de cocineros
        Cocineros cocinero1 = new Cocineros("Cocinero 1", estanteria);
        Cocineros cocinero2 = new Cocineros("Cocinero 2", estanteria);

        // Iniciar todos los hilos
        camarero1.start();
        camarero2.start();
        cocinero1.start();
        cocinero2.start();

        try {
            // Esperar a que todos los hilos terminen
            camarero1.join();
            camarero2.join();
            cocinero1.join();
            cocinero2.join();

        } catch (InterruptedException e) {
            System.out.println("Main interrumpido.");
            e.printStackTrace();
        }
    }
}
