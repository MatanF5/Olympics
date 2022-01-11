package Final_Project_TheView;

import java.sql.Statement;

import Final_Project_TheListeners.UIEventsListener;

public interface AbstractOlympicView {
	void addCountry(String name);

	void addReferee(String name, String country, int type);

	void addStadium(String name, String location, int seats);

	void olympicsRankings();

	void registerListener(UIEventsListener listener);

	void actionCompletedMessage(String msg);

	void actionFailed(String msg);

	void addCompetition(int compType, int type, int i);

	void countryEmpty(String string);

	void countrySame(String string);

	void EndOlympicsFailed(String string);

	void addAthletes(int sportType, String name, String country, Statement stmt);

}
