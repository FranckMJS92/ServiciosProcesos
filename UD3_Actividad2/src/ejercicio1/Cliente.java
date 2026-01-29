package ejercicio1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        try {
            // Conectamos al servidor
            Socket socket = new Socket();
            InetSocketAddress direccion = new InetSocketAddress("localhost", 2000);

            socket.connect(direccion);

            System.out.println("CONECTADO AL SERVIDOR...");

            // Obtemos los streams del socket para poder enviar y recibir informacion con el
            // servidor
            ObjectInputStream inOb = new ObjectInputStream(socket.getInputStream());
            OutputStream salida = socket.getOutputStream();

            // MENU
            System.out.println("=== MENU  ===");
            System.out.println("1. Buscar por Id");
            System.out.println("2. Mostrar todos");
            System.out.println("3. Añadir Persona");
            System.out.println("4. Eliminar Persona");
            System.out.println("5. Maores de edad");
            System.out.println("6. Salir");

            int opcion = scan.nextInt();

            switch (opcion) {
                case 1:

                    break;

                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

                    break;
                default:
                    System.out.println("Opcion incorrecta!");
                    break;
            }

            String peticion = "";

            System.out.println("Id de la persona: ");
            peticion = scan.nextLine();

            salida.write(peticion.getBytes());

            // Enviar id de la pesona
            Persona persona = (Persona) inOb.readObject();

            if (persona == null) {
                System.out.println("No existe la persona con el ID: " + peticion);
            } else {
                System.out.println(persona);
            }

            // Recibir objeto del servidor
            scan.close();
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}