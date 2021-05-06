import javafx.application.Application;
import view.RPGView;

/**
 * 
 * This class is used to launch "Society", the game. No command line argments
 * required.
 * 
 * @author Adi, Joesph, Shane, Dillion
 *
 */
public class Society {

	/**
	 * Runs the game
	 * @param args None required
	 */
	public static void main(String[] args) {
		Application.launch(RPGView.class, args);
	}
}
