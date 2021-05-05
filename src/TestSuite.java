import static org.junit.jupiter.api.Assertions.*;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import org.junit.jupiter.api.Test;
import controller.RPGController;
import model.City;
import model.Model;
import model.Nation;
import model.Tile;
import view.RPGView;



public class TestSuite{
	final boolean[] updated = new boolean[1];
		Observer observer = new Observer() {
		@Override
		public void update(Observable o, Object arg) {
			updated[0] = true;
		}
	};
	@Test
	void testModel() {
		
		Nation newNation = new Nation("test", false);
		newNation.removeGold(20);
		assertEquals(newNation.getGoldAmount(), 480);
		assertEquals(newNation.getCityCost(), 200);
		newNation.increaseCityCount(2);
		assertEquals(newNation.getCityCost(), 400);
		newNation.decreaseCityCount(2);
		assertEquals(newNation.getCityCost(), 200);
		
		Tile tile = new Tile(newNation, "land");
		tile.setNation(newNation);
		assertEquals(tile.getLandType(), "land");
		assertEquals(tile.getCity(), null);
		City city = new City();
		tile.setCity(city);
		assertTrue(tile.getCity() instanceof City);
		
		city.setNation(newNation);
		city.setName("temp");
		assertEquals(city.getName(), "temp");
		assertTrue(city.getNation() instanceof Nation);
		assertTrue(city.toString() instanceof String);
		
		Model newModel = new Model(observer);
		
		assertEquals(newModel.getTileAt(-1, 0), null);
		assertEquals(newModel.getTileAt(0, -1), null);
		assertEquals(newModel.getTileAt(23, 0), null);
		assertEquals(newModel.getTileAt(0, 23), null);
		assertTrue(newModel.getTileAt(1, 1) instanceof Tile);
		assertTrue(newModel.getCurrentTurn() instanceof Nation);
		
		assertFalse(newModel.getCurrentTurn().isAI());
		
		newModel.endTurn();
		assertTrue(newModel.getCurrentTurn().isAI());
		newModel.updateView();
		newModel.gameOver();
		
		

	}
	
	@Test
	void testController() {
		
		RPGController controller = new RPGController(observer);
		
		int value = controller.getPlayerGold();
		int value2 = controller.getAIGold();
		assertTrue(value == (int)value);
		assertTrue(value2 == (int)value2);
		assertTrue(controller.getTile(0, 0) instanceof Tile);
		assertTrue(controller.canBuildCity(0,0));
		controller.canAddUnit(0, 0);
		assertFalse(controller.addUnit(0, 0, "archer"));
		assertTrue(controller.addUnit(0, 2, "archer"));
		assertTrue(controller.addUnit(1, 3, "foot_soldier"));
		assertFalse(controller.moveUnit(0, 2, 16, 16));
		controller.moveUnit(0, 2, 1, 2);
		controller.moveUnit(1, 3, 14, 13);
		assertTrue(controller.canBuildCity(14, 14));
		//controller.buildCity(0, 0);
		
		//assertTrue(controller.canAddUnit(5, 5));
		//controller.addUnit(5, 5, "archer");
		

		controller.takeTurn();
		controller.updateView();
		controller.newGame();
		
		//assertFalse(controller.selectUnit(-1, -1));
		
	}
	
}
