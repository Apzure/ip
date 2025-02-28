package duke;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Bird bird;
    private Main main;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image birdImage = new Image(this.getClass().getResourceAsStream("/images/DaBird.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

     /**
     * Creates the initial welcome message for user on startup
     */
    @FXML
    public void displayInitialMessage() {
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(bird.greet(), birdImage)
        );
    }

    public void setBird(Bird b) {
        bird = b;
    }

    public void setMain(Main m) {
        main = m;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = bird.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, birdImage)
        );
        userInput.clear();

        // Closes Duke
        if (response.equals("bye")) {
            main.close();
        }
    }
}
