package model;

import javafx.scene.image.Image;

public class City {
	
	private final int buildCost = 200;
	private String cityName;
	private Nation nation;
	private final Image sprite;
	
	public City() {
		cityName = "";
		nation = null;
		sprite = null;
	}
	
	public int getBuildCost() {
		return (int) (this.buildCost*Math.pow(2, this.nation.getCityCount())); // Power series to double cost each time
	}
	
	public City(String name, int money, Nation nation) {
		this.cityName = name;
		this.nation = nation;
		
		String cityNationName = this.nation.name;
		if(cityNationName.equals(Model.BLUE_NATION.name)) {
			this.sprite = new Image("/res/blue_nation_city.png", 40, 40, false, false);
		}else {
			this.sprite = new Image("/res/red_nation_city.png", 40, 40, false, false);
		}
		
	}
	
	public Image getSprite() {
		return this.sprite;
	}
	
	public void setName(String name) {
		cityName = name;
	}
	
	
	public void setNation(Nation nation) {
		this.nation = nation;
	}
	
	public String getName() {
		return cityName;
	}
	
	
	public Nation getNation() {
		return nation;
	}
	
	public String toString() {
		String output = "";
		output += "City name: " + cityName + "\n";
		output += "Nation: " + nation + "\n";
		return output;
	}
	
}
