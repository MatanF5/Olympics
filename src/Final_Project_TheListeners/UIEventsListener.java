package Final_Project_TheListeners;

public interface UIEventsListener {
	void addCountryToUI(String name);

	void addRefereeToUI(String name, String country, int type);

	void addStadiumToUI(String name, String location, int seats);

	void addAthletesToUI(int sportType, String name, String country);

	void addCompetitionToUI(int compType, int type, int ref);

	void olympicsRankings();
}
