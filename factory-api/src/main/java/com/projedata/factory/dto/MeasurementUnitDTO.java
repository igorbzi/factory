package com.projedata.factory.dto;

import com.projedata.factory.enumerators.MeasurementUnit;

public record MeasurementUnitDTO(String name, String description) {

    public MeasurementUnitDTO(MeasurementUnit unit) {
        this(unit.name(), unit.getDescription());
    }
}