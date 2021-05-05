package units;

import model.Nation;
import view.RPGView;

/**
 * This class represents a foot soldier unit.
 * 
 * @author Adi, Joesph, Shane, Dillion
 *
 */
public class FootSoldier extends Unit {

	/**
	 * Create a foot soldier
	 * @param x The column index
	 * @param y The row index
	 * @param nation The nation this unit is commanded by
	 */
	public FootSoldier(int x, int y, Nation nation) {
		// health = 100 / defense
		super(x, y, nation, 25.0, 1, 2, 1, RPGView.FOOT_SOLDIER);
		this.buildCost = FOOT_SOLDIER_BUILD_COST;
	}

}
