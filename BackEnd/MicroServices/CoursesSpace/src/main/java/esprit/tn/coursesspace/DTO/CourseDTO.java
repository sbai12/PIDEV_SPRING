package esprit.tn.coursesspace.DTO;

import esprit.tn.coursesspace.Entity.ContentType;
import jakarta.persistence.Lob;

public class CourseDTO {
    private Long idCourse;
    private String title;
    private String description;
    private ContentType contentType;
    private int duration;
    private String fileName;
    @Lob
    private byte[] fileData;
    private String filePath;


    public CourseDTO(Long idCourse, String title, String description, ContentType contentType, int duration, String fileName, byte[] fileData) {
        this.idCourse = idCourse;
        this.title = title;
        this.description = description;
        this.contentType = contentType;
        this.duration = duration;
        this.fileName = fileName;
        this.fileData = fileData;
    }





    public Long getIdCourse() { return idCourse; }
    public void setIdCourse(Long idCourse) { this.idCourse = idCourse; }


}
