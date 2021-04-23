package city;

public class City {
	
	private String cityName;
	private int revenue;
	private String nation;
	
	public City() {
		cityName = "";
		revenue = 0;
		nation = "";
	}
	
	public City(String name, int money, String name2) {
		cityName = name;
		revenue = money;
		nation = name2;
	}
	
	public void setName(String name) {
		cityName = name;
	}
	
	public void setRevenue(int money) {
		revenue = money;
	}
	
	public void setNation(String name) {
		nation = name;
	}
	
	public String getName() {
		return cityName;
	}
	
	public int getRevenue() {
		return revenue;
	}
	
	public String getNation() {
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
