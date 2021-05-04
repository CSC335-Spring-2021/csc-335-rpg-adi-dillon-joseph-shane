package units;

import javafx.scene.image.Image;
import model.Nation;
import view.RPGView;

public class Settler extends Unit {

	public Settler(int x, int y, Nation nation) {
		super(x, y, nation, 0.0, 0.5, 2, 0, RPGView.SETTLER);
		this.buildCost = 50;
	}

}
