package ttn.developer.fuzzylogicproject;

import javafx.fxml.FXML;

public class InitialController {
    @FXML
    public void goToChat() {
        FuzzyLogicApplication.setRoot("fuzzy-logic-view");
    }
}
