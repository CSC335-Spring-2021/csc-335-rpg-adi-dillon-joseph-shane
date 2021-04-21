package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class RPGView extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		BorderPane root = new BorderPane();
		// Finalizing
		stage.setTitle("RPG");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
