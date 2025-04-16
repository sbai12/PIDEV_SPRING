import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-rating-stars',
  template: `
    <div class="star-rating">
      <i
        *ngFor="let star of stars; let index = index"
        class="fa-star"
        [class.fas]="index < rating"
        [class.far]="index >= rating"
        [class.read-only]="readOnly"
        (click)="onStarClick(index + 1)"
      ></i>
    </div>
  `,
  styles: [`
    .star-rating {
      font-size: 1.5rem;
      color: #ffc107;
      cursor: pointer;
    }

    .read-only {
      pointer-events: none;
    }

    .fa-star {
      margin-right: 5px;
    }
  `]
})
export class RatingStarsComponent {
  @Input() rating: number = 0;
  @Input() readOnly: boolean = false; // ✅ ajouter ceci
  @Output() ratingChange = new EventEmitter<number>();

  stars = Array(5).fill(0);

  onStarClick(value: number) {
    if (this.readOnly) return; // 🔒 ne rien faire si readOnly
    this.rating = value;
    this.ratingChange.emit(this.rating);
  }
}
