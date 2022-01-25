package Final_Project_TheModel;

import java.util.ArrayList;

import java.util.Random;
import java.util.Vector;

import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;

import Final_Project_TheListeners.EventsListener;

import java.sql.*;

public class SysManager {
	private Olympics oly = new Olympics("Corona 2020");
	private ArrayList<Referee> referees = new ArrayList<Referee>();
	private ArrayList<Stadium> stadiums = new ArrayList<Stadium>();
	private Vector<EventsListener> listeners;
	private int AID = 0;
	private int CID = 0;
	private int SID = 0;
	private int RID = 0;
	private int CID2 = 0;

	public SysManager() {

		listeners = new Vector<EventsListener>();
		// Setting up a default of 3 refs 3 stadiums 3 countries and 3 atheletes.
		addReferee("Avi", "Brazil", 2);
		addReferee("Haim", "Italy", 1);
		addReferee("Boten", "Godadelaja", 3);
		addStadium("sami", "israel", 30);
		addStadium("shmino", "israel", 3077);
		addStadium("hatol", "israel", 350);
		addCountry("England");
		addCountry("Peru");
		addCountry("Brazil");
		addAthletes(1, "Moshe", "England");
		addAthletes(1, "Shimon", "Peru");
		addAthletes(1, "buzaglo", "Brazil");

	}

