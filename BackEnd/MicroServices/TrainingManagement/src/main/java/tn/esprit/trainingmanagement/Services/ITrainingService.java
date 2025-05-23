package tn.esprit.trainingmanagement.Services;

import tn.esprit.trainingmanagement.Entity.Training;

import java.util.List;

public interface ITrainingService {

    Training SaveTraining(Training training);
    List<Training> getAllTrainings();
    Training updateTraining(Long id, Training trainingDetails);
    void deleteTraining(Long id);

}
