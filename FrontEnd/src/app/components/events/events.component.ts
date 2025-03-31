import { Component, OnInit } from '@angular/core';
import { TrainingService } from 'src/app/services/training.service';  
import { Router } from '@angular/router'; // Pour la navigation entre pages
import { Training } from 'src/app/models/training.model';
@Component({
  selector: 'app-events', // Le nom du composant
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {

  
  trainings: any[] = [];  // Tableau pour stocker les formations

  constructor(private trainingService: TrainingService, private router: Router) { }

  ngOnInit(): void {
    // Récupérer la liste des formations depuis l'API
    this.trainingService.getTrainings().subscribe({
      next: (data: any[]) => {
        this.trainings = data;  // Assigner les données des formations à la variable 'trainings'
      },
      error: (error: any) => {
        console.error('Error fetching trainings!', error);  // Gérer les erreurs
      },
      complete: () => {
        console.log('Trainings fetching completed');  // Optionnel : pour afficher un message lorsque l'appel est terminé
      }
    });
  }

  // Méthode pour gérer l'inscription
  onRegister(trainingId: number): void {
    console.log(`Registering for training with ID: ${trainingId}`);
    // Redirige l'utilisateur vers la page de tarification avec l'ID de la formation dans l'URL
    this.router.navigate(['/pricing', { trainingId }]);
  }
}