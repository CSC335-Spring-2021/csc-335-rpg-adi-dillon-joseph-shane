package units;

import model.Model;
import model.Nation;
import view.RPGView;

/**
 * This class represents an archer unit.
 * 
 * @author Adi, Joesph, Shane, Dillion
 *
 */
public class Archer extends Unit {

	/**
	 * Create an archer
	 * @param x The column index
	 * @param y The row index
	 * @param nation The nation this unit is commanded by
	 */
	public Archer(int x, int y, Nation nation) {
		super(x, y, nation, 20.0, 0.75, 4, 3, nation == Model.BLUE_NATION ? RPGView.BLUE_ARCHER : RPGView.RED_ARCHER);
		this.buildCost = ARCHER_BUILD_COST;
	}

}
