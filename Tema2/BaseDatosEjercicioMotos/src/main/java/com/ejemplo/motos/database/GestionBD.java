package com.ejemplo.motos.database;

import com.ejemplo.motos.Model.Moto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase GestionBD - Gestiona todas las operaciones CRUD (Create, Read, Update, Delete) con la base de datos
 * Implementa el patrón DAO (Data Access Object) para separar la lógica de acceso a datos
 * Utiliza PreparedStatement para prevenir inyección SQL
 */
public class GestionBD {

    /** Lista temporal para almacenar las motos recuperadas de la base de datos */
    private List<Moto> listaMotos = new ArrayList<>();

    /**
     * Inserta una nueva moto en la base de datos
     * Utiliza PreparedStatement para evitar inyección SQL
     * 
     * @param m Objeto Moto con los datos a insertar (marca, modelo, cilindrada)
     */
    public void insertarMoto(Moto m){
        // Paso 1: Definir la consulta SQL con parámetros (?) para evitar inyección SQL
        // El ID no se incluye porque es AUTO_INCREMENT en la base de datos
        String sql = "INSERT INTO motos (marca, modelo, cilindrada) VALUES (?, ?, ?)";
        
        // Paso 2: Obtener una conexión a la base de datos
        Connection conn = ConexionBD.conectar();

        // Paso 3: Ejecutar la consulta usando try-with-resources para cerrar automáticamente
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            // Sustituir los parámetros (?) por los valores reales
            stmt.setString(1, m.getMarca());      // Primer ? = marca
            stmt.setString(2, m.getModelo());     // Segundo ? = modelo
            stmt.setString(3, m.getCilindrada()); // Tercer ? = cilindrada
            
            // Ejecutar la inserción (executeUpdate devuelve el número de filas afectadas)
            stmt.executeUpdate();
            System.out.println("Insertada la moto en la base de datos");
        }
        catch (SQLException e){
            // Capturar cualquier error SQL (conexión, sintaxis, etc.)
            System.out.println("Error al insertar en la base de datos");
        }
        finally {
            // Paso 4: SIEMPRE cerrar la conexión para liberar recursos
            ConexionBD.desconectar(conn);
        }
    }

    /**
     * Lee todas las motos de la base de datos
     * 
     * @return Lista con todas las motos almacenadas en la BD
     */
    public List<Moto> leerMotos(){
        // Paso 1: Limpiar la lista para evitar duplicados en consultas sucesivas
        listaMotos.clear();
        
        // Paso 2: Definir la consulta SQL (SELECT * trae todas las columnas)
        String sql = "SELECT * FROM motos";
        
        // Paso 3: Obtener conexión a la base de datos
        Connection conn = ConexionBD.conectar();

        // Paso 4: Ejecutar la consulta
        try (Statement st = conn.createStatement()){
            // executeQuery devuelve un ResultSet con los resultados
            ResultSet rs = st.executeQuery(sql);

            // Paso 5: Iterar por cada fila del resultado
            while (rs.next()){
                // Crear un objeto Moto con los datos de la fila actual
                Moto m = new Moto(
                        rs.getInt("id"),           // Obtener columna 'id'
                        rs.getString("marca"),     // Obtener columna 'marca'
                        rs.getString("modelo"),    // Obtener columna 'modelo'
                        rs.getString("cilindrada") // Obtener columna 'cilindrada'
                );
                // Añadir la moto a la lista
                listaMotos.add(m);
            }
        }
        catch (SQLException e){
            System.out.println("Error al leer de la base de datos");
        }
        finally {
            // Cerrar la conexión
            ConexionBD.desconectar(conn);
        }
        // Paso 6: Devolver la lista con todas las motos
        return listaMotos;
    }

    /**
     * Lee las motos de una marca específica
     * Utiliza PreparedStatement para filtrar de forma segura
     * 
     * @param marca La marca de motos a buscar (ej: "Ducati", "Yamaha")
     * @return Lista con las motos que coinciden con la marca especificada
     */
    public List<Moto> leerMotosPorMarca(String marca){
        // Paso 1: Limpiar la lista
        listaMotos.clear();
        
        // Paso 2: Consulta SQL con filtro WHERE y parámetro (?)
        String sql = "SELECT * FROM motos WHERE marca = ?";
        
        // Paso 3: Obtener conexión
        Connection conn = ConexionBD.conectar();
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            // Paso 4: Establecer el valor del parámetro de búsqueda
            stmt.setString(1, marca);
            
            // Paso 5: Ejecutar la consulta
            ResultSet rs = stmt.executeQuery();
            
            // Paso 6: Procesar cada resultado
            while (rs.next()){
                Moto moto = new Moto (
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("cilindrada")
                );
                listaMotos.add(moto);
            }

        }
        catch (SQLException e){
            System.out.println("Error al buscar las motos por marcas");
        }
        finally {
            ConexionBD.desconectar(conn);
        }
        return listaMotos;
    }

    /**
     * Elimina una moto de la base de datos según su ID
     * 
     * @param id Identificador único de la moto a eliminar
     */
    public void eliminarMoto(int id){
        // Paso 1: Consulta SQL DELETE con parámetro
        String sql = "DELETE FROM motos WHERE id = ?";
        
        // Paso 2: Obtener conexión
        Connection conn = ConexionBD.conectar();

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            // Paso 3: Establecer el ID de la moto a eliminar
            stmt.setInt(1, id);
            
            // Paso 4: Ejecutar la eliminación
            // executeUpdate devuelve el número de filas eliminadas
            int filas = stmt.executeUpdate();
            
            // Paso 5: Verificar si se eliminó alguna fila
            if (filas > 0) 
                System.out.println("Moto eliminada correctamente");
            else 
                System.out.println("No se encontró la moto a eliminar");
        }
        catch (SQLException e){
            System.out.println("Error al eliminar la moto");
        }
        finally {
            ConexionBD.desconectar(conn);
        }
    }
    
    /**
     * Getter para obtener la lista de motos
     * 
     * @return La lista actual de motos en memoria
     */
    public List<Moto> getListaMotos(){
        return listaMotos;
    }
}
