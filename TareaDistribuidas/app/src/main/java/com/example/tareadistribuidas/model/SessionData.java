package com.example.tareadistribuidas.model;

public class SessionData {
    static User user;

    public static User getUsuario() {
        return user;
    }

    public static void setUsuario(User user) {
        SessionData.user = user;
    }

}
