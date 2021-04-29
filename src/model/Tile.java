package model;

import java.util.ArrayList;
import java.util.List;

import city.City;
import units.Unit;

public class Tile {

	public static final String DRY_LAND = "dry_land";
	public static final String WATER = "water";
	private String nation;
	private String landType;
	private List<Unit> units;
	private City city;
	
	public Tile(String nation, String landType) {
		this.nation= nation;
		this.landType = landType;
		this.units = new ArrayList<Unit>();
		this.city = null;
	}
	
	public void setNation(String nation) {
		this.nation = nation;
	}
	
	public void setLandType(String landType) {
		this.landType = landType;
	}
	
	public void setCity(City city) {
		this.city = city;
	}
	
	public void addUnit(Unit unit) {
		this.units.add(unit);
	}
	
	public String getNation() {
		return this.nation;
	}
	
	public City getCity() {
		return this.city;
	}
	
	public String getLandType() {
		return this.landType;
	}
	
	public List<Unit> getUnits(){
		return this.units;
	}
}
