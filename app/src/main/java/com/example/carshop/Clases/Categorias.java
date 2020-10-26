package com.example.carshop.Clases;

public class Categorias {

    private int foto;
    private int id_categria;
    private String nombre;

    public Categorias() {
    }

    public Categorias(int foto, int id_categria, String nombre) {
        this.foto = foto;
        this.id_categria = id_categria;
        this.nombre = nombre;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public int getId_categria() {
        return id_categria;
    }

    public void setId_categria(int id_categria) {
        this.id_categria = id_categria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
