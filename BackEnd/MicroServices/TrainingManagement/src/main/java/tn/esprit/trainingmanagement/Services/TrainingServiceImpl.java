package tn.esprit.trainingmanagement.Services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.trainingmanagement.Entity.Admin;
import tn.esprit.trainingmanagement.Entity.Training;
import tn.esprit.trainingmanagement.Repository.AdminRepo;
import tn.esprit.trainingmanagement.Repository.StudentRepo;
import tn.esprit.trainingmanagement.Repository.TrainingRepo;

import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor

public class TrainingServiceImpl implements ITrainingService{

    @Autowired
    TrainingRepo trainingRepo;

    @Autowired
    AdminRepo adminRepo;


    @Override
    public Training SaveTraining(Training training) {
        // Vérifier si la formation existe déjà par son nom
        if (trainingRepo.existsByName(training.getName())) {
            throw new RuntimeException("Formation existe déjà.");
        }
        return trainingRepo.save(training);
    }

    // Méthode pour vérifier si la formation existe
    public boolean existsByName(String name) {
        return trainingRepo.existsByName(name);
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

        }).orElseThrow(() -> new RuntimeException("Training not found with ID: " + id));
    }

    @Override
    public void deleteTraining(Long id) {
        trainingRepo.deleteById(id);

    }

    @Override
    public Training getTrainingById(Long id) {
        return trainingRepo.findById(id).orElse(null);  // Renvoie le training ou null s'il n'est pas trouvé
    }

    @Override
    public Training assignProfessorToTraining(Long trainingId, Long professorId) {
        // Fetch the training by ID
        Training training = trainingRepo.findById(trainingId)
                .orElseThrow(() -> new RuntimeException("Training not found"));

        // Fetch the professor by ID
        Admin professor = adminRepo.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor not found"));

        // Check if the professor's specialization matches the training name
        if (professor.getSpecialization().equals(training.getName())) {
            // Assign the professor to the training
            training.setAdmin(professor);
            // Save the updated training with the assigned professor
            return trainingRepo.save(training);
        } else {
            throw new RuntimeException("The professor's specialization does not match the training name");
        }
    }

    @Override
    public List<Training> getEnrolledTrainings(String email) {
        return trainingRepo.findTrainingsEnrolledByStudentEmail(email);
    }
}