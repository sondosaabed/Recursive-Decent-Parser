
package model;

import control.*;
import javafx.application.Application;
import javafx.stage.Stage;
/**
 * 			Sondos Aabed 1190652
 * 			In this project a recursive decent parser is used to parse samples of programs
 * 			Written based on the given production rules, the ERROR function is based on the
 * 			Panic mode which means this will return only one error each time the parsing fails
 */
public class Main extends Application {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		@SuppressWarnings("unused")
		BrowseFileCtrl scene = new BrowseFileCtrl(stage);
	}
}