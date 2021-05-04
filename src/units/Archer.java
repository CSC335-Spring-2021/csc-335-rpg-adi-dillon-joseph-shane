package units;

import javafx.scene.image.Image;
import model.Nation;

public class Archer extends Unit {

	public Archer(int x, int y, Nation nation) {
		super(x, y, nation, 20.0, 0.75, 4, 3, new Image("/res/Scout.png", 40, 40, false, false));
		this.buildCost = 75;
	}

}
