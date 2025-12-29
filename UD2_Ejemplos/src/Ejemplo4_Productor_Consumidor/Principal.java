package Ejemplo4_Productor_Consumidor;

public class Principal {
    public static void main(String[] args) {

        BufferFrases frases = new BufferFrases();

        Consumidor c1 = new Consumidor("C1", frases);
        c1.start();
        Consumidor c2 = new Consumidor("C2", frases);
        c2.start();
        Consumidor c3 = new Consumidor("C3", frases);
        c3.start();

        Productor p1 = new Productor("P1", frases, "Hola mundo!!!");
        p1.start();
        Productor p2 = new Productor("P2", frases, "Hoy es lunes!!!");
        p2.start();
        Productor p3 = new Productor("P3", frases, "Hostia tio!!");
        p3.start();

    }
}
