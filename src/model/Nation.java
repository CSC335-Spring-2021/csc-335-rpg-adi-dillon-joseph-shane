package model;

import java.util.ArrayList;
import java.util.List;

import units.Unit;

public class Nation {
	public String name;
	public int movesLeft;
	public Nation enemyNation;
	private final boolean isAI;
	private final ArrayList<Unit> units;

	public Nation(String name, boolean isAI) {
		this.name = name;
		this.isAI = isAI;
		this.units = new ArrayList<>();
	}

	public boolean isAI() {
		return isAI;
	}
	
	public List<Unit> getUnitList() {
		return units;
	}
}
