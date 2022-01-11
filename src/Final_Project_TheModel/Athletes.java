package Final_Project_TheModel;

public class Athletes {
	private int sportType; // 1 For Runner, 2 For PoleJumper, 3 PoleRunner
	private String name;
	private String conutry;

	public Athletes(int SportType, String name, String country) {
		this.name = name;
		this.conutry = country;
		this.sportType = SportType;

	}

	public String getName() {
		return name;
	}

	public String getConutry() {
		return conutry;
	}

	public int getSportType() {
		return sportType;
	}


	public String toString() {
		String Type;
		if (sportType == 1)
			Type = "Runner";
		else if (sportType == 2)
			Type = "Jumper";
		else
			Type = "Both";
		return ("\nThe Athtlete Name is : " + name + "\nFrom : " + conutry + "\nHe Competes in :" + Type + "\n");
	}

}