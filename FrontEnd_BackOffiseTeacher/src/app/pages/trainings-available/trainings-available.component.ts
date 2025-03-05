import { Component,OnInit  } from '@angular/core';
import { TrainingService } from '../../services/training.service'; // Assurez-vous de bien importer le service
import { Router } from '@angular/router'; // Pour rediriger après l'inscription


@Component({
  selector: 'app-trainings-available',
  templateUrl: './trainings-available.component.html',
  styleUrls: ['./trainings-available.component.css']
})
export class TrainingsAvailableComponent implements OnInit {
   // Variable pour stocker les formations récupérées
   trainings: any[] = [];  // Définit correctement trainings ici
   selectedTrainingId: number | null = null;
   selectedTrainingName: string = '';
   payment = {
     cardNumber: '',
     expiryDate: '',
     cvv: ''
   };
 
   constructor(
     private trainingService: TrainingService,
     private router: Router
   ) {}
 
   ngOnInit(): void {
     // Appel à la méthode pour récupérer les formations
     this.getTrainings();
   }
 
   // Méthode pour récupérer toutes les formations disponibles
   getTrainings(): void {
     this.trainingService.getAllTrainings().subscribe(
       (trainings) => {
         this.trainings = trainings;  // Met à jour la variable trainings avec les données récupérées
       },
       (error) => {
         console.error('Error fetching trainings:', error); // Affiche une erreur en cas de problème avec l'API
       }
     );
   }
 
   // Méthode pour sélectionner une formation et préparer l'inscription
   selectTraining(trainingId: number, trainingName: string): void {
     this.selectedTrainingId = trainingId;
     this.selectedTrainingName = trainingName;
   }
 
   // Méthode pour simuler le paiement et inscrire l'étudiant
   processPayment(): void {
     if (this.payment.cardNumber && this.payment.expiryDate && this.payment.cvv) {
       console.log('Payment processed successfully!');
       if (this.selectedTrainingId) {
         this.trainingService.registerStudentToTraining(this.selectedTrainingId, 1).subscribe(
           (response) => {
             alert('You are successfully registered to the training!');
             this.router.navigate(['/trainings']); // Redirige vers la page des formations après l'inscription
           },
           (error) => {
             alert('Registration failed. Please try again later.');
           }
         );
       }
     } else {
       alert('Please fill in all payment details.');
     }
   }
 }