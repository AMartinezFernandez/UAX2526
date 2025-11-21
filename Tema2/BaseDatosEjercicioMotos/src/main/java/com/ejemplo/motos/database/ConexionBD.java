package com.ejemplo.motos.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase ConexionBD - Gestiona las conexiones a la base de datos MySQL
 * Implementa el patrón de métodos estáticos para facilitar el acceso desde cualquier parte
 * Utiliza JDBC (Java Database Connectivity) para conectarse a MySQL
 */
public class ConexionBD {
    // CONSTANTES DE CONFIGURACIÓN DE LA BASE DE DATOS
    
    /** URL de conexión JDBC - Especifica el servidor (localhost), puerto (8889) y nombre de BD (uax) */
    private static final String URL = "jdbc:mysql://localhost:8889/uax";
    
    /** Usuario de la base de datos - En MAMP el usuario por defecto es 'root' */
    private static final String USER = "root";
    
    /** Contraseña del usuario - En MAMP la contraseña por defecto es 'root' */
    private static final String PASS = "root";

    /**
     * Método para establecer una conexión con la base de datos
     * Este método es estático para poder llamarlo sin crear una instancia de la clase
     * 
     * @return Connection objeto de conexión a la BD, o null si hay un error
     */
    public static Connection conectar(){
        try {
            // Paso 1: Cargar el driver JDBC de MySQL en memoria
            // Esto es necesario para que Java sepa cómo comunicarse con MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Paso 2: Establecer la conexión usando la URL, usuario y contraseña
            // DriverManager gestiona los drivers JDBC y crea la conexión
            return DriverManager.getConnection(URL, USER, PASS);
        }
        catch (SQLException | ClassNotFoundException e){
            // Si hay un error de SQL o no se encuentra el driver, mostramos información
            System.out.println("Error al conectar con la base de datos");
            System.out.println("Detalles del error: " + e.getMessage());
            e.printStackTrace(); // Muestra la traza completa del error para debugging
            return null; // Retornamos null para indicar que la conexión falló
        }
    }

    /**
     * Método para cerrar una conexión a la base de datos
     * Es importante cerrar las conexiones para liberar recursos del servidor
     * 
     * @param conn La conexión que se desea cerrar
     */
    public static void desconectar (Connection conn){
        try {
            // Verificamos que la conexión existe y está abierta antes de cerrarla
            if(conn != null && !conn.isClosed()){
                // Cerramos la conexión
                conn.close();
                System.out.println("Cerrada la conexión correctamente");
            }
        } catch (SQLException e) {
            // Si hay un error al cerrar, lo mostramos
            System.out.println("Error al cerrar la conexión");
        }
    }
}
