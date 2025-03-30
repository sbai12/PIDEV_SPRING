export class Training {
    idForm: number;
    name: string;
    description: string;
    duration: number; // Durée en heures
    maxCapacity: number; // Capacité maximale
    professor: { id: number, name: string }; // Object professor avec juste les propriétés nécessaires
    enrollments: number; // Juste une indication du nombre d'inscriptions (par exemple)
    meetingLink: string;
    status: string; // Propriété 'status' ajoutée

  
    constructor(idForm: number, name: string, description: string, duration: number, maxCapacity: number, professor: { id: number, name: string }, enrollments: number, meetingLink: string, status: string) {
      this.idForm = idForm;
      this.name = name;
      this.description = description;
      this.duration = duration;
      this.maxCapacity = maxCapacity;
      this.professor = professor;
      this.enrollments = enrollments; // ou un tableau des ids des inscriptions si tu veux en garder une trace
      this.meetingLink = meetingLink;
      this.status = status;

    }
  }
  