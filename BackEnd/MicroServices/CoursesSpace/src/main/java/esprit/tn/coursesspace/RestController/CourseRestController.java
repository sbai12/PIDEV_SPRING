package esprit.tn.coursesspace.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import esprit.tn.coursesspace.ApiResponse;
import esprit.tn.coursesspace.DTO.CourseDTO;
import esprit.tn.coursesspace.Entity.ContentType;
import esprit.tn.coursesspace.Entity.Course;
import esprit.tn.coursesspace.Entity.CourseFile;
import esprit.tn.coursesspace.Service.CourseServiceImpl;
import esprit.tn.coursesspace.Service.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "http://localhost:4200")
public class CourseRestController {

    @Autowired
    private CourseServiceImpl courseServiceImpl;
    @Autowired
    private FileServiceImpl fileServiceImpl;


    private final String uploadDirectory = "C:/Users/ghada/Downloads";



    @PostMapping("/add-course-with-file")
    public ResponseEntity<?> addCourseWithFile(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("duration") int duration,
            @RequestParam("contentType") String contentTypeStr,
            @RequestParam("file") MultipartFile file) {
        try {
            ContentType contentType;
            try {
                contentType = ContentType.valueOf(contentTypeStr); // Vérifie que la valeur est correcte
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Type de contenu invalide : " + contentTypeStr);
            }

            Course newCourse = new Course();
            newCourse.setTitle(title);
            newCourse.setDescription(description);
            newCourse.setDuration(duration);
            newCourse.setContentType(contentType);

            if (!file.isEmpty()) {
                newCourse.setFileName(file.getOriginalFilename());
                newCourse.setFileData(file.getBytes());
            }

            // Sauvegarde du cours
            Course savedCourse = courseServiceImpl.addCourseWithFile(newCourse, file);

            // Renvoie une réponse JSON avec un message de succès
            return ResponseEntity.ok(new ApiResponse("Course added successfully: " + savedCourse.getIdCourse()));
        } catch (Exception e) {
            // Renvoie une réponse JSON avec un message d'erreur en cas d'exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error: " + e.getMessage()));
        }
    }

