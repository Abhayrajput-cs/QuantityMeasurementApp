package com.example;

public enum WeightUnit {

    KILOGRAM(1.0),          
    GRAM(0.001),            
    POUND(0.453592);        

    private final double ToKg;

    WeightUnit(double conversionFactorToKilogram) {
        this.ToKg = conversionFactorToKilogram;
        
    }

    public double getConversionFactor() {
        return ToKg;
    }

    // Convert value → base unit (kilogram)
    public double convertToBaseUnit(double value) {
        return value * ToKg;
    }

    // Convert from base unit → this unit
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / ToKg;
    }
}