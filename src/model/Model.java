package model;

public class Model {

	private Tile[][] map;
	private int mapSize;
	
	public Model() {
		// Create a 10x10 map where every tile
		// has no nation affiliation and is dry land
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
