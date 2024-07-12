package com.example;

public class Serie {

    private int idSerie; 
    private String titulo;
    private String temporadas; 
    private String imagen;
    

// Constructor vacío necesario para deserialización de JSON
public Serie() {}

// Constructor con parámetros para inicializar todos los atributos
public Serie(int idSerie, String titulo, String temporadas, String imagen) {
    this.idSerie = idSerie;
    this.titulo = titulo;
    this.temporadas = temporadas;
    this.imagen = imagen;


    
}

    // Método getter para obtener el ID de la serie
    public int getIdSerie() {
        return idSerie;
    }

    // Método setter para establecer el ID de la serie
    public void setIdSerie(int idSerie) {
        this.idSerie = idSerie;
    }


    // Método getter para obtener el título de la serie
    public String getTitulo() {
        return titulo;
    }

    // Método setter para establecer el título de la serie
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public String getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(String temporadas) {
        this.temporadas = temporadas;
    }


    public String getImagen() {
        return imagen;
    }


    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
