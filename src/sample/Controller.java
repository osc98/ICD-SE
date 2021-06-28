package sample;


import com.sun.org.apache.bcel.internal.generic.IADD;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Controller {
    @FXML
    ImageView imageView;
    Image image = new Image(getClass().getResourceAsStream("mama.png"));

    public void displayImage(){
        imageView.setImage(image);
    }
}
