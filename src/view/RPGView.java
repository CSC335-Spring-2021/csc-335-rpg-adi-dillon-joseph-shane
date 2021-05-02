package view;

import java.util.Observable;
import java.util.Observer;

import controller.RPGController;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

@SuppressWarnings("deprecation")
public class RPGView extends Application implements Observer {
	private int mouseX = -1, mouseY = -1;
	private boolean turnOption;
	private RPGController controller;
	private Rectangle[][] backgroundRectangles;
	private GridPane gridPane;

	@Override
	public void start(Stage stage) throws Exception {
		// Create the controller this view is based on
		this.controller =  new RPGController(this);
		
		
		// Create the borderpane and a set of grids inside it
		// We don't care what color each rectangle is, yet
		// That will be updated by the model soon
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: black;");
		backgroundRectangles = new Rectangle[Model.MAP_SIZE][Model.MAP_SIZE];

		gridPane = new GridPane();
		for (int x = 0; x < Model.MAP_SIZE; x++) {
			for (int y = 0; y < Model.MAP_SIZE; y++) {
				StackPane stackPane = new StackPane();
				//Rectangle rect = new Rectangle(64, 64);
				Rectangle background = new Rectangle(40, 40);
				background.setFill(Color.BLACK);
				stackPane.getChildren().add(background);
				//stackPane.getChildren().add(rect);
				backgroundRectangles[x][y] = background;
				gridPane.add(stackPane, x, y);
			}
		}
		gridPane.setOnMouseClicked(event -> gridClicked(event));
		root.setCenter(gridPane);
	

		createOptionsBar(root);

		
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
	
	private void createOptionsBar(BorderPane root) {
		GridPane optionsBar = new GridPane();
		root.setBottom(optionsBar);
		
		// Two options
		// Either build or to move pieces
		for (int i = 0; i < 1; i++) {
			ColumnConstraints colConstraint = new ColumnConstraints();
			colConstraint.setPercentWidth(50);
			colConstraint.setHalignment(HPos.CENTER);
			optionsBar.getColumnConstraints().add(colConstraint);
		}
		
		Button buildOption = new Button();
		buildOption.setText("Build");
		// When this button is clicked, turnOption is set to true
		// If turnOption is set to true, it means the player
		// is trying to build at whatever tile they click
		buildOption.setOnAction((event) -> {this.turnOption = true;});
		
		Button moveUnitOption = new Button();
		moveUnitOption.setText("Move Unit");
		// When this button is clicked, turnOption is set to false
		// So now it means the player wants to move a unit
		// If later on we see turnOption is set to false, we know
		// we should let the player select two tiles in succession
		moveUnitOption.setOnAction((event) -> {this.turnOption = false;});
			
		optionsBar.add(buildOption, 0, 0);
		optionsBar.add(moveUnitOption, 1, 0);
		
	}

	private void gridClicked(MouseEvent event) {
		// When we get here, we can use turnOption to see what the user
		// wants to do. Based on that we can let them build on this rectangle,
		//or prompt them to choose another rectangle
		
		int mouseX = (int) ((event.getSceneX()) / 40 );
		int mouseY = (int) ((event.getSceneY()) / 40);
		
		
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
		Tile[][] map = (Tile[][]) arg;
		for(int i=0; i< Model.MAP_SIZE; i++) {
			for(int j=0; j< Model.MAP_SIZE; j++) {
				Tile current = map[i][j];			
				if(current.getLandType().equals(Tile.DRY_LAND)) {
					this.backgroundRectangles[i][j].setFill(Color.GREEN);
				}else if(current.getLandType().equals(Tile.WATER)) {
					this.backgroundRectangles[i][j].setFill(Color.BLUE);
				}
			}
		}
	}

}