    @PostMapping("/add-course")
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        try {
            Course savedCourse = courseServiceImpl.addCourse(course);
            return ResponseEntity.ok(new ApiResponse("Course added successfully: " + savedCourse.getIdCourse()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error: " + e.getMessage()));
        }
    }








    @GetMapping("/{id}/file")
    public ResponseEntity<byte[]> getCourseFile(@PathVariable Long id) {
        try {
            Course course = courseServiceImpl.getCourseById(id)
                    .orElseThrow(() -> new RuntimeException("Course not found with ID: " + id));

            if (course.getFileData() == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }

            String fileName = course.getFileName();
            MediaType mediaType = getMediaTypeFromFileName(fileName);

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .header("Content-Disposition", "inline; filename=\"" + fileName + "\"")
                    .body(course.getFileData());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    private MediaType getMediaTypeFromFileName(String fileName) {
        if (fileName.endsWith(".pdf")) {
            return MediaType.APPLICATION_PDF;
        } else if (fileName.endsWith(".docx")) {
            return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        } else if (fileName.endsWith(".pptx")) {
            return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.presentationml.presentation");
        } else if (fileName.endsWith(".txt")) {
            return MediaType.TEXT_PLAIN;
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }
    @GetMapping("/{idCourse}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long idCourse) {
        Optional<Course> courseOptional = courseServiceImpl.getCourseById(idCourse);

        if (courseOptional.isPresent()) {
            return ResponseEntity.ok(courseOptional.get());
        } else {
            // Retourne une réponse 404 si le cours n'est pas trouvé
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/delete/{idCourse}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long idCourse) {
        courseServiceImpl.deleteCourse(idCourse);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        Course updatedCourse = courseServiceImpl.updateCourse(id, course);
        if (updatedCourse != null) {
            return ResponseEntity.ok(updatedCourse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/getall-courses")
    public List<CourseDTO> getAllCourses() {
        return courseServiceImpl.getAllCourses();
    }

    @PutMapping("/{idCourse}/assign-module/{id_module}")
    public ResponseEntity<Course> assignCourseToModule(@PathVariable Long idCourse, @PathVariable Long moduleId) {
        Course updatedCourse = courseServiceImpl.assignCourseToModule(idCourse, moduleId);
        return ResponseEntity.ok(updatedCourse);
    }

    @PutMapping("/{idCourse}/assign-teacher/{id_user}")
    public ResponseEntity<Course> assignTeacherToCourse(@PathVariable Long idCourse, @PathVariable Long userId) {
        Course updatedCourse = courseServiceImpl.assignTeacherToCourse(idCourse, userId);
        return ResponseEntity.ok(updatedCourse);
    }

    @GetMapping("/details/{idCourse}")
    public ResponseEntity<?> getCourseDetails(@PathVariable Long idCourse) {
        Optional<Course> course = courseServiceImpl.getCourseById(idCourse);
        if (course.isPresent()) {
            return ResponseEntity.ok(course.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Course not found"));
        }
    }
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> genererPdfCours(@PathVariable Long id) {
        byte[] pdfData = courseServiceImpl.genererPdfCours(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cours_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfData);
    }
    @PostMapping("/{idCourse}/upload")
    public ResponseEntity<?> uploadFile(@PathVariable Long idCourse, @RequestParam("file") MultipartFile file) {
        try {
            // Vérifier si le fichier est vide
            if (file.isEmpty()) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Aucun fichier sélectionné");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            if (!file.getContentType().equals("application/pdf") && !file.getContentType().equals("application/vnd.ms-powerpoint")) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Type de fichier non autorisé");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            Path targetLocation = new File(uploadDirectory, file.getOriginalFilename()).toPath();

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Fichier téléchargé avec succès");
            response.put("fileName", file.getOriginalFilename());
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erreur lors de l'upload du fichier");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{idCourse}/files")
    public ResponseEntity<List<CourseFile>> getFiles(@PathVariable Long idCourse) {
        try {
            List<CourseFile> files = fileServiceImpl.getCourseFilesByidCourse(idCourse);

            if (files.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/courses/{idCourse}/files")
    public ResponseEntity<List<CourseFile>> getFilesForCourse(@PathVariable Long idCourse) {
        try {
            List<CourseFile> files = courseServiceImpl.getFilesForCourse(idCourse);
            if (files != null && !files.isEmpty()) {
                return ResponseEntity.ok(files);
            } else {
                System.out.println("Aucun fichier trouvé pour ce cours.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des fichiers : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/{idCours}/download")
    public ResponseEntity<?> downloadFile(@PathVariable Long idCours) {
        Course course = courseServiceImpl.getCourseById(idCours)
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));

        if (course.getFileData() == null || course.getFileData().length == 0) {
            throw new RuntimeException("Aucun fichier disponible pour ce cours");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + course.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)  // ou un type MIME plus précis si nécessaire
                .body(course.getFileData());
    }
    @GetMapping("/{idCours}/downloadConverted")
    public ResponseEntity<?> downloadConvertedFile(@PathVariable Long idCours) {
        Course course = courseServiceImpl.getCourseById(idCours)
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));

        if (course.getFileData() == null || course.getFileData().length == 0) {
            throw new RuntimeException("Aucun fichier disponible pour ce cours");
        }

        try {
            // Convertir le fichier DOCX en PDF
            byte[] pdfData = courseServiceImpl.convertDocxToPdf(course.getFileData());

            // Envoyer le fichier PDF en tant que réponse
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + course.getFileName() + ".pdf\"")
                    .contentType(MediaType.APPLICATION_PDF)  // Spécifier le type MIME PDF
                    .body(pdfData);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la conversion du fichier en PDF", e);
        }
    }
    @GetMapping("/{idCours}/downloadTranslated")
    public ResponseEntity<?> downloadTranslatedCourseFile(@PathVariable Long idCours) {
        Course course = courseServiceImpl.getCourseById(idCours)
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));

        String translatedFilePath = "/path/to/translated/file.docx";  // Le chemin du fichier traduit

        File file = new File(translatedFilePath);
        if (!file.exists()) {
            throw new RuntimeException("Fichier traduit introuvable");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new FileSystemResource(file));
    }


}