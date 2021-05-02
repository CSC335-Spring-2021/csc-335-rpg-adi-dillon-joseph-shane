package model;

public class Nation {
	public String name;
	public int movesLeft;
	public Nation enemyNation;
	private final boolean isAI;

	public Nation(String name, boolean isAI) {
		this.name = name;
		this.isAI = isAI;
	}

	public void takeTurn() {
		if (!isAI) {
			return;
		}

		// AI functionality
		// See if there's another unit that you can attack
		// See if you have any settlers to make a city
		// See if you have a city to build units with
		// Move a unit randomly
	}
}
