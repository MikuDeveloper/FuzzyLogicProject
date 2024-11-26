package ttn.developer.fuzzylogicproject;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.ResourceBundle;

import static ttn.developer.fuzzylogicproject.FuzzyLogicApplication.incomeAmount;
import static ttn.developer.fuzzylogicproject.FuzzyLogicApplication.pointerCounter;

public class FuzzyLogicController implements Initializable {
    Image profileImageRobot;
    Image profileImageUser;
    FuzzyLogicModel model;
    Question currentQuestion;

    char turn = 'r';

    @FXML
    ScrollPane conversationPane;
    @FXML
    VBox conversationBox;
    @FXML
    HBox userAnswerBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new FuzzyLogicModel();
        profileImageRobot = new Image(Objects.requireNonNull(
                FuzzyLogicController.class.getResourceAsStream("icons/robot-assistant.png")));
        profileImageUser = new Image(Objects.requireNonNull(
                FuzzyLogicController.class.getResourceAsStream("icons/user.png")));

        currentQuestion = model.nextQuestion();

        setInitialMessages();
    }

    void setInitialMessages() {
        Queue<Thread> messages = new LinkedList<>();
        messages.add(generateMessage("Buen día, estimado cliente.", false));
        messages.add(generateMessage("Comenzaremos por hacerle una serie de preguntas.", false));
        messages.add(generateMessage("Responda con sinceridad para poder determinar si podremos ofrecerle una línea de crédito en nuestro banco.", false));
        messages.add(generateMessage(currentQuestion.question(), true));
        SendMessages send = new SendMessages(messages);
        new Thread(send).start();
    }

    void onClickAnswer(MouseEvent event) {
        String answer = ((Label) event.getSource()).getText();
        FuzzyLogicApplication.pointerCounter += model.getAnswerValue(currentQuestion.id() - 1, answer);
        generateMessage(answer, true).start();
        userAnswerBox.getChildren().clear();
        changeTurnToRobot();
    }

    Thread generateMessage(String message, boolean changeTurn) {
        Circle dot1 = new Circle(5);
        Circle dot2 = new Circle(5);
        Circle dot3 = new Circle(5);
        Circle[] dots = new Circle[]{ dot1, dot2, dot3 };

        ImageView profilePhoto = generateProfilePhoto();
        Label messageLabel = generateMessageLabel(message);
        HBox bubbleBox = generateBubbleBox(profilePhoto, messageLabel, dots);

        if (turn == 'u') return new Thread(() -> Platform.runLater(() -> addToConversationBox(bubbleBox)));

        Timeline timeline = setTypingAnimation(dots);
        timeline.play();

        return new Thread(() -> {
            try {
                Platform.runLater(() -> addToConversationBox(bubbleBox));
                Thread.sleep(message.length() * 25L);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            Platform.runLater(() -> {
                removeTypingAnimation(timeline, bubbleBox, messageLabel);
                if (changeTurn) {
                    turn = 'u';
                    if (Objects.equals(currentQuestion.question(), "¿Cuáles son tus ingresos mensuales netos?")) {
                        TextField textField = getTextField();
                        textField.getStyleClass().add("input");
                        Button setAmount = new Button("Agregar");
                        setAmount.getStyleClass().add("material-button");
                        setAmount.setOnMouseClicked(event -> formatInput(textField));
                        userAnswerBox.getChildren().addAll(textField, setAmount);
                    } else {
                        for (String question : currentQuestion.answers().keySet()) {
                            Label answerLabel = new Label(question);
                            answerLabel.getStyleClass().addAll("chat-message-user", "answer");
                            answerLabel.setOnMouseClicked(this::onClickAnswer);
                            userAnswerBox.getChildren().add(answerLabel);
                        }
                    }
                }
            });
        });
    }

    private void formatInput(TextField textField) {
        if (textField.getText() != null && !textField.getText().isEmpty()) {
            FuzzyLogicApplication.incomeAmount = Integer.parseInt(textField.getText());
            DecimalFormat formatter = new DecimalFormat("$###,###.##");
            String number = formatter.format(FuzzyLogicApplication.incomeAmount);
            userAnswerBox.getChildren().clear();
            generateMessage(number, true).start();
            changeTurnToRobot();
        }
    }

    private void addToConversationBox(HBox bubbleBox) {
        addMessageAnimation(bubbleBox);
        conversationBox.getChildren().add(bubbleBox);
        conversationPane.vvalueProperty().bind(bubbleBox.layoutYProperty().divide(conversationPane.getHeight()));
    }

    private static TextField getTextField() {
        TextField textField = new TextField();
        textField.setTextFormatter(new TextFormatter<Integer>(c -> {
            if (c.isDeleted()) {
                return c;
            }

            try {
                Integer.parseInt(c.getText());
                return c;
            } catch (NumberFormatException e) {
                return null; // Invalid character, reject it
            }
        }));
        textField.setPromptText("Ingreso mensual");
        return textField;
    }

    void changeTurnToRobot() {
        turn = 'r';
        if (model.hasNextQuestion()) {
            currentQuestion = model.nextQuestion();
            generateMessage(currentQuestion.question(), true).start();
        } else {
            FuzzyLogicApplication.setRoot("result-view");
        }
    }

    void removeTypingAnimation(Timeline timeline, HBox bubbleBox, Label messageLabel) {
        timeline.stop();
        bubbleBox.getChildren().removeLast();
        bubbleBox.getChildren().removeLast();
        bubbleBox.getChildren().removeLast();
        bubbleBox.getChildren().add(messageLabel);
    }

    Timeline setTypingAnimation(Circle[] dots) {
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(dots[0].translateYProperty(), 0)),
            new KeyFrame(Duration.seconds(0.5), new KeyValue(dots[0].translateYProperty(), -10)),
            new KeyFrame(Duration.seconds(1), new KeyValue(dots[0].translateYProperty(), 0)),

            new KeyFrame(Duration.seconds(0.25), new KeyValue(dots[1].translateYProperty(), 0)),
            new KeyFrame(Duration.seconds(0.75), new KeyValue(dots[1].translateYProperty(), -10)),
            new KeyFrame(Duration.seconds(1.25), new KeyValue(dots[1].translateYProperty(), 0)),

            new KeyFrame(Duration.seconds(0.5), new KeyValue(dots[2].translateYProperty(), 0)),
            new KeyFrame(Duration.seconds(1), new KeyValue(dots[2].translateYProperty(), -10)),
            new KeyFrame(Duration.seconds(1.5), new KeyValue(dots[2].translateYProperty(), 0))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }

    void addMessageAnimation(HBox bubbleBox) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), bubbleBox);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), bubbleBox);
        if (turn == 'r') {
            scaleTransition.setFromX(0.5);
            scaleTransition.setFromY(0.5);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
        } else {
            scaleTransition.setFromX(1);
            scaleTransition.setFromY(1);
            scaleTransition.setToX(0.5);
            scaleTransition.setToY(0.5);
        }

        ParallelTransition parallelTransition = new ParallelTransition(fadeTransition, scaleTransition);
        parallelTransition.play();
    }

    HBox generateBubbleBox(ImageView profileImageView, Label messageLabel, Circle[] dots) {
        HBox bubbleBox = turn == 'r'
                ? new HBox(10, profileImageView, dots[0], dots[1], dots[2])
                : new HBox(10, messageLabel, profileImageView);

        bubbleBox.getStyleClass().add(turn == 'r' ? "chat-bubble-robot" : "chat-bubble-user");

        return bubbleBox;
    }

    ImageView generateProfilePhoto() {
        ImageView profileImageView = new ImageView(turn == 'r' ? profileImageRobot : profileImageUser);
        profileImageView.setFitWidth(45);
        profileImageView.setFitHeight(45);

        return profileImageView;
    }

    Label generateMessageLabel(String message) {
        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.getStyleClass().add(turn == 'r' ? "chat-message-robot" : "chat-message-user");

        return messageLabel;
    }

    @FXML
    void backToHome() {
        pointerCounter = 0;
        incomeAmount = 0;
        FuzzyLogicApplication.setRoot("initial-view");
    }
}
