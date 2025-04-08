package tn.esprit.trainingmanagement.Services;

public interface IEmailNotificationService {
    public void sendCourseNotification(Long courseId, String courseName, String message);
}
