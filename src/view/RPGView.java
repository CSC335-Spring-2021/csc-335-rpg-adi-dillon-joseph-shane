package view;

import java.util.Observable;
import java.util.Observer;

import controller.RPGController;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Model;
import model.Tile;
import units.Settler;

/**
 * The view for the game.
 * 
 * @author Adi, Joesph, Shane, Dillion
 *
 */
@SuppressWarnings("deprecation")
public class RPGView extends Application implements Observer {
	/** The borderpane root of the scene */
	private BorderPane root;
	/** The label for the player's gold amount */
	private Label playerGoldLabel;
	/** The label for the AI's gold amount */
	private Label AIGoldLabel;
	/** The last mouseX position */
	private int mouseX = -1;
	/** The last mouseY position */
	private int mouseY = -1;
	/** The controller this view relies on */
	private RPGController controller;
	/** The rectangles used for the map tiles */
	private Rectangle[][] backgroundRectangles;
	/** The city sprite for each index position */
	private ImageView[][] cityImages;
	/** The unit sprite for each index position */
	private ImageView[][] unitImages;
	/** The tile highlight sprite for each index position */
	private ImageView[][] highlightImages;
	/** The pane with actions associated with a tile that has a city */
	FlowPane cityActions;
	/** The pane with actions associated with a tile that has a settler */
	FlowPane settlerActions;
	/** The gridpane for the map */
	private GridPane gridPane;

	// Some images to use
	/** The image for the foot soldier */
	public static Image FOOT_SOLDIER;
	/** The image for the archer */
	public static Image ARCHER;
	/** The image for the settler */
	public static Image SETTLER;
	/** The tile highlight image */
	private final Image highlight = new Image("/res/highlight.png");

	/**
	 * Creates a new view with the appropriate images for each unit.
	 */
	public RPGView() {
		FOOT_SOLDIER = new Image("/res/Infantry.png", 40, 40, false, false);
		ARCHER = new Image("/res/Scout.png", 40, 40, false, false);
		SETTLER = new Image("/res/Settler.png", 40, 40, false, false);
	}

	/**
	 * Starts the application
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// Create the controller this view is based on
		this.controller = new RPGController(this);

		// Create the borderpane and a set of grids inside it
		// We don't care what color each rectangle is, yet
		// That will be updated by the model soon
		this.root = new BorderPane();
		root.setStyle("-fx-background-color: white;");
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
				backgroundRectangles[y][x] = background;

				ImageView cityView = new ImageView((Image) null);
				stackPane.getChildren().add(cityView);
				cityImages[y][x] = cityView;

				ImageView unitView = new ImageView((Image) null);
				stackPane.getChildren().add(unitView);
				unitImages[y][x] = unitView;

				ImageView highlightView = new ImageView((Image) null);
				stackPane.getChildren().add(highlightView);
				highlightImages[y][x] = highlightView;

				gridPane.add(stackPane, x, y);
			}
		}
		gridPane.setOnMouseClicked(event -> gridClicked(event));
		root.setCenter(gridPane);

		// Adding the gold amounts to the top
		addStatsBar(root);

		BorderPane actionMenu = new BorderPane();
		actionMenu.setPadding(new Insets(8, 8, 8, 8));
		actionMenu.setLeft(new Label("Actions: "));
		StackPane actionsStackPane = new StackPane();

		// The pane for settler and city actions
		settlerActions = new FlowPane();
		Button buildCity = new Button("Build City");
		buildCity.setOnMouseClicked(e -> createCity());
		settlerActions.getChildren().add(buildCity);
		actionsStackPane.getChildren().add(settlerActions);

		cityActions = new FlowPane();
		Button recruitSettler = new Button("Recruit Settler");
		recruitSettler.setOnMouseClicked(e -> recruitUnit("settler"));
		cityActions.getChildren().add(recruitSettler);
		Button recruitFootSoldier = new Button("Recruit Warrior");
		recruitFootSoldier.setOnMouseClicked(e -> recruitUnit("foot_soldier"));
		cityActions.getChildren().add(recruitFootSoldier);
		Button recruitArcher = new Button("Recruit Archer");
		recruitArcher.setOnMouseClicked(e -> recruitUnit("archer"));
		cityActions.getChildren().add(recruitArcher);
		actionsStackPane.getChildren().add(cityActions);

		actionMenu.setCenter(actionsStackPane);
		Button skipTurn = new Button("Skip turn");
		skipTurn.setOnMouseClicked(e -> this.controller.endTurn());
		actionMenu.setRight(skipTurn);
		root.setBottom(actionMenu);
		cityActions.setVisible(false);
		settlerActions.setVisible(false);

		// Finalizing
		stage.setTitle("RPG");
		Scene scene = new Scene(root);
		stage.setScene(scene);

		// The model in the controller knows what the map looks like
		// We can tell the controller to tell the map to
		// update the view
		this.controller.updateView();
		this.controller.takeTurn();

		stage.show(); // Show the stage
	}

	/**
	 * Create the stats bar at the top
	 * 
	 * @param root The root to add the stats bar to
	 */
	private void addStatsBar(BorderPane root) {
		GridPane statsBar = new GridPane();

		// We only need 2 columns
		for (int i = 0; i < 2; i++) {
			ColumnConstraints colConstraint = new ColumnConstraints();
			colConstraint.setPercentWidth(50);
			colConstraint.setHalignment(HPos.CENTER);
			statsBar.getColumnConstraints().add(colConstraint);
		}

		Label playerText = new Label("You");
		playerText.setFont(new Font(20));

		this.playerGoldLabel = new Label("Gold: " + String.valueOf(this.controller.getPlayerGold()));
		this.playerGoldLabel.setFont(new Font(15));

		statsBar.add(playerText, 0, 0);
		statsBar.add(this.playerGoldLabel, 0, 1);

		Label AIText = new Label("AI");
		AIText.setFont(new Font(20));

		this.AIGoldLabel = new Label("Gold: " + String.valueOf(this.controller.getAIGold()));
		this.AIGoldLabel.setFont(new Font(15));

		statsBar.add(AIText, 1, 0);
		statsBar.add(this.AIGoldLabel, 1, 1);

		root.setTop(statsBar);
	}

