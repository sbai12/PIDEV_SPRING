import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
@Component({
  selector: 'app-delete-user',
  templateUrl: './delete-user.component.html',
  styleUrls: ['./delete-user.component.css']
})
export class DeleteUserComponent {
 constructor(
    public dialogRef: MatDialogRef<DeleteUserComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { companyName: string }
  ) {}

  onCancel(): void {
    this.dialogRef.close(false); // User canceled
  }

  onConfirm(): void {
    this.dialogRef.close(true); // User confirmed
  }
}
