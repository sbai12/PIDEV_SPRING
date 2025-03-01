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

    errorMessage: string = ''; 
  
    constructor(private http: HttpClient) {}
  
    onSubmit() {
      // Vérifie si la formation existe déjà
      this.checkIfTrainingExists(this.training.name).then(exists => {
        if (exists) {
          this.errorMessage = 'Formation existe déjà';
          console.log('Formation existe déjà');
        } else {
          // Si la formation n'existe pas, on l'envoie
          this.errorMessage = '';
          this.http.post('http://localhost:8086/trainings', this.training)
            .subscribe(response => {
              console.log('Training successfully added:', response);
              alert('Training successfully added!');
            }, error => {
              console.error('Error adding training:', error);
              alert('There was an error adding the training.');
            });
        }
      }).catch(error => {
        console.error('Error checking if training exists:', error);
        alert('There was an error checking if the training exists.');
      });
    }
      // Vérifie si une formation existe déjà par son nom
  checkIfTrainingExists(name: string): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.http.get(`http://localhost:8086/trainings/exists/${name}`).subscribe((response: any) => {
        // Si la formation existe déjà, la réponse est vraie
        resolve(response.exists);
      }, error => {
        reject(error);
      });
    });
  }
}