package tn.esprit.trainingmanagement.Services;

import java.time.LocalDateTime;

public interface IMeetingService {
    String genererLienReunion(Long idFormation, LocalDateTime dateSession);
    void envoyerInvitations(Long idFormation, LocalDateTime dateSession);
}