	public void addReferee(String name, String country, int type) {
		if (name.isEmpty() || country.isEmpty() || name.startsWith(" ") || country.startsWith(" ")) {
			fireActionFailed();
		} else {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/olympics", "root",
						"root");
				PreparedStatement stm = con
						.prepareStatement("INSERT INTO Referee (Name,Type,Country,RID) Values(?,?,?,?)");
				this.RID++;
				stm.setString(1, name);
				stm.setInt(2, type);
				stm.setString(3, country);
				stm.setInt(4, RID);
				try {
					stm.executeUpdate();
					System.out.println("Referee Added to DB!");
				} catch (Exception e) {
					System.out.println(e);
				}
				referees.add(new Referee(name, type, country, RID));
				fireActionCompleted();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	public void addStadium(String name, String location, int seats) {
		if (name.isEmpty() || location.startsWith(" ") || name.startsWith(" ") || location.isEmpty() || seats <= 0
				|| String.valueOf(seats).isEmpty()) {
			fireActionFailed();
		} else {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/olympics", "root",
						"root");
				PreparedStatement stm = con
						.prepareStatement("INSERT INTO Stadium (Name,Seats,Location,SID) Values(?,?,?,?)");
				this.SID++;
				stm.setString(1, name);
				stm.setInt(2, seats);
				stm.setString(3, location);
				stm.setInt(4, SID);
				stadiums.add(new Stadium(name, location, seats, SID));
				try {
					stm.executeUpdate();
					System.out.println("Stadium Added to DB!");
				} catch (Exception e) {
					System.out.println(e);
				}
				fireActionCompleted();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public void addCountry(String name) { // Adds A Country To The Olympics
		boolean check = true;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/olympics", "root", "root");
			PreparedStatement stm = con
					.prepareStatement("INSERT INTO Country (Name,SoloMedals,TeamMedals,CID) Values(?,?,?,?)");
			for (Country c : oly.getAllCountries())
				if (c.getName().equalsIgnoreCase(name))
					check = false;
			if (name.isEmpty() || name.startsWith(" "))
				fireActionFailed();
			else if (check) {
				oly.addCountry(new Country(name, CID));
				this.CID++;
				stm.setString(1, name);
				stm.setInt(2, 0);
				stm.setInt(3, 0);
				stm.setInt(4, CID);
				try {
					stm.executeUpdate();
					System.out.println("Country Added to DB!");
				} catch (Exception e) {
					System.out.println(e);
				}
				fireActionCompleted();
			} else
				fireCountrySame();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void fireCountrySame() {
		for (EventsListener l : listeners) {
			l.fireCountrySame();
		}
	}

	public void addAthletes(int sportType, String name, String country) { // Adds An Athlete To His
		int check = 0; // Country
		if ((name != null) && (country != null) && (sportType != 0) && (!(name.startsWith(" ")))) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/olympics", "root",
						"root");
				for (Country c : oly.getAllCountries())
					if (c.getName().equalsIgnoreCase(country)) {
						Athletes ath = new Athletes(sportType, name, country, AID);
						c.AddAthlete(ath);
						this.AID++;
						Statement stCID = con.createStatement();
						String SCID = "Select CID from Country WHERE Country.Name = '" + country + "'";
						ResultSet rs = stCID.executeQuery(SCID);
						rs.next();
						int cid = rs.getInt("cid");
						PreparedStatement stm = con
								.prepareStatement("INSERT INTO athletes (Name,CID,SportsType,AID) Values(?,?,?,?)");
						stm.setString(1, name);
						stm.setInt(2, cid);
						stm.setInt(3, sportType);
						stm.setInt(4, AID);
						PreparedStatement stm2;
						if (sportType == 1) {
							stm2 = con.prepareStatement("INSERT INTO runteam (AID,Name,CID) Values(?,?,?)");
							stm2.setInt(1, AID);
							stm2.setString(2, name);
							stm2.setInt(3, c.getCID() + 1);

						} else if (sportType == 2) {
							stm2 = con.prepareStatement("INSERT INTO jumpteam (AID,Name,CID) Values(?,?,?)");
							stm2.setInt(1, AID);
							stm2.setString(2, name);
							stm2.setInt(3, c.getCID() + 1);

						} else {
							stm2 = con.prepareStatement("INSERT INTO runteam (AID,Name,CID) Values(?,?,?)");
							stm2.setInt(1, AID);
							stm2.setString(2, name);
							stm2.setInt(3, c.getCID() + 1);
							PreparedStatement stm3 = con
									.prepareStatement("INSERT INTO jumpteam (AID,Name,CID) Values(?,?,?)");
							stm3.setInt(1, AID);
							stm3.setString(2, name);
							stm3.setInt(3, c.getCID() + 1);
							stm3.executeUpdate();

						}

						try {
							stm.executeUpdate();
							stm2.executeUpdate();
							System.out.println("Athlete Added to DB!");

						} catch (Exception e) {
							System.out.println("Meow " + AID);
							System.out.println(e);
						}
						fireActionCompleted();
						check++;
					}
			} catch (Exception e) {
				System.out.println(e);
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
					try {
						Class.forName("com.mysql.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/olympics", "root",
								"root");
						oly.addCompetition(new RunningCompetition(getReferees().get(refereeIndex), stadiums.get(rand1),
								type, this.oly)); // Running
						CID2++;
						PreparedStatement stm = con
								.prepareStatement("INSERT INTO competition (CID,RID,SID,type) Values(?,?,?,?)");
						stm.setInt(1, CID2);
						stm.setInt(2, getReferees().get(refereeIndex).getRID());
						stm.setInt(3, stadiums.get(rand1).getSID());
						stm.setInt(4, compType);

						try {
							stm.executeUpdate();
							System.out.println("Competetion Added to DB!");
						} catch (Exception e) {
							System.out.println(e);
						}
						fireActionCompleted();
					} catch (Exception e) {
						System.out.println(e);
					}
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
					try {
						Class.forName("com.mysql.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/olympics", "root",
								"root");
						oly.addCompetition(new RunningCompetition(getReferees().get(refereeIndex), stadiums.get(rand1),
								type, this.oly)); // Running
						CID2++;
						PreparedStatement stm = con
								.prepareStatement("INSERT INTO competition (CID,RID,SID,type) Values(?,?,?,?)");
						stm.setInt(1, CID2);
						stm.setInt(2, getReferees().get(refereeIndex).getRID());
						stm.setInt(3, stadiums.get(rand1).getSID());
						stm.setInt(4, compType);

						try {
							stm.executeUpdate();
							System.out.println("Competetion Added to DB!");
						} catch (Exception e) {
							System.out.println(e);
						}
					} catch (Exception e) {
						System.out.println(e);
					}
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
		try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/olympics", "root",
				"root");
		Statement stm = con.createStatement();
		String query = ("SELECT * FROM athletes;");
		ResultSet rs = stm.executeQuery(query);
		System.out.println("Country Athletes from DB");
		while(rs.next()) {
			String name = rs.getString("Name");
			int CID = rs.getInt("CID");
			int sportsType = rs.getInt("sportsType");
			int AID = rs.getInt("AID");
			System.out.println("Athelete Name: "+ name + " ID: "+ AID + " SportsType: " + sportsType+ " Country ID: " + CID);
		}
		con.close();
		}catch (Exception e) {
			System.out.println(e);
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
		try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/olympics", "root",
				"root");
		Statement stm = con.createStatement();
		String query = ("SELECT * FROM referee;");
		ResultSet rs = stm.executeQuery(query);
		System.out.println("Referees from DB");
		while(rs.next()) {
			String name = rs.getString("Name");
			int Type = rs.getInt("Type");
			String Country = rs.getString("Country");
			int RID = rs.getInt("RID");
			System.out.println("Referee Name: "+ name + " ID: "+ RID + " Type: " + Type+ " Country: " + Country);
		}
		con.close();
		}catch (Exception e) {
			System.out.println(e);
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
