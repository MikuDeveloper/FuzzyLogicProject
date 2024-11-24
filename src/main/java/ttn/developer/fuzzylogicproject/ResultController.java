package ttn.developer.fuzzylogicproject;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.ResourceBundle;

import static ttn.developer.fuzzylogicproject.FuzzyLogicApplication.incomeAmount;
import static ttn.developer.fuzzylogicproject.FuzzyLogicApplication.pointerCounter;
import static ttn.developer.fuzzylogicproject.FuzzyLogicModel.evaluate;

public class ResultController implements Initializable {
    Image image;
    ImageView resultImage;
    @FXML
    VBox resultBox;
    @FXML
    Button backButton;

    @FXML
    public void goToHome() {
        FuzzyLogicApplication.setRoot("initial-view");
        pointerCounter = 0;
        incomeAmount = 0;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        image = new Image(Objects.requireNonNull(ResultController.class.getResource("icons/money.png")).toExternalForm());
        resultImage = new ImageView(image);
        resultImage.setFitHeight(200);
        resultImage.setFitWidth(200);

        Label resultMessage = new Label("El crédito que podemos ofrecerle es de:");
        resultMessage.setFont(new Font("Dubai Medium", 20));

        DecimalFormat formatter = new DecimalFormat("$###,###.##");
        String number = formatter.format(evaluate(pointerCounter, incomeAmount));
        Label resultLabel = new Label(number);
        resultLabel.setFont(new Font("Dubai Medium", 28));

        new Thread(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> {
                resultBox.getChildren().clear();
                resultBox.getChildren().addAll(resultImage, resultMessage, resultLabel);
                backButton.setVisible(true);
            });
        }).start();
    }
}
