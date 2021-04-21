package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class RPGView extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: black;");

		GridPane gridPane = new GridPane();
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 12; y++) {
				StackPane stackPane = new StackPane();
				Rectangle rect = new Rectangle(40, 40);
				stackPane.setPadding(new Insets(1, 1, 1, 1));
				switch ((int) (Math.random() * 2)) {
				case 0:
					rect.setFill(new Color(0.25, 0.33, 0.75, 1));
					break;
				case 1:
					rect.setFill(new Color(0.33, 0.75, 0.33, 1));
					break;
				}
				stackPane.getChildren().add(rect);
				gridPane.add(stackPane, x, y);
			}
		}
		root.setCenter(gridPane);

		// Finalizing
		stage.setTitle("RPG");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
