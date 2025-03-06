package tn.esprit.trainingmanagement.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.trainingmanagement.Services.EvaluationServiceImpl;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {
    @Autowired
    EvaluationServiceImpl evaluationService;

    // Ajouter une évaluation
    @PostMapping("/add")
    public ResponseEntity<String> ajouterEvaluation(
            @RequestParam Long studentId,
            @RequestParam Long trainingId,
            @RequestParam int stars,
            @RequestParam String commentaire) {

        try {
            // Appeler le service pour ajouter l'évaluation
            evaluationService.ajouterEvaluation(studentId, trainingId, stars, commentaire);
            return ResponseEntity.ok("Évaluation ajoutée avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Calculer la moyenne des étoiles pour une formation
    @GetMapping("/average/{trainingId}")
    public ResponseEntity<Double> calculerMoyenneEtoiles(@PathVariable Long trainingId) {
        double moyenne = evaluationService.calculerMoyenneEtoiles(trainingId);
        return ResponseEntity.ok(moyenne);
    }
}
