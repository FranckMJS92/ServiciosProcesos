package funcionesHash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EjemploHash {

    public static void main(String[] args) {

        String mensaje = "Hola mundo";

        // Hash trabaja con flujo de bytes
        // Convertimos la cadena a flujo de bytes
        byte[] bytes = mensaje.getBytes();

        MessageDigest mDigest = null;
        try {
            mDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        mDigest.update(bytes);

        // Llamamos al metodo digest para obtener el resumen (encriptacion)
        byte[] bytesResumen = mDigest.digest();

        System.out.println("Resumen hash : " + new String(bytesResumen));
    }
}
