package units;

import javafx.scene.image.Image;
import model.Nation;

public class Settler extends Unit{

	public Settler(Nation nation, Image sprite) {
		super(nation, 0.0, 25.0, 2, 0, new Image("/res/Settler.png", 40, 40, false, false));
		// TODO Auto-generated constructor stub
	}

}