package Final_Project_TheModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class Olympics {
	private String name;
	private LocalDate start;
	private LocalDate end;
	private ArrayList<Competition> allCompetitions = new ArrayList<Competition>();
	private ArrayList<Country> allCountries = new ArrayList<Country>();
	private ArrayList<Athletes> allAthelets = new ArrayList<Athletes>();

	public Olympics(String name) {
		this.name = name;
		this.start = LocalDate.now();
		this.end = LocalDate.now().plusMonths(1);
	}

	public void addCompetition(Competition C) {
		allCompetitions.add(C);
	}

	public void addCountry(Country C) {
		allCountries.add(C);
	}

	public String getName() {
		return name;
	}

	public String getStart() {
		return start.toString();
	}

	public String getEnd() {
		return end.toString();
	}

	public ArrayList<Competition> getAllCompetitions() {
		return allCompetitions;
	}

	public ArrayList<Country> getAllCountries() {
		return allCountries;
	}

	public void addAthelets(Athletes a) {
		allAthelets.add(a);
	}

	public String GetAllCountries() {
		StringBuffer sb = new StringBuffer("All Countries Are :\n ");
		for (Country C : allCountries) {
			sb.append(C.ToString());
			sb.append("\n");
		}
		return sb.toString();

	}

	public String GetAllAthlets() {
		StringBuffer sb = new StringBuffer("All Athletes Are : \n");
		for (Country C : allCountries) {
			for (Athletes A : C.getNationalRunnerTeam()) {
				if (A.getSportType() != 3) {
					sb.append(A.toString());
					sb.append("\n");
				}
			}

			for (Athletes A : C.getNationalJumpingTeam())
				sb.append(A.toString());
			sb.append("\n");

		}
		return sb.toString();
	}

	public String getAllCompetitionInfo() {
		StringBuffer sb = new StringBuffer("All Competition Are :");
		for (Competition C : allCompetitions) {
			sb.append("\n");
			sb.append(C.ToString());
		}
		return sb.toString();
	}

}
