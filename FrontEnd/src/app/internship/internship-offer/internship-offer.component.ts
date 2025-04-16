import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { InternshipOffer } from 'src/app/models/InternshipOffer';
import { InternshipOfferService } from 'src/app/services/internship-offer.service';

@Component({
  selector: 'app-internship-offer',
  templateUrl: './internship-offer.component.html',
  styleUrls: ['./internship-offer.component.css'],
})
export class InternshipOfferUserComponent implements OnInit {
  internshipOffers: InternshipOffer[] = [];
  searchTerm = '';

  constructor(private offerService: InternshipOfferService, private router: Router) {}

  ngOnInit() {
    this.loadInternshipOffers();
  }

  loadInternshipOffers() {
    this.offerService.getInternshipOffers().subscribe((data) => {
      this.internshipOffers = data;
    });
  }

  filteredOffers() {
    return this.internshipOffers.filter((offer) =>
      offer.description.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  postuler(offerId?: number) {
    if (!offerId) {
      console.error('Error: Internship offer ID is undefined!');
      return;
    }
    // Navigate to the postulation page (to be defined later)
    this.router.navigate(['/postuler', offerId]);
  }
}
