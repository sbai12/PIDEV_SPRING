package tn.esprit.trainingmanagement.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.trainingmanagement.Entity.Training;
import tn.esprit.trainingmanagement.Repository.TrainingRepo;
import tn.esprit.trainingmanagement.Services.TrainingServiceImpl;

import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trainings")
public class TrainingController {
    @Autowired
    TrainingServiceImpl serviceImpl;
    @GetMapping("/exists/{name}")
    public ResponseEntity<Map<String, Boolean>> checkIfTrainingExists(@PathVariable String name) {
        boolean exists = serviceImpl.existsByName(name);
        return ResponseEntity.ok(Map.of("exists", exists));
    }
    // Récupère toutes les formations
    @GetMapping
    public List<Training> getAllTrainings() {
        return serviceImpl.getAllTrainings();  // Retourne la liste de toutes les formations
    }


    @PostMapping
    public Training SaveTraining(@RequestBody Training training) {
        return serviceImpl.SaveTraining(training);
    }
    @PutMapping("/{id}")
    public Training updateTraining(@PathVariable Long id, @RequestBody Training trainingDetails){
        return serviceImpl.updateTraining(id, trainingDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteTraining(@PathVariable Long id){
        serviceImpl.deleteTraining(id);
    }}

