package units;

import javafx.scene.image.Image;
import model.Nation;

public class FootSoldier extends Unit {

	public FootSoldier(int x, int y, Nation nation) {
		// health = 100 / defense
		super(x, y, nation, 25.0, 1, 2, 1, new Image("/res/Infantry.png", 40, 40, false, false));
		this.buildCost = 100;
	}

}
