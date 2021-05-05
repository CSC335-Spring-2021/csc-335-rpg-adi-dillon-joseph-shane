package model;

import java.util.ArrayList;
import java.util.List;

import units.Unit;

/**
 * This class represents a nation in the game. Each nation is associated with a
 * player or AI.
 * 
 * @author Adi, Joesph, Shane, Dillion
 *
 */
public class Nation {
	/** The name of the nation */
	public String name;
	/** The number of moves left for the nation's current turn */
	public int movesLeft;
	/** The enemy nation for this nation */
	public Nation enemyNation;
	/** The amount of gold the nation has */
	private int gold;
	/** The number of cities the nation has */
	private int cityCount;
	/** The cost of creating a city for this nation */
	private int cityCost;
	/** Is this nation controller by an AI */
	private final boolean isAI;
	/** A list of the units the nation commands */
	private final ArrayList<Unit> units;
	/** A list of the cities under this nation */
	private final ArrayList<City> cities;

	/**
	 * Creates a new nation
	 * 
	 * @param name The name of the nation
	 * @param isAI If the nation is controller by an AI
	 */
	public Nation(String name, boolean isAI) {
		this.name = name;
		this.isAI = isAI;
		this.units = new ArrayList<>();
		this.cities = new ArrayList<>();
		this.gold = 500;
		this.cityCost = 200;
		this.cityCount = 0;
	}

	/**
	 * Checks if the nation is AI controlled
	 * 
	 * @return true if it is controlled by an AI otherwise false
	 */
	public boolean isAI() {
		return isAI;
	}

	/**
	 * Grabs the list of units associated with this nation
	 * 
	 * @return The list of units
	 */
	public List<Unit> getUnitList() {
		return units;
	}

	/**
	 * Grabs the list of cities associated with this nation
	 * 
	 * @return The list of cities
	 */
	public List<City> getCitiesList() {
		return cities;
	}

	/**
	 * Gets the amount of gold possesed by this nation
	 * 
	 * @return The amount of gold
	 */
	public int getGoldAmount() {
		return this.gold;
	}

	/**
	 * Adds a certain amount of gold to this nation's treasury
	 * 
	 * @param amt the amount of gold to add
	 */
	public void addGold(int amt) {
		this.gold += amt;
	}

	/**
	 * Subtracts a certain amount of gold to this nation's treasury
	 * 
	 * @param amt the amount of gold to subtract
	 */
	public void removeGold(int amt) {
		this.gold -= amt;
	}

	/**
	 * Get the number of cities under this nation
	 * 
	 * @return The number of cities
	 */
	public int getCityCount() {
		return this.cityCount;
	}

	/**
	 * Get the cost of building a city in this nation
	 * 
	 * @return The cost of building a city
	 */
	public int getCityCost() {
		return this.cityCost;
	}

	/**
	 * Increases the number of cities this nation has. Also increases the cost of
	 * cities
	 * 
	 * @param amt The amount of cities to add. Should be 1 at most
	 */
	public void increaseCityCount(int amt) {
		this.cityCount += amt;
		cityCost *= 2;
	}

	/**
	 * Decreases the number of cities this nation has. Also decreases the cost of
	 * cities
	 * 
	 * @param amt The amount of cities to remove. Should be 1 at most
	 */
	public void decreaseCityCount(int amt) {
		this.cityCount -= amt;
		cityCost /= 2;
	}
}
