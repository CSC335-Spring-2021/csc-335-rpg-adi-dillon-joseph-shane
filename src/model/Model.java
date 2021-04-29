package model;

import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class Model extends Observable {

	private Tile[][] map;
	private int mapSize;
	
	public Model(Observer view) {
		// Create a 10x10 map where every tile
		// has no nation affiliation and is dry land
		this.addObserver(view);
		this.mapSize = 10;
		this.map = new Tile[mapSize][mapSize];
		for(int i=0; i< mapSize; i++) {
			for(int j=0; j< mapSize; j++) {
				this.map[i][j] = new Tile("", Tile.DRY_LAND);
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
}
