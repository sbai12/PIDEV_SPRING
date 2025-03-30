package tn.esprit.trainingmanagement.Services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.trainingmanagement.Entity.Student;
import tn.esprit.trainingmanagement.Entity.Training;
import tn.esprit.trainingmanagement.Repository.StudentRepo;
import tn.esprit.trainingmanagement.Repository.TrainingRepo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor

public class TrainingServiceImpl implements ITrainingService{

    @Autowired
    TrainingRepo trainingRepo;
    @Autowired
    EmailService emailService;

    @Autowired
    StudentRepo studentRepo;

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




    public String genererLienReunion(Long idForm, LocalDateTime dateSession) {
        Training training = trainingRepo.findById(idForm)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));
        String baseLink = "https://meet.example.com/join?meeting=" + idForm + "-" + System.currentTimeMillis();
        training.setMeetingLink(baseLink);
        trainingRepo.save(training);
        return baseLink;
    }
    }