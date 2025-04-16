import { Component, OnInit } from '@angular/core';
import { Company } from 'src/app/models/Company';
import { CompanyService } from 'src/app/services/company.service';
import { MatDialog } from '@angular/material/dialog';
import { DeleteConfirmationComponent } from './delete-confirmation/delete-confirmation.component';


@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.css'],
})
export class CompanyComponent implements OnInit {
  companies: Company[] = [];
  searchTerm = '';
  showModal = false;
  isEditMode = false;
  selectedCompany: Company = { phone: '', address: '', description: '' };

  constructor(private companyService: CompanyService, private dialog: MatDialog) {}

  ngOnInit() {
    this.loadCompanies();
  }

  loadCompanies() {
    this.companyService.getCompanies().subscribe((data) => {
      console.log("Fetched Companies:", data); // Debugging
      this.companies = data;
    });
  }

  filteredCompanies() {
    return this.companies.filter((company) =>
      company.address.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  openModal(company: Company | null = null) {
    this.isEditMode = !!company;
    this.selectedCompany = company ? { ...company } : { phone: '', address: '', description: '' };
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  saveCompany() {
    if (this.isEditMode && this.selectedCompany.id_company != null) {
      this.companyService.updateCompany(this.selectedCompany.id_company, this.selectedCompany).subscribe(() => {
        this.loadCompanies();
        this.closeModal();
      });
    } else {
      this.companyService.addCompany(this.selectedCompany).subscribe((newCompany) => {
        if (newCompany && newCompany.id_company != null) {
          this.selectedCompany.id_company = newCompany.id_company; // Assign ID properly
          this.companies.push(newCompany); // Add it to the local list
        }
        this.closeModal();
      });
    }
  }
  
  

  editCompany(company: Company) {
    this.openModal(company);
  }

  deleteCompany(id?: number, companyName?: string) {
    if (!id) {
      console.error('Invalid company ID: Cannot delete!');
      return;
    }
  
    const dialogRef = this.dialog.open(DeleteConfirmationComponent, {
      width: '400px',
      maxWidth: '90vw',
      disableClose: true,
      hasBackdrop: true,
      autoFocus: false,
      data: { companyName }
    });
    
    
    
    
    
    
  
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.companyService.deleteCompany(id).subscribe(() => {
          console.log(`Deleted company with ID: ${id}`);
          this.loadCompanies();
        });
      }
    });
  }
  
  
  
}
