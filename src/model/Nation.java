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
	private int cityCost;
	private final boolean isAI;
	private final ArrayList<Unit> units;
	private final ArrayList<City> cities;

	public Nation(String name, boolean isAI) {
		this.name = name;
		this.isAI = isAI;
		this.units = new ArrayList<>();
		this.cities = new ArrayList<>();
		this.gold = 500;
		this.cityCost = 200;
		this.cityCount = 0;
	}

	public boolean isAI() {
		return isAI;
	}
	
	public List<Unit> getUnitList() {
		return units;
	}
	
	public List<City> getCitiesList() {
		return cities;
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
	
	public int getCityCost() {
		return this.cityCost;
	}
	
	public void increaseCityCount(int amt) {
		this.cityCount += amt;
		cityCost *= 2;
	}
	
	public void decreaseCityCount(int amt) {
		this.cityCount -= amt;
		cityCost /= 2;
	}
}
