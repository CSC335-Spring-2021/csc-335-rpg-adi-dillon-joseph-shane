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
		final Unit selectedUnit = model.getTileAt(row, col).getUnit();
		if (selectedUnit == null) {
			return false;
		} else if (selectedUnit.getNation() != model.getCurrentTurn()) {
			return false;
		} else {
			model.getCurrentTurn().movesLeft = selectedUnit.getMovesPerTurn();
			return true;
		}
	}

	public void buildCity(int col, int row) {
		Tile tile = this.model.getTileAt(row, col);
		Nation currentTurn = model.getCurrentTurn();

		tile.setUnit(null); // Remove the settler
		tile.setNation(currentTurn); // Land now belongs to current turn user
		City city = new City(currentTurn.name + " city", 0, currentTurn);
		tile.setCity(city);
		model.endTurn();
		this.model.updateView();
	}

	public boolean canBuildCity(int col, int row) {
		Tile tile = this.model.getTileAt(row, col);
		return tile.getLandType().equals(Tile.DRY_LAND) && tile.getUnit() instanceof Settler
				&& tile.getNation() == null;
	}

	public boolean canAddUnit(int col, int row) {
		Tile tile = this.model.getTileAt(row, col);
		Nation tileNation = tile.getNation();
		return this.model.getCurrentTurn() == tileNation && this.model.getTileAt(row, col).getCity() != null;
	}

	public void addUnit(int col, int row, String type) {
		Tile tile = this.model.getTileAt(row, col);
		if (tile.getUnit() == null) {

			if (type.equals("settler")) {
				tile.setUnit(new Settler(this.model.getCurrentTurn()));
			} else if (type.equals("foot_soilder")) {
				tile.setUnit(new FootSoldier(this.model.getCurrentTurn()));
			} else if (type.equals("archer")) {
				tile.setUnit(new Archer(this.model.getCurrentTurn()));
			}
			model.endTurn();
			this.model.updateView();
		}
	}

	public boolean moveUnit(int fromCol, int fromRow, int toCol, int toRow) {
		final Tile fromTile = model.getTileAt(fromRow, fromCol);
		final Tile toTile = model.getTileAt(toRow, toCol);
		final Unit fromUnit = fromTile.getUnit();
		final Unit toUnit = toTile.getUnit();

		int moveLength = Math.abs(fromCol - toCol) + Math.abs(fromRow - toRow);
		System.out.println("Move length: " + moveLength + " Nation moves left " + model.getCurrentTurn().movesLeft);
		// Move length is too large, ignore move
		if (model.getCurrentTurn().movesLeft < moveLength) {
			return false;
		}
		// Unit moved not own unit, ignore
		else if (fromUnit.getNation() != model.getCurrentTurn()) {
			return false;
		}
		// Unit moved to another unit, attack or ignore
		else if (toUnit != null) {
			if (fromUnit.getAttackRange() >= moveLength) {
				if (toTile.getNation() == model.getCurrentTurn()) {
					model.endTurn();
					return false; // Cannot attack own pieces
				} else {
					toUnit.setHealth(toUnit.getHealth() - fromUnit.getAttackPoints() / toUnit.getDefensePoints());
					System.out.println(toUnit.getHealth());
					// Unit is killed
					if (toUnit.getHealth() <= 0) {
						toTile.setUnit(null);
					}
					model.endTurn();
					this.model.updateView();
					return true;
				}
			} else {
				return false;
			}

		}
		// Normal unit movement
		else {
			// Move unit
			toTile.setUnit(fromUnit);
			fromTile.setUnit(null);
			toTile.setNation(model.getCurrentTurn());
			this.model.updateView();
			model.endTurn();
			return true;
		}
	}

	public Tile getTile(int col, int row) {
		return model.getTileAt(row, col);
	}

	public void updateView() {
		this.model.updateView();
	}
}
