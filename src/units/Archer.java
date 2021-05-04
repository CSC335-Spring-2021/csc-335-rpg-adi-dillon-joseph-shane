package units;

import model.Nation;
import view.RPGView;

public class Archer extends Unit {

	public Archer(int x, int y, Nation nation) {
		super(x, y, nation, 20.0, 0.75, 4, 3, RPGView.ARCHER);
		this.buildCost = 75;
	}

}
