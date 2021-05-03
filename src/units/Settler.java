package units;

import javafx.scene.image.Image;
import model.Nation;

public class Settler extends Unit {

	public Settler(int x, int y, Nation nation) {
		super(x, y, nation, 0.0, 0.5, 2, 0, new Image("/res/Settler.png", 40, 40, false, false));
	}

}