	/**
	 * Selects a tile and highlights it if the tile selected is valid
	 * 
	 * @param mouseX The x coordinate from javafx
	 * @param mouseY The y vlae from javafx
	 */
	private void selectTile(int mouseX, int mouseY) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		highlightImages[this.mouseY][this.mouseX].setImage(highlight);
		Tile tile = controller.getTile(this.mouseX, this.mouseY);
		if (tile.getCity() != null && tile.getCity().getNation() == Model.BLUE_NATION) {
			cityActions.setVisible(true);
			settlerActions.setVisible(false);
		} else if (tile.getUnit() instanceof Settler && tile.getUnit().getNation() == Model.BLUE_NATION) {
			cityActions.setVisible(false);
			settlerActions.setVisible(true);
		} else {
			cityActions.setVisible(false);
			settlerActions.setVisible(false);
		}
	}

	/**
	 * Deselects the previously selected tile
	 */
	private void deselectTile() {
		cityActions.setVisible(false);
		settlerActions.setVisible(false);
		if (this.mouseX != -1 && this.mouseY != -1) {
			highlightImages[this.mouseY][this.mouseX].setImage((Image) null);
		}
		this.mouseX = -1;
		this.mouseY = -1;
	}

	/**
	 * Called when a tile is clciekd
	 * 
	 * @param event The mouse event
	 */
	private void gridClicked(MouseEvent event) {
		int mouseX = (int) ((event.getSceneX()) / 40);
		int mouseY = (int) ((event.getSceneY() - 51) / 40);

		if (this.mouseX == mouseX && this.mouseY == mouseY) {
			deselectTile();
		} else if (this.mouseX != -1 && this.mouseY != -1) {
			if (controller.moveUnit(this.mouseX, this.mouseY, mouseX, mouseY)) {
				deselectTile();
				controller.endTurn();
			} else if (controller.selectUnit(mouseX, mouseY)) {
				deselectTile();
				selectTile(mouseX, mouseY);
			} else {
				deselectTile();
			}
		} else {
			if (controller.selectUnit(mouseX, mouseY)) {
				selectTile(mouseX, mouseY);
			} else {
				deselectTile();
			}
		}
	}

	/**
	 * Creates a city at the selected tile, if it is valid to. Asks for confirmation
	 */
	private void createCity() {
		if (this.controller.canBuildCity(mouseX, mouseY)) {
			Alert confirm = new Alert(AlertType.CONFIRMATION);
			confirm.setContentText(
					"Are you sure you'd like to build a city here for " + Model.BLUE_NATION.getCityCost() + " gold?");
			confirm.showAndWait().ifPresent(response -> {
				if (response.getText().equals("OK")) {
					this.controller.buildCity(this.mouseX, this.mouseY);
					highlightImages[this.mouseY][this.mouseX].setImage((Image) null);
					this.mouseX = -1;
					this.mouseY = -1;
					cityActions.setVisible(false);
					settlerActions.setVisible(false);
					controller.endTurn();
				}
			});
		} else {
			Alert error = new Alert(AlertType.ERROR);
			error.setContentText("You can't build a city here!");
			error.show();
		}
	}

	/**
	 * Recruits a unit of the specified type at the selected tile, if valid. Asks
	 * for confirmation.
	 * 
	 * @param unit The unit to recruit
	 */
	private void recruitUnit(String unit) {
		if (this.controller.canAddUnit(mouseX, mouseY)) {
			Alert confirm = new Alert(AlertType.CONFIRMATION);
			confirm.setContentText("Are you sure you'd like to recruit a " + unit + "?");
			confirm.showAndWait().ifPresent(response -> {
				if (response.getText().equals("OK")) {
					this.controller.addUnit(mouseX, mouseY, unit);
					highlightImages[this.mouseY][this.mouseX].setImage((Image) null);
					this.mouseX = -1;
					this.mouseY = -1;
					cityActions.setVisible(false);
					settlerActions.setVisible(false);
					controller.endTurn();
				}
			});
		} else {
			Alert error = new Alert(AlertType.ERROR);
			error.setContentText("You can't recruit here!");
			error.show();
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
		System.out.println(arg);
		if (arg == null) {
			Alert gameOver = new Alert(AlertType.CONFIRMATION);
			gameOver.setHeaderText("Game Over!");
			gameOver.setTitle("Game Over!");
			if (Model.RED_NATION.getUnitList().isEmpty()) {
				gameOver.setContentText("You Won!");
			} else {
				gameOver.setContentText("You Lost!");
			}
			gameOver.setGraphic(null);
			((Button) gameOver.getDialogPane().lookupButton(ButtonType.OK)).setText("New Game");
			((Button) gameOver.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Quit");
			gameOver.showAndWait().ifPresent(response -> {
				if (response.getText().equals("OK")) {
					this.controller.newGame();
					this.controller.updateView();
					this.controller.takeTurn();
				} else if (response.getText().equals("Cancel")) {
					System.exit(0);
				}
			});
		} else {
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

			this.playerGoldLabel.setText("Gold: " + String.valueOf(this.controller.getPlayerGold()));
			this.AIGoldLabel.setText("Gold: " + String.valueOf(this.controller.getAIGold()));
		}
	}

}
