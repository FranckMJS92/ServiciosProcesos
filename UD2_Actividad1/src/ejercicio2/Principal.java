package ejercicio2;

public class Principal {
    public static void main(String[] args) {

        HiloFibonacci f1 = new HiloFibonacci(5);
        HiloFibonacci f2 = new HiloFibonacci(8);

        f1.start();
        f2.start();
    }
}
