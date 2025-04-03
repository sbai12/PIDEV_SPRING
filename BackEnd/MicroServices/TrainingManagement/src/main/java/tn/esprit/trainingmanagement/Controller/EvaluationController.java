package tn.esprit.trainingmanagement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.trainingmanagement.Entity.FctsAv.Evaluation;
import tn.esprit.trainingmanagement.Entity.FctsAv.EvaluationRequest;
import tn.esprit.trainingmanagement.Services.IEvalService;

import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {
    @Autowired
    public final IEvalService evalService;  // Injection de l'interface IEvalService

    // Injection du service EvalServiceImpl via le constructeur
    public EvaluationController(IEvalService evalService) {
        this.evalService = evalService;
    }

    /**
     * Ajouter une évaluation pour une formation
     * @param evaluationRequest Contient les informations sur l'évaluation
     * @return L'évaluation ajoutée
     */
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Evaluation ajouterEvaluation(@RequestBody EvaluationRequest evaluationRequest) {
        // Appel au service pour ajouter l'évaluation
        return evalService.ajouterEvaluation(
                evaluationRequest.idEtudiant,
                evaluationRequest.idFormation,
                evaluationRequest.nombreEtoiles,
                evaluationRequest.commentaire
        );
    }

    /**
     * Calculer la moyenne des étoiles pour une formation spécifique
     * @param idFormation L'ID de la formation
     * @return La moyenne des étoiles pour la formation
     */
    @GetMapping("/moyenne/{idFormation}")
    public double calculerMoyenneEtoiles(@PathVariable Long idFormation) {
        return evalService.calculerMoyenneEtoiles(idFormation);
    }

    /**
     * Récupérer toutes les évaluations pour une formation spécifique
     * @param idFormation L'ID de la formation
     * @return Liste des évaluations pour cette formation
     */
    @GetMapping("/formation/{idFormation}")
    public List<Evaluation> getEvaluationsByFormation(@PathVariable Long idFormation) {
        return evalService.getEvaluationsByFormation(idFormation);
    }}