import { Component, OnInit } from '@angular/core';
import { InternshipOffer } from 'src/app/models/InternshipOffer';
import { InternshipOfferService } from 'src/app/services/internship-offer.service';
import { MatDialog } from '@angular/material/dialog';
import { DeleteOfferConfirmationComponent } from '../delete-confirmation/delete-confirmation.component';

@Component({
  selector: 'app-internship-offer',
  templateUrl: './internship-offer.component.html',
  styleUrls: ['./internship-offer.component.css'],
})
export class InternshipOfferComponent implements OnInit {
  internshipOffers: InternshipOffer[] = [];
  searchTerm = '';
  showModal = false;
  isEditMode = false;
  selectedOffer: InternshipOffer = { description: '', competence: '', duration: '', type: '', status: '' };
  statusOptions: string[] = ['Pending', 'Approved', 'Rejected']; // Define possible statuses
  selectedOfferId?: number;
  showCandidateModal = false;
  showCompanyModal = false;

  constructor(private offerService: InternshipOfferService, private dialog: MatDialog) {}

  ngOnInit() {
    this.loadInternshipOffers();
    this.internshipOffers.forEach((offer) => {
      if (!offer.status) {
        offer.status = 'Pending'; // Default status
      }
    });
    
  }

  showChartModal: boolean = false;

  openChartModal() {
    this.showChartModal = true;
  }
  

  closeChartModal() {
    this.showChartModal = false;
  }

  loadInternshipOffers() {
    this.offerService.getInternshipOffers().subscribe((data) => {
      console.log('Fetched Internship Offers:', data); // Debugging
      this.internshipOffers = data;
  
      // Check if status exists
      this.internshipOffers.forEach((offer) => {
        console.log(`Offer ID: ${offer.id_offer}, Status: ${offer.status}`);
      });
    });
  }
  

  filteredOffers() {
    return this.internshipOffers.filter((offer) =>
      offer.description.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  openModal(offer: InternshipOffer | null = null) {
    this.isEditMode = !!offer;
    this.selectedOffer = offer ? { ...offer } : { description: '', competence: '', duration: '', type: '', status: '' };
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  saveOffer() {
    if (this.isEditMode && this.selectedOffer.id_offer != null) {
      this.offerService.updateInternshipOffer(this.selectedOffer.id_offer, this.selectedOffer).subscribe(() => {
        this.loadInternshipOffers();
        this.closeModal();
      });
    } else {
      this.offerService.createInternshipOffer(this.selectedOffer).subscribe((newOffer) => {
        if (newOffer && newOffer.id_offer != null) {
          this.internshipOffers.push(newOffer);
        }
        this.closeModal();
      });
    }
  }

  deleteOffer(id?: number, description?: string) {
    if (!id) {
      console.error('Invalid internship offer ID: Cannot delete!');
      return;
    }

    const dialogRef = this.dialog.open(DeleteOfferConfirmationComponent, {
      width: '400px',
      maxWidth: '90vw',
      disableClose: true,
      hasBackdrop: true,
      autoFocus: false,
      data: { companyName: description }
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.offerService.deleteInternshipOffer(id).subscribe(() => {
          console.log(`Deleted internship offer with ID: ${id}`);
          this.loadInternshipOffers();
        });
      }
    });
  }

  updateStatus(offer: InternshipOffer) {
    if (!offer.id_offer) {
      console.error('Invalid internship offer ID: Cannot update status!');
      return;
    }
  
    this.offerService.updateOfferStatus(offer.id_offer, offer.status).subscribe(
      (updatedOffer) => {
        console.log(`Updated status of offer ID ${updatedOffer.id_offer} to ${updatedOffer.status}`);
      },
      (error) => {
        console.error('Failed to update status:', error);
      }
    );
  }

  openCandidateModal(offerId?: number) {
    if (!offerId) {
      console.error('Error: Internship offer ID is undefined!');
      return;
    }
    this.selectedOfferId = offerId;
    this.showCandidateModal = true;
  }
  

  closeCandidateModal() {
    this.showCandidateModal = false;
  }

  openCompanyModal(offerId?: number) {
    this.selectedOfferId = offerId;
    this.showCompanyModal = true;
  }

  closeCompanyModal() {
    this.showCompanyModal = false;
  }
  
}
