package com.cristobalbernal.lacasanostraapk.modelos;

import java.io.Serializable;

public class Reservas implements Serializable {
    private int id;
    private String cantidad;
    private String fecha;
    private int usuarioId;
    private String hora;

    public Reservas(String cantidad, String fecha, int usuarioId, String hora) {
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Reservas{" +
                "id=" + id +
                ", cantidad='" + cantidad + '\'' +
                ", fecha='" + fecha + '\'' +
                ", usuarioId='" + usuarioId + '\'' +
                ", hora='" + hora + '\'' +
                '}';
    }
}
