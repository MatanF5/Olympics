package Final_Project_TheController;

import Final_Project_TheListeners.EventsListener;
import Final_Project_TheListeners.UIEventsListener;
import Final_Project_TheModel.SysManager;
import Final_Project_TheView.AbstractOlympicView;

public class OlympicController implements EventsListener, UIEventsListener {
	private SysManager theModel;
	private AbstractOlympicView theView;

	public OlympicController(SysManager theModel, AbstractOlympicView theView) {
		this.theModel = theModel;
		this.theView = theView;

		theModel.registerListener(this);
		theView.registerListener(this);
	}

	@Override
	public void olympicsRankings() {
		theModel.olympicsRankings();
	}

	@Override
	public void addCountryToUI(String name) {
		theModel.addCountry(name);
	}

	@Override
	public void addRefereeToUI(String name, String country, int type) {
		theModel.addReferee(name, country, type);
	}

	@Override
	public void addStadiumToUI(String name, String location, int seats) {
		theModel.addStadium(name, location, seats);
	}

	@Override
	public void addAthletesToUI(int sportType, String name, String country) {
		theModel.addAthletes(sportType, name, country);
	}

	@Override
	public void addCompetitionToUI(int compType, int type, int ref) {
		theModel.addCompetition(compType, type, ref);
	}

	@Override
	public void fireActionCompleted() {
		theView.actionCompletedMessage("Action completed succesfully");
	}

	@Override
	public void fireActionFailed() {
		theView.actionFailed("Action failed, invalid input!");
	}

	@Override
	public void fireCountryEmpty() {
		theView.countryEmpty("There is country with no athletes. Add athletes before you start a competition.");
	}

	@Override
	public void fireCountrySame() {
		theView.countrySame("There is country with This Name Already.");
	}

	@Override
	public void fireEndOlympicsFailed() {
		theView.EndOlympicsFailed("There Are No Competitons");
	}

}
