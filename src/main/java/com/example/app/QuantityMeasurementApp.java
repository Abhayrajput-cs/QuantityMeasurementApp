package com.example.app;

import com.example.controller.QuantityMeasurementController;
import com.example.repository.IQuantityMeasurementRepository;
import com.example.repository.QuantityMeasurementDatabaseRepository;
import com.example.service.IQuantityMeasurementService;
import com.example.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

    	IQuantityMeasurementRepository repository =
    	        new QuantityMeasurementDatabaseRepository();

    	IQuantityMeasurementService service =
    	        new QuantityMeasurementServiceImpl(repository);

    	QuantityMeasurementController controller =
    	        new QuantityMeasurementController(service);

    	controller.run();

    }
}