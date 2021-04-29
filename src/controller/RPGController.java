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

	public void moveUnit(int fromCol, int fromRow, int toCol, int toRow) {
		final Tile fromTile = model.getTileAt(fromRow, fromCol);
		final Tile toTile = model.getTileAt(toRow, toCol);
		final Unit fromUnit = fromTile.getUnit();
		final Unit toUnit = toTile.getUnit();

		// Unit moved not own unit, ignore
		if (fromUnit.getNation() != model.getCurrentTurn()) {
			return;
		}
		// Unit moved to another unit, attack or ignore
		else if (toUnit != null) {
			if (toTile.getNation() == model.getCurrentTurn()) {
				return; // Cannot attack own pieces
			} else {
				toUnit.setHealth(toUnit.getHealth() - fromUnit.getAttackPoints() / toUnit.getDefensePoints());
			}
		}
		// Normal unit movement
		else {
			// Check model turn
			if (model.getCurrentTurn().movesLeft == -1) {
				model.getCurrentTurn().movesLeft = fromUnit.getMovesPerTurn();
			}
			// Move unit
			toTile.setUnit(fromUnit);
			fromTile.setUnit(null);
			toTile.setNation(model.getCurrentTurn());
			model.getCurrentTurn().movesLeft--;
			// Check if turn is over
			if (model.getCurrentTurn().movesLeft == 0) {
				model.endTurn();
			}
		}
	}

	public Tile getTile(int col, int row) {
		return model.getTileAt(row, col);
	}
}
