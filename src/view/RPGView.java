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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
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
	private Stage stage;
	private BorderPane root;
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
		this.stage = stage;

		// Create the borderpane and a set of grids inside it
		// We don't care what color each rectangle is, yet
		// That will be updated by the model soon
		this.root = new BorderPane();
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
		this.removeOptionsBar(); // Sanity check

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
			if(this.controller.canAddUnit(mouseY, mouseX)) {
				ButtonType addUnit = new ButtonType("Add Unit", ButtonData.OK_DONE);
				ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
				Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to adda unit?", addUnit, cancel);
				alert.showAndWait().ifPresent(response -> {
					if(response.getText().equals("Add Unit")) {
						this.createSettlerOptionsBar(mouseY, mouseX);
					}
				});
			}
			else {
				if(this.controller.canBuildCity(mouseY, mouseX)) {
					this.createCityOptionsBar(mouseY, mouseX);			
				}
			}
		}
	}
	
	private void createCityOptionsBar(int mouseY, int mouseX) {
		GridPane optionsBar = new GridPane();

		for (int i = 0; i < 1; i++) {
			ColumnConstraints colConstraint = new ColumnConstraints();
			colConstraint.setPercentWidth(100);
			colConstraint.setHalignment(HPos.CENTER);
			optionsBar.getColumnConstraints().add(colConstraint);
		}

		Button buildCity = new Button();
		buildCity.setText("Build City (-500 gold)");
		buildCity.setOnAction((event) -> {
			Alert confirm =  new Alert(AlertType.CONFIRMATION);
			confirm.setContentText("Are you sure you'd like to build a city here for 500 gold?");
			confirm.showAndWait().ifPresent(response -> {
				if(response.getText().equals("OK")) {
					this.controller.buildCity(mouseY, mouseX);
					this.removeOptionsBar();
				}
			});
		});


		optionsBar.add(buildCity, 0, 0);
		
		this.root.setBottom(optionsBar);
		this.stage.sizeToScene();

	}
	
	private void createSettlerOptionsBar(int mouseY, int mouseX) {
		GridPane optionsBar = new GridPane();

		for (int i = 0; i < 3; i++) {
			ColumnConstraints colConstraint = new ColumnConstraints();
			colConstraint.setPercentWidth(33.33);
			colConstraint.setHalignment(HPos.CENTER);
			optionsBar.getColumnConstraints().add(colConstraint);
		}

		Button addSettler = new Button();
		addSettler.setText("Add Settler");
		addSettler.setOnAction((event) -> {
			this.controller.addUnit(mouseY, mouseX, "settler");
			this.removeOptionsBar(); 
		});
		
		Button addFootSoilder = new Button();
		addFootSoilder.setText("Add Foot Soilder");
		addFootSoilder.setOnAction((event) -> {
			this.controller.addUnit(mouseY, mouseX, "foot_soilder");
			this.removeOptionsBar(); 
		});
		
		Button addArcher = new Button();
		addArcher.setText("Add Archer");
		addArcher.setOnAction((event) -> {
			this.controller.addUnit(mouseY, mouseX, "archer");
			this.removeOptionsBar(); 
		});

		optionsBar.add(addSettler, 0, 0);
		optionsBar.add(addFootSoilder, 1, 0);
		optionsBar.add(addArcher, 2, 0);
		
		this.root.setBottom(optionsBar);
		this.stage.sizeToScene();

	}
	
	private void removeOptionsBar() {
		this.root.setBottom(null);
		this.stage.sizeToScene();
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
