package Final_Project_TheModel;

import java.util.ArrayList;

public class Country {
	private String name;
	private int SoloMedals = 0;
	private int TeamMedals = 0;
	private ArrayList<Athletes> NationalRunnerTeam = new ArrayList<Athletes>();
	private ArrayList<Athletes> NationalJumpingTeam = new ArrayList<Athletes>();

	public Country(String name) {
		this.name = name;
	}

	public Country() {

	}

	public void AddAthlete(Athletes A) {
		if (A.getSportType() == 1)
			NationalRunnerTeam.add(A);
		else if (A.getSportType() == 2)
			NationalJumpingTeam.add(A);
		else {
			NationalRunnerTeam.add(A);
			NationalJumpingTeam.add(A);
		}
	}

	public void AddMedals(int Type, int num) {
		if (Type == 1)
			this.SoloMedals += num;
		else
			this.TeamMedals += num;
	}

	public int getTotalMedals() {
		return SoloMedals + TeamMedals;
	}

	public String getName() {
		return name;
	}

	public int getSoloMedals() {
		return SoloMedals;
	}

	public int getTeamMedals() {
		return TeamMedals;
	}

	public ArrayList<Athletes> getNationalRunnerTeam() {
		return NationalRunnerTeam;
	}

	public ArrayList<Athletes> getNationalJumpingTeam() {
		return NationalJumpingTeam;
	}

	public String getAtheletsInfo() {
		StringBuffer sb = new StringBuffer("Country Runners: \n");
		for (Athletes A : NationalRunnerTeam)
			sb.append(A.toString());
		sb.append("\n");
		sb.append("Country Jumpers: \n");
		for (Athletes A : NationalJumpingTeam)
			sb.append(A.toString());
		sb.append("\n");
		return sb.toString();
	}

	public String ToString() {
		return "\nName:" + name + "\nNumber of Solo Medals = " + SoloMedals + "\nNumber Of Team Medals : " + TeamMedals
				+ "\nTotal Medals Number is :" + getTotalMedals() + "\n";
	}

}
