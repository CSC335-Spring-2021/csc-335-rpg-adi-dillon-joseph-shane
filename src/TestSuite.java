import static org.junit.jupiter.api.Assertions.*;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import org.junit.jupiter.api.Test;
import city.City;
import controller.RPGController;
import model.Model;
import model.Nation;
import view.RPGView;



public class TestSuite{

	@Test
	public void testSuite() {
		final boolean[] updated = new boolean[1];
		Observer observer = new Observer() {
		@Override
		public void update(Observable o, Object arg) {
			updated[0] = true;
		}
	};
	
		Model newModel = new Model(observer);
		//RPGController controller = new RPGController(observer);
		//Nation newNation = new Nation("test", true);
		
		
		
		
		//assertEquals(newNation.isAI(), true);

		//assertEquals(newModel.getTileAt(-1, -1), null);
	}
	
	
}
