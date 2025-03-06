package tn.esprit.trainingmanagement.Services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.mockito.MockitoAnnotations;

import org.junit.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.trainingmanagement.Entity.Evaluation;
import tn.esprit.trainingmanagement.Entity.Student;
import tn.esprit.trainingmanagement.Entity.Training;
import tn.esprit.trainingmanagement.Repository.EvaluationRepo;
import tn.esprit.trainingmanagement.Repository.StudentRepo;
import tn.esprit.trainingmanagement.Repository.TrainingRepo;
import static org.mockito.Mockito.any;


import java.util.Optional;

import static org.mockito.Mockito.when;


public class testAjouterEvaluation {
    @Mock
    StudentRepo studentRepo;

    @Mock
    TrainingRepo trainingRepo;

    @Mock
    EvaluationRepo evaluationRepo;

    @InjectMocks
    EvaluationServiceImpl evaluationService;  // Injecter les mocks

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Ouvrir les mocks
    }

    @Test
    public void testAjouterEvaluation() {
        // Créer un étudiant mocké
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("John");
        student.setLastName("Doe");

        // Créer une formation mockée
        Training training = new Training();
        training.setId(1L);
        training.setName("Java Basics");

        // Simuler le comportement des repos
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(trainingRepo.findById(1L)).thenReturn(Optional.of(training));
        when(evaluationRepo.findByStudentIdAndTrainingId(1L, 1L)).thenReturn(null);  // L'étudiant n'a pas encore évalué cette formation

        // Appeler la méthode à tester
        evaluationService.ajouterEvaluation(1L, 1L, 4, "Great course!");

        // Vérifier que la méthode save a été appelée une fois
        verify(evaluationRepo, times(1)).save(any(Evaluation.class));
    }
}