package com.example;

// Importaciones necesarias para la clase Controlador
import com.fasterxml.jackson.databind.ObjectMapper; // Importación de ObjectMapper de Jackson para convertir objetos Java a JSON y viceversa
import javax.servlet.ServletException; // Importación de ServletException para manejar excepciones relacionadas con Servlets
import javax.servlet.annotation.WebServlet; // Importación de WebServlet para la anotación que mapea este servlet a una URL específica
import javax.servlet.http.HttpServlet; // Importación de HttpServlet para extender esta clase y manejar peticiones HTTP
import javax.servlet.http.HttpServletRequest; // Importación de HttpServletRequest para manejar las solicitudes HTTP
import javax.servlet.http.HttpServletResponse; // Importación de HttpServletResponse para manejar las respuestas HTTP
import java.io.IOException; // Importación de IOException para manejar excepciones de entrada/salida
import java.sql.*; // Importación de todas las clases JDBC para operaciones de base de datos
import java.util.ArrayList; // Importación de ArrayList para manejar listas dinámicas de objetos
import java.util.List; // Importación de List para manejar colecciones de elementos

@WebServlet("/series")
public class Controlador extends HttpServlet {

    // Método POST para insertar una nueva película desde una solicitud JSON
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configurar cabeceras CORS
        response.setHeader("Access-Control-Allow-Origin", "*"); // Permitir acceso desde cualquier origen
        response.setHeader("Access-Control-Allow-Methods", "*"); // Métodos permitidos
        response.setHeader("Access-Control-Allow-Headers", "Content-Type"); // Cabeceras permitidas
        Conexion conexion = new Conexion();  // Crear una nueva conexión a la base de datos
        Connection conn = conexion.getConnection();  // Obtener la conexión establecida

        try {
            ObjectMapper mapper = new ObjectMapper();  // Crear un objeto ObjectMapper para convertir JSON a objetos Java
            Serie serie = mapper.readValue(request.getInputStream(), Serie.class);  // Convertir el JSON de la solicitud a un objeto Pelicula
        
            // Consulta SQL para insertar una nueva película en la tabla 'peliculas'
            String query = "INSERT INTO series (titulo, temporadas, imagen) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);  // Indicar que queremos obtener las claves generadas automáticamente
        
            // Establecer los parámetros de la consulta de inserción
            statement.setString(1, serie.getTitulo());
            statement.setString(2, serie.getTemporadas());
            statement.setString(3, serie.getImagen());
        
            statement.executeUpdate();  // Ejecutar la consulta de inserción en la base de datos
        
            // Obtener las claves generadas automáticamente (en este caso, el ID de la película)
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                Long idPeli = rs.getLong(1);  // Obtener el valor del primer campo generado automáticamente (en este caso, el ID)
                
                // Devolver el ID de la película insertada como JSON en la respuesta
                response.setContentType("application/json");  // Establecer el tipo de contenido de la respuesta como JSON
                String json = mapper.writeValueAsString(idPeli);  // Convertir el ID a formato JSON
                response.getWriter().write(json);  // Escribir el JSON en el cuerpo de la respuesta HTTP
            }
            
