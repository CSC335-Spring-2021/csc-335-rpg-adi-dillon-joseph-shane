
package units;

public abstract class Unit {
	private String nation;
	private int posX, posY;
	private final UnitType type;

	public Unit(String nation, int posX, int posY, UnitType type) {
		this.nation = nation;
		this.posX = posX;
		this.posY = posY;
		this.type = type;
	}

	// Called when unit needs to move to a new tile
	public abstract void move(int x, int y);

	// Called when the unit will attack another tile
	public abstract void attack(int x, int y);

	// Called when unit defends
	public abstract void defend();

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public UnitType getType() {
		return type;
	}

}