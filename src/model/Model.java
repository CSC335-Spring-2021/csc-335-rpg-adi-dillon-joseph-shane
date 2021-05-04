package model;

import java.util.Observable;
import java.util.Observer;

import units.FootSoldier;
import units.Settler;

@SuppressWarnings("deprecation")
public class Model extends Observable {

	private Tile[][] map;
	public static final int MAP_SIZE = 15;
	public static final Nation BLUE_NATION = new Nation("Blue Nation", false);
	public static final Nation RED_NATION = new Nation("Red Nation", true);
	private Nation currentTurn;

	public Model(Observer view) {
		// Set up nations
		BLUE_NATION.enemyNation = RED_NATION;
		RED_NATION.enemyNation = BLUE_NATION;
		currentTurn = BLUE_NATION;

		// Add view as observer of model
		this.addObserver(view);

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
		this.map[0][0].setLandType(Tile.DRY_LAND);
		this.map[0][0].setUnit(new Settler(0, 0, BLUE_NATION));
		this.map[0][1].setLandType(Tile.DRY_LAND);
		this.map[0][1].setUnit(new FootSoldier(1, 0, BLUE_NATION));

		this.map[14][14].setLandType(Tile.DRY_LAND);
		this.map[14][14].setUnit(new Settler(14, 14, RED_NATION));
		this.map[14][13].setLandType(Tile.DRY_LAND);
		this.map[14][13].setUnit(new FootSoldier(13, 14, RED_NATION));
	}

	public Tile getTileAt(int col, int row) {
		if (row > MAP_SIZE - 1 || row < 0 || col > MAP_SIZE - 1 || col < 0) {
			return null;
		} else {
			return this.map[row][col];
		}
	}

	public Nation getCurrentTurn() {
		return currentTurn;
	}

	public void endTurn() {
		currentTurn.addGold(5 * currentTurn.getCityCount());
		currentTurn = currentTurn.enemyNation;
	}

	public void updateView() {
		setChanged();
		notifyObservers(this.map);
	}
}
