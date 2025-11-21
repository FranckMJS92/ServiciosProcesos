package Ejercicio2;

import java.util.ArrayList;

import OwnException.EstructurasException;

public class PilaArrayList<E> implements PilaInterfaz<E> {
    private ArrayList<E> pila;

    // Constructor
    public PilaArrayList() {
        this.pila = new ArrayList<E>();
    }

    @Override
    public void apilar(E element) {
        this.pila.add(element);
    }

    @Override
    public E desapilar() throws EstructurasException {
        if (pila.isEmpty()) {
            throw new EstructurasException(EstructurasException.PILA_VACIA);
        }
        E element = this.pila.removeFirst();
        return element;
    }

    @Override
    public E getFirst() throws EstructurasException {
        if (pila.isEmpty()) {
            throw new EstructurasException(EstructurasException.PILA_VACIA);
        }
        // El primero en una pila es el ultimo elemento ingresado
        return this.pila.getLast();
    }

    @Override
    public void mostrarPila() {
        System.out.println("Elementos en la pila : " + pila.size());
        for (E element : pila) {
            System.out.println(element);
        }

    }

}
