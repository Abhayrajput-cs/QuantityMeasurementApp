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

//        boolean result = thisValue == thatValue;

        entity.setOperation("COMPARE");
        

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
    @Override
    public QuantityMeasurementEntity divide(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = createBaseEntity(input);
        String type = input.getThisQuantityDTO().getMeasurementType();

        double thisValue = input.getThisQuantityDTO().getValue();
        double thatValue = input.getThatQuantityDTO().getValue();

        String thisUnit = input.getThisQuantityDTO().getUnit();
        String thatUnit = input.getThatQuantityDTO().getUnit();

        if(type.equals("LENGTH")) {

            thisValue = convertToInches(thisValue, thisUnit);
            thatValue = convertToInches(thatValue, thatUnit);

            double result = thisValue / thatValue;

            entity.setResultValue(result / 12);
            entity.setResultUnit("FEET");
        }

        if(type.equals("TEMPERATURE")) {

            thisValue = convertToCelsius(thisValue, thisUnit);
            thatValue = convertToCelsius(thatValue, thatUnit);

            double result = thisValue / thatValue;

            entity.setResultValue(result);
            entity.setResultUnit("CELSIUS");
        }

        entity.setOperation("DIVIDE");

        return repository.save(entity);
    }
    private double convertToInches(double value, String unit){

        if(unit.equals("FEET")){
            return value * 12;
        }

        return value;
    }
 
    private QuantityMeasurementEntity createBaseEntity(QuantityInputDTO input){

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setThisValue(input.getThisQuantityDTO().getValue());
        entity.setThisUnit(input.getThisQuantityDTO().getUnit());
        entity.setThisMeasurementType(input.getThisQuantityDTO().getMeasurementType());

        entity.setThatValue(input.getThatQuantityDTO().getValue());
        entity.setThatUnit(input.getThatQuantityDTO().getUnit());
        entity.setThatMeasurementType(input.getThatQuantityDTO().getMeasurementType());

        return entity;
    }

    // ---------------- ADD ----------------

    @Override
    public QuantityMeasurementEntity add(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        double thisValue = input.getThisQuantityDTO().getValue();
        double thatValue = input.getThatQuantityDTO().getValue();

        String thisUnit = input.getThisQuantityDTO().getUnit();
        String thatUnit = input.getThatQuantityDTO().getUnit();

        String measurementType = input.getThisQuantityDTO().getMeasurementType();

        entity.setThisValue(thisValue);
        entity.setThisUnit(thisUnit);
        entity.setThisMeasurementType(measurementType);

        entity.setThatValue(thatValue);
        entity.setThatUnit(thatUnit);
        entity.setThatMeasurementType(input.getThatQuantityDTO().getMeasurementType());

        entity.setOperation("ADD");

        // ---------------- LENGTH ----------------
        if(measurementType.equals("LENGTH")){

            if(thisUnit.equals("FEET")){
                thisValue = thisValue * 12;
            }

            if(thatUnit.equals("FEET")){
                thatValue = thatValue * 12;
            }

            double resultInInches = thisValue + thatValue;
            double resultInFeet = resultInInches / 12;

            entity.setResultValue(resultInFeet);
            entity.setResultUnit("FEET");
        }

        // ---------------- TEMPERATURE ----------------
        if(measurementType.equals("TEMPERATURE")){

            thisValue = convertToCelsius(thisValue, thisUnit);
            thatValue = convertToCelsius(thatValue, thatUnit);

            double result = thisValue + thatValue;

            entity.setResultValue(result);
            entity.setResultUnit("CELSIUS");
        }

        return repository.save(entity);
    }
    
    @Override
    public QuantityMeasurementEntity subtract(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = createBaseEntity(input);
        String type = input.getThisQuantityDTO().getMeasurementType();

        double thisValue = input.getThisQuantityDTO().getValue();
        double thatValue = input.getThatQuantityDTO().getValue();

        String thisUnit = input.getThisQuantityDTO().getUnit();
        String thatUnit = input.getThatQuantityDTO().getUnit();

        if(type.equals("LENGTH")) {

            thisValue = convertToInches(thisValue, thisUnit);
            thatValue = convertToInches(thatValue, thatUnit);

            double result = thisValue - thatValue;

            entity.setResultValue(result / 12);
            entity.setResultUnit("FEET");
        }

        if(type.equals("TEMPERATURE")) {

            thisValue = convertToCelsius(thisValue, thisUnit);
            thatValue = convertToCelsius(thatValue, thatUnit);

            double result = thisValue - thatValue;

            entity.setResultValue(result);
            entity.setResultUnit("CELSIUS");
        }

        entity.setOperation("SUBTRACT");

        return repository.save(entity);
    }
    @Override
    public QuantityMeasurementEntity multiply(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = createBaseEntity(input);
        String type = input.getThisQuantityDTO().getMeasurementType();

        double thisValue = input.getThisQuantityDTO().getValue();
        double thatValue = input.getThatQuantityDTO().getValue();

        String thisUnit = input.getThisQuantityDTO().getUnit();
        String thatUnit = input.getThatQuantityDTO().getUnit();

        if(type.equals("LENGTH")) {

            thisValue = convertToInches(thisValue, thisUnit);
            thatValue = convertToInches(thatValue, thatUnit);

            double result = thisValue * thatValue;

            entity.setResultValue(result / 12);
            entity.setResultUnit("FEET");
        }

        if(type.equals("TEMPERATURE")) {

            thisValue = convertToCelsius(thisValue, thisUnit);
            thatValue = convertToCelsius(thatValue, thatUnit);

            double result = thisValue * thatValue;

            entity.setResultValue(result);
            entity.setResultUnit("CELSIUS");
        }

        entity.setOperation("MULTIPLY");

        return repository.save(entity);
    }
    // ---------------- HISTORY ----------------

    @Override
    public List<QuantityMeasurementEntity> getHistoryByOperation(String operation) {
        return repository.findByOperation(operation);
    }

	@Override
	public long getOperationCount(String operation) {
		// TODO Auto-generated method stub
		return operation.length();
	}
	private double convertToCelsius(double value, String unit){

	    if(unit.equals("FAHRENHEIT")){
	        return (value - 32) * 5 / 9;
	    }

	    if(unit.equals("KELVIN")){
	        return value - 273.15;
	    }

	    return value; 
	}

    
}