package ejercicio1;

public class HiloColecta extends Thread {

    // Atributo objeto compartido
    private Colecta colecta;

    public HiloColecta(String nombre, Colecta colecta) {
        super(nombre);
        this.colecta = colecta;
    }

    @Override
    public void run() {

        // Bucle hasta que se llegue a la cantidad maxima
        while (colecta.getRecaudacion() < colecta.getMaximoRecaudacion()) {

            // Generamos aleatorios: entre 100 y 500 ms, cantidad de entre 5 y 50 euros.
            int espera = (int) (Math.random() * 401) + 100;
            int cantidad = (int) (Math.random() * 46) + 5;

            colecta.addRecaudacion(cantidad);
            System.out.println(getName() + " ha recaudado " + cantidad);

            try {
                HiloColecta.sleep(espera);
            } catch (InterruptedException e) {
                System.out.println("ERROR en la ejecucion del hilo" + getName());
                e.printStackTrace();
            }
        }

        System.out.println("ha finalizado " + getName());
    }
}
