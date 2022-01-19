package Final_Project_TheModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RunningCompetition extends Competition {

	public RunningCompetition(Referee ref, Stadium stad, int type, Olympics oly) {
		super(ref, stad, type);
		boolean test = true;
		for (Country c : oly.getAllCountries())
			if (c.getNationalRunnerTeam().isEmpty())
				test = false;
		if (test) {
			if (this.type == 1) {
				Random rand = new Random();
				for (Country c : oly.getAllCountries()) {
					int rand2 = rand.nextInt(c.getNationalRunnerTeam().size());
					competitors.add(c.getNationalRunnerTeam().get(rand2));
				}
				int medals = 4;
				ArrayList<Integer> nums = new ArrayList<Integer>();
				for (int i = 0; i < competitors.size(); ++i)
					nums.add(i);
				Collections.shuffle(nums);// Randomizing Numbers
				for (int i = 0; i < rankings.length; i++)
					rankings[i] = competitors.get(nums.get(i));
				for (int i = 0; i < rankings.length; i++) {
					String check = rankings[i].getConutry();
					for (Country c : oly.getAllCountries())
						if (c.getName().equals(check)) {
							medals--;
							c.AddMedals(1, medals);
							try {
								Class.forName("com.mysql.jdbc.Driver");
								Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/olympics",
										"root", "super462");
								PreparedStatement stm = con.prepareStatement(
										"Update Country SET SoloMedals = "+medals+" WHERE Name = '"+c.getName()+"'");

								stm.executeUpdate();
							} catch (Exception e) {
								System.out.println(e);
							}
						}
				}
			} else {
				int medals = 4;
				for (int i = 0; i < oly.getAllCountries().size(); i++)
					teams.add(oly.getAllCountries().get(i));
				ArrayList<Integer> nums = new ArrayList<Integer>();
				for (int i = 0; i < teams.size(); i++)
					nums.add(i);
				Collections.shuffle(nums);// Randomizing Numbers
				for (int j = 0; j < teamRankings.length; j++)
					teamRankings[j] = teams.get(nums.get(j));
				for (int i = 0; i < teamRankings.length; i++) {
					medals--;
					teamRankings[i].AddMedals(2, medals);
					try {
						Class.forName("com.mysql.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/olympics",
								"root", "super462");
						PreparedStatement stm = con.prepareStatement(
								"Update Country SET TeamMedals = "+medals+" WHERE Name = '"+teamRankings[i].getName()+"'");

						stm.executeUpdate();
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			}
		}

	}
}
