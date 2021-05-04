package units;

import model.Nation;
import view.RPGView;

public class FootSoldier extends Unit {

	public FootSoldier(int x, int y, Nation nation) {
		// health = 100 / defense
		super(x, y, nation, 25.0, 1, 2, 1, RPGView.FOOT_SOLDIER);
		this.buildCost = FOOT_SOLDIER_BUILD_COST;
	}

}
