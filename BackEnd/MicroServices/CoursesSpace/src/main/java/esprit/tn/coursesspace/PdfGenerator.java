package esprit.tn.coursesspace;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.FileOutputStream;

public class PdfGenerator {
    public static void main(String[] args) {
        try {
            PdfWriter writer = new PdfWriter("test.pdf");
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Hello, this is a PDF document!"));

            document.close();
            System.out.println("PDF created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
