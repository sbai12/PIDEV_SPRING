export interface User {
    id?: number;
    firstname: string;
    lastname: string;
    email: string;
    password: string;
    specialite?: string;
    competence?: string;
    niveau?: string;
    phone?: string;
    status?: string;
    role: 'Admin' | 'Supervisor' | 'Candidate' | 'Teacher' | 'Representant';
  }
  