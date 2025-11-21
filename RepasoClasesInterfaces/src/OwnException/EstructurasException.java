package OwnException;

public class EstructurasException extends Exception {

    // Constantes para los mensajes de Error
    public static final String COLA_VACIA = "Error, la cola esta vacia!";
    public static final String PILA_VACIA = "Error, la pila esta vacia!";

    public EstructurasException(String mensaje) {
        super(mensaje);
    }
}
