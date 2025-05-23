package tn.esprit.trainingmanagement.Services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.trainingmanagement.Entity.Training;
import tn.esprit.trainingmanagement.Repository.TrainingRepo;

import java.util.List;


@Service
@AllArgsConstructor

public class TrainingServiceImpl implements ITrainingService{

    @Autowired
    TrainingRepo trainingRepo;

    @Override
    public Training SaveTraining(Training training) {
        return trainingRepo.save(training);
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingRepo.findAll();
    }

    @Override
    public Training updateTraining(Long id, Training trainingDetails) {
        return trainingRepo.findById(id).map( training -> {
            training.setName(trainingDetails.getName());
            training.setDescription(trainingDetails.getDescription());
            training.setDuration(trainingDetails.getDuration());
            training.setMaxCapacity(trainingDetails.getMaxCapacity());
            return trainingRepo.save(training);

        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void deleteTraining(Long id) {
        trainingRepo.deleteById(id);

    }
}
