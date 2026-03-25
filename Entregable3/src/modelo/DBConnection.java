package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que gestiona la conexión a la base de datos.
 * <p>
 * Esta clase proporciona un punto centralizado para establecer la conexión
 * con la base de datos MySQL. Utiliza el patrón de fábrica para obtener
 * conexiones mediante {@link #getConnection()}.
 * </p>
 * <p>
 * Proyecto desarrollado para la asignatura de Programación de Servicios y
 * Procesos.
 * </p>
 * 
 * @author Francisco Lopez
 * @version 1.0
 */
public class DBConnection {

    /** URL de conexión a la base de datos MySQL */
    private static final String URL = "jdbc:mysql://localhost:3306/tienda";

    /** Usuario de la base de datos */
    private static final String USER = "root";

    /** Contraseña del usuario de la base de datos */
    private static final String PASS = "root";

    /**
     * Obtiene una conexión a la base de datos.
     * <p>
     * Establece y devuelve una nueva conexión a la base de datos utilizando
     * los parámetros de configuración definidos en las constantes de la clase.
     * </p>
     * 
     * @return Una conexión activa a la base de datos
     * @throws SQLException Si ocurre un error al conectar con la base de datos
     *                      (servidor no disponible, credenciales incorrectas, etc.)
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}