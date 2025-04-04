import { Component, OnInit } from '@angular/core';
import { TrainingService } from 'src/app/services/training.service';  
import { Router } from '@angular/router'; // Pour la navigation entre pages
import { EvaluationService } from 'src/app/services/evaluation.service'; // Service pour les évaluations

@Component({

  selector: 'app-events', // Le nom du composant
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {

  
  trainings: any[] = [];  // Tableau pour stocker les formations
  rating: number = 0; // Note de l'évaluation
  comment: string = ''; // Commentaire de l'évaluation
  registeredTrainings: Set<number> = new Set(); // Simule l'inscription de l'étudiant



  constructor(
    private trainingService: TrainingService, 
    private router: Router, 
    private evaluationService: EvaluationService) { }

    ngOnInit(): void {
      // Récupérer la liste des formations depuis l'API
      this.trainingService.getTrainings().subscribe({
        next: (data: any[]) => {
          this.trainings = data;  // Assigner les données des formations à la variable 'trainings'
          this.trainings.forEach(training => {
            this.getAverageRating(training.id); // Récupérer la moyenne des étoiles pour chaque formation
          });
        },
        error: (error: any) => {
          console.error('Error fetching trainings!', error);  // Gérer les erreurs
        },
        complete: () => {
          console.log('Trainings fetching completed');  // Optionnel : pour afficher un message lorsque l'appel est terminé
        }
      });
    }
  
    // Méthode pour récupérer la moyenne des étoiles pour une formation
    getAverageRating(trainingId: number) {
      this.evaluationService.getAverageRating(trainingId).subscribe(average => {
        const training = this.trainings.find(t => t.id === trainingId);
        if (training) {
          training.averageRating = average;
        }
      });
    }
  
    // Méthode pour vérifier si l'étudiant est inscrit à la formation
    isRegistered(training: any): boolean {
      return this.registeredTrainings.has(training.id); // Vérifie si l'ID de la formation est dans la liste des formations enregistrées
    }



    // Méthode pour gérer l'inscription
    onRegister(trainingId: number): void {
      console.log(`Registering for training with ID: ${trainingId}`);
      // Redirige l'utilisateur vers la page de tarification avec l'ID de la formation dans l'URL
      this.router.navigate(['/pricing', { trainingId }]);
      this.registeredTrainings.add(trainingId); // Simuler l'inscription
    }
  
    // Soumettre l'évaluation
    submitRating(trainingId: number) {
      if (this.rating < 1 || this.rating > 5) {
        alert('Please provide a rating between 1 and 5.');
        return;
      }
    
      const evaluation = {
        idFormation: trainingId,
        nombreEtoiles: this.rating,
        commentaire: this.comment
      };
    
      // Soumettre l'évaluation via le service
      this.evaluationService.submitEvaluation(evaluation).subscribe({
        next: (response) => {
          alert('Your review has been submitted!');
          this.ngOnInit(); // Recharger les formations et mettre à jour les évaluations
        },
        error: (error) => {
          alert('There was an error submitting your review.');
        }
      });
    }
  }    