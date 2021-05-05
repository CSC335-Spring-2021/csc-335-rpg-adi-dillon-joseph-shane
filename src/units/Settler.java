package units;

import model.Model;
import model.Nation;
import view.RPGView;

/**
 * This class represents a settler unit.
 * 
 * @author Adi, Joesph, Shane, Dillion
 *
 */
public class Settler extends Unit {
	
	/**
	 * Create a settler
	 * @param x The column index
	 * @param y The row index
	 * @param nation The nation this unit is commanded by
	 */
	public Settler(int x, int y, Nation nation) {
		super(x, y, nation, 0.0, 0.5, 2, 0, nation == Model.BLUE_NATION ? RPGView.BLUE_SETTLER : RPGView.RED_SETTLER);
		this.buildCost = SETTLER_BUILD_COST;
	}

}
