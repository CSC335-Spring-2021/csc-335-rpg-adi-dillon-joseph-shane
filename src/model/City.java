package model;

import javafx.scene.image.Image;
import view.RPGView;

/**
 * This class represents a city in the game
 * 
 * @author Adi, Joesph, Shane, Dillion
 *
 */
public class City {
	/** The x coordinate of the city*/
	int x; 
	/** The y coordinate of the city*/
	int y;
	/** The city's name*/
	private String cityName;
	/** The nation this city is affiliated with*/
	private Nation nation;
	/** The sprite associated with this city*/
	private final Image sprite;

	/**
	 * Creates a blank city
	 */
	public City() {
		cityName = "";
		nation = null;
		sprite = null;
	}

	/**
	 * Creates a city at the given coordinates with the given name and nation.
	 * @param x The column index
	 * @param y The row index
	 * @param name The city name
	 * @param nation The nation this city is under
	 */
	public City(int x, int y, String name,Nation nation) {
		this.x = x;
		this.y = y;
		this.cityName = name;
		this.nation = nation;
		if (nation == Model.BLUE_NATION) {
			this.sprite = RPGView.BLUE_CITY;
		} else {
			this.sprite = RPGView.RED_CITY;
		}
		nation.getCitiesList().add(this);
	}

	/**
	 * gets the x coordinate of the city (the col)
	 * @return The x index
	 */
	public int getX() {
		return x;
	}

	/**
	 * gets the y coordinate of the city (the row)
	 * @return The y index
	 */
	public int getY() {
		return y;
	}

	/**
	 * Gets the sprite associated with the city
	 * @return The sprite
	 */
	public Image getSprite() {
		return this.sprite;
	}

	/**
	 * Set the name of the city
	 * @param name The new name
	 */
	public void setName(String name) {
		cityName = name;
	}

	/**
	 * Set the nation of the city
	 * @param nation The new nation
	 */
	public void setNation(Nation nation) {
		this.nation = nation;
	}

	/**
	 * Get the city name
	 * @return The city name
	 */
	public String getName() {
		return cityName;
	}

	/**
	 * Get the nation this city is a part of
	 * @return The nation
	 */
	public Nation getNation() {
		return nation;
	}

}
