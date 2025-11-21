package Ejercicio2;

import OwnException.EstructurasException;

public class Principal2 {
    public static void main(String[] args) {
        PilaArrayList<String> pila1 = new PilaArrayList<>();

        try {
            pila1.desapilar();
        } catch (EstructurasException e) {
            System.out.println(e.getMessage());
        }

        pila1.apilar("Carne");
        pila1.apilar("Ajo");
        pila1.apilar("Limo");
        pila1.apilar("Zanahoria");
        pila1.apilar("Camote");

        pila1.mostrarPila();

        try {
            pila1.desapilar();
        } catch (EstructurasException e) {
            e.getMessage();
        }

        pila1.mostrarPila();

        try {
            System.out.println(pila1.getFirst());
        } catch (EstructurasException e) {
            System.out.println(e.getMessage());
        }
    }
}
