package Ejercicio1;

import OwnException.EstructurasException;

public class Principal1 {
    public static void main(String[] args) {
        //Instancia cola1
        ColaArrayList<String> cola1 = new ColaArrayList<>();
        try {
            System.out.println(cola1.desencolar());
        } catch (EstructurasException e) {
            System.out.println(e.getMessage());
        }

        cola1.encolar("Juan");
        cola1.encolar("Pedro");
        cola1.encolar("Ana");
        cola1.encolar("Esteban");
        cola1.encolar("Carlos");
        cola1.encolar("Piero");
        cola1.encolar("Lucas");

        cola1.mostrarElementos();

        try {
            cola1.desencolar();
        } catch (EstructurasException e) {
            e.getMessage();
        }
        try {
            System.out.println("Elemento desencolado : " + cola1.desencolar());
        } catch (EstructurasException e) {
            e.getMessage();
        }

        cola1.mostrarElementos();

    }
}
