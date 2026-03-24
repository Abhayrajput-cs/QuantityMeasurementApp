package com.example.service;

import com.example.dto.QuantityInputDTO;
import com.example.entity.QuantityMeasurementEntity;
import com.example.repository.QuantityMeasurementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    @Autowired
    private QuantityMeasurementRepository repository;

    // ---------------- BASE ENTITY ----------------

    private QuantityMeasurementEntity createBaseEntity(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setThisValue(input.getThisQuantityDTO().getValue());
        entity.setThisUnit(input.getThisQuantityDTO().getUnit());
        entity.setThisMeasurementType(input.getThisQuantityDTO().getMeasurementType());

        entity.setThatValue(input.getThatQuantityDTO().getValue());
        entity.setThatUnit(input.getThatQuantityDTO().getUnit());
        entity.setThatMeasurementType(input.getThatQuantityDTO().getMeasurementType());

        return entity;
    }

    // ---------------- LENGTH CONVERSION ----------------

    private double convertToInches(double value, String unit) {
        if ("FEET".equals(unit)) return value * 12;
        return value;
    }

    private double convertToFeet(double inches) {
        return inches / 12;
    }

    // ---------------- TEMPERATURE CONVERSION ----------------

    private double convertToCelsius(double value, String unit) {
        if ("FAHRENHEIT".equals(unit)) return (value - 32) * 5 / 9;
        if ("KELVIN".equals(unit)) return value - 273.15;
        return value;
    }

    // ---------------- ADD ----------------

    @Override
    public QuantityMeasurementEntity add(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = createBaseEntity(input);
        String type = input.getThisQuantityDTO().getMeasurementType();

        double thisValue = input.getThisQuantityDTO().getValue();
        double thatValue = input.getThatQuantityDTO().getValue();

        String thisUnit = input.getThisQuantityDTO().getUnit();
        String thatUnit = input.getThatQuantityDTO().getUnit();

        entity.setOperation("ADD");

        if ("LENGTH".equals(type)) {

            thisValue = convertToInches(thisValue, thisUnit);
            thatValue = convertToInches(thatValue, thatUnit);

            double result = thisValue + thatValue;

            entity.setResultValue(convertToFeet(result));
            entity.setResultUnit("FEET");
        }

        if ("TEMPERATURE".equals(type)) {

            thisValue = convertToCelsius(thisValue, thisUnit);
            thatValue = convertToCelsius(thatValue, thatUnit);

            double result = thisValue + thatValue;

            entity.setResultValue(result);
            entity.setResultUnit("CELSIUS");
        }

        return repository.save(entity);
    }

    // ---------------- SUBTRACT ----------------

    @Override
    public QuantityMeasurementEntity subtract(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = createBaseEntity(input);
        String type = input.getThisQuantityDTO().getMeasurementType();

        double thisValue = input.getThisQuantityDTO().getValue();
        double thatValue = input.getThatQuantityDTO().getValue();

        String thisUnit = input.getThisQuantityDTO().getUnit();
        String thatUnit = input.getThatQuantityDTO().getUnit();

        entity.setOperation("SUBTRACT");

        if ("LENGTH".equals(type)) {

            thisValue = convertToInches(thisValue, thisUnit);
            thatValue = convertToInches(thatValue, thatUnit);

            double result = thisValue - thatValue;

            entity.setResultValue(convertToFeet(result));
            entity.setResultUnit("FEET");
        }

        if ("TEMPERATURE".equals(type)) {

            thisValue = convertToCelsius(thisValue, thisUnit);
            thatValue = convertToCelsius(thatValue, thatUnit);

            double result = thisValue - thatValue;

            entity.setResultValue(result);
            entity.setResultUnit("CELSIUS");
        }

        return repository.save(entity);
    }

    // ---------------- MULTIPLY ----------------

    @Override
    public QuantityMeasurementEntity multiply(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = createBaseEntity(input);
        String type = input.getThisQuantityDTO().getMeasurementType();

        double thisValue = input.getThisQuantityDTO().getValue();
        double thatValue = input.getThatQuantityDTO().getValue();

        String thisUnit = input.getThisQuantityDTO().getUnit();
        String thatUnit = input.getThatQuantityDTO().getUnit();

        entity.setOperation("MULTIPLY");

        if ("LENGTH".equals(type)) {

            thisValue = convertToInches(thisValue, thisUnit);
            thatValue = convertToInches(thatValue, thatUnit);

            double result = thisValue * thatValue;

            entity.setResultValue(convertToFeet(result));
            entity.setResultUnit("FEET");
        }

        if ("TEMPERATURE".equals(type)) {

            thisValue = convertToCelsius(thisValue, thisUnit);
            thatValue = convertToCelsius(thatValue, thatUnit);

            double result = thisValue * thatValue;

            entity.setResultValue(result);
            entity.setResultUnit("CELSIUS");
        }

        return repository.save(entity);
    }

    // ---------------- DIVIDE ----------------

    @Override
    public QuantityMeasurementEntity divide(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = createBaseEntity(input);
        String type = input.getThisQuantityDTO().getMeasurementType();

        double thisValue = input.getThisQuantityDTO().getValue();
        double thatValue = input.getThatQuantityDTO().getValue();

        String thisUnit = input.getThisQuantityDTO().getUnit();
        String thatUnit = input.getThatQuantityDTO().getUnit();

        entity.setOperation("DIVIDE");

        if ("LENGTH".equals(type)) {

            thisValue = convertToInches(thisValue, thisUnit);
            thatValue = convertToInches(thatValue, thatUnit);

            double result = thisValue / thatValue;

            entity.setResultValue(result);
            entity.setResultUnit("INCHES");
        }

        if ("TEMPERATURE".equals(type)) {

            thisValue = convertToCelsius(thisValue, thisUnit);
            thatValue = convertToCelsius(thatValue, thatUnit);

            double result = thisValue / thatValue;

            entity.setResultValue(result);
            entity.setResultUnit("CELSIUS");
        }

        return repository.save(entity);
    }

    // ---------------- COMPARE ----------------

    @Override
    public QuantityMeasurementEntity compare(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = createBaseEntity(input);

        double thisValue = convertToInches(
                input.getThisQuantityDTO().getValue(),
                input.getThisQuantityDTO().getUnit());

        double thatValue = convertToInches(
                input.getThatQuantityDTO().getValue(),
                input.getThatQuantityDTO().getUnit());

        boolean result = thisValue == thatValue;

        entity.setOperation("COMPARE");
        entity.setResultValue(result ? 1.0 : 0.0);
        entity.setResultUnit("BOOLEAN");

        return repository.save(entity);
    }

    // ---------------- CONVERT ----------------

    @Override
    public QuantityMeasurementEntity convert(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = createBaseEntity(input);

        double value = input.getThisQuantityDTO().getValue();
        String unit = input.getThisQuantityDTO().getUnit();

        entity.setOperation("CONVERT");

        if ("FEET".equals(unit)) {
            entity.setResultValue(value * 12);
            entity.setResultUnit("INCHES");
        }

        if ("INCHES".equals(unit)) {
            entity.setResultValue(value / 12);
            entity.setResultUnit("FEET");
        }

        return repository.save(entity);
    }

    // ---------------- HISTORY ----------------

    @Override
    public List<QuantityMeasurementEntity> getHistoryByOperation(String operation) {
        return repository.findByOperation(operation);
    }

    @Override
    public long getOperationCount(String operation) {
        return repository.countByOperation(operation);
    }
}