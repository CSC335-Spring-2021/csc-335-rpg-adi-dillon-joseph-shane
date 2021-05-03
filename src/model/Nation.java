package model;

import java.util.ArrayList;
import java.util.List;

import units.Unit;

public class Nation {
	public String name;
	public int movesLeft;
	public Nation enemyNation;
	private int gold;
	private int cityCount;
	private final boolean isAI;
	private final ArrayList<Unit> units;

	public Nation(String name, boolean isAI) {
		this.name = name;
		this.isAI = isAI;
		this.units = new ArrayList<>();
		this.gold = 500;
		this.cityCount = 0;
	}

	public boolean isAI() {
		return isAI;
	}
	
	public List<Unit> getUnitList() {
		return units;
	}
	
	public int getGoldAmount() {
		return this.gold;
	}
	
	public void addGold(int amt) {
		this.gold += amt;
	}
	
	public void removeGold(int amt) {
		this.gold -= amt;
	}
	
	public int getCityCount() {
		return this.cityCount;
	}
	
	public void increaseCityCount(int amt) {
		this.cityCount += amt;
	}
	
	public void decreaseCityCount(int amt) {
		this.cityCount -= amt;
	}
}
