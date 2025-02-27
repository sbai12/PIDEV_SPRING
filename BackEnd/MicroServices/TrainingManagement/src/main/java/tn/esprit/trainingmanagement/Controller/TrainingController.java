package tn.esprit.trainingmanagement.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.trainingmanagement.Entity.Training;
import tn.esprit.trainingmanagement.Repository.TrainingRepo;
import tn.esprit.trainingmanagement.Services.TrainingServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/trainings")
public class TrainingController {
    @Autowired
    TrainingServiceImpl serviceImpl;
    @PostMapping
    public Training SaveTraining(@RequestBody Training training){
        return serviceImpl.SaveTraining(training);
    }

    @GetMapping
    public List<Training> getAllTrainings(){
        return serviceImpl.getAllTrainings() ;
    }
    @PutMapping("/{id}")
    public Training updateTraining(@PathVariable Long id, @RequestBody Training trainingDetails){
        return serviceImpl.updateTraining(id, trainingDetails);
    }
    @DeleteMapping("/{id}")
    public void deleteTraining(@PathVariable Long id){
        serviceImpl.deleteTraining(id);
    }

}
