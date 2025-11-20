package com.basedatos.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase de utilidad que centraliza la lógica de conexión y desconexión
 * a la base de datos MySQL.
 */
public class ConexionBD { // Examen

    // URL de conexión al servidor MySQL sin seleccionar base de datos.
    private static final String URL_ROOT = "jdbc:mysql://localhost:8889/";  // Solo dirección
    private static final String USER= "root";
    private static final String PASS = "root";

    // Es buena práctica mantener las credenciales separadas de la URL de conexión.
    private static final String URL_DB   = "jdbc:mysql://localhost:8889/uax1";


    /**
     * Método estático que abre una conexión con la base de datos.
     * Primero se conecta al servidor para crear la base de datos si no existe,
     * luego se conecta a la base de datos específica.
     *
     * @return un objeto Connection abierto y listo para usar.
     * @throws RuntimeException si ocurre un problema al conectar o crear la base de datos.
     */
    public static Connection conectar() { // obligatorio
        try {
            // Conectar al servidor MySQL sin seleccionar base de datos.
            Connection connRoot = DriverManager.getConnection(URL_ROOT, USER, PASS);
            try (Statement stmt = connRoot.createStatement()) {
                // Crear la base de datos "uax" si no existe.
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS uax1");
            }
            connRoot.close();
            // Conectar a la base de datos "uax".
            return DriverManager.getConnection(URL_DB, USER, PASS);
        } catch (SQLException e) {
            // Error al intentar conectarnos o crear la base de datos.
            throw new RuntimeException("Error al conectar con la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Cierra de forma segura una conexión abierta.
     *
     * @param conn conexión que queremos cerrar (puede ser null).
     */
    public static void desconectar(Connection conn) {
        try {
            // 1️⃣ Comprobamos que la conexión no sea null y que siga abierta.
            if (conn != null && !conn.isClosed()) {
                // 2️⃣ Cerramos la conexión física contra la BD.
                conn.close();
                System.out.println("Desconectado correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
