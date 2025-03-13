package esprit.tn.coursesspace.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import esprit.tn.coursesspace.DTO.CourseDTO;
import esprit.tn.coursesspace.Entity.*;
import esprit.tn.coursesspace.Repository.ICourseFileRepository;
import esprit.tn.coursesspace.Repository.ICourseRepository;
import esprit.tn.coursesspace.Repository.ICoursesModuleRepository;
import esprit.tn.coursesspace.Repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.itextpdf.layout.Document;
import java.io.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import java.io.InputStream;

@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private ICoursesModuleRepository coursesModuleRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ICourseFileRepository courseFileRepository;




    @Override
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course addCourseWithFile(Course course, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        byte[] fileBytes = file.getBytes();
        course.setFileData(fileBytes);
        return courseRepository.save(course);
    }

    @Override
    public void uploadFile(Long idCours, MultipartFile file) {
        Course course = courseRepository.findById(idCours)
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));

        try {
            course.setFileData(file.getBytes());
            course.setFileName(file.getOriginalFilename());
            courseRepository.save(course);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du fichier", e);
        }
    }




    @Override
    public byte[] genererPdfCours(Long idCours) {
        Course course = courseRepository.findById(idCours)
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));

        if (course.getFileData() == null || course.getFileData().length == 0) {
            throw new RuntimeException("Aucun fichier disponible pour ce cours");
        }

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Ajout du titre du cours
            document.add(new Paragraph("Cours : " + course.getTitle()).setBold().setFontSize(16));

            // Vérifier si le fichier est un texte lisible ou un binaire (ex. PDF, DOCX, PPTX)
            if (course.getContentType() == ContentType.TXT || course.getContentType() == ContentType.EXERCISE) {
                // Cas d'un fichier texte lisible
                String fileContent = new String(course.getFileData()); // Décodage texte
                document.add(new Paragraph(fileContent));
            } else {
                // Fichiers binaires -> Affichage d'un message
                document.add(new Paragraph("Le fichier original est un document binaire : " + course.getFileName()));
                document.add(new Paragraph("Veuillez télécharger l'original."));
            }

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }
    }


    public byte[] convertDocxToPdf(byte[] docxData) throws Exception {
        try (ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream()) {
            // Créer un writer PDF et un document PDF
            PdfWriter writer = new PdfWriter(pdfOutputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Charger le fichier DOCX
            try (InputStream docxInputStream = new ByteArrayInputStream(docxData)) {
                XWPFDocument doc = new XWPFDocument(docxInputStream);
                for (org.apache.poi.xwpf.usermodel.XWPFParagraph paragraph : doc.getParagraphs()) {
                    // Ajouter chaque paragraphe du DOCX au PDF
                    document.add(new Paragraph(paragraph.getText()));
                }
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la lecture du fichier DOCX", e);
            }

            // Fermer le document PDF
            document.close();

            // Retourner le PDF généré en tant que byte array
            return pdfOutputStream.toByteArray();
        }
    }



    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        if (courseRepository.existsById(id)) {
            course.setIdCourse(id);
            return courseRepository.save(course);
        }
        return null;
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDTO> courseDTOs = new ArrayList<>();

        for (Course course : courses) {
            CourseDTO courseDTO = new CourseDTO(course.getIdCourse(),
                    course.getTitle(),
                    course.getDescription(),
                    course.getContentType(),
                    course.getDuration(),
                    course.getFileName(),
                    course.getFileData());
            courseDTOs.add(courseDTO);
        }
        return courseDTOs;
    }





    @Override
    public byte[] getFileByCourseId(Long courseId) throws IOException {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));

        if (course.getFileData() == null) {
            throw new RuntimeException("No file found for this course");
        }

        return course.getFileData();
    }

    @Override
    public Course assignCourseToModule(Long courseId, Long moduleId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        CourseModule module = coursesModuleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));

        course.setCourseModule(module);
        return courseRepository.save(course);
    }

    @Override
    public Course assignTeacherToCourse(Long courseId, Long userId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().equals(Role.Teacher)) {
            throw new RuntimeException("User is not a Teacher");
        }

        if (course.getTeachers() == null) {
            course.setTeachers(new HashSet<>());
        }

        course.getTeachers().add(user);
        return courseRepository.save(course);
    }


    @Override
    public Map<String, String> saveFile(Long idCourse, MultipartFile file) throws IOException {
        Optional<Course> optionalCourse = courseRepository.findById(idCourse);
        if (optionalCourse.isEmpty()) {
            throw new RuntimeException("Course not found");
        }

        Course course = optionalCourse.get();
        // Créer un nouveau CourseFile pour ce fichier
        CourseFile courseFile = new CourseFile();
        courseFile.setFileName(file.getOriginalFilename());
        courseFile.setFileData(file.getBytes());
        courseFile.setCourse(course);

        // Sauvegarder le CourseFile dans le repository
        courseFileRepository.save(courseFile);

        Map<String, String> response = new HashMap<>();
        response.put("message", "File uploaded successfully");
        response.put("fileName", file.getOriginalFilename());
        return response;
    }

    @Override
    public List<CourseFile> getFilesForCourse(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            // Vérifie si le cours a des fichiers associés
            List<CourseFile> files = courseFileRepository.findByCourse(course);
            if (files.isEmpty()) {
                System.out.println("Aucun fichier associé à ce cours.");
            }
            return files;
        } else {
            throw new EntityNotFoundException("Course not found with ID: " + courseId);
        }
    }


}