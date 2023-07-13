package control;

import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.RecursiveDecentParser;
import model.SyntaxErorr;
import model.Tokenizer;
import visuals.ResultScene;

/**
 * In this class the result scene controller is displayed
 */
public class ResultCtrl {
	/*
	 * Fields
	 */
	private Button cancel;
	private StringBuilder file;
	private String answer;
	private ResultScene scene;
	private Stage stage;

	/*
	 * Constructor
	 */
	/**
	 * @param stringBuilder
	 * @throws SyntaxErorr 
	 */
	public ResultCtrl(StringBuilder stringBuilder, Stage stage) throws SyntaxErorr {
		this.setFile(stringBuilder);
		/*
		 * Get the tokens form the tokenizer
		 * Get the answer from the parser 
		 */
		
		Tokenizer tokenizer = new Tokenizer(stringBuilder);
		ArrayList<String> tokens=  tokenizer.tokenize();
		for (String string : tokens) {
			System.out.println(string);
		}
		RecursiveDecentParser parser = new RecursiveDecentParser(tokens);
		parser.parse();
		
		/*
		 * Set the answer to be displayed on the scene
		 */
		setAnswer(parser.returnResult());
		setScene(new ResultScene(answer, parser.getError()));
		setCancel(getScene().getCancel());
		setStage(getScene().getWindow());
		handle_cancel(getCancel(), stage);
	}
	/*
	 * Show the scene
	 */
	public void show() {
		getScene().getWindow().show();
	}
	/*
	 * Handle the cancel button
	 */
	private void handle_cancel(Button cancel, Stage stage2) {
		cancel.setOnAction(i -> {
			@SuppressWarnings("unused")
			BrowseFileCtrl scene = new BrowseFileCtrl(stage2);
			getStage().close();
		});
	}
	/*
	 * Getters and Setters
	 */
	/**
	 * @return the stage
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * @param stage the stage to set
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
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
	 * @return the scene
	 */
	public ResultScene getScene() {
		return scene;
	}

	/**
	 * @param scene the scene to set
	 */
	public void setScene(ResultScene scene) {
		this.scene = scene;
	}

	/**
	 * @return the file
	 */
	public StringBuilder getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(StringBuilder file) {
		this.file = file;
	}
}