package city;

import javafx.scene.image.Image;
import model.Model;
import model.Nation;

public class City {
	
	private String cityName;
	private int revenue;
	private Nation nation;
	private final Image sprite;
	
	public City() {
		cityName = "";
		revenue = 0;
		nation = null;
		sprite = null;
	}
	
	public City(String name, int money, Nation nation) {
		this.cityName = name;
		this.revenue = money;
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
	
	public void setRevenue(int money) {
		revenue = money;
	}
	
	public void setNation(Nation nation) {
		this.nation = nation;
	}
	
	public String getName() {
		return cityName;
	}
	
	public int getRevenue() {
		return revenue;
	}
	
	public Nation getNation() {
		return nation;
	}
	
	public String toString() {
		String output = "";
		output += "City name: " + cityName + "\n";
		output += "Revenue generated: " + revenue + "\n";
		output += "Nation: " + nation + "\n";
		return output;
	}
	
}
