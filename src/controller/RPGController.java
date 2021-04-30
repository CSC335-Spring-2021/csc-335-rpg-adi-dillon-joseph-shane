package controller;

import java.util.Observer;

import model.Model;
import model.Tile;
import units.Unit;

@SuppressWarnings("deprecation")
public class RPGController {
	private final Model model;

	public RPGController(Observer view) {
		model = new Model(view);
	}

	public boolean moveUnit(int fromCol, int fromRow, int toCol, int toRow) {
		final Tile fromTile = model.getTileAt(fromRow, fromCol);
		final Tile toTile = model.getTileAt(toRow, toCol);
		final Unit fromUnit = fromTile.getUnit();
		final Unit toUnit = toTile.getUnit();

		int moveLength = Math.abs(fromCol - toCol) + Math.abs(fromRow - toRow);
		// Move length is too large, ignore move
		if (model.getCurrentTurn().movesLeft >= moveLength) {
			return false;
		}
		// Unit moved not own unit, ignore
		else if (fromUnit.getNation() != model.getCurrentTurn()) {
			return false;
		}
		// Unit moved to another unit, attack or ignore
		else if (toUnit != null) {
			if (toTile.getNation() == model.getCurrentTurn()) {
				this.model.updateView();
				return true; // Cannot attack own pieces
			} else {
				toUnit.setHealth(toUnit.getHealth() - fromUnit.getAttackPoints() / toUnit.getDefensePoints());
				this.model.updateView();
				return true;
			}
		}
		// Normal unit movement
		else {
			// Check model moves
			if (model.getCurrentTurn().movesLeft == -1) {
				model.getCurrentTurn().movesLeft = fromUnit.getMovesPerTurn();
			}

			// Move unit
			toTile.setUnit(fromUnit);
			fromTile.setUnit(null);
			toTile.setNation(model.getCurrentTurn());
			model.getCurrentTurn().movesLeft -= moveLength;
			// Check if turn is over
			if (model.getCurrentTurn().movesLeft == 0) {
				model.endTurn();
			}
			this.model.updateView();
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