            response.setStatus(HttpServletResponse.SC_CREATED);  // Configurar el código de estado de la respuesta HTTP como 201 (CREATED)
        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir el error en caso de problemas con la base de datos
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // Configurar el código de estado de la respuesta HTTP como 500 (INTERNAL_SERVER_ERROR)
        } catch (IOException e) {
            e.printStackTrace();  // Imprimir el error en caso de problemas de entrada/salida (por ejemplo, problemas con la solicitud o respuesta HTTP)
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // Configurar el código de estado de la respuesta HTTP como 500 (INTERNAL_SERVER_ERROR)
        } finally {
            conexion.close();  // Cerrar la conexión a la base de datos al finalizar la operación
        }
        
    }

    // Método GET para obtener todas las películas almacenadas en la base de datos y devolverlas como JSON
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configurar cabeceras CORS
        response.setHeader("Access-Control-Allow-Origin", "*"); // Permitir acceso desde cualquier origen
        response.setHeader("Access-Control-Allow-Methods", "*"); // Métodos permitidos
        response.setHeader("Access-Control-Allow-Headers", "Content-Type"); // Cabeceras permitidas
        Conexion conexion = new Conexion();  // Crear una nueva conexión a la base de datos
        Connection conn = conexion.getConnection();  // Obtener la conexión establecida

        try {
            // Consulta SQL para seleccionar todas las películas de la tabla 'peliculas'
            String query = "SELECT * FROM series";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);  // Ejecutar la consulta y obtener los resultados

            List<Serie> series = new ArrayList<>();  // Crear una lista para almacenar objetos Pelicula

            // Iterar sobre cada fila de resultados en el ResultSet
            while (resultSet.next()) {
                // Crear un objeto Pelicula con los datos de cada fila
                Serie serie = new Serie(
                    resultSet.getInt("id_serie"),
                    resultSet.getString("titulo"),  
                    resultSet.getString("temporadas"),
                    resultSet.getString("imagen")
                );
                series.add(serie);  // Agregar el objeto Pelicula a la lista
            }

            ObjectMapper mapper = new ObjectMapper();  // Crear un objeto ObjectMapper para convertir objetos Java a JSON
            String json = mapper.writeValueAsString(series);  // Convertir la lista de películas a formato JSON

            response.setContentType("application/json");  // Establecer el tipo de contenido de la respuesta como JSON
            response.getWriter().write(json);  // Escribir el JSON en el cuerpo de la respuesta HTTP
        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir el error en caso de problemas con la base de datos
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // Configurar el código de estado de la respuesta HTTP como 500 (INTERNAL_SERVER_ERROR)
        } finally {
            conexion.close();  // Cerrar la conexión a la base de datos al finalizar la operación
        }
    }



        // Método DELETE para borrar una película desde una solicitud JSON
        protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // Configurar cabeceras CORS
            response.setHeader("Access-Control-Allow-Origin", "*"); // Permitir acceso desde cualquier origen
            response.setHeader("Access-Control-Allow-Methods", "*"); // Métodos permitidos
            response.setHeader("Access-Control-Allow-Headers", "Content-Type"); // Cabeceras permitidas
            Conexion conexion = new Conexion();  // Crear una nueva conexión a la base de datos
            Connection conn = conexion.getConnection();  // Obtener la conexión establecida

            try {

                // Consulta SQL para borrar elementos en la tabla 'peliculas'
                ObjectMapper mapper = new ObjectMapper();
                var idToDelete = mapper.readValue(request.getInputStream(), Integer.class);

                System.out.println(idToDelete);
                
                String query = "delete from series where id_serie =?";
                PreparedStatement statement = conn.prepareStatement(query); 

                // Establecer los parámetros de la consulta de borrado
                statement.setInt(1, idToDelete);

                statement.executeUpdate();  // Ejecutar la consulta de borrado en la base de datos

                response.setStatus(HttpServletResponse.SC_OK);  // Configurar el código de estado de la respuesta HTTP como 200 (OK)
            } catch (SQLException e) {
                e.printStackTrace();  // Imprimir el error en caso de problemas con la base de datos
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // Configurar el código de estado de la respuesta HTTP como 500 (INTERNAL_SERVER_ERROR)
            } catch (IOException e) {
                e.printStackTrace();  // Imprimir el error en caso de problemas de entrada/salida (por ejemplo, problemas con la solicitud o respuesta HTTP)
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  // Configurar el código de estado de la respuesta HTTP como 500 (INTERNAL_SERVER_ERROR)
            } finally {
                conexion.close();  // Cerrar la conexión a la base de datos al finalizar la operación
            }
            
        }

    
}
