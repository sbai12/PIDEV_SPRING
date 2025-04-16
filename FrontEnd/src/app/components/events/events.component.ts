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

    // Fetch student's enrolled and other available trainings
    this.trainingService.getStudentTrainings(this.studentId).subscribe({
      next: (data) => {
        this.enrolledTrainings = data.enrolledTrainings || [];
        this.otherTrainings = data.otherTrainings || [];

        // Get the average ratings for all trainings (both enrolled and available)
        this.fetchAverageRatingsForTrainings();
      },
      error: (err) => {
        console.error('Error loading trainings:', err);
      }
    });
  }

  // Fetch average rating for all enrolled and available trainings
  fetchAverageRatingsForTrainings() {
    const allTrainings = [...this.enrolledTrainings, ...this.otherTrainings];
    allTrainings.forEach(training => {
      if (training.idForm) {
        this.getAverageRating(training.idForm);
      }
    });
  }

  // Fetch average rating for a specific training
  getAverageRating(trainingId: number) {
    this.evaluationService.getAverageRating(trainingId).subscribe({
      next: (average) => {
        const training = this.findTrainingById(trainingId);
        if (training) {
          training.averageRating = average;
        }
      },
      error: () => {
        console.warn(`Failed to load average rating for training ${trainingId}`);
      }
    });
  }

  // Helper method to find a training by its ID
  findTrainingById(trainingId: number) {
    return [
      ...this.enrolledTrainings,
      ...this.otherTrainings
    ].find(t => t.idForm === trainingId);  // Use idForm for training identification
  }

  // Check if a student is enrolled in the training
  isRegistered(training: any): boolean {
    return this.enrolledTrainings.some(t => t.idForm === training.idForm); // Using idForm to match
  }

  // Navigate to the pricing/registration page
  onRegister(trainingId: number): void {
    this.router.navigate(['/pricing', { trainingId }]);
  }

  // Open the rating form for a specific training
  openRatingForm(trainingId: number) {
    this.selectedTrainingId = trainingId;
    this.rating = 0;
    this.comment = '';
  }

  // Submit the evaluation for a training
  submitRating(trainingId: number) {
    if (this.rating < 1 || this.rating > 5) {
      alert('Please choose a rating between 1 and 5.');
      return;
    }

    if (!this.comment || this.comment.trim() === '') {
      alert('Please add a comment.');
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
        this.resetForm();
        this.getAverageRating(trainingId); // Refresh average after submission
      },
      error: (err) => {
        console.error('Evaluation error:', err);
        alert(err?.error?.message || 'An error occurred while submitting your review.');
      }
    });
  }

  // Reset the rating form after submission
  resetForm() {
    this.rating = 0;
    this.comment = '';
    this.selectedTrainingId = null;
  }
}
