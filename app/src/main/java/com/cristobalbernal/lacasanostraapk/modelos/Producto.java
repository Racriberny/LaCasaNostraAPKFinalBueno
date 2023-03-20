package com.cristobalbernal.lacasanostraapk.modelos;

public class Producto {
    private int id;
    private String nombre;
    private String precio;
    private String ingredientees;
    private String calorias;
    private int tipoIdtipo;
    private String urlImagen;

    public Producto(int id, String name, String precio, String ingredientes, String calorias, int tipo_idtipo, String url_imagen) {
        this.id = id;
        this.nombre = name;
        this.precio = precio;
        this.ingredientees = ingredientes;
        this.calorias = calorias;
        this.tipoIdtipo = tipo_idtipo;
        this.urlImagen = url_imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getIngredientes() {
        return ingredientees;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientees = ingredientes;
    }

    public String getCalorias() {
        return calorias;
    }

    public void setCalorias(String calorias) {
        this.calorias = calorias;
    }

    public int getTipoIdtipo() {
        return tipoIdtipo;
    }

    public void setTipoIdtipo(int tipoIdtipo) {
        this.tipoIdtipo = tipoIdtipo;
    }

    public String getUrl_imagen() {
        return urlImagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.urlImagen = url_imagen;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", name='" + nombre + '\'' +
                ", precio='" + precio + '\'' +
                ", ingredientes='" + ingredientees + '\'' +
                ", calorias='" + calorias + '\'' +
                ", tipo_idtipo=" + tipoIdtipo +
                ", url_imagen='" + urlImagen + '\'' +
                '}';
    }
}
