package model;

import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class Model extends Observable {

	private Tile[][] map;
	private int mapSize;
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
		
		// Create a 10x10 map where every tile
		// has no nation affiliation and is dry land
		this.mapSize = 10;
		this.map = new Tile[mapSize][mapSize];
		for(int i=0; i< mapSize; i++) {
			for(int j=0; j< mapSize; j++) {
				this.map[i][j] = new Tile(null, Tile.DRY_LAND);
			}
		}
	}
	
	public Tile getTileAt(int row, int col) {
		if(row> this.mapSize-1 || row<0 || col> this.mapSize-1 || col<0) {
			throw new IndexOutOfBoundsException("Invalid map index");
		}else {
			return this.map[row][col];
		}
	}
	
	public Nation getCurrentTurn() {
		return currentTurn;
	}
	
	public void endTurn() {
		currentTurn = currentTurn.enemyNation;
	}
}
