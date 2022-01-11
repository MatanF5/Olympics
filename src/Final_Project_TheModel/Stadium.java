package Final_Project_TheModel;

public class Stadium {
	private String name;
	private String location;
	private int seats;

	public Stadium(String name, String location, int seats) {
		this.name = name;
		this.location = location;
		this.seats = seats;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public int getSeats() {
		return seats;
	}

	@Override
	public String toString() {
		return "Stadium Name : " + name + "\nLocation : " + location + "\nNumber Of seats: " + seats + "\n";
	}

}
