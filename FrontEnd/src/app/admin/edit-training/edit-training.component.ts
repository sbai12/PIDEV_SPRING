import { Component , OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TrainingService } from 'src/app/services/training.service'; 
import { Training } from 'src/app/models/training.model';



@Component({
  selector: 'app-edit-training',
  templateUrl: './edit-training.component.html',
  styleUrls: ['./edit-training.component.css']
})
export class EditTrainingComponent implements OnInit {
  training: Training;

  constructor(
    private route: ActivatedRoute,
    private trainingService: TrainingService,
    private router: Router
  ) {
    this.training = new Training(0, '', '', 0, 0, { id: 0, name: '' }, 0, '', '');
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    console.log('ID from URL:', id); // Doit afficher un numéro (comme "3", "5", etc.)
    if (id) {
      this.trainingService.getTrainingById(+id).subscribe({
        next: (trainingData) => {
          console.log('Training loaded:', trainingData);  // Vérifie si les données sont récupérées correctement
          this.training = trainingData;
        },
        error: (err) => {
          console.error('Erreur lors du chargement du training :', err);
        }
      });
    } else {
      console.log('Aucun ID trouvé dans l\'URL');
    }
  }
  

  onSubmit(): void {
    const updatedTraining = {
      name: this.training.name,
      description: this.training.description,
      duration: this.training.duration,
      maxCapacity: this.training.maxCapacity,
      status: this.training.status
    };
    
    this.trainingService.updateTraining(this.training.idForm, updatedTraining).subscribe({
      next: () => {
        alert('Training updated successfully');  // Affiche un message de succès
        this.router.navigate(['/trainings']);   // Redirige vers la liste des formations
      },
  
      error: (error) => {
        alert('Error: Could not update training. Please try again.');
        console.error('Error updating training:', error);
      },
      complete: () => {
        console.log('Update completed');
      }
    });
  }
}