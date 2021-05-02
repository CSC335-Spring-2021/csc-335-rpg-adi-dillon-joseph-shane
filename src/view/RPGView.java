package view;

import java.util.Observable;
import java.util.Observer;

import controller.RPGController;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Model;
import model.Tile;
import units.Unit;

@SuppressWarnings("deprecation")
public class RPGView extends Application implements Observer {
	private int mouseX = -1, mouseY = -1;
	private RPGController controller;
	private Rectangle[][] backgroundRectangles;
	private ImageView[][] cityImages;
	private ImageView[][] unitImages;
	private ImageView[][] highlightImages;
	private final Image highlight = new Image("/res/highlight.png");
	private GridPane gridPane;

	@Override
	public void start(Stage stage) throws Exception {
		// Create the controller this view is based on
		this.controller = new RPGController(this);

		// Create the borderpane and a set of grids inside it
		// We don't care what color each rectangle is, yet
		// That will be updated by the model soon
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: black;");
		backgroundRectangles = new Rectangle[Model.MAP_SIZE][Model.MAP_SIZE];
		unitImages = new ImageView[Model.MAP_SIZE][Model.MAP_SIZE];
		cityImages = new ImageView[Model.MAP_SIZE][Model.MAP_SIZE];
		highlightImages = new ImageView[Model.MAP_SIZE][Model.MAP_SIZE];

		gridPane = new GridPane();
		for (int x = 0; x < Model.MAP_SIZE; x++) {
			for (int y = 0; y < Model.MAP_SIZE; y++) {
				StackPane stackPane = new StackPane();
				Rectangle background = new Rectangle(40, 40);
				background.setFill(Color.BLACK);
				stackPane.getChildren().add(background);
				backgroundRectangles[x][y] = background;

				ImageView unitView = new ImageView((Image) null);
				stackPane.getChildren().add(unitView);
				unitImages[x][y] = unitView;

				ImageView highlightView = new ImageView((Image) null);
				stackPane.getChildren().add(highlightView);
				highlightImages[x][y] = highlightView;
				
				ImageView cityView = new ImageView((Image) null);
				stackPane.getChildren().add(cityView);
				cityImages[x][y] = cityView;

				gridPane.add(stackPane, x, y);
			}
		}
		gridPane.setOnMouseClicked(event -> gridClicked(event));
		root.setCenter(gridPane);

		// Finalizing
		stage.setTitle("RPG");
		Scene scene = new Scene(root);
		stage.setScene(scene);

		// The model in the controller knows what the map looks like
		// We can tell the controller to tell the map to
		// update the view
		this.controller.updateView();

		stage.show(); // Show the stage
	}

	private void gridClicked(MouseEvent event) {

		int mouseX = (int) ((event.getSceneX()) / 40);
		int mouseY = (int) ((event.getSceneY()) / 40);

		if (event.getButton() == MouseButton.SECONDARY) {
			if (this.mouseX != -1 && this.mouseY != -1) {
				controller.moveUnit(this.mouseY, this.mouseX, mouseY, mouseX);
				highlightImages[this.mouseX][this.mouseY].setImage((Image) null);
				this.mouseX = -1;
				this.mouseY = -1;
			} else {
				if (controller.selectUnit(mouseY, mouseX)) {
					this.mouseX = mouseX;
					this.mouseY = mouseY;
					highlightImages[this.mouseX][this.mouseY].setImage(highlight);
				} else {
					this.mouseX = -1;
					this.mouseY = -1;
				}
			}
		}else {
			if(this.controller.canBuildCity(mouseY, mouseX)) {
				Alert confirm =  new Alert(AlertType.CONFIRMATION);
				confirm.setContentText("Would you like to build a city here for 500 gold?");
				confirm.showAndWait().ifPresent(response -> {
					if(response.getText().equals("OK")) {
						this.controller.buildCity(mouseY, mouseX);
					}
				});
				
			}else {
				Alert error = new Alert(AlertType.ERROR);
				error.setContentText("You can't build a city here!");
				error.show();
			}
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
		Tile[][] map = (Tile[][]) arg;
		for (int i = 0; i < Model.MAP_SIZE; i++) {
			for (int j = 0; j < Model.MAP_SIZE; j++) {
				
				Tile current = map[i][j];
				if (current.getLandType().equals(Tile.DRY_LAND)) {
					this.backgroundRectangles[i][j].setFill(Color.GREEN);
				} else if (current.getLandType().equals(Tile.WATER)) {
					this.backgroundRectangles[i][j].setFill(Color.BLUE);
				}
				if (current.getUnit() != null) {
					this.unitImages[i][j].setImage(current.getUnit().getSprite());
				} else {
					this.unitImages[i][j].setImage(null);
				}
				if (current.getCity() != null) {
					this.cityImages[i][j].setImage(current.getCity().getSprite());
				}
			}
		}
	}

}
