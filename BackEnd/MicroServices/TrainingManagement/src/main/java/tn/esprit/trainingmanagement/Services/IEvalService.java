package tn.esprit.trainingmanagement.Services;

import tn.esprit.trainingmanagement.Entity.FctsAv.Evaluation;

import java.util.List;

public interface IEvalService {

    Evaluation ajouterEvaluation(Long idEtudiant, Long idFormation, int nombreEtoiles, String commentaire);

    double calculerMoyenneEtoiles(Long idFormation);

    List<Evaluation> getEvaluationsByFormation(Long idFormation);
}

