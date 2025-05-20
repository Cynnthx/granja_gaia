package com.example.granja_gaia.enums; // Ajusta el paquete según tu ubicación

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter(autoApply = true)
public class RolConverter implements AttributeConverter<Rol, String> {

    @Override
    public String convertToDatabaseColumn(Rol rol) {
        return rol != null ? rol.name().toLowerCase() : null;
    }

    @Override
    public Rol convertToEntityAttribute(String dbData) {
        return dbData != null ? Rol.valueOf(dbData.toLowerCase()) : null;
    }
}