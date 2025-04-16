import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-postuler',
  templateUrl: './postuler.component.html',
  styleUrls: ['./postuler.component.css'],
})
export class PostulerComponent implements OnInit {
  offerId!: number;
  application = {
    fullname: '',
    email: '',
    phone: '',
    motivation: ''
  };
  selectedFile!: File | null;

  constructor(private route: ActivatedRoute, private router: Router) {}

  ngOnInit() {
    this.offerId = Number(this.route.snapshot.paramMap.get('id'));
    console.log('Applying for Offer ID:', this.offerId);
  }

  onFileSelected(event: Event) {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files && fileInput.files.length > 0) {
      this.selectedFile = fileInput.files[0];
      console.log('Selected CV:', this.selectedFile.name);
    }
  }

  submitApplication() {
    if (!this.selectedFile) {
      alert('Please upload your CV.');
      return;
    }

    const formData = new FormData();
    formData.append('fullname', this.application.fullname);
    formData.append('email', this.application.email);
    formData.append('phone', this.application.phone);
    formData.append('motivation', this.application.motivation);
    formData.append('cv', this.selectedFile);
    formData.append('offerId', this.offerId.toString());

    console.log('Application Submitted:', formData);

    alert('Application submitted successfully!');

    // Redirect back to internship offers page
    this.router.navigate(['/internship-offer']);
  }
}
