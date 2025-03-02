import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';  
import { Training } from '../../models/training.model';  

@Component({
  selector: 'app-trainings-list',
  templateUrl: './trainings-list.component.html',
  styleUrls: ['./trainings-list.component.css']
})
export class TrainingsListComponent implements OnInit {
  trainings: Training[] = [];  // Liste des formations
  currentTraining: Training | null = null;  // Formation actuellement en cours d'édition

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.getTrainings();  // Charger la liste des formations au démarrage
  }

  // Méthode pour récupérer la liste des formations
  getTrainings() {
    this.http.get<Training[]>('http://localhost:8086/trainings')  // Utilise Training[] pour indiquer que la réponse est un tableau de Training
      .subscribe((response) => {
        this.trainings = response;  // Mettre à jour la liste des formations
      }, error => {
        console.error('Error fetching trainings:', error);
      });
  }


  // Méthode pour supprimer une formation
  deleteTraining(trainingId: number) {
    this.http.delete(`http://localhost:8086/trainings/${trainingId}`)
      .subscribe(response => {
        console.log('Training deleted successfully');
        this.getTrainings();  // Rafraîchir la liste après suppression
      }, error => {
        console.error('Error deleting training:', error);
      });
  }


   // Méthode pour éditer une formation
   editTraining(training: Training) {
    console.log('Edit training:', training);
    this.currentTraining = { ...training };  // Clone la formation pour éviter la mutation directe
  }

  // Méthode pour mettre à jour une formation
  updateTraining() {
    if (this.currentTraining) {
      this.http.put(`http://localhost:8086/trainings/${this.currentTraining.idForm}`, this.currentTraining)
        .subscribe(response => {
          console.log('Training updated successfully');
          this.getTrainings();  // Rafraîchir la liste des formations après mise à jour
          this.currentTraining = null;  // Réinitialiser le formulaire
        }, error => {
          console.error('Error updating training:', error);
        });
    }
  }
}