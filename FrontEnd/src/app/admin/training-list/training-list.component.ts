import { Component , OnInit } from '@angular/core';
import { TrainingService } from 'src/app/services/training.service'; 
import { Router } from '@angular/router';
import { Training } from 'src/app/models/training.model';



@Component({
  selector: 'app-training-list',
  templateUrl: './training-list.component.html',
  styleUrls: ['./training-list.component.css']
})
export class TrainingListComponent implements OnInit {
  trainings: Training[] = [];

  constructor(private trainingService: TrainingService, private router: Router) {}

  ngOnInit(): void {
    this.loadTrainings();
  }

  // Charger la liste des formations
  loadTrainings(): void {
    this.trainingService.getTrainings().subscribe((trainings: Training[]) => {
      this.trainings = trainings;
    });
  }

  // Supprimer une formation
  deleteTraining(id: number): void {
    this.trainingService.deleteTraining(id).subscribe(() => {
      this.trainings = this.trainings.filter(training => training.idForm !== id);  // Mise à jour de la liste localement après suppression
    });
  }

  // Rediriger vers la page d'édition d'une formation
  editTraining(id: number): void {
    this.router.navigate(['/edit-training', id]);  // Rediriger vers la page d'édition avec l'ID de la formation
  }
}