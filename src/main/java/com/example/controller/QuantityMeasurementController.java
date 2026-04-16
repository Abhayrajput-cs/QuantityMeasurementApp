package com.example.controller;

import com.example.dto.QuantityInputDTO;
import com.example.entity.QuantityMeasurementEntity;
import com.example.service.IQuantityMeasurementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService service;

    @PostMapping("/compare")
    public ResponseEntity<QuantityMeasurementEntity> compare(@RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(service.compare(input));
    }

    @PostMapping("/convert")
    public ResponseEntity<QuantityMeasurementEntity> convert(@RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(service.convert(input));
    }

    @PostMapping("/add")
    public ResponseEntity<QuantityMeasurementEntity> add(@RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(service.add(input));
    }

    @PostMapping("/divide")
    public ResponseEntity<QuantityMeasurementEntity> divide(@RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(service.divide(input));
    }

    @PostMapping("/multiply")
    public ResponseEntity<QuantityMeasurementEntity> multiply(@RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(service.multiply(input));
    }

    @PostMapping("/subtract")
    public ResponseEntity<QuantityMeasurementEntity> subtract(@RequestBody QuantityInputDTO input) {
        return ResponseEntity.ok(service.subtract(input));
    }

    @GetMapping("/history/operation/{operation}")
    public ResponseEntity<List<QuantityMeasurementEntity>> getHistory(@PathVariable String operation) {
        return ResponseEntity.ok(service.getHistoryByOperation(operation));
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuantityMeasurementEntity>> getAll(
            @RequestParam(required = false) String operation,
            @RequestParam(required = false) String type) {

        return ResponseEntity.ok(service.getFiltered(operation, type));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuantityMeasurementEntity> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        System.out.println("DELETE REQUEST ID: " + id);

        service.delete(id);

        return ResponseEntity.ok(Map.of("message", "deleted"));
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<?> deleteAll() {

        service.deleteAllByUser();

        return ResponseEntity.ok(Map.of("message", "All history deleted successfully"));
    }

    @DeleteMapping("/delete-filtered")
    public ResponseEntity<?> deleteFiltered(
            @RequestParam(required = false) String operation,
            @RequestParam(required = false) String type) {

        service.deleteFiltered(operation, type);

        return ResponseEntity.ok(Map.of("message", "Filtered records deleted successfully"));
    }
}
