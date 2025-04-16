import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TrainingService } from 'src/app/services/training.service';
import { EvaluationService } from 'src/app/services/evaluation.service';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {

  enrolledTrainings: any[] = [];
  otherTrainings: any[] = [];

  studentId: number = 0;

  // Evaluation form
  rating: number = 0;
  comment: string = '';
  selectedTrainingId: number | null = null;

  constructor(
    private trainingService: TrainingService,
    private router: Router,
    private evaluationService: EvaluationService
  ) {}

  ngOnInit(): void {
    const id = localStorage.getItem('user_id');
    if (!id) {
      alert("You must be logged in.");
      return;
    }

    this.studentId = +id;

    this.trainingService.getStudentTrainings(this.studentId).subscribe({
      next: (data) => {
        this.enrolledTrainings = data.enrolledTrainings || [];
        this.otherTrainings = data.otherTrainings || [];

        const allTrainings = [...this.enrolledTrainings, ...this.otherTrainings];

        allTrainings.forEach(training => {
          this.getAverageRating(training.id);
        });
      },
      error: (err) => {
        console.error('Error loading trainings:', err);
      }
    });
  }

  // Fetch average rating for a training
  getAverageRating(trainingId: number) {
    this.evaluationService.getAverageRating(trainingId).subscribe({
      next: (average) => {
        const allTrainings = [...this.enrolledTrainings, ...this.otherTrainings];
        const training = allTrainings.find(t => t.id === trainingId);
        if (training) {
          training.averageRating = average;
        }
      },
      error: () => {
        console.warn(`Failed to load average rating for training ${trainingId}`);
      }
    });
  }

  // Check if a student is enrolled in the training
  isRegistered(training: any): boolean {
    return this.enrolledTrainings.some(t => t.id === training.id);
  }

  // Redirect to payment/registration page
  onRegister(trainingId: number): void {
    this.router.navigate(['/pricing', { trainingId }]);
  }

  // Open the evaluation form for a specific training
  openRatingForm(trainingId: number) {
    this.selectedTrainingId = trainingId;
    this.rating = 0;
    this.comment = '';
  }

  // Submit a rating for a training
  submitRating(trainingId: number) {
    if (this.rating < 1 || this.rating > 5) {
      alert('Please choose a rating between 1 and 5.');
      return;
    }

    const evaluation = {
      idEtudiant: this.studentId,
      idFormation: trainingId,
      nombreEtoiles: this.rating,
      commentaire: this.comment
    };

    this.evaluationService.submitEvaluation(evaluation).subscribe({
      next: () => {
        alert('Your review has been submitted!');
        this.rating = 0;
        this.comment = '';
        this.selectedTrainingId = null;
        this.getAverageRating(trainingId); // Refresh average after submission
      },
      error: (err) => {
        console.error('Evaluation error:', err);
        alert(err?.error?.message || 'An error occurred while submitting your review.');
      }
    });
  }
}
