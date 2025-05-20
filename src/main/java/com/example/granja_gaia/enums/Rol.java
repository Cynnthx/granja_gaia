package com.example.granja_gaia.enums;

public enum Rol {
    cliente, admin;


    // Método para convertir desde String (opcional pero útil)
    public static Rol fromString(String value) {
        return valueOf(value.toLowerCase());
    }
}
