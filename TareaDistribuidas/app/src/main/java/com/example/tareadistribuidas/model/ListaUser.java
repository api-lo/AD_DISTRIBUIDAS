package com.example.tareadistribuidas.model;

public class ListaUser {
    String username="";
    String tipo="";

    public ListaUser(String username, String tipo) {
        this.username = username;
        this.tipo = tipo;
    }

    public ListaUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
