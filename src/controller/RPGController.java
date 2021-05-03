package controller;

import java.util.Observer;

import city.City;
import model.Model;
import model.Nation;
import model.Tile;
import units.Archer;
import units.FootSoldier;
import units.Settler;
import units.Unit;

@SuppressWarnings("deprecation")
public class RPGController {
	private final Model model;

	public RPGController(Observer view) {
		model = new Model(view);
	}

	public boolean selectUnit(int col, int row) {
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

	public void buildCity(int col, int row) {
		Tile tile = this.model.getTileAt(col, row);
		Nation currentTurn = model.getCurrentTurn();

		tile.setUnit(null); // Remove the settler
		tile.setNation(currentTurn); // Land now belongs to current turn user
		City city = new City(currentTurn.name + " city", 0, currentTurn);
		tile.setCity(city);
		model.endTurn();
		this.model.updateView();
	}

	public boolean canBuildCity(int col, int row) {
		Tile tile = this.model.getTileAt(col, row);
		return tile.getLandType().equals(Tile.DRY_LAND) && tile.getUnit() instanceof Settler && tile.getCity() == null;
	}

	public boolean canAddUnit(int col, int row) {
		Tile tile = this.model.getTileAt(col, row);
		Nation tileNation = tile.getNation();
		return this.model.getCurrentTurn() == tileNation && this.model.getTileAt(row, col).getCity() != null;
	}

	public void addUnit(int col, int row, String type) {
		Tile tile = this.model.getTileAt(col, row);
		if (tile.getUnit() == null) {

			if (type.equals("settler")) {
				tile.setUnit(new Settler(col, row, this.model.getCurrentTurn()));
			} else if (type.equals("foot_soilder")) {
				tile.setUnit(new FootSoldier(col, row, this.model.getCurrentTurn()));
			} else if (type.equals("archer")) {
				tile.setUnit(new Archer(col, row, this.model.getCurrentTurn()));
			}
			model.endTurn();
			this.model.updateView();
		}
	}

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
		System.out.println("Move length: " + moveLength + " Nation moves left " + model.getCurrentTurn().movesLeft);
		// Move length is too large, ignore move
		// Selected unit doesn't exist
		if (fromUnit == null) {
			System.out.println("Need to move a unit (" + fromCol + ", " + fromRow + ")");
			return false; // Need to move an actual unit
		}
		// Unit moved not own unit, ignore
		else if (fromUnit.getNation() != model.getCurrentTurn()) {
			return false;
		}else if(toTile.getLandType().equals(Tile.WATER)) {
			return false;
		}
		// Unit moved to another unit, attack or ignore
		else if (toUnit != null) {
			if (fromUnit.getAttackRange() >= moveLength) {
				if (toTile.getNation() == model.getCurrentTurn()) {
					System.out.println("Cannot attack own unit");
					return false; // Cannot attack own pieces
				} else {
					toUnit.setHealth(toUnit.getHealth() - fromUnit.getAttackPoints() / toUnit.getDefensePoints());
					System.out.println(toUnit.getHealth());
					// Unit is killed
					if (toUnit.getHealth() <= 0) {
						toUnit.getNation().getUnitList().remove(toUnit);
						toTile.setUnit(null);
					}
					return true;
				}
			} else {
				System.out.println("Out of attack range");
				return false;
			}

		}
		// Normal unit movement
		else {
			// Move unit
			if (model.getCurrentTurn().movesLeft < moveLength) {
				System.out.println("Move is too far");
				return false;
			}
			toTile.setUnit(fromUnit);
			fromTile.setUnit(null);
			toTile.setNation(model.getCurrentTurn());
			fromUnit.setPositition(toCol, toRow);
			return true;
		}
	}

	/*
	 * Ends the turn for the nation, and calls the other nation to take their turn
	 */
	public void endTurn() {
		model.endTurn();
		this.model.updateView();

		takeTurn();
	}

	/*-
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
				System.out.println("AI has built a settler");
				buildCity(friendlyUnit.getX(), friendlyUnit.getY());
				endTurn();
				return;
			}
		}

		// See if you have a city to build units with
		// TODO (cities should build units before this is done)

		// Move a unit randomly
		for (Unit friendlyUnit : model.getCurrentTurn().getUnitList()) {
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
		System.out.println("AI has no moves");
		endTurn();
	}

	public Tile getTile(int col, int row) {
		return model.getTileAt(row, col);
	}

	public void updateView() {
		this.model.updateView();
	}
}
