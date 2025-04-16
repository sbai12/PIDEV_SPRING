import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/User';

@Component({
  selector: 'app-assign-candidate',
  templateUrl: './assign-candidate.component.html',
  styleUrls: ['./assign-candidate.component.css'],
})
export class AssignCandidateComponent implements OnInit {
  @Input() isVisible = false;
  @Input() offerId!: number;
  @Output() close = new EventEmitter<void>();
  @Output() assigned = new EventEmitter<void>();

  searchTerm = '';
  selectedCompetence = '';
  candidates: User[] = [];
  filteredCandidates: User[] = [];
  uniqueCompetences: string[] = [];
  loading = false;

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.loadCandidates();
  }

  loadCandidates() {
    this.loading = true;
    this.userService.getCandidates().subscribe(
      (data) => {
        this.candidates = data;
        this.extractUniqueCompetences();
        this.applyFilters();
        this.loading = false;
      },
      (error) => {
        console.error('Error fetching candidates:', error);
        this.loading = false;
      }
    );
  }

  extractUniqueCompetences() {
    const competencesSet = new Set<string>();
    this.candidates.forEach((c) => {
      if (c.competence) {
        competencesSet.add(c.competence);
      }
    });
    this.uniqueCompetences = Array.from(competencesSet);
  }

  applyFilters() {
    this.filteredCandidates = this.candidates.filter((c) => {
      const matchesCompetence = !this.selectedCompetence || c.competence === this.selectedCompetence;
      const matchesSearch = !this.searchTerm || 
        (`${c.firstname} ${c.lastname}`.toLowerCase().includes(this.searchTerm.toLowerCase()));

      return matchesCompetence && matchesSearch;
    });
  }

  onSearchChange() {
    this.applyFilters();
  }

  onCompetenceChange() {
    this.applyFilters();
  }

  acceptCandidate(candidateId: number) {
    console.log(`✅ Candidate ${candidateId} accepted for offer ${this.offerId}`);
    alert(`Candidate ${candidateId} has been accepted!`);
    this.assigned.emit();
    this.closeModal();
  }

  rejectCandidate(candidateId: number) {
    console.log(`❌ Candidate ${candidateId} rejected for offer ${this.offerId}`);
    alert(`Candidate ${candidateId} has been rejected.`);
    this.assigned.emit();
    this.closeModal();
  }

  closeModal() {
    this.close.emit();
  }
}
