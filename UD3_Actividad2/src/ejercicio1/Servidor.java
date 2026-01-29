package ejercicio1;

import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

    private static ArrayList<Persona> personas = new ArrayList<Persona>();

    private static void cargaInicial() {
        personas.add(new Persona("pepe", "perez", 20));
        personas.add(new Persona("ana", "lopez", 22));
        personas.add(new Persona("jose", "gomez", 15));
    }

    private synchronized static Persona getPersona(int id) {

        for (Persona p : personas) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        cargaInicial();
        try {
            // Creamos objeto para el servidor Socket
            ServerSocket servidor;

            servidor = new ServerSocket();

            // Direccion + puerto
            InetSocketAddress direccion = new InetSocketAddress("localhost", 2000);
            // Vincular Direccion + puerto al servidor
            servidor.bind(direccion);

            System.out.println("SERVIDOR ARRANCADO.....");
            Socket socketCliente = servidor.accept();
            System.out.println("CLIENTE CONECTADO.....");

            InputStream entrada = socketCliente.getInputStream();

            ObjectInputStream inOb = new ObjectInputStream(socketCliente.getInputStream());
            ObjectOutputStream outOb = new ObjectOutputStream(socketCliente.getOutputStream());

            while (true) {
                // La peticion la recibimos en formato text
                String peticion = (String) inOb.readObject();

                String partes[] = peticion.split(",");

                if (partes[0].equals("BUSCAR")) {

                    int id = Integer.parseInt(partes[1]);
                    outOb.writeObject(getPersona(id));

                } else if (partes[0].equals("MOSTRAR TODOS")) {

                    outOb.writeObject(new ArrayList<>(personas));

                } else if (partes[0].equals("ADD")) {

                    String nombre = partes[1];
                    String apellidos = partes[2];
                    int edad = Integer.parseInt(partes[3]);
                    personas.add(new Persona(nombre, apellidos, edad));
                    outOb.writeObject("Persona añadida correctamente");

                } else if (partes[0].equals("SALIR")) {
                    break;
                }
            }

            byte mensaje[] = new byte[100];
            entrada.read(mensaje);

            String textoId = new String(mensaje).trim();
            int id = Integer.parseInt(textoId);

            Persona persona = getPersona(id);

            outOb.writeObject(persona);

            servidor.close();
            outOb.close();
            inOb.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}