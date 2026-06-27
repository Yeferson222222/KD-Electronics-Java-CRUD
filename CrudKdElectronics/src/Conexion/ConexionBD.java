package Conexion;

import Excepciones.ConexionException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3307/kd_electronics";
    private static final String USER = "root";
    private static final String PASSWORD = "T3sting123*";

    public static Connection conectar() throws ConexionException {

        try {

            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("CONEXION EXITOSA");
            return con;

        } catch (SQLException e) {

            throw new ConexionException("Error al conectar con la base de datos: " + e.getMessage());

        }
    }

}