package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        test_libreri test=new test_libreri();
//        test.cargarInstancias();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Inicio");
        primaryStage.setScene(new Scene(root, 400, 500));
        primaryStage.show();

    }



    public static void main(String[] args) {
        launch(args);
    }
}
