import { Component,OnInit  } from '@angular/core';
import { TrainingService } from '../../services/training.service'; // Assurez-vous de bien importer le service
import { Router } from '@angular/router'; // Pour rediriger après l'inscription


@Component({
  selector: 'app-trainings-available',
  templateUrl: './trainings-available.component.html',
  styleUrls: ['./trainings-available.component.css']
})
export class TrainingsAvailableComponent implements OnInit {
  trainings: any[] = []; // Liste des formations
  selectedTrainingId: number | null = null; // ID de la formation sélectionnée
  showForm: boolean = false; // Contrôle l'affichage du formulaire
  student = { firstName: '', lastName: '', cardNumber: '' }; // Données de l'étudiant

  constructor(
    private trainingService: TrainingService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getTrainings(); // Charger les formations au démarrage
  }

  // Récupère toutes les formations
  getTrainings(): void {
    this.trainingService.getAllTrainings().subscribe(
      (trainings) => {
        this.trainings = trainings;
      },
      (error) => {
        console.error('Error fetching trainings:', error);
      }
    );
  }

  // Sélectionne la formation pour l'inscription
  selectTraining(trainingId: number): void {
    if (trainingId) { // Vérifie si un ID de formation est sélectionné
      this.selectedTrainingId = trainingId;
      this.showForm = true;  // Affiche le formulaire d'inscription
    } else {
      alert('Please select a valid training.');
    }
  }

  // Inscription de l'étudiant et ajout dans la base de données
  registerStudent(): void {
    // Vérifier que l'ID de la formation est valide et que les champs sont remplis
    if (this.selectedTrainingId !== null && this.student.firstName && this.student.lastName && this.student.cardNumber) {
      // Appeler le service pour enregistrer l'étudiant
      this.trainingService.registerStudentToTraining(this.selectedTrainingId, this.student).subscribe(
        (response) => {
          alert('You are successfully registered to the training!');
          this.showForm = false;  // Cacher le formulaire après inscription
          this.getTrainings();  // Rafraîchir la liste des formations
        },
        (error) => {
          alert('Registration failed. You may already be enrolled in this course.');
        }
      );
    } else {
      alert('Please fill in all fields before registering or select a valid training.');
    }
  }
}