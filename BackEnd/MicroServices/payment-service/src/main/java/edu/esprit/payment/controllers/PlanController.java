package edu.esprit.payment.controllers;

import edu.esprit.payment.dto.PlanRequest;
import edu.esprit.payment.entities.Plan;
import edu.esprit.payment.services.PlanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Plan")
@RequestMapping("/plans")
public class PlanController {

    private final PlanService service;

    public PlanController(PlanService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Plan>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<Plan> create(@RequestBody PlanRequest request) {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Plan> update(@PathVariable String id, @RequestBody PlanRequest request) {
        return new ResponseEntity<>(service.update(id,request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> get(@PathVariable String id) {
        Plan plan = service.getById(id);
        return (plan != null) ? ResponseEntity.ok(plan) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Plan> delete(@PathVariable String id) {
        service.delete(id);
        return  ResponseEntity.accepted().build();
    }
}

