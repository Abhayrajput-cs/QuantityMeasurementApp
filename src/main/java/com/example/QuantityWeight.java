package com.example;

import java.util.Objects;

public final class QuantityWeight {

    private final double value;
    private final WeightUnit unit;

    private static final double EPSILON = 1e-6;

    public QuantityWeight(double value, WeightUnit unit) {

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be finite");

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }

    // Conversion

    public QuantityWeight convertTo(WeightUnit targetUnit) {

        if (targetUnit == null)throw new IllegalArgumentException("Target unit cannot be null");

        double baseValue = unit.convertToBaseUnit(value);
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

        return new QuantityWeight(convertedValue, targetUnit);
    }

    // Addition from UC6 

    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.unit);
    }


    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
        if (other == null)throw new IllegalArgumentException("Other quantity cannot be null");
        if (targetUnit == null)throw new IllegalArgumentException("Target unit cannot be null");

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double sumBase = base1 + base2;

        double finalValue = targetUnit.convertFromBaseUnit(sumBase);

        return new QuantityWeight(finalValue, targetUnit);
    }

    // Equality (Category Safe)

    private double toBase() {
        return unit.convertToBaseUnit(value);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        QuantityWeight other = (QuantityWeight) obj;

        return Math.abs(this.toBase() - other.toBase()) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(toBase());
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}