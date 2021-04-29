package controller;

import java.util.Observer;

import model.Model;
import model.Tile;

@SuppressWarnings("deprecation")
public class RPGController {
	private final Model model;

	public RPGController(Observer view) {
		model = new Model(view);
	}

	public void moveUnit(int fromCol, int fromRow, int toCol, int toRow) {
		final Tile fromTile = model.getTileAt(fromRow, fromCol);
		final Tile toTile = model.getTileAt(toRow, toCol);

		if (fromTile.getUnit().getNation() != model.getCurrentTurn()) {
			// Exception: Must move one of your own pieces, ignore
		} else if (toTile.getUnit() != null) {
			// another unit is already in destination
			if(toTile.getNation() == model.getCurrentTurn()) {
				// Cannot attack own pieces, ignore
			} else {
				// Attack other piece, end turn
			}
		} else { // Normal unit movement
			// Check model turn
			if(model.getCurrentTurn().movesLeft == -1) {
				model.getCurrentTurn().movesLeft = fromTile.getUnit().getMovesPerTurn();
			}
			toTile.setUnit(fromTile.getUnit());
			fromTile.setUnit(null);
			toTile.setNation(model.getCurrentTurn());
			model.getCurrentTurn().movesLeft--;
			if(model.getCurrentTurn().movesLeft == 0) {
				// end turn
			}
		}
	}

	public Tile getTile(int col, int row) {
		return model.getTileAt(row, col);
	}
}
