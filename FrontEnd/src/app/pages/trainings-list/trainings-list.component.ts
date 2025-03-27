import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';  
import { Training } from '../../models/training.model';  
import { TrainingService } from '../../services/training.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-trainings-list',
  templateUrl: './trainings-list.component.html',
  styleUrls: ['./trainings-list.component.css']
})
export class TrainingsListComponent implements OnInit {
  trainings: Training[] = []; // Liste des formations
  currentTraining: Training | null = null; // Formation actuellement en cours d'édition
  selectedTrainingId: number | null = null; // ID de la formation sélectionnée
  dateSession: string = ''; // Date de la session (format ISO, ex: "2025-03-10T10:00:00")

  constructor(
    private http: HttpClient,
    private trainingService: TrainingService // Injection du service TrainingService
  ) {}

  ngOnInit() {
    this.getTrainings(); // Charger la liste des formations au démarrage
  }

  // Méthode pour récupérer la liste des formations
  getTrainings() {
    this.trainingService.getAllTrainings().subscribe(
      (response) => {
        this.trainings = response; // Mettre à jour la liste des formations
      },
      (error) => {
        console.error('Error fetching trainings:', error);
      }
    );
  }

  // Méthode pour supprimer une formation
  deleteTraining(trainingId: number) {
    this.http.delete(`http://localhost:8086/trainings/${trainingId}`).subscribe(
      (response) => {
        console.log('Training deleted successfully');
        this.getTrainings(); // Rafraîchir la liste après suppression
      },
      (error) => {
        console.error('Error deleting training:', error);
      }
    );
  }

  // Méthode pour éditer une formation
  editTraining(training: Training) {
    console.log('Edit training:', training);
    this.currentTraining = { ...training }; // Clone la formation pour éviter la mutation directe
  }

  // Méthode pour mettre à jour une formation
  updateTraining() {
    if (this.currentTraining) {
      this.http.put(`http://localhost:8086/trainings/${this.currentTraining.idForm}`, this.currentTraining).subscribe(
        (response) => {
          console.log('Training updated successfully');
          this.getTrainings(); // Rafraîchir la liste des formations après mise à jour
          this.currentTraining = null; // Réinitialiser le formulaire
        },
        (error) => {
          console.error('Error updating training:', error);
        }
      );
    }
  }

  // Générer un lien de réunion pour une formation sélectionnée
  generateMeetingLink(): void {
    if (this.selectedTrainingId && this.dateSession) {
      this.trainingService.generateMeetingLink(this.selectedTrainingId, this.dateSession).subscribe(
        (link) => {
          alert('Lien de réunion généré : ' + link);
          // Optionnel : Mettre à jour la formation avec le lien (si le backend ne le fait pas)
          const training = this.trainings.find(t => t.idForm === this.selectedTrainingId);
          if (training) training.meetingLink = link;
        },
        (error) => {
          console.error('Erreur lors de la génération du lien', error);
        }
      );
    } else {
      alert('Veuillez sélectionner une formation et une date.');
    }
  }

  // Envoyer des invitations
  sendInvitations(): void {
    if (this.selectedTrainingId && this.dateSession) {
      const selectedTraining = this.trainings.find(t => t.idForm === this.selectedTrainingId);
      const studentIds: number[] = selectedTraining?.enrolledStudents.map((s: any) => s.id) || [];
      this.trainingService.sendInvitations(this.selectedTrainingId, studentIds, this.dateSession).subscribe(
        () => {
          alert('Invitations envoyées avec succès !');
        },
        (error) => {
          console.error('Erreur lors de l\'envoi des invitations', error);
        }
      );
    } else {
      alert('Veuillez sélectionner une formation et une date.');
    }
  }

  // Vérifier si la capacité est atteinte
  checkCapacity(training: Training): boolean {
    return training.enrolledStudents && training.enrolledStudents.length >= training.maxCapacity;
  }
}