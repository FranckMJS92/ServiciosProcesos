package ejercicio1_2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class Servidor {

    private static String generarFecha() {
        LocalDateTime actual = LocalDateTime.now();
        int dia = actual.getDayOfMonth();
        int mes = actual.getMonthValue();
        int year = actual.getYear();
        String respuesta = String.format("DIA ACTUAL: %02d-%02d-%04d", dia, mes, year);
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
            // Creamos objeto para el servidor socket
            ServerSocket servidor = new ServerSocket();
            // Direccion + puerto
            InetSocketAddress direccion = new InetSocketAddress("localhost", 2000);
            // Vincular Direccion + puerto al servidor
            servidor.bind(direccion);

            System.out.println("Servidor creado y escuchando ... ");

            Socket socketCliente = servidor.accept();
            System.out.println("CLIENTE CONECTADO ... ");

            // Obtenemos los streams del socket para poder enviar y recibir informacion
            InputStream entrada = socketCliente.getInputStream();
            OutputStream salida = socketCliente.getOutputStream();

            // Leemos la peticion que nos envia el cliente
            // Declara una variable donde guardamos mensaje del cliente
            byte[] mensaje = new byte[100];
            // Leemos la petifcion que nos envia el cliente y lo guardamos en mensaje
            entrada.read(mensaje);
            String texto = new String(mensaje).trim();
            System.out.println("PETICION RECIBIDA: " + texto);

            // Si es un "1" -> Hora
            if (texto.equals("1")) {
                salida.write(generarHora().getBytes());
                // Si es un "2" -> Fecha
            } else if (texto.equals("2")) {
                salida.write(generarFecha().getBytes());
            } else {
                System.out.println("PETICION INCORRECTA".getBytes());
            }

            // Enviamos respuesta al cliente
            String respuesta = "Tamaño: " + texto.length();
            salida.write(respuesta.getBytes());

            // Cuando terminemos la comunicacioon, cerramos TODO
            salida.close();
            entrada.close();
            socketCliente.close();
            servidor.close();

        } catch (IOException e) {
            System.out.println("[ERROR] Al iniciar servidor");
            e.printStackTrace();
        }
    }
}
