import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CompanyService } from 'src/app/services/company.service';
import { Company } from 'src/app/models/Company';
import { InternshipOfferService } from 'src/app/services/internship-offer.service';

@Component({
  selector: 'app-assign-company',
  templateUrl: './assign-company.component.html',
  styleUrls: ['./assign-company.component.css'],
})
export class AssignCompanyComponent implements OnInit {
  @Input() isVisible = false;
  @Input() offerId!: number;
  @Output() close = new EventEmitter<void>();
  @Output() assigned = new EventEmitter<void>();

  companies: Company[] = [];
  searchTerm = '';

  constructor(private companyService: CompanyService, private offerService: InternshipOfferService) {}

  ngOnInit() {
    this.loadCompanies();
  }

  loadCompanies() {
    this.companyService.getCompanies().subscribe((data) => {
      this.companies = data;
    });
  }

  filteredCompanies() {
    return this.companies.filter((c) =>
      c.address.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  assignCompany(companyId?: number) {
    if (!companyId || !this.offerId) {
      console.error('Error: Invalid company ID or offer ID');
      return;
    }

    this.offerService.assignCompany(this.offerId, companyId).subscribe(() => {
      console.log(`Company ${companyId} assigned to offer ${this.offerId}`);
      this.assigned.emit();
      this.closeModal();
    });
  }

  closeModal() {
    this.close.emit();
  }
}
