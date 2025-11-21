package Ejercicio2;

import OwnException.EstructurasException;

public interface PilaInterfaz<E> {

    public void apilar(E element);

    public E desapilar() throws EstructurasException;

    public E getFirst() throws EstructurasException;

    public void mostrarPila();
}
