package sample;


import com.sun.org.apache.bcel.internal.generic.IADD;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller{
    private static Stage stage;
    @FXML
    ImageView imageView;
    Image image = new Image(getClass().getResourceAsStream("mama.png"));

    public void displayImage(){
        imageView.setImage(image);
    }




}
