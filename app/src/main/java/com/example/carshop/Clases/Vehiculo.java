package com.example.carshop.Clases;

import java.util.Date;

public class Vehiculo {

    private String modelo;
    private int precio;
    private String estado;
    private String fecha_publicacion;
    private String categoria;
    private int asientos;
    private String otros;

    public Vehiculo() {
    }

    public Vehiculo(String modelo, int precio, String estado, String fecha_publicacion, String categoria, int asientos, String otros) {
        this.modelo = modelo;
        this.precio = precio;
        this.estado = estado;
        this.fecha_publicacion = fecha_publicacion;
        this.categoria = categoria;
        this.asientos = asientos;
        this.otros = otros;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setFecha_publicacion(String fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getAsientos() {
        return this.asientos;
    }

    public void setAsientos(int asientos) {
        this.asientos = asientos;
    }

    public String getOtros() {
        return this.otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }
}
