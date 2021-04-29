package view;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

@SuppressWarnings("deprecation")
public class RPGView extends Application implements Observer {
	private int mouseX = -1, mouseY = -1;
	Rectangle[][] backgroundRectangles;
	GridPane gridPane;

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: black;");
		backgroundRectangles = new Rectangle[16][12];

		gridPane = new GridPane();
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 12; y++) {
				StackPane stackPane = new StackPane();
				Rectangle rect = new Rectangle(40, 40);
				Rectangle background = new Rectangle(40, 40);
				background.setFill(Color.BLACK);
				switch ((int) (Math.random() * 5)) {
				case 0:
				case 1:
					rect.setFill(new Color(0.25, 0.33, 0.75, 1));
					break;
				case 2:
					rect.setFill(new Color(0.75, 0.75, 0.25, 1));
					break;
				case 3:
					rect.setFill(new Color(0.33, 0.75, 0.33, 1));
					break;
				case 4:
					rect.setFill(new Color(0.15, 0.5, 0.15, 1));
					break;
				}
				stackPane.getChildren().add(background);
				stackPane.getChildren().add(rect);
				backgroundRectangles[x][y] = background;
				gridPane.add(stackPane, x, y);
			}
		}
		gridPane.setOnMouseClicked(event -> gridClicked(event));
		root.setCenter(gridPane);

		// Finalizing
		stage.setTitle("RPG");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	private void gridClicked(MouseEvent event) {
		int mouseX = (int) ((event.getSceneX()) / 42.0);
		int mouseY = (int) ((event.getSceneY()) / 42.0);
		if (this.mouseX != -1 && this.mouseY != -1) {
			backgroundRectangles[this.mouseX][this.mouseY].setFill(Color.BLACK);
		}
		if (this.mouseX == mouseX && this.mouseY == mouseY) {
			backgroundRectangles[this.mouseX][this.mouseY].setFill(Color.BLACK);
			this.mouseX = -1;
			this.mouseY = -1;
		} else {
			this.mouseX = mouseX;
			this.mouseY = mouseY;
			backgroundRectangles[this.mouseX][this.mouseY].setFill(Color.WHITE);
		}
	}

	/**
	 * This method is called when the game model is updated
	 * 
	 * Calls a constructor method to rebuild the tile map to reflect the new game
	 * state
	 */
	@Override
	public void update(Observable o, Object arg) {

	}

}
