package Final_Project_TheModel;

public class Referee {
	private String name;
	private int type; // 1 for Runner, 2 for PoleJumper , 3 for PoleRunner
	private String country;

	public Referee(String name, int type, String country) {
		this.name = name;
		this.type = type;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public int getTypeIndex() {
		return type;
	}

	public String getType() {
		if (type == 1) {
			return "Running";
		} else if (type == 2) {
			return "Jumping";
		} else {
			return "Both";
		}
	}

	public String getCountry() {
		return country;
	}

	public String toString() {
		String Type;
		if (type == 1)
			Type = " Runner";
		else if (type == 2)
			Type = " Jumper";
		else
			Type = " Both";

		return "Name : " + name + "\nReferee type: " + Type + "\nCountry: " + country + "\n";
	}

}