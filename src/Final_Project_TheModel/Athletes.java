package Final_Project_TheModel;

public class Athletes {
	private int sportType; // 1 For Runner, 2 For PoleJumper, 3 PoleRunner
	private String name;
	private String conutry;
	private int AID;

	public Athletes(int SportType, String name, String country, int AID) {
		this.name = name;
		this.conutry = country;
		this.sportType = SportType;
		this.AID = AID;

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

	public int getAID() {
		return AID;
	}

}