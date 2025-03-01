import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';  // Assurez-vous d'importer HttpClient


@Component({
  selector: 'app-add-training',
  templateUrl: './add-training.component.html',
  styleUrls: ['./add-training.component.css']
})
export class AddTrainingComponent {
 
    training = {
      name: '',
      description: '',
      duration: 0,
      maxCapacity: 0  // Change ici à "maxCapacity"
    };
  
    constructor(private http: HttpClient) {}
  
    onSubmit() {
      console.log('Training Added:', this.training);
  
      this.http.post('http://localhost:8086/trainings', this.training)
        .subscribe(response => {
          console.log('Training successfully added:', response);
          alert('Training successfully added!');
        }, error => {
          console.error('Error adding training:', error);
          alert('There was an error adding the training.');
        });
    }
  }
  