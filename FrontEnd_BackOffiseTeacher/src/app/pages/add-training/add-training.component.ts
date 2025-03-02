import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';  // Assurez-vous d'importer HttpClient
import { Router } from '@angular/router';  // Importation de Router pour rediriger après l'ajout


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
    maxCapacity: 0  // Assurez-vous que ce champ est correctement défini
  };

  errorMessage: string = '';  // Pour afficher les messages d'erreur

  constructor(private http: HttpClient, private router: Router) {}

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
            console.log('Formation ajoutée avec succès:', response);
            alert('Formation ajoutée avec succès!');
            
            // Rediriger vers la liste des formations après ajout (recharge la liste)
            this.router.navigate(['/trainings']);  // Assurez-vous d'avoir cette route configurée pour les formations
          }, error => {
            console.error('Erreur lors de l\'ajout de la formation:', error);
            alert('Il y a eu une erreur lors de l\'ajout de la formation.');
          });
      }
    }).catch(error => {
      console.error('Erreur lors de la vérification de l\'existence de la formation:', error);
      alert('Il y a eu une erreur lors de la vérification de l\'existence de la formation.');
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