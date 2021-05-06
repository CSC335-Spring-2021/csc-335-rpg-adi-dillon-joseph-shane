package controller;

import java.util.Observer;

import model.City;
import model.Model;
import model.Nation;
import model.Tile;
import units.Archer;
import units.FootSoldier;
import units.Settler;
import units.Unit;

/**
 * This class represents the controller for the game. It provides various
 * methods that the view can use to get and set information about the game
 * 
 * @author Adi, Joesph, Shane, Dillion
 */
@SuppressWarnings("deprecation")
public class RPGController {
	/** The model this controller operates on*/
	private final Model model;
	
	/**
	 * Creates an instance of the controller
	 * 
	 * @param view The view that should observe the model
	 */
	public RPGController(Observer view) {
		model = new Model(view);
	}

	/**
	 * Checks if the given tile can be selected by whoever's turn it is
	 * 
	 * @param col The column index of the tile
	 * @param row The row index of the tile
	 * @return true if th current tile can be selected
	 */
	public boolean selectUnit(int col, int row) {
		if (model.getTileAt(col, row).getCity() != null
				&& model.getTileAt(col, row).getCity().getNation() == model.getCurrentTurn()) {
			return true;
		}
		final Unit selectedUnit = model.getTileAt(col, row).getUnit();
		if (selectedUnit == null) {
			System.out.println("Selected unit is null");
			return false;
		} else if (selectedUnit.getNation() != model.getCurrentTurn()) {
			System.out.println("Selected unit is not correct nation");
			return false;
		} else {
			model.getCurrentTurn().movesLeft = selectedUnit.getMovesPerTurn();
			return true;
		}
	}

	/**
	 * Get the player's gold amount
	 * @return The amount of gold the player has
	 */
	public int getPlayerGold() {
		return Model.BLUE_NATION.getGoldAmount();
	}

	/**
	 * Get the AI's gold amount
	 * @return The amount of gold the AI has
	 */
	public int getAIGold() {
		return Model.RED_NATION.getGoldAmount();
	}

	/**
	 * Builds a city on the tile at the given coordinates.
	 * Make sure the city can be built before this is called.
	 * 
	 * @param col The column index of the tile
	 * @param row The row index of the tile
	 */
	public void buildCity(int col, int row) {
		Tile tile = this.model.getTileAt(col, row);
		Nation currentTurn = model.getCurrentTurn();

		tile.getUnit().getNation().getUnitList().remove(tile.getUnit());
		tile.setUnit(null); // Remove the settler
		tile.setNation(currentTurn); // Land now belongs to current turn user
		City city = new City(col, row, currentTurn.name + " city", currentTurn);
		tile.setCity(city);
		currentTurn.removeGold(currentTurn.getCityCost());
		currentTurn.increaseCityCount(1);
	}

	/**
	 * Checks if a city can be built by whoever's turn it is on the
	 * given tile
	 * 
	 * @param col The column index of the tile
	 * @param row The row index of the tile
	 * @return true if a city can be built and false otherwise
	 */
	public boolean canBuildCity(int col, int row) {
		Tile tile = this.model.getTileAt(col, row);
		System.out.println(tile.getLandType());
		return tile.getLandType().equals(Tile.DRY_LAND) && tile.getUnit() instanceof Settler && tile.getCity() == null
				&& this.model.getCurrentTurn().getGoldAmount() >= this.model.getCurrentTurn().getCityCost();
	}

	/**
	 * Checks if a unit can be added by whoever's turn it is on the
	 * given tile
	 * 
	 * @param col The column index of the tile
	 * @param row The row index of the tile
	 * @return true if it can be added and false otherwise
	 */
	public boolean canAddUnit(int col, int row) {
		Tile tile = this.model.getTileAt(col, row);
		Nation tileNation = tile.getNation();
		return this.model.getCurrentTurn() == tileNation && tile.getUnit() == null;
	}


	/**
	 * Adds a unit of the specified type at the specified index. Make sure to
	 * check that a unit can be added before calling this
	 * 
	 * @param col The column index of the tile
	 * @param row The row index of the tile
	 * @param type The type of unit to build. "settler", "foot_soilder", or "archer".
	 * @return true if the unit was added and false otherwise
	 */
	public boolean addUnit(int col, int row, String type) {
		Tile tile = this.model.getTileAt(col, row);
		Nation currentTurn = this.model.getCurrentTurn();
		if (tile.getUnit() == null) {
			Unit unit = null;
			if (type.equals("settler")) {
				unit = new Settler(col, row, currentTurn);
			} else if (type.equals("foot_soldier")) {
				unit = new FootSoldier(col, row, currentTurn);
			} else if (type.equals("archer")) {
				unit = new Archer(col, row, currentTurn);
			}
			if (unit.getBuildCost() <= currentTurn.getGoldAmount()) {
				tile.setUnit(unit);
				currentTurn.removeGold(unit.getBuildCost());
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Moves a unit from one tile to another
	 * 
	 * @param fromCol The current column index
	 * @param fromRow The current row index
	 * @param toCol The column index to  move to
	 * @param toRow The row index to move to
	 * @return true if the unit moved and false otherwise
	 */
	public boolean moveUnit(int fromCol, int fromRow, int toCol, int toRow) {
		final Tile fromTile = model.getTileAt(fromCol, fromRow);
		final Tile toTile = model.getTileAt(toCol, toRow);
		// To tile out of bounds
		if (toTile == null) {
			return false;
		}
		final Unit fromUnit = fromTile.getUnit();
		final Unit toUnit = toTile.getUnit();

		int moveLength = Math.abs(fromCol - toCol) + Math.abs(fromRow - toRow);
		// Move length is too large, ignore move
		// Selected unit doesn't exist
		if (fromUnit == null) {
			return false; // Need to move an actual unit
		}
		// Unit moved not own unit, ignore
		else if (fromUnit.getNation() != model.getCurrentTurn()) {
			return false;
		} else if (toTile.getLandType().equals(Tile.WATER)) {
			return false;
		}
		// Unit moved to another unit, attack or ignore
		else if (toUnit != null) {
			if (fromUnit.getAttackRange() >= moveLength) {
				if (toUnit.getNation() == model.getCurrentTurn()) {
					return false; // Cannot attack own pieces
				} else {
					toUnit.setHealth(toUnit.getHealth() - fromUnit.getAttackPoints() / toUnit.getDefensePoints());
					// Unit is killed
					if (toUnit.getHealth() <= 0) {
						toUnit.getNation().getUnitList().remove(toUnit);
						toTile.setUnit(null);
					}
					return true;
				}
			} else {
				return false;
			}

		}
		// Normal unit movement
		else {
			// Move unit
			if (model.getCurrentTurn().movesLeft < moveLength) {
				return false;
			}
			toTile.setUnit(fromUnit);
			fromTile.setUnit(null);
			toTile.setNation(model.getCurrentTurn());
			fromUnit.setPositition(toCol, toRow);
			return true;
		}
	}

	/**
	 * Ends the turn for the nation, and calls the other nation to take their turn
	 */
	public void endTurn() {
		if (isGameOver()) {
			this.model.gameOver();
		}
		model.endTurn();
		this.model.updateView();

		takeTurn();
	}

	/**
	 * If the nation is not AI controlled, does nothing
	 * 
	 * If the nation is AI controlled, will first check:
	 * 1. If there is other unit to attack 
	 * 2. If can settle city 
	 * 3. If can build anything 
	 * 4. Move random piece
	 * 5. Skip turn
	 * 
	 * If any of the above are true, performs that action for that turn, and then
	 * returns.
	 */
	public void takeTurn() {
		if (!model.getCurrentTurn().isAI()) {
			return;
		}

		// AI functionality
		// See if there's another unit that you can attack
		for (Unit friendlyUnit : model.getCurrentTurn().getUnitList()) {
			for (Unit enemyUnit : model.getCurrentTurn().enemyNation.getUnitList()) {
				selectUnit(friendlyUnit.getX(), friendlyUnit.getY());
				if (moveUnit(friendlyUnit.getX(), friendlyUnit.getY(), enemyUnit.getX(), enemyUnit.getY())) {
					System.out.println("AI has attacked another unit");
					endTurn();
					return;
				}
			}
		}

		// See if you have any settlers to make a city
		for (Unit friendlyUnit : model.getCurrentTurn().getUnitList()) {
			if (friendlyUnit instanceof Settler && canBuildCity(friendlyUnit.getX(), friendlyUnit.getY())) {
				System.out.println("AI has built a city");
				buildCity(friendlyUnit.getX(), friendlyUnit.getY());
				endTurn();
				return;
			}
		}

		// See if you have a city to build units with
		for (City city : model.getCurrentTurn().getCitiesList()) {
			if (addUnit(city.getX(), city.getY(), "foot_soldier")) {
				System.out.println("AI has built a unit");
				endTurn();
				return;
			} else if (addUnit(city.getX(), city.getY(), "archer")) {
				System.out.println("AI has built a unit");
				endTurn();
				return;
			} else if (addUnit(city.getX(), city.getY(), "settler")) {
				System.out.println("AI has built a unit");
				endTurn();
				return;
			}
		}

		// Move a unit randomly
		for (Unit friendlyUnit : model.getCurrentTurn().getUnitList()) {
			for (int i = 0; i < 4; i++) {
				int moveX = (int) ((Math.random() * 2 - 1) * friendlyUnit.getMovesPerTurn());
				int moveY = (int) ((Math.random() * 2 - 1) * friendlyUnit.getMovesPerTurn());
				selectUnit(friendlyUnit.getX(), friendlyUnit.getY());
				if (moveUnit(friendlyUnit.getX(), friendlyUnit.getY(), friendlyUnit.getX() + moveX,
						friendlyUnit.getY() + moveY)) {
					System.out.println("AI has moved a unit");
					endTurn();
					return;
				}
			}
		}
		System.out.println("AI has no moves");
		endTurn();
	}

	/**
	 * Checks if the game is over. The game is over when either the AI or the player
	 * has no units left.
	 * 
	 * @return true if the game is over and false otherwise 
	 */
	private boolean isGameOver() {
		System.out.println(Model.RED_NATION.getUnitList());
		return Model.RED_NATION.getUnitList().isEmpty() || Model.BLUE_NATION.getUnitList().isEmpty();
	}

	/**
	 * Creates a new game
	 */
	public void newGame() {
		this.model.newGame();
	}

	/**
	 * Gets the tile at the given index
	 * 
	 * @param col The column index
	 * @param row The row index
	 * @return The tile
	 */
	public Tile getTile(int col, int row) {
		return model.getTileAt(col, row);
	}

	/**
	 * Asks the model to update the view
	 */
	public void updateView() {
		this.model.updateView();
	}
}
