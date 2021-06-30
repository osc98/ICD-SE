package sample;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;

public class pdf extends controllerFormulario{

    public void impresion(Stage stage, String nombre, int biRads, int edad, int forma, int margen, int densidad){
        String dir = null;
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A6);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Text
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 32);
            contentStream.newLineAtOffset( 20, page.getMediaBox().getHeight() - 52);
            contentStream.showText(nombre);
            contentStream.showText(String.valueOf(biRads));
            contentStream.endText();

            // Image
//            PDImageXObject image = PDImageXObject.createFromByteArray(document, Main.class.getResourceAsStream("/java.png").readAllBytes(), "Java Logo");
//            contentStream.drawImage(image, 20, 20, image.getWidth() / 3, image.getHeight() / 3);

            contentStream.close();

            //String dir1 =

            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(stage);
            document.save(selectedDirectory.getAbsolutePath() + "\\" + "documento_"+nombre+".pdf");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
