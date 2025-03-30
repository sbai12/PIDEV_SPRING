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
    const id = this.route.snapshot.paramMap.get('id');  // Récupérer l'ID depuis l'URL
    if (id) {
      this.trainingService.getTrainings().subscribe(trainings => {
        this.training = trainings.find(t => t.idForm === +id) as Training;  // Trouver la formation correspondant à l'ID
      });
    }
  }

  onSubmit(): void {
    this.trainingService.updateTraining(this.training.idForm, this.training).subscribe(() => {
      this.router.navigate(['/trainings']);  // Rediriger vers la liste des formations après modification
    });
  }
}