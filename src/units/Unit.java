
package units;

/**
 * Base class for all units in the game.
 * 
 * Each unit has a position, belongs to a nation, and has a type. All units have
 * a method to move, attack, and defend.
 * 
 * @author Joseph Shimel
 */
public abstract class Unit {
	private String nationName;
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
	public Unit(String nationName, double attackPoints, double defensePoints, int movesPerTurn) {
		this.nationName = nationName;
		this.attackPoints = attackPoints;
		this.defensePoints = defensePoints;
		this.movesPerTurn = movesPerTurn;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public double getAttackPoints() {
		return attackPoints;
	}

	public double getDefensePoints() {
		return defensePoints;
	}

	public int getMovesPerTurn() {
		return movesPerTurn;
	}

}