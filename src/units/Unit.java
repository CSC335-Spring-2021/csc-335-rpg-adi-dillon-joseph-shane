
package units;

import javafx.scene.image.Image;
import model.Nation;

/**
 * Base class for all units in the game.
 * 
 * Each unit has a position, belongs to a nation, and has a type. All units have
 * a method to move, attack, and defend.
 * 
 * @author Adi, Joesph, Shane, Dillion
 */
public abstract class Unit {
	/** The column index*/
	private int x;
	/** The row index*/
	private int y;
	/** The nation this unit is associated with*/
	private Nation nation;
	/** The health of this unit*/
	private double health;
	/** The build cost of this unit in gold*/
	protected int buildCost;
	/** The amount of damage the unit does*/
	private final double attackPoints;
	/** The amount of defense the unit has */
	private final double defensePoints;
	/** The number of moves this unit can make in one turn*/
	private final int movesPerTurn;
	/** The tile range this unit can attack in */
	private final int attackRange;
	/** The sprite associated with the unit */
	private final Image sprite;
	
	/** The cost to built an archer*/
	public static final int ARCHER_BUILD_COST = 75;
	/** The cost to build a foot soldier*/
	public static final int FOOT_SOLDIER_BUILD_COST = 100;
	/** The cost to build a settler*/
	public static final int SETTLER_BUILD_COST = 50;

	/**
	 * Constructor for a unit
	 * 
	 * Should only be called by subclasses. Takes in a position, nation and some
	 * stats for the unit.
	 * 
	 * @param x The column index
	 * @param y The row index
	 * @param nation    Name of the nation the unit belongs to
	 * @param attackPoints  How many attack points each unit does per turn
	 * @param defensePoints How much defense each unit has
	 * @param movesPerTurn  How many tiles each unit can move per turn
	 * @param attackRange The number of tiles a unit can attack within
	 * @param sprite The sprite for this unit
	 */
	public Unit(int x, int y, Nation nation, double attackPoints, double defensePoints, int movesPerTurn,
			int attackRange, Image sprite) {
		this.x = x;
		this.y = y;
		this.nation = nation;
		this.health = 100;
		this.buildCost = 0;
		this.attackPoints = attackPoints;
		this.defensePoints = defensePoints;
		this.movesPerTurn = movesPerTurn;
		this.sprite = sprite;
		this.attackRange = attackRange;
		nation.getUnitList().add(this);
	}

	/**
	 * Get the column index of the tile this unit is on
	 * @return The column index
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get the row index of the tile this unit is on
	 * @return The row index
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Get the cost of building this unit
	 * @return The cost in gold
	 */
	public int getBuildCost() {
		return this.buildCost;
	}

	/**
	 * Change the position of this unit
	 * @param x The column index
	 * @param y The row index
	 */
	public void setPositition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Get the nation this unit is commanded by
	 * @return The nation
	 */
	public Nation getNation() {
		return nation;
	}

	/**
	 * Set the health of this unit
	 * @param d The health
	 */
	public void setHealth(double d) {
		this.health = d;
	}

	/**
	 * Set the nation of this unit
	 * @param nation The nation
	 */
	public void setNation(Nation nation) {
		this.nation = nation;
	}

	/**
	 * Get the current health of this unit
	 * @return The health
	 */
	public double getHealth() {
		return this.health;
	}

	/**
	 * Get the attack points of this unit
	 * @return The attack points
	 */
	public double getAttackPoints() {
		return this.attackPoints;
	}

	/**
	 * Get the defense points of this unit
	 * @return The defense points
	 */
	public double getDefensePoints() {
		return this.defensePoints;
	}

	/**
	 * Get the number of mvoes this unit can make per trn
	 * @return The number of moves/ turn
	 */
	public int getMovesPerTurn() {
		return this.movesPerTurn;
	}

	/**
	 * Get the attack range of this unit
	 * @return The attack range
	 */
	public int getAttackRange() {
		return this.attackRange;
	}

	/**
	 * Get the sprite associated with this unit
	 * @return The sprite
	 */
	public Image getSprite() {
		return this.sprite;
	}

}