package ejercicio3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;

public class Servidor {

    private static String generarFecha() {
        LocalDateTime actual = LocalDateTime.now();
        int dia = actual.getDayOfMonth();
        int mes = actual.getMonthValue();
        int year = actual.getYear();
        String respuesta = String.format("DIA ACTUAL: %02d-%02d-%02d", dia, mes, year);
        return respuesta;
    }

    private static String generarHora() {
        LocalDateTime actual = LocalDateTime.now();
        int hora = actual.getHour();
        int minutos = actual.getMinute();
        int segundos = actual.getSecond();

        String respuesta = String.format("HORA ACTUAL : %02d:%02d:%02d", hora, minutos, segundos);
        return respuesta;
    }

    public static void main(String[] args) {
        try {
            // Direccion + puerto
            InetSocketAddress direccion = new InetSocketAddress("localhost", 2000);
            DatagramSocket ds = new DatagramSocket(direccion);
            // Creamos datagrama donde recibimos la peticion
            byte mensajeRecibido[] = new byte[100];
            DatagramPacket recibido = new DatagramPacket(mensajeRecibido, 100);

            // El servidor se queda a la espera de recibir el datagrama del cliente
            ds.receive(recibido);

            // Dependiendo del valor del texto, enviamos un dato u otro
            String textoRecibido = new String(mensajeRecibido).trim();

            // Para enviar respuesta al ciente, necesito la direccion IP y el puerto
            // por el que el cliente ha enviado el mensaje
            InetAddress direccionCliente = recibido.getAddress();
            int puertoCliente = recibido.getPort();

            String respuesta = "";
            
            // Tenemos que crear el datagrama que se va a enviar al cliente
            if (textoRecibido.equals("1")) {
                respuesta = generarHora();
                DatagramPacket enviado = new DatagramPacket(respuesta.getBytes(), respuesta.length(), direccionCliente,
                        puertoCliente);
                ds.send(enviado);
            } else if (textoRecibido.equals("2")) {
                respuesta = generarFecha();
                DatagramPacket enviado = new DatagramPacket(respuesta.getBytes(), respuesta.length(), direccionCliente,
                        puertoCliente);
                ds.send(enviado);
            } else {
                System.out.println("PETICION INCORRECTA".getBytes());
            }
            ds.close();
        } catch (IOException e) {
            System.out.println("ERROR al arrancar el servidor");
            e.printStackTrace();
        }
    }
}
