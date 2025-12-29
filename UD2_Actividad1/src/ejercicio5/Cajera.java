package ejercicio5;

public class Cajera extends Thread {

    private String nombre;
    private Cliente cliente;
    private long tiempoInicio;

    public Cajera(String nombre, Cliente cliente) {
        super(nombre);
        this.cliente = cliente;
        this.tiempoInicio = System.currentTimeMillis();
    }

    @Override
    public void run() {
        System.out.println("Cajera : " + getName() + " COMIENZA LA COMPRA DEL CLIENTE " + cliente.getNombre());

        // Recorremos los productos de los clientes
        int producto = 1;
        for (int tiempo : cliente.getProductos()) {
            esperarXsegundos(tiempo);
            System.out.println("Cajera : " + getName() + " ha procesado el producto " + producto + " Tiempo : "
                    + tiempoTranscurrido());
        }

        System.out.println("Cajera : " + getName() + " ha terminado en " + tiempoTranscurrido());
    }

    private void esperarXsegundos(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException e) {
            System.out.println("[ERROR] Hilo interrumpido");
            e.printStackTrace();
        }
    }

    private long tiempoTranscurrido() {
        return (System.currentTimeMillis() - this.tiempoInicio) / 1000;
    }

}
