import { Component, OnInit } from '@angular/core';
import { Encadrement } from 'src/app/models/Encadrement';
import { EncadrementService } from 'src/app/services/encadrement.service';
import { MatDialog } from '@angular/material/dialog';
import { DeleteEncadrementConfirmationComponent } from './delete-confirmation/delete-confirmation.component';

@Component({
  selector: 'app-encadrement',
  templateUrl: './encadrement.component.html',
  styleUrls: ['./encadrement.component.css'],
})
export class EncadrementComponent implements OnInit {
  encadrements: Encadrement[] = [];
  searchTerm = '';
  showModal = false;
  isEditMode = false;
  selectedEncadrement: Encadrement = { status: '', tache: '', encadrement_date: '' };

  constructor(private encadrementService: EncadrementService, private dialog: MatDialog) {}

  ngOnInit() {
    this.loadEncadrements();
  }

  loadEncadrements() {
    this.encadrementService.getEncadrements().subscribe((data) => {
      console.log('Fetched Encadrements:', data);
      this.encadrements = data;
    });
  }

  filteredEncadrements() {
    return this.encadrements.filter((enc) =>
      enc.status.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  openModal(encadrement: Encadrement | null = null) {
    console.log("Open Modal Clicked", encadrement);
    this.isEditMode = !!encadrement;
    this.selectedEncadrement = encadrement ? { ...encadrement } : { status: '', tache: '', encadrement_date: '' };
    this.showModal = true;
  }
  

  closeModal() {
    this.showModal = false;
  }

  saveEncadrement() {
    if (this.isEditMode && this.selectedEncadrement.id != null) {
      this.encadrementService.updateEncadrement(this.selectedEncadrement.id, this.selectedEncadrement).subscribe(() => {
        this.loadEncadrements();
        this.closeModal();
      });
    } else {
      this.encadrementService.createEncadrement(this.selectedEncadrement).subscribe((newEncadrement) => {
        if (newEncadrement && newEncadrement.id != null) {
          this.selectedEncadrement.id = newEncadrement.id;
          this.encadrements.push(newEncadrement);
        }
        this.closeModal();
      });
    }
  }

  editEncadrement(encadrement: Encadrement) {
    this.openModal(encadrement);
  }

  deleteEncadrement(id?: number, encadrementStatus?: string) {
    if (!id) {
      console.error('Invalid Encadrement ID: Cannot delete!');
      return;
    }

    const dialogRef = this.dialog.open(DeleteEncadrementConfirmationComponent, {
      width: '400px',
      maxWidth: '90vw',
      disableClose: true,
      hasBackdrop: true,
      autoFocus: false,
      data: { encadrementStatus }
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.encadrementService.deleteEncadrement(id).subscribe(() => {
          console.log(`Deleted encadrement with ID: ${id}`);
          this.loadEncadrements();
        });
      }
    });
  }
}
