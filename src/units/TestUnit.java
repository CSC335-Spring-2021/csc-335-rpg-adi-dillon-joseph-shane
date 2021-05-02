package units;

import javafx.scene.image.Image;
import model.Nation;

public class TestUnit extends Unit {
	public TestUnit(Nation nation) {
		super(nation, 25, 1, 4, new Image("/res/foot_soldier.png"));
	}
}
