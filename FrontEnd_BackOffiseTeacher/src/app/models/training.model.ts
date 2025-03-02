// Définition de l'interface Training pour représenter les propriétés de chaque formation
export interface Training {
    idForm: number;          // Identifiant unique de la formation
    name: string;            // Nom de la formation
    description: string;     // Description de la formation
    duration: number;        // Durée de la formation (en heures, par exemple)
    maxCapacity: number;     // Capacité maximale des étudiants dans la formation
    date?: string;           // Date de la formation, qui est optionnelle
  }
  