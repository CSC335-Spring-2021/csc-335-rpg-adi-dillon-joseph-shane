package units;

public enum UnitType {
	EXAMPLE_UNIT(1.0, 1.0, 3);
	
	double attackPoints;
	double defensePoints;
	int movesPerTurn;
	
	UnitType(double attackPoints, double defensePoints, int movesPerTurn) {
		this.attackPoints = attackPoints;
		this.defensePoints = defensePoints;
		this.movesPerTurn = movesPerTurn;
	}
}
