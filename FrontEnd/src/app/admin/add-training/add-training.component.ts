import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TrainingService } from 'src/app/services/training.service'; 


@Component({
  selector: 'app-add-training',
  templateUrl: './add-training.component.html',
  styleUrls: ['./add-training.component.css']
})
export class AddTrainingComponent {
  training = {
    title: '',
    description: '',
    maxStudents: null,
    status: 'pending',
    name: '',            // Nom de la formation, sera affecté par le titre
    maxCapacity: null,   // Capacité maximale des étudiants, sera affectée par maxStudents
    duration: null      // Nouvelle propriété pour la durée

  };

  constructor(private trainingService: TrainingService, private router: Router) {}

  onSubmit() {
    // Vérifie si maxStudents est nul ou en dehors de la plage valide
    if (this.training.maxStudents === null || this.training.maxStudents < 1 || this.training.maxStudents > 15) {
      alert('The number of students must be between 1 and 15.');
      return;
    }


    // Assigner le titre de la formation au nom dans le backend
  this.training.name = this.training.title;  // Assigner le titre de la formation à 'name'
  this.training.maxCapacity = this.training.maxStudents;  // Assigner max_students à max_capacity
  this.training.duration = this.training.duration;  // Durée de la formation


  
    this.trainingService.addTraining(this.training).subscribe({
      next: (training) => {
        alert('Training added successfully!');
        this.router.navigate(['/admin/trainings']);
      },
      error: (err) => {
        alert('An error occurred. Please try again.');
      }
    });


    
  }}