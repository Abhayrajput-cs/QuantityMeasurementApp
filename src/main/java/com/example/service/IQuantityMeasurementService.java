package com.example.service;

import com.example.dto.*;
import com.example.entity.QuantityMeasurementEntity;
import com.example.model.*;

import java.util.List;

public interface IQuantityMeasurementService {


    QuantityMeasurementEntity convert(QuantityInputDTO input);

    QuantityMeasurementEntity add(QuantityInputDTO input);

    List<QuantityMeasurementEntity> getHistoryByOperation(String operation);

    long getOperationCount(String operation);

	QuantityMeasurementEntity compare(QuantityInputDTO input);

	QuantityMeasurementEntity subtract(QuantityInputDTO input);

	QuantityMeasurementEntity multiply(QuantityInputDTO input);

	QuantityMeasurementEntity divide(QuantityInputDTO input);

	
}