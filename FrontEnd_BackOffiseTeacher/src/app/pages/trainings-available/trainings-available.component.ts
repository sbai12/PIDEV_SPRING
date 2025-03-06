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
  student = { firstName: '', lastName: '', id: 1 }; // Simuler un ID étudiant (à récupérer dynamiquement dans une vraie application)

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
  this.selectedTrainingId = trainingId;
  console.log('Selected Training ID:', this.selectedTrainingId);
}


  // Inscription de l'étudiant
  registerStudent(): void {
    if (this.selectedTrainingId && this.student.id) {
      // Appel du service pour inscrire l'étudiant avec son ID
      this.trainingService.registerStudentToTraining(this.selectedTrainingId, this.student.id).subscribe(
        (response) => {
          alert('You are successfully registered to the training!');
          // Rafraîchir la liste des formations après inscription
          this.getTrainings();
        },
        (error) => {
          alert('Registration failed. You may already be enrolled in this course.');
        }
      );
    } else {
      alert('Please fill in all fields before registering.');
    }
  }
}