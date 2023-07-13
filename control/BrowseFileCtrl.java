package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.SyntaxErorr;
import javafx.stage.FileChooser.ExtensionFilter;
import visuals.BrowseFileScene;

/*
 * This class creates a controller for the browse file scene
 */
public class BrowseFileCtrl {
	// Fields
	private FileChooser fileChooser;
	private File file;
	private BrowseFileScene BrowseScene;
	private GridPane pane;
	private Label label;
	private Button next;
	private Button browse;
	private Button cancel;

	// Constructor
	/**
	 * @param stage
	 */
	public BrowseFileCtrl(Stage stage) {
		initialize(stage);
	}

	// Initialize objects
	/**
	 * @param stage
	 */
	private void initialize(Stage stage) {
		BrowseScene = new BrowseFileScene(stage);
		setPane(BrowseScene.getPane());
		setLabel(BrowseScene.getLabel());
		next = BrowseScene.getNext();
		cancel = BrowseScene.getCancel();
		this.fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
		browse = BrowseScene.getBrowse();
		handle_browse(browse, stage);
		handle_cancel(cancel);
	}

	/*
	 * Handlers
	 */

	/**
	 * @param cancel2
	 */
	private void handle_cancel(Button cancel2) {
		// Handle the button cancel
		cancel2.setOnAction(e -> {
			Platform.exit();
		});
	}

	/**
	 * @param next2
	 * @param stage
	 * @param file
	 */
	private void handle_next(Button next2, Stage stage, StringBuilder file) {
		// Handle next butotn to proceedd to the next scene
		next2.setOnAction(e -> {
			ResultCtrl scene;
			try {
				scene = new ResultCtrl(file, stage);
				scene.show();
			} catch (SyntaxErorr e1) {
				e1.printStackTrace();
			}
		});
	}

	/**
	 * @param browse2
	 * @param stage
	 */
	private void handle_browse(Button browse2, Stage stage) {
		// Handle browsing button
		browse2.setOnAction(e -> {
			this.file = fileChooser.showOpenDialog(stage);
			if (file == (null)) {
				AlertBoxCtrl a = new AlertBoxCtrl("You haven't chose a file yet!", "No file");
				a.show();
			} else {
				GridPane.setHalignment(next, HPos.CENTER);
				pane.add(next, 3, 1);
				handle_next(next, stage, readFileAsString(file));
				label.setText("            Click Parse to proceed...");
			}
		});
	}

	/**
	 * @param test
	 */
	private StringBuilder readFileAsString(File test) {
		/*
		 * This method is created to read the inputted file by the user to convert it
		 * into one string that will be tokenized
		 */
		StringBuilder file = new StringBuilder();
		try {
			FileReader fileR = new FileReader(test);
			BufferedReader buffer = new BufferedReader(fileR);

			String line;

			// Read the file as one string
			while ((line = buffer.readLine()) != null) {
				file = file.append(line);
			}
			buffer.close();
		} // catch any type of exceptions and alert the user
		catch (Exception e) {
			AlertBoxCtrl a = new AlertBoxCtrl(e.getLocalizedMessage(), "Exception");
			a.show();
		}
		return file;
	}
	
	/*
	 * Getters and Setters
	 */

	/**
	 * @return the fileChooser
	 */
	public FileChooser getFileChooser() {
		return fileChooser;
	}

	/**
	 * @param fileChooser the fileChooser to set
	 */
	public void setFileChooser(FileChooser fileChooser) {
		this.fileChooser = fileChooser;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the browseScene
	 */
	public BrowseFileScene getBrowseScene() {
		return BrowseScene;
	}

	/**
	 * @param browseScene the browseScene to set
	 */
	public void setBrowseScene(BrowseFileScene browseScene) {
		BrowseScene = browseScene;
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

	/**
	 * @return the label
	 */
	public Label getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(Label label) {
		this.label = label;
	}

	/**
	 * @return the next
	 */
	public Button getNext() {
		return next;
	}

	/**
	 * @param next the next to set
	 */
	public void setNext(Button next) {
		this.next = next;
	}

	/**
	 * @return the browse
	 */
	public Button getBrowse() {
		return browse;
	}

	/**
	 * @param browse the browse to set
	 */
	public void setBrowse(Button browse) {
		this.browse = browse;
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
}