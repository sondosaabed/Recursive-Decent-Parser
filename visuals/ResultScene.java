/**
 * 
 */
package visuals;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;

/**
 * In this class I create the scene of the risk result
 */
public class ResultScene {
	// Attributes
	private Stage window;
	private Label message;
	private Button cancel;
	private GridPane pane;

	// Constructor
	/**
	 * @param string
	 * @param string2
	 */
	public ResultScene(String answer, String error) {
		initialize(answer, error);
	}

	/*
	 * This method will initialize feilds and set their styles
	 */
	/**
	 * @param string
	 * @param errorsList
	 */
	private void initialize(String answer, String error) {
		BackgroundFill c1 = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(c1);

		pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(70));
		pane.setHgap(15);
		pane.setVgap(15);
		pane.setBackground(background);

		Image img = new Image("resources/notParsed.png");
		ImageView v = new ImageView(img);
		v.setFitWidth(150);
		v.setFitHeight(150);

		Image img2 = new Image("resources/parsed.png");
		ImageView v2 = new ImageView(img2);
		v2.setFitWidth(150);
		v2.setFitHeight(150);

		message = new Label();
		message.setFont(Font.font("Lucida Sans Unicode", FontWeight.BOLD, FontPosture.REGULAR, 20));
		message.setTextFill(Color.DARKRED);
		pane.add(message, 0, 1);

		// User button to exit
		cancel = new Button("Okay");
		cancel.setFont(Font.font("Lucida Sans Unicode", FontWeight.BOLD, FontPosture.REGULAR, 16));
		cancel.setPrefSize(80, 20);
		cancel.setTextFill(Color.WHITE);
		GridPane.setHalignment(cancel, HPos.CENTER);

		Button logo = new Button();
		logo.setPrefSize(160, 160);
		if (answer == "parsed") {
			logo.setGraphic(v2);
			message.setText("Your file was successfully parsed!");
			logo.setStyle(
					"-fx-border-color: transparent;-fx-border-width: 0;-fx-background-radius: 7;-fx-background-color: darkgreen;");
			cancel.setStyle("-fx-background-radius: 18, 7;-fx-background-color:darkgreen;");
			pane.add(cancel, 0, 2);
		} else if (answer == "failed") {
			logo.setGraphic(v);
			message.setText("Your file wasn't parsed due to");
			logo.setStyle(
					"-fx-border-color: transparent;-fx-border-width: 0;-fx-background-radius: 7;-fx-background-color: darkred;");
			cancel.setStyle("-fx-background-radius: 18, 7;-fx-background-color:darkred;");
			pane.add(cancel, 0, 3);

			TextArea errors = new TextArea();
			errors.setEditable(false);
			errors.setFont(Font.font("Lucida Sans Unicode", FontWeight.BOLD, FontPosture.REGULAR, 16));
			errors.setPrefSize(500, 80);
			/*
			 * To add the errors to the scene put the text area text as the error passed
			 * form the parser
			 */
			errors.setText(error);
			pane.add(errors, 0, 2);

		}
		GridPane.setHalignment(message, HPos.CENTER);
		GridPane.setHalignment(logo, HPos.CENTER);
		pane.add(logo, 0, 0);

		Scene scene = new Scene(pane);
		window = new Stage();
		window.setScene(scene);
		window.setTitle("Parsing result");
		window.getIcons().add(new Image("resources/logo.png"));
	}

	/*
	 * Getters and Setters
	 */
	/**
	 * @return the window
	 */
	public Stage getWindow() {
		return window;
	}

	/**
	 * @param window the window to set
	 */
	public void setWindow(Stage window) {
		this.window = window;
	}

	/**
	 * @return the message
	 */
	public Label getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(Label message) {
		this.message = message;
	}

	/**
	 * @return the cancel
	 */
	public Button getCancel() {
		return cancel;
	}

	/**
	 * @param cancel the cancel to set
	 */
	public void setCancel(Button cancel) {
		this.cancel = cancel;
	}

	/**
	 * @return the pane
	 */
	public GridPane getPane() {
		return pane;
	}

	/**
	 * @param pane the pane to set
	 */
	public void setPane(GridPane pane) {
		this.pane = pane;
	}
}