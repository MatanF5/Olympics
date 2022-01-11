package Final_Project_TheModel;

import java.util.ArrayList;

import java.util.Random;
import java.util.Vector;
import Final_Project_TheListeners.EventsListener;
import java.sql.DriverManager;
import java.sql.*;

public class SysManager {
	private Olympics oly = new Olympics("Corona 2020");
	private ArrayList<Referee> referees = new ArrayList<Referee>();
	private ArrayList<Stadium> stadiums = new ArrayList<Stadium>();
	private Vector<EventsListener> listeners;
	private int athNum = 0;

	public SysManager() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/olympics", "root", "super462");
			Statement stmt = con.createStatement();
		} catch (Exception e) {
			System.out.println(e);
		}
		listeners = new Vector<EventsListener>();
		referees.add(new Referee("Avi", 2, "Brazil"));
		referees.add(new Referee("Haim", 1, "Italy"));
		referees.add(new Referee("Boten", 3, "Godadelaja"));
		stadiums.add(new Stadium("sami", "israel", 30));
		stadiums.add(new Stadium("hatol", "israel", 350));
		stadiums.add(new Stadium("shmino", "israel", 3077));
		addCountry("Brazil");
		addCountry("England");
		addCountry("Peru");
		oly.getAllCountries().get(0).AddAthlete(new Athletes(1, "buzaglo", "Brazil"));
		oly.getAllCountries().get(1).AddAthlete(new Athletes(1, "messi", "England"));
		oly.getAllCountries().get(2).AddAthlete(new Athletes(1, "shimon", "Peru"));
	}

	public void addReferee(String name, String country, int type) {
		if (name.isEmpty() || country.isEmpty() || name.startsWith(" ") || country.startsWith(" ")) {
			fireActionFailed();
		} else {
			referees.add(new Referee(name, type, country));
			// sql connection add to table
			fireActionCompleted();
		}
	}

	public void addStadium(String name, String location, int seats) {
		if (name.isEmpty() || location.startsWith(" ") || name.startsWith(" ") || location.isEmpty() || seats <= 0
				|| String.valueOf(seats).isEmpty()) {
			fireActionFailed();
		} else {
			stadiums.add(new Stadium(name, location, seats));
			fireActionCompleted();
		}
	}

	public void addCountry(String name) { // Adds A Country To The Olympics
		boolean check = true;
		for (Country c : oly.getAllCountries())
			if (c.getName().equalsIgnoreCase(name))
				check = false;
		if (name.isEmpty() || name.startsWith(" "))
			fireActionFailed();
		else if (check) {
			oly.addCountry(new Country(name));
			fireActionCompleted();
		} else
			fireCountrySame();
	}

	public void fireCountrySame() {
		for (EventsListener l : listeners) {
			l.fireCountrySame();
		}
	}

	public void addAthletes(int sportType, String name, String country, Statement stmt) { // Adds An Athlete To His
																							// Country
		if ((name != null) && (country != null) && (sportType != 0) && (!(name.startsWith(" ")))) {
			int check = 0;
			for (Country c : oly.getAllCountries())
				if (c.getName().equalsIgnoreCase(country)) {
					Athletes ath = new Athletes(sportType, name, country);
					c.AddAthlete(ath);
					this.athNum++;
					String query = "INSERT INTO athletes (Name,Country,SportsType,AID) Values(" + name + " " + country
							+ " " + sportType + " " + athNum + ")";
					try {
						stmt.executeUpdate(query);
					} catch (Exception e) {
						System.out.println(e);
					}
					fireActionCompleted();
					check++;
				}
			if (check == 0) {
				fireActionFailed();
			}
		} else {
			fireActionFailed();
		}
	}

	public void addCompetition(int compType, int type, int refereeIndex) { // Adds A Competition compType is : 1 For
																			// Running
		// 2 For Jumping , type is 1 for Solo , 2 for Team.
		Random rnd = new Random();
		int rand1 = rnd.nextInt(stadiums.size());
		boolean check = true;
		if (compType == getReferees().get(refereeIndex).getTypeIndex()
				|| getReferees().get(refereeIndex).getTypeIndex() == 3) {
			if (compType == 1) {
				for (Country c : oly.getAllCountries())
					if (c.getNationalRunnerTeam().isEmpty())
						check = false;
				if (check) {
					oly.addCompetition(new RunningCompetition(getReferees().get(refereeIndex), stadiums.get(rand1),
							type, this.oly)); // Running
					fireActionCompleted();
				} else {
					fireCountryEmpty();
				}
			} else {
				for (Country c : oly.getAllCountries())
					if (c.getNationalJumpingTeam().isEmpty())
						check = false;
				if (check) {
					oly.addCompetition(new JumpingCompetition(getReferees().get(refereeIndex), stadiums.get(rand1),
							type, this.oly)); // Jumping
					fireActionCompleted();
				} else {
					fireCountryEmpty();
				}
			}
		} else {
			fireActionFailed();
		}
	}

	public void fireCountryEmpty() {
		for (EventsListener l : listeners) {
			l.fireCountryEmpty();
		}
	}

	public String olympicsRankings() { // Prints Olympics Final Rankings
		Country rank1 = new Country();
		Country rank2 = new Country();
		Country rank3 = new Country();
		for (Country C : oly.getAllCountries()) {
			if (C.getTotalMedals() > rank1.getTotalMedals()) {
				rank3 = rank2;
				rank2 = rank1;
				rank1 = C;

			} else if (C.getTotalMedals() > rank2.getTotalMedals()) {
				rank3 = rank2;
				rank2 = C;
			} else if (C.getTotalMedals() > rank3.getTotalMedals())
				rank3 = C;

		}
		if (rank1.getTotalMedals() == 0) {
			fireEndOlympicsFailed();
			return "There is no competitions, Therefore no winners.";
		} else {
			return "The First Place Country is : " + rank1.ToString() + "\n" + "The Second Place Country is : "
					+ rank2.ToString() + "\n" + "The Third Place Country is : " + rank3.ToString();
		}

	}

	public String GetCountryAthletes() {
		StringBuffer sb = new StringBuffer();
		for (Country C : oly.getAllCountries()) {
			sb.append("Country name: " + C.getName() + "\n");
			sb.append(C.getAtheletsInfo());
		}
		return sb.toString();

	}

	public void fireEndOlympicsFailed() {
		for (EventsListener l : listeners)
			l.fireEndOlympicsFailed();

	}

	public void fireActionCompleted() {
		for (EventsListener l : listeners)
			l.fireActionCompleted();

	}

	public void fireActionFailed() {
		for (EventsListener l : listeners)
			l.fireActionFailed();

	}

	public Olympics getOlyimpcs() {
		return this.oly;
	}

	public ArrayList<Referee> getReferees() {
		return referees;
	}

	public ArrayList<Stadium> getStadiums() {
		return stadiums;
	}

	public String GetAllRefereesInfo() {
		StringBuffer sb = new StringBuffer("All The Referees Are : \n");
		for (Referee r : referees) {
			sb.append("\n");
			sb.append(r.toString());
		}
		return sb.toString();

	}

	public String GetStadiumInfo() {
		StringBuffer sb = new StringBuffer("All The Stadiums Are : \n");
		for (Stadium s : stadiums) {
			sb.append("\n");
			sb.append(s.toString());
		}
		return sb.toString();
	}

	public void registerListener(EventsListener listener) {
		listeners.add(listener);
	}

}
