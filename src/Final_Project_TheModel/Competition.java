package Final_Project_TheModel;

import java.util.ArrayList;

public class Competition {
	protected Referee ref;
	protected Stadium stad;
	protected int type; // 1 for Solo , 2 for Team.
	protected ArrayList<Athletes> competitors = new ArrayList<Athletes>();
	protected ArrayList<Country> teams = new ArrayList<Country>();
	protected Athletes[] rankings = new Athletes[3]; // First place is 3 medals , second 2 , third 1
	protected Country[] teamRankings = new Country[3]; // First place is 3 medals

	public Competition(Referee ref, Stadium stad, int type) {
		this.ref = ref;
		this.stad = stad;
		this.type = type;
	}

	public Referee getRef() {
		return ref;
	}

	public Stadium getStad() {
		return stad;
	}

	public int getType() {
		return type;
	}

	public String ToString() {
		StringBuffer sb = new StringBuffer("\nCompetition Details: \n");
		String Type;
		if (type == 1) {
			for (Athletes A : competitors)
				sb.append(A.toString());
			Type = "Solo";
		} else {
			for (Country C : teams)
				sb.append(C.ToString());
			Type = "Team";
		}

		sb.append("\nReferee Name is : " + ref.getName() + "\nStadium Name is : " + stad.getName()
				+ "\nCompetiton Type is : " + Type + "\nCompetition Kind : " + getClass().getSimpleName());
		return sb.toString();
	}

}
