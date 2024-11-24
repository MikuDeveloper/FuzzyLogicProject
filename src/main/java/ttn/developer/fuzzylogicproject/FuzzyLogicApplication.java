package ttn.developer.fuzzylogicproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class FuzzyLogicApplication extends Application {
    public static int pointerCounter = 0;
    public static int incomeAmount = 0;
    private static Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Image image = new Image(Objects.requireNonNull(FuzzyLogicApplication.class.getResourceAsStream("icons/favicon.png")));
        scene = new Scene(loadFXML("initial-view"));
        scene.getStylesheets().add(String.valueOf(FuzzyLogicApplication.class.getResource("styles.css")));
        primaryStage.getIcons().add(image);
        primaryStage.setTitle("Fuzzy Logic Chat");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static void setRoot(String fxml) {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(FuzzyLogicApplication.class.getResource(fxml + ".fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
