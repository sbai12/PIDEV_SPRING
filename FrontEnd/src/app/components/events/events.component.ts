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

 
  enrolledTrainings: any[] = [];
  otherTrainings: any[] = [];
  rating: number = 0;
  comment: string = '';
  studentId: number = 0;

  constructor(
    private trainingService: TrainingService, 
    private router: Router, 
    private evaluationService: EvaluationService
  ) {}

  ngOnInit(): void {
    const id = localStorage.getItem('user_id');
    if (id) {
      this.studentId = +id;
      this.trainingService.getStudentTrainings(this.studentId).subscribe({
        next: (data) => {
          this.enrolledTrainings = data.enrolledTrainings;
          this.otherTrainings = data.otherTrainings;

          [...this.enrolledTrainings, ...this.otherTrainings].forEach(t => {
            this.getAverageRating(t.id);
          });
        },
        error: (err) => {
          console.error('Error fetching trainings:', err);
        }
      });
    }
  }

  getAverageRating(trainingId: number) {
    this.evaluationService.getAverageRating(trainingId).subscribe(average => {
      const training = [...this.enrolledTrainings, ...this.otherTrainings].find(t => t.id === trainingId);
      if (training) {
        training.averageRating = average;
      }
    });
  }

  isRegistered(training: any): boolean {
    return this.enrolledTrainings.some(t => t.id === training.id);
  }

  onRegister(trainingId: number): void {
    console.log(`Registering student ${this.studentId} for training ${trainingId}`);
    // ici tu peux appeler un vrai service d'inscription à une formation si tu veux
    this.router.navigate(['/pricing', { trainingId }]);
  }

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

    this.evaluationService.submitEvaluation(evaluation).subscribe({
      next: () => {
        alert('Review submitted!');
        this.ngOnInit();
      },
      error: () => {
        alert('Failed to submit review.');
      }
    });
  }
}