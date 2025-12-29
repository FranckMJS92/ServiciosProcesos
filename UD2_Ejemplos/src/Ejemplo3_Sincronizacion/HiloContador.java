package Ejemplo3_Sincronizacion;

public class HiloContador extends Thread {

    private Contador contador;
    private int repeteciones;
    private int miCuenta = 0;

    public HiloContador(String nombre, Contador contador, int repeteciones) {
        super(nombre);
        this.contador = contador;
        this.repeteciones = repeteciones;
    }

    @Override
    public void run() {
        for (int i = 1; i <= repeteciones; i++) {
            contador.incrementar();
            miCuenta++;
        }
        System.out.println("HILO " + getName() + " ha terminado mi cuenta : " + miCuenta);
    }
}
