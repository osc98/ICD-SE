package sample;


import com.sun.org.apache.bcel.internal.generic.IADD;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    ImageView imageView;
    @FXML
    Button btninit;

    Image image = new Image(getClass().getResourceAsStream("mama.png"));

    public void displayImage(){
        imageView.setImage(image);
    }
    javafx.event.EventHandler<ActionEvent> cambio = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Parent root = null;
            try {

                root = FXMLLoader.load(getClass().getResource("formulario.fxml"));
                controllerForm controllerForm= new controllerForm();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Puto");
                Stage cerrar = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                cerrar.close();
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    };
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btninit.setOnAction(cambio);
    }
}
