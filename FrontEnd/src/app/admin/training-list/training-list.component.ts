import { Component , OnInit } from '@angular/core';
import { TrainingService } from 'src/app/services/training.service'; 
import { Router , ActivatedRoute} from '@angular/router';
import { Training } from 'src/app/models/training.model';



@Component({
  selector: 'app-training-list',
  templateUrl: './training-list.component.html',
  styleUrls: ['./training-list.component.css']
})
export class TrainingListComponent implements OnInit {
  trainings: Training[] = [];

  constructor(
    private trainingService: TrainingService,
    private router: Router,
    private route: ActivatedRoute // ← Ajouté ici
  ) {}
  
  ngOnInit(): void {
    this.loadTrainings();
  }

  // Charger la liste des formations
  loadTrainings(): void {
    this.trainingService.getTrainings().subscribe((trainings: Training[]) => {
      console.log('Trainings:', trainings); // Vérifiez dans la console si les données sont bien récupérées
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
  editTraining(trainingId: number): void {
    this.router.navigate(['edit', trainingId], { relativeTo: this.route });
    
  }
  
  
}