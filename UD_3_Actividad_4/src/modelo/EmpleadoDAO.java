package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    // 1. Listar todos los empleados.
    public synchronized List<Empleado> obtenerTodos() {
        List<Empleado> listaEmpleados = new ArrayList<Empleado>();
        try {
            // Obtenemos la conexion a la BBDD
            Connection con = DBConnection.getConnection();
            // String con la consulta a realizar
            String sql = "SELECT * FROM empleados";
            // Statement: objeto que nos permitira realizar consultas SQL
            Statement st = con.createStatement();
            // Ejecutamos la consulta select y nos devuelve un ResulSet
            // Result set es un cursos o iterados
            ResultSet rs = st.executeQuery(sql);

            // Recorremos todos los registros de la consulta
            while (rs.next()) {
                // Creo un empleado y lo añado al arraylist
                Empleado e = mapearEmpleado(rs);
                listaEmpleados.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaEmpleados;
    }

    // 2. Buscar un empleado por ID
    public Empleado buscarPorId(int id) {
        return null;
    }

    // 3. Buiiscar empleados por departamento
    public List<Empleado> buscarPorDepartamento(String departamento) {
        return null;
    }

    private Empleado mapearEmpleado(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        String apellido = rs.getString("apellido");
        String departamento1 = rs.getString("departamento");
        String puesto = rs.getString("puesto");
        double salario = rs.getDouble("salario");
        // Creo un empleado y lo añado al arraylist
        Empleado e = new Empleado(id, nombre, apellido, departamento1, puesto, salario);
        return e;
    }
}
