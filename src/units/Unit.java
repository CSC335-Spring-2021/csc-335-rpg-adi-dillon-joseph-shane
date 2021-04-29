
package units;

import model.Nation;

/**
 * Base class for all units in the game.
 * 
 * Each unit has a position, belongs to a nation, and has a type. All units have
 * a method to move, attack, and defend.
 * 
 * @author Joseph Shimel
 */
public abstract class Unit {
	private Nation nation;
	private double health;
	private final double attackPoints;
	private final double defensePoints;
	private final int movesPerTurn;

	/**
	 * Constructor for a unit
	 * 
	 * Should only be called by subclasses. Takes in a nation name and some stats
	 * for the unit.
	 * 
	 * @param nationName    Name of the nation the unit belongs to
	 * @param attackPoints  How many attack points each unit does per turn
	 * @param defensePoints How much defense each unit has
	 * @param movesPerTurn  How many tiles each unit can move per turn
	 */
	public Unit(Nation nation, double attackPoints, double defensePoints, int movesPerTurn) {
		this.nation = nation;
		this.health = 100;
		this.attackPoints = attackPoints;
		this.defensePoints = defensePoints;
		this.movesPerTurn = movesPerTurn;
	}

	public Nation getNation() {
		return nation;
	}

	public void setHealth(double d) {
		this.health = d;
	}

	public void setNation(Nation nation) {
		this.nation = nation;
	}

	public double getHealth() {
		return this.health;
	}

	public double getAttackPoints() {
		return this.attackPoints;
	}

	public double getDefensePoints() {
		return this.defensePoints;
	}

	public int getMovesPerTurn() {
		return this.movesPerTurn;
	}

}