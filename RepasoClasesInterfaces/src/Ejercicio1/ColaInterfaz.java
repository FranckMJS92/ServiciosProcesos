package Ejercicio1;

import OwnException.EstructurasException;

public interface ColaInterfaz<E>{

    public void encolar(E element);

    public E desencolar() throws EstructurasException;

    public E getFirst() throws EstructurasException;

    public void mostrarElementos();
}
