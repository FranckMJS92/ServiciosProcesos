import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class AplicacionSegura {

    private static final String CARPETA_USUARIOS = "usuarios";
    private static final int DESPLAZAMIENTO_CESAR = 3;

    public static void main(String[] args) {

        // Crear carpeta de usuarios si no existe
        File carpeta = new File(CARPETA_USUARIOS);
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== MENÚ DE INICIO ===");
            System.out.println("1 - Registro de usuario");
            System.out.println("2 - Login");
            System.out.println("3 - Salir");
            System.out.print("Elige una opción: ");

            opcion = leerEntero(scanner);

            switch (opcion) {
                case 1:
                    registrarUsuario(scanner);
                    break;
                case 2:
                    String rol = login(scanner);
                    if (rol != null) {
                        if (rol.equals("admin")) {
                            menuAdmin(scanner);
                        } else if (rol.equals("usuario")) {
                            menuUsuario(scanner);
                        }
                    }
                    break;
                case 3:
                    System.out.println("Saliendo de la aplicación...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 3);

        scanner.close();
    }

    // --------------------------------------------------------------
    // 1. REGISTRO DE USUARIO
    // --------------------------------------------------------------
    public static void registrarUsuario(Scanner scanner) {
        System.out.print("Nombre de usuario: ");
        String nombre = scanner.nextLine();

        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        System.out.print("Rol (admin/usuario): ");
        String rol = scanner.nextLine().toLowerCase();

        if (!rol.equals("admin") && !rol.equals("usuario")) {
            System.out.println("Rol no válido. Debe ser 'admin' o 'usuario'.");
            return;
        }

        // Verificar si el usuario ya existe
        File ficheroUsuario = new File(CARPETA_USUARIOS + "/" + nombre + ".txt");
        if (ficheroUsuario.exists()) {
            System.out.println("Error: El usuario ya existe.");
            return;
        }

        // Generar hash de la contraseña
        String hashContrasena = generarHash(contrasena);

        // Guardar en el fichero: primera línea rol, segunda línea hash
        try (PrintWriter writer = new PrintWriter(ficheroUsuario)) {
            writer.println(rol);
            writer.println(hashContrasena);
            System.out.println("Usuario registrado correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("Error al guardar el usuario.");
        }
    }

    // Generar hash con SHA-256
    private static String generarHash(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(texto.getBytes());

            // Convertir bytes a hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // --------------------------------------------------------------
    // 2. LOGIN
    // --------------------------------------------------------------
    public static String login(Scanner scanner) {
        System.out.print("Nombre de usuario: ");
        String nombre = scanner.nextLine();

        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        File ficheroUsuario = new File(CARPETA_USUARIOS + "/" + nombre + ".txt");
        if (!ficheroUsuario.exists()) {
            System.out.println("Error: Usuario no encontrado.");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ficheroUsuario))) {
            String rol = reader.readLine();
            String hashAlmacenado = reader.readLine();
            String hashIngresado = generarHash(contrasena);

            if (hashAlmacenado.equals(hashIngresado)) {
                System.out.println("Login exitoso. Bienvenido " + nombre + " (" + rol + ")");
                return rol;
            } else {
                System.out.println("Error: Contraseña incorrecta.");
                return null;
            }
        } catch (IOException e) {
            System.out.println("Error al leer el usuario.");
            return null;
        }
    }

    // --------------------------------------------------------------
    // 3. MENÚ ADMINISTRADOR
    // --------------------------------------------------------------
    public static void menuAdmin(Scanner scanner) {
        int opcion;
        do {
            System.out.println("\n=== MENÚ ADMINISTRADOR ===");
            System.out.println("1 - Listar usuarios");
            System.out.println("2 - Borrar usuario");
            System.out.println("3 - Cambiar rol de usuario");
            System.out.println("4 - Salir");
            System.out.print("Elige una opción: ");

            opcion = leerEntero(scanner);

            switch (opcion) {
                case 1:
                    listarUsuarios();
                    break;
                case 2:
                    borrarUsuario(scanner);
                    break;
                case 3:
                    cambiarRolUsuario(scanner);
                    break;
                case 4:
                    System.out.println("Saliendo del menú administrador...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 4);
    }

    // Listar usuarios
    public static void listarUsuarios() {
        File carpeta = new File(CARPETA_USUARIOS);
        File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(".txt"));

        if (archivos == null || archivos.length == 0) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        System.out.println("\n=== LISTA DE USUARIOS ===");
        for (File archivo : archivos) {
            String nombre = archivo.getName().replace(".txt", "");
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String rol = reader.readLine();
                System.out.println("- " + nombre + " -> Rol: " + rol);
            } catch (IOException e) {
                System.out.println("Error al leer el usuario " + nombre);
            }
        }
    }

    // Borrar usuario
    public static void borrarUsuario(Scanner scanner) {
        System.out.print("Nombre del usuario a borrar: ");
        String nombre = scanner.nextLine();

        File fichero = new File(CARPETA_USUARIOS + "/" + nombre + ".txt");
        if (fichero.exists()) {
            fichero.delete();
            System.out.println("Usuario " + nombre + " borrado correctamente.");
        } else {
            System.out.println("El usuario no existe.");
        }
    }

    // Cambiar rol de usuario
    public static void cambiarRolUsuario(Scanner scanner) {
        System.out.print("Nombre del usuario a modificar: ");
        String nombre = scanner.nextLine();

        File fichero = new File(CARPETA_USUARIOS + "/" + nombre + ".txt");
        if (!fichero.exists()) {
            System.out.println("El usuario no existe.");
            return;
        }

        System.out.print("Nuevo rol (admin/usuario): ");
        String nuevoRol = scanner.nextLine().toLowerCase();

        if (!nuevoRol.equals("admin") && !nuevoRol.equals("usuario")) {
            System.out.println("Rol no válido.");
            return;
        }

        // Leer el hash actual y escribir con el nuevo rol
        try (BufferedReader reader = new BufferedReader(new FileReader(fichero))) {
            reader.readLine(); // rol antiguo (lo ignoramos)
            String hash = reader.readLine();

            try (PrintWriter writer = new PrintWriter(fichero)) {
                writer.println(nuevoRol);
                writer.println(hash);
            }
            System.out.println("Rol cambiado correctamente a " + nuevoRol);
        } catch (IOException e) {
            System.out.println("Error al modificar el rol.");
        }
    }

    // --------------------------------------------------------------
    // 4. MENÚ USUARIO (Cifrado César)
    // --------------------------------------------------------------
    public static void menuUsuario(Scanner scanner) {
        int opcion;
        do {
            System.out.println("\n=== MENÚ USUARIO ===");
            System.out.println("1 - Cifrar con algoritmo César");
            System.out.println("2 - Descifrar con algoritmo César");
            System.out.println("3 - Salir");
            System.out.print("Elige una opción: ");

            opcion = leerEntero(scanner);

            switch (opcion) {
                case 1:
                    cifrarCesar(scanner);
                    break;
                case 2:
                    descifrarCesar(scanner);
                    break;
                case 3:
                    System.out.println("Saliendo del menú usuario...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 3);
    }

    // Cifrar con César
    public static void cifrarCesar(Scanner scanner) {
        System.out.print("Introduce el texto a cifrar: ");
        String texto = scanner.nextLine();

        StringBuilder cifrado = new StringBuilder();
        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                char nuevo = (char) ((c - base + DESPLAZAMIENTO_CESAR) % 26 + base);
                cifrado.append(nuevo);
            } else {
                cifrado.append(c);
            }
        }
        System.out.println("Texto cifrado: " + cifrado.toString());
    }

    // Descifrar con César inverso
    public static void descifrarCesar(Scanner scanner) {
        System.out.print("Introduce el texto a descifrar: ");
        String texto = scanner.nextLine();

        StringBuilder descifrado = new StringBuilder();
        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                char nuevo = (char) ((c - base - DESPLAZAMIENTO_CESAR + 26) % 26 + base);
                descifrado.append(nuevo);
            } else {
                descifrado.append(c);
            }
        }
        System.out.println("Texto descifrado: " + descifrado.toString());
    }

    // --------------------------------------------------------------
    // Metodo auxiliar para leer enteros sin errores
    // --------------------------------------------------------------
    private static int leerEntero(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Por favor, introduce un número válido: ");
            scanner.next();
        }
        int num = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer
        return num;
    }
}