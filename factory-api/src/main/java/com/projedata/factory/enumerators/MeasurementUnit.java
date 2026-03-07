package com.projedata.factory.enumerators;

public enum MeasurementUnit {

    G("Gram"),
    L("Liter"),
    UN("Unit"),
    M("Meter"),
    M2("Square Meter");

    private final String description;

    MeasurementUnit(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}