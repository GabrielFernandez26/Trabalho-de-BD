package src.com.example.projetoaeroporto.control;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AirportControll {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}