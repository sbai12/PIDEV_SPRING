import { Component } from '@angular/core';

@Component({
  selector: 'app-add-training',
  templateUrl: './add-training.component.html',
  styleUrls: ['./add-training.component.css']
})
export class AddTrainingComponent {
  training = {
    name: '',
    description: '',
    date: ''
  };

  onSubmit() {
    console.log('Training Added:', this.training);
    alert('Training successfully added!');
  }
}