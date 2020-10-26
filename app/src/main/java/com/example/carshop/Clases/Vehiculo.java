package com.example.carshop.Clases;

public class Vehiculo {

    private int id_vehiculo;
    private String modelo;
    private int precio;
    private String estado;
    private String fecha_publicacion;
    private String categoria;
    private int asientos;
    private String tipo;
    private String cantidad;

    public Vehiculo() {
    }

    public Vehiculo(int id_vehiculo, String modelo, int precio, String estado, String fecha_publicacion, String categoria, int asientos, String tipo, String cantidad) {
        this.id_vehiculo = id_vehiculo;
        this.modelo = modelo;
        this.precio = precio;
        this.estado = estado;
        this.fecha_publicacion = fecha_publicacion;
        this.categoria = categoria;
        this.asientos = asientos;
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    public int getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(int id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
