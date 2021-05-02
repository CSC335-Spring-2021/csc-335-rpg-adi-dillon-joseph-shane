package units;

import javafx.scene.image.Image;
import model.Nation;

public class FootSoldier extends Unit {

	public FootSoldier(Nation nation) {
		// health = 100 / defense
		super(nation, 25.0, 1, 2, 1, new Image("/res/Infantry.png", 40, 40, false, false));
		// TODO Auto-generated constructor stub
	}

}
