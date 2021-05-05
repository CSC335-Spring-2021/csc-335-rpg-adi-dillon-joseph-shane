package model;

import java.util.Observable;
import java.util.Observer;

import units.FootSoldier;
import units.Settler;

/**
 * This class represents the model the game uses.
 * 
 * @author Adi, Joesph, Shane, Dillion
 *
 */
@SuppressWarnings("deprecation")
public class Model extends Observable {

	/** The map of tiles representing the game map */
	private Tile[][] map;
	/** The size of the grid map */
	public static final int MAP_SIZE = 15;
	/** The blue nation */
	public static Nation BLUE_NATION = new Nation("Blue Nation", false);
	/** The red nation */
	public static Nation RED_NATION = new Nation("Red Nation", true);
	/** The nation whose turn it is */
	private Nation currentTurn;

	/**
	 * Creates a model
	 * 
	 * @param view The vbiew that should observe this instance of the model
	 */
	public Model(Observer view) {
		this.addObserver(view);
		newGame();
	}

	/**
	 * Creates a new game
	 */
	public void newGame() {// Set up nations
		BLUE_NATION = new Nation("Blue Nation", false);
		RED_NATION = new Nation("Red Nation", true);
		BLUE_NATION.enemyNation = RED_NATION;
		RED_NATION.enemyNation = BLUE_NATION;
		currentTurn = BLUE_NATION;

		// Add view as observer of model

		// Create a map where every tile
		// has no nation affiliation and is dry land
		this.map = new Tile[MAP_SIZE][MAP_SIZE];
		for (int i = 0; i < MAP_SIZE; i++) {
			for (int j = 0; j < MAP_SIZE; j++) {
				// Some tiles are dry land, and others are water
				// It should be easy to add more tile variants here
				int random = (int) Math.floor(Math.random() * 3);
				switch (random) {
				case 0:
					this.map[i][j] = new Tile(null, Tile.WATER);
					break;
				case 1:
					this.map[i][j] = new Tile(null, Tile.DRY_LAND);
					break;
				case 2:
					this.map[i][j] = new Tile(null, Tile.DRY_LAND);
					break;
				}
			}
		}
		// Force set some tiles to dry land so our units always
		// start on dry land
		this.map[0][0].setLandType(Tile.DRY_LAND);
		this.map[0][0].setUnit(new Settler(0, 0, BLUE_NATION));
		this.map[0][1].setLandType(Tile.DRY_LAND);
		this.map[0][1].setUnit(new FootSoldier(1, 0, BLUE_NATION));

		this.map[14][14].setLandType(Tile.DRY_LAND);
		this.map[14][14].setUnit(new Settler(14, 14, RED_NATION));
		this.map[14][13].setLandType(Tile.DRY_LAND);
		this.map[14][13].setUnit(new FootSoldier(13, 14, RED_NATION));
	}

	/**
	 * Get the tile at the specified index
	 * 
	 * @param col The column index
	 * @param row Thr row index
	 * @return The tike at the index or null if it doesn't exist
	 */
	public Tile getTileAt(int col, int row) {
		if (row > MAP_SIZE - 1 || row < 0 || col > MAP_SIZE - 1 || col < 0) {
			return null;
		} else {
			return this.map[row][col];
		}
	}

	/**
	 * Gets the nation whose turn it is rightnow
	 * 
	 * @return The current nation
	 */
	public Nation getCurrentTurn() {
		return currentTurn;
	}

	/**
	 * Ends the turn of the current nation. It becomes the next natons turn when
	 * this method is called. The gold values for the nation whose turn is ending is
	 * also updated.
	 */
	public void endTurn() {
		currentTurn.addGold(5 * currentTurn.getCityCount());
		currentTurn = currentTurn.enemyNation;
	}

	/**
	 * Updates any observers that the map has changed.
	 */
	public void updateView() {
		setChanged();
		notifyObservers(this.map); // Send the map to the observers
	}

	/**
	 * The game is marked as over and observers are notified.
	 */
	public void gameOver() {
		setChanged();
		notifyObservers(null);
	}
}
