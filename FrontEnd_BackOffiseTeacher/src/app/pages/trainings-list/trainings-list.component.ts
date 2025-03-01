import { Component } from '@angular/core';

@Component({
  selector: 'app-trainings-list',
  templateUrl: './trainings-list.component.html',
  styleUrls: ['./trainings-list.component.css']
})
export class TrainingsListComponent {
  trainings = [
    { id: 1, name: 'Introduction to Web Design', description: 'Learn the basics of web design.', date: '2025-09-26' },
    { id: 2, name: 'Marketing Strategies', description: 'Advanced marketing strategies for businesses.', date: '2025-11-15' }
  ];

  editTraining(training: any) {
    alert('Edit Training: ' + training.name);
    // Ici, tu peux rediriger vers une autre page pour modifier la formation
  }

  deleteTraining(trainingId: number) {
    const confirmation = confirm('Are you sure you want to delete this training?');
    if (confirmation) {
      this.trainings = this.trainings.filter(t => t.id !== trainingId);
      alert('Training deleted successfully!');
    }
  }
}