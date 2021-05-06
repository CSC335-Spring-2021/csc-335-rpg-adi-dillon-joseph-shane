import static org.junit.jupiter.api.Assertions.*;
import java.util.Observable;
import java.util.Observer;
import org.junit.jupiter.api.Test;
import controller.RPGController;
import model.City;
import model.Model;
import model.Nation;
import model.Tile;


/**
 * This class is the test suite for our RPG game.
 * The class tests the controller and model classes as well as the 
 * various classes that are in the respective packages.
 * 
 * @author Dillon, Joseph, Shane, Adi
 *
 */

public class TestSuite{

	final boolean[] updated = new boolean[1];
		Observer observer = new Observer() {
		@Override
		public void update(Observable o, Object arg) {
			updated[0] = true;
		}
	};
	/**
	 * This method tests the getters, setters and constructors for the
	 * classes in the model including the model, nation, city and tile
	 * classes. 
	 * 
	 */
	@Test
	void testModel() {
		//These tests are for the getters and setters of the Nation class
		Nation newNation = new Nation("test", false);
		newNation.removeGold(20);
		assertEquals(newNation.getGoldAmount(), 480);
		assertEquals(newNation.getCityCost(), 200);
		newNation.increaseCityCount(2);
		assertEquals(newNation.getCityCost(), 400);
		newNation.decreaseCityCount(2);
		assertEquals(newNation.getCityCost(), 200);
		
		// These tests are for the getters and setters of the Tile class 
		// which require a new City creation as well.
		Tile tile = new Tile(newNation, "land");
		tile.setNation(newNation);
		assertEquals(tile.getLandType(), "land");
		assertEquals(tile.getCity(), null);
		City city = new City();
		tile.setCity(city);
		assertTrue(tile.getCity() instanceof City);
		
		// These test some of the  getters and setters of the City class
		// The rest are tested with the controller.
		city.setNation(newNation);
		city.setName("temp");
		assertEquals(city.getName(), "temp");
		assertTrue(city.getNation() instanceof Nation);
		assertTrue(city.toString() instanceof String);
		
		
		// Here we create a new instance of the model and test the 
		// getters of the model
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
	/**
	 * This method tests the controller and the AI logic that is implemented
	 * in it. Mechanics tested include placing units, moving units, building cities,
	 * attacking units and letting the AI run through a few turns.
	 * 
	 */
	@Test
	void testController() {
		
		// Creates a new controller and makes sure that
		// the initial values in the controller are the proper types
		RPGController controller = new RPGController(observer);
		int value = controller.getPlayerGold();
		int value2 = controller.getAIGold();
		assertTrue(value == (int)value);
		assertTrue(value2 == (int)value2);
		assertTrue(controller.getTile(0, 0) instanceof Tile);
		
		// These tests check if you can build a city where you are allowed
		// and place units in viable spots.
		assertTrue(controller.canBuildCity(0,0));
		assertFalse(controller.canAddUnit(0, 0));
		assertFalse(controller.addUnit(0, 0, "archer"));
		assertTrue(controller.addUnit(0, 2, "archer"));
		assertTrue(controller.addUnit(12, 13, "foot_soldier"));
		assertTrue(controller.addUnit(12, 12, "settler"));
		
		// These tests allow the player to move units in order to test
		// combat with the AI as well as the limits of moving
		// off the board.
		controller.moveUnit(12, 13, 14, 14);
		controller.selectUnit(1, 3);
		controller.selectUnit(14, 14);
		assertFalse(controller.moveUnit(0, 2, 16, 16));
		controller.moveUnit(3, 3, 4, 4);
		controller.moveUnit(0, 2, 1, 2);
		controller.moveUnit(1, 3, 14, 13);
		controller.moveUnit(14, 14, 13, 13);
		assertTrue(controller.canBuildCity(14, 14));
		controller.buildCity(0, 0);
		
		// These tests are to test the last getters of the City class
		Nation newNation = new Nation("test", false);
		City newCity = new City(5, 5, "temp", newNation);
		assertEquals(newCity.getX(), 5);
		assertEquals(newCity.getY(), 5);
		assertFalse(controller.canAddUnit(6, 5));

		// These are to test the functionality of the AI's moves
		controller.endTurn();
		controller.takeTurn();
		controller.updateView();
		controller.endTurn();
		controller.takeTurn();
		controller.updateView();
		controller.endTurn();
		controller.takeTurn();
		controller.updateView();
		controller.endTurn();
		controller.takeTurn();
		controller.updateView();
		controller.endTurn();
		controller.takeTurn();
		controller.updateView();
		controller.endTurn();
		controller.takeTurn();
		controller.updateView();
		controller.endTurn();
		controller.takeTurn();
		controller.updateView();
		controller.endTurn();
		controller.takeTurn();
		controller.updateView();
		controller.endTurn();
		controller.takeTurn();
		controller.updateView();
		controller.endTurn();
		controller.updateView();
		controller.newGame();
		

		
	}
	
}
