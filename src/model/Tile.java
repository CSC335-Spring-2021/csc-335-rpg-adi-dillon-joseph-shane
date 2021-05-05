package model;

import units.Unit;

/**
 * This class represents a tile on the game map and all the information
 * associated with that tile.
 * 
 * @author Adi, Joesph, Shane, Dillion
 *
 */
public class Tile {
	
	/** A constant to represent dry land */
	public static final String DRY_LAND = "dry_land";
	/** A constant to represent a water tile */
	public static final String WATER = "water";
	/** The nation this tile is associated with, if any*/
	private Nation nation;
	/** The type of land this tile is. Use constants to set it. */
	private String landType;
	/**The unit on this tile, if any */
	private Unit unit;
	/** The city on this tile, if any */
	private City city;

	/**
	 * Creates a new tile
	 * @param nation The nation that owns this tile, if any
	 * @param landType The type of land. Use the class constants to set this
	 */
	public Tile(Nation nation, String landType) {
		this.nation = nation;
		this.landType = landType;
		this.unit = null;
		this.city = null;
	}

	/**
	 * Change the nation that commands this tile
	 * @param nation The nation
	 */
	public void setNation(Nation nation) {
		this.nation = nation;
	}

	/**
	 * Change the type of land this tile is
	 * @param landType The type of land. use the class constants
	 */
	public void setLandType(String landType) {
		this.landType = landType;
	}

	/**
	 * Change the city on this tile
	 * @param city The city
	 */
	public void setCity(City city) {
		this.city = city;
	}

	/** 
	 * Change the unit on this tile
	 * @param unit The unit
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * Gets the nation that owns this tile
	 * @return The nation
	 */ 
	public Nation getNation() {
		return this.nation;
	}

	/**
	 * Gets the city on this tile
	 * @return The city
	 */
	public City getCity() {
		return this.city;
	}

	/**
	 * Gets the type of land this tile is
	 * @return The type of land
	 */
	public String getLandType() {
		return this.landType;
	}

	/**
	 * Gets the unit on this tile
	 * @return The unit
	 */
	public Unit getUnit() {
		return this.unit;
	}
}
