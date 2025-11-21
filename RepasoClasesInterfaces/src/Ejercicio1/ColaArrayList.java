package Ejercicio1;

import java.util.ArrayList;
import OwnException.EstructurasException;

public class ColaArrayList<E> implements ColaInterfaz<E> {
    // Declaramos el ArrayList donde guardamos los datos
    private ArrayList<E> cola;

    // Constructor
    public ColaArrayList() {
        this.cola = new ArrayList<E>();
    }

    @Override
    public void encolar(E element) {
        // Añado el elemento al final de la cola
        this.cola.add(element);
    }

    @Override
    public E desencolar() throws EstructurasException {
        if (this.cola.isEmpty()) {
            throw new EstructurasException(EstructurasException.COLA_VACIA);
        }
        // Elimino el primer elemento
        E element = this.cola.remove(0);
        return element;
    }

    @Override
    public E getFirst() throws EstructurasException {
        if (this.cola.isEmpty()) {
            throw new EstructurasException(EstructurasException.COLA_VACIA);
        }
        return this.cola.getFirst();
    }

    @Override
    public void mostrarElementos() {
        System.out.println("Elementos en la cola : " + cola.size());
        for (E element : cola) {
            System.out.println(element);
        }
    }
}
