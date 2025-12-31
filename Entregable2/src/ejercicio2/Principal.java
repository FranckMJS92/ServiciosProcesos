package ejercicio2;

public class Principal {
    public static void main(String[] args) {
        // Crear los almacenes compartidos
        AlmacenTelas almacenTelas = new AlmacenTelas(20);
        AlmacenCamisetas almacenCamisetas = new AlmacenCamisetas(10);

        // Crear los hilos de suministradores
        Suministradores suministrador1 = new Suministradores("Suministrador 1", almacenTelas);
        Suministradores suministrador2 = new Suministradores("Suministrador 2", almacenTelas);

        // Crear los hilos de costureros
        Costureros costurero1 = new Costureros("Costurero 1", almacenTelas, almacenCamisetas);
        Costureros costurero2 = new Costureros("Costurero 2", almacenTelas, almacenCamisetas);

        // Crear los hilos de clientes
        Clientes cliente1 = new Clientes("Cliente 1", almacenCamisetas);
        Clientes cliente2 = new Clientes("Cliente 2", almacenCamisetas);

        // Iniciar todos los hilos
        suministrador1.start();
        suministrador2.start();
        costurero1.start();
        costurero2.start();
        cliente1.start();
        cliente2.start();

        // Esperar un tiempo para que el sistema funcione
        try {
            // Dejar que el sistema funcione durante 15 segundos
            Thread.sleep(15000);

            // Interrumpir todos los hilos después de 15 segundos
            suministrador1.interrupt();
            suministrador2.interrupt();
            costurero1.interrupt();
            costurero2.interrupt();
            cliente1.interrupt();
            cliente2.interrupt();

            // Esperar a que todos los hilos terminen
            suministrador1.join();
            suministrador2.join();
            costurero1.join();
            costurero2.join();
            cliente1.join();
            cliente2.join();

            System.out.println("\n=== SIMULACIÓN FINALIZADA ===");
            System.out.println("Estado final del almacén de telas: " + almacenTelas.getRollos() + " rollos");
            System.out.println(
                    "Estado final del almacén de camisetas: " + almacenCamisetas.getCamisetas() + " camisetas");

        } catch (InterruptedException e) {
            System.out.println("Main interrumpido.");
            Thread.currentThread().interrupt();
        }
    }
}
