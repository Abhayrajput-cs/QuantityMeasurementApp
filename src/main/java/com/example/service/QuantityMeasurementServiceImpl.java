package com.example.service;

import com.example.dto.QuantityInputDTO;
import com.example.entity.QuantityMeasurementEntity;
import com.example.repository.QuantityMeasurementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityMeasurementServiceImpl
        implements IQuantityMeasurementService {

    @Autowired
    private QuantityMeasurementRepository repository;

    // ---------------- COMPARE ----------------

    @Override
    public QuantityMeasurementEntity compare(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        double thisValue = input.getThisQuantityDTO().getValue();
        double thatValue = input.getThatQuantityDTO().getValue();

        String thisUnit = input.getThisQuantityDTO().getUnit();
        String thatUnit = input.getThatQuantityDTO().getUnit();

        entity.setThisValue(thisValue);
        entity.setThisUnit(thisUnit);
        entity.setThisMeasurementType(input.getThisQuantityDTO().getMeasurementType());

        entity.setThatValue(thatValue);
        entity.setThatUnit(thatUnit);
        entity.setThatMeasurementType(input.getThatQuantityDTO().getMeasurementType());

        // unit conversion
        if (thisUnit.equals("FEET") && thatUnit.equals("INCHES")) {
            thisValue = thisValue * 12;
        }

        if (thisUnit.equals("INCHES") && thatUnit.equals("FEET")) {
            thatValue = thatValue * 12;
        }

        boolean result = thisValue == thatValue;

        entity.setOperation("COMPARE");
        entity.setResultString(String.valueOf(result));

        return repository.save(entity);
    }

    // ---------------- CONVERT ----------------

    @Override
    public QuantityMeasurementEntity convert(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setThisValue(input.getThisQuantityDTO().getValue());
        entity.setThisUnit(input.getThisQuantityDTO().getUnit());
        entity.setThisMeasurementType(input.getThisQuantityDTO().getMeasurementType());

        entity.setOperation("CONVERT");

        double value = input.getThisQuantityDTO().getValue();

        if (input.getThisQuantityDTO().getUnit().equals("FEET")) {
            entity.setResultValue(value * 12);
            entity.setResultUnit("INCHES");
        }

        return repository.save(entity);
    }

    // ---------------- ADD ----------------

    @Override
    public QuantityMeasurementEntity add(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setThisValue(input.getThisQuantityDTO().getValue());
        entity.setThisUnit(input.getThisQuantityDTO().getUnit());
        entity.setThisMeasurementType(input.getThisQuantityDTO().getMeasurementType());

        entity.setThatValue(input.getThatQuantityDTO().getValue());
        entity.setThatUnit(input.getThatQuantityDTO().getUnit());
        entity.setThatMeasurementType(input.getThatQuantityDTO().getMeasurementType());

        double thisValue = input.getThisQuantityDTO().getValue();
        double thatValue = input.getThatQuantityDTO().getValue();

        String thisUnit = input.getThisQuantityDTO().getUnit();
        String thatUnit = input.getThatQuantityDTO().getUnit();

        // Convert everything to inches
        if(thisUnit.equals("FEET")){
            thisValue = thisValue * 12;
        }

        if(thatUnit.equals("FEET")){
            thatValue = thatValue * 12;
        }

        double resultInInches = thisValue + thatValue;

        // convert back to feet
        double resultInFeet = resultInInches / 12;

        entity.setResultValue(resultInFeet);
        entity.setResultUnit("FEET");
        entity.setOperation("ADD");

        return repository.save(entity);
    }
    // ---------------- HISTORY ----------------

    @Override
    public List<QuantityMeasurementEntity> getHistoryByOperation(String operation) {
        return repository.findByOperation(operation);
    }

    // ---------------- COUNT ----------------

    @Override
    public long getOperationCount(String operation) {
        return repository.countByOperationAndErrorFalse(operation);
    }
}