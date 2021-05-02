package model;

import java.util.Observable;
import java.util.Observer;

import units.FootSoldier;
import units.Settler;

@SuppressWarnings("deprecation")
public class Model extends Observable {

	private Tile[][] map;
	public static final int MAP_SIZE = 15;
	private Nation currentTurn;

	public Model(Observer view) {
		// Set up nations
		Nation blueNation = new Nation("Blue Nation");
		Nation redNation = new Nation("Red Nation");
		blueNation.enemyNation = redNation;
		redNation.enemyNation = blueNation;
		currentTurn = blueNation;

		// Add view as observer of model
		this.addObserver(view);

		// Create a map where every tile
		// has no nation affiliation and is dry land
		this.map = new Tile[MAP_SIZE][MAP_SIZE];
		for (int i = 0; i < MAP_SIZE; i++) {
			for (int j = 0; j < MAP_SIZE; j++) {
				// Some tiles are dry land, and others are water
				// It should be asy to add more tile variants here
				int random = (int) Math.floor(Math.random() * 2);
				switch (random) {
				case 0:
					this.map[i][j] = new Tile(null, Tile.WATER);
					break;
				case 1:
					this.map[i][j] = new Tile(null, Tile.DRY_LAND);
					break;
				}
			}
		}
		this.map[0][0].setUnit(new FootSoldier(blueNation));
		this.map[6][4].setUnit(new Settler(redNation));
	}

	public Tile getTileAt(int row, int col) {
		if (row > MAP_SIZE - 1 || row < 0 || col > MAP_SIZE - 1 || col < 0) {
			throw new IndexOutOfBoundsException("Invalid map index");
		} else {
			return this.map[row][col];
		}
	}

	public Nation getCurrentTurn() {
		return currentTurn;
	}

	public void endTurn() {
		currentTurn = currentTurn.enemyNation;
	}

	public void updateView() {
		setChanged();
		notifyObservers(this.map);
	}
}
