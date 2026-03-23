package com.example.controller;

import com.example.dto.*;

import com.example.entity.QuantityMeasurementEntity;
import com.example.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService service;

    @PostMapping("/compare")
    public QuantityMeasurementEntity compare(@RequestBody QuantityInputDTO input) {
        return service.compare(input);
    }

    @PostMapping("/convert")
    public QuantityMeasurementEntity convert(@RequestBody QuantityInputDTO input) {
        return service.convert(input);
    }

    @PostMapping("/add")
    public QuantityMeasurementEntity add(@RequestBody QuantityInputDTO input) {
        return service.add(input);
    }
    @PostMapping("/divide")
    public QuantityMeasurementEntity divide(@RequestBody QuantityInputDTO input) {
        return service.divide(input);
    }
    @PostMapping("/multiply")
    public QuantityMeasurementEntity multiply(@RequestBody QuantityInputDTO input) {
        return service.multiply(input);
    }
    @PostMapping("/subtract")
    public QuantityMeasurementEntity subtract(@RequestBody QuantityInputDTO input) {
        return service.subtract(input);
    }

    @GetMapping("/history/operation/{operation}")
    public List<QuantityMeasurementEntity> getHistory(@PathVariable String operation) {
        return service.getHistoryByOperation(operation);
    }

    @GetMapping("/count/{operation}")
    public long getCount(@PathVariable String operation) {
        return service.getOperationCount(operation);
    }
}