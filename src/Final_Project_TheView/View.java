package Final_Project_TheView;

import java.util.Vector;

import javax.swing.JOptionPane;

import Final_Project_TheListeners.UIEventsListener;
import Final_Project_TheModel.SysManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.sql.DriverManager;
import java.sql.*;
import java.sql.Connection;

public class View extends Application implements AbstractOlympicView {
	Scene scene1, scene2, scene3, scene4, scene5, scene6, scene, startOly, endOly;
	private Vector<UIEventsListener> allListeners;
	Stage window;
	private BackgroundFill background_fill;
	private Background background;
	public Statement stmt;

	public View(Stage theStage, SysManager theModel) {
		allListeners = new Vector<UIEventsListener>();
		window = theStage;
		window.setTitle("Welcome To Corona Olympic 2020");

		// Start olympic scene
		Label l = new Label("Welcome to Corona Olympic 2020");
		Button start = new Button("Start Olympic");
		start.setOnAction(e -> window.setScene(scene));
		VBox v = new VBox(10);
		background_fill = new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, null);
		background = new Background(background_fill);
		v.setBackground(background);
		v.getChildren().addAll(l, start);
		v.setAlignment(Pos.CENTER);
		startOly = new Scene(v, 600, 600);

		// Main Scene - label,buttons,inputs,scenes.
		Label startDate = new Label("Olympic Start Date : " + theModel.getOlyimpcs().getStart());
		Label endDate = new Label("Olympic End Date : " + theModel.getOlyimpcs().getEnd());
		Button addCountry = new Button("Add Country");
		addCountry.setOnAction(e -> window.setScene(scene1));
		Button addAthletes = new Button("Add Athletes");
		ChoiceBox<String> sportType2 = new ChoiceBox<String>();
		addAthletes.setOnAction(e -> {
			sportType2.getItems().clear();
			sportType2.getItems().addAll("Runner", "Jumper", "Both");
			sportType2.getSelectionModel().selectFirst();
			window.setScene(scene2);
		});
		Button addStadium = new Button("Add Stadium");
		addStadium.setOnAction(e -> window.setScene(scene3));
		Button addReferee = new Button("Add Referee");
		ChoiceBox<String> sportType4 = new ChoiceBox<String>();
		addReferee.setOnAction(e -> {
			sportType4.getItems().clear();
			sportType4.getItems().addAll("Runner", "Jumper", "Both");
			sportType4.getSelectionModel().selectFirst();
			window.setScene(scene4);
		});
		ChoiceBox<String> sportType5 = new ChoiceBox<String>();
		ChoiceBox<String> compType = new ChoiceBox<String>();
		ChoiceBox<String> referees = new ChoiceBox<String>();
		Button addCompitition = new Button("Add Competition");
		addCompitition.setOnAction(e -> {
			sportType5.getItems().clear();
			compType.getItems().clear();
			referees.getItems().clear();
			sportType5.getItems().addAll("Running Competition", "Jumping Competition");
			sportType5.getSelectionModel().selectFirst();
			compType.getItems().addAll("Solo Competition", "Team Competition");
			compType.getSelectionModel().selectFirst();
			for (int i = 0; i < theModel.getReferees().size(); i++) {
				referees.getItems().add(
						theModel.getReferees().get(i).getName() + " type: " + theModel.getReferees().get(i).getType());
			}
			referees.getSelectionModel().selectFirst();
			window.setScene(scene5);
		});
		Button showDetails = new Button("Show details");
		showDetails.setOnAction(e -> window.setScene(scene6));

		// End olympic scene
		Button end = new Button("End olympic");
		end.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				VBox showLayout11 = new VBox(10);
				showLayout11.setBackground(background);
				Button MenuButton12 = new Button("Exit");
				Button MenuButton11 = new Button("Return");
				MenuButton11.setOnAction(e -> window.setScene(scene));
				MenuButton12.setOnAction(e -> window.close());
				ScrollPane s11 = new ScrollPane();
				s11.setBackground(background);
				s11.setPrefSize(600, 600);
				s11.setContent(new Label(theModel.olympicsRankings()));
				showLayout11.getChildren().addAll(s11, MenuButton11, MenuButton12);
				showLayout11.setAlignment(Pos.CENTER);
				Scene rankings = new Scene(showLayout11, 600, 600);
				window.setScene(rankings);
			}
		});
		Button exit = new Button("Exit");
		exit.setOnAction(e -> deleteDB(window));
		VBox layout = new VBox(20);
		Image image = new Image("file:boom.jpg");
		ImageView iv = new ImageView();
		iv.setImage(image);
		iv.setFitWidth(200);
		iv.setFitHeight(200);
		iv.setPreserveRatio(true);
		layout.setBackground(background);
		layout.getChildren().addAll(startDate, iv, addCountry, addAthletes, addStadium, addReferee, addCompitition,
				showDetails, end, exit, endDate);
		layout.setAlignment(Pos.CENTER);
		scene = new Scene(layout, 600, 600);

		// scene 1 - add country
		Label option1 = new Label("Enter the country name:");
		TextField tx1 = new TextField();
		tx1.setPromptText("Write your text here...");
		Button confirm1 = new Button("Confirm");
		confirm1.setOnAction(e -> {
			addCountry(tx1.getText());
			tx1.clear();
			VBox showLayout1 = new VBox(10);
			showLayout1.setBackground(background);
			Button MenuButton = new Button("Back To Menu");
			MenuButton.setOnAction(e1 -> window.setScene(scene));
			ScrollPane s1 = new ScrollPane();
			s1.setPrefSize(600, 600);
			s1.setBackground(background);
			s1.setContent(new Label(theModel.getOlyimpcs().GetAllCountries()));
			showLayout1.getChildren().addAll(s1, MenuButton);
			showLayout1.setAlignment(Pos.CENTER);
			Scene showCountries = new Scene(showLayout1, 600, 600);
			window.setScene(showCountries);
		});

		HBox layout1 = new HBox(20);
		layout1.setBackground(background);
		layout1.setAlignment(Pos.CENTER);
		layout1.getChildren().addAll(option1, tx1, confirm1);
		scene1 = new Scene(layout1, 600, 600);

		// scene 2 - add athletes
		Label option2 = new Label("Enter the athelete name:");
		TextField tx2 = new TextField();
		tx2.setPromptText("Write your text here...");
		Label country2 = new Label("Enter the athelete country:");
		TextField c2 = new TextField();
		c2.setPromptText("Write your text here...");
		Label type2 = new Label("Choose the sports type");
		Button confirm2 = new Button("Confirm");
		confirm2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				addAthletes(sportType2.getSelectionModel().getSelectedIndex() + 1, tx2.getText(), c2.getText(), 1);
				tx2.clear();
				c2.clear();
				VBox showLayout2 = new VBox(10);
				showLayout2.setBackground(background);
				Button MenuButton2 = new Button("Back To Menu");
				MenuButton2.setOnAction(e1 -> window.setScene(scene));
				ScrollPane s2 = new ScrollPane();
				s2.setPrefSize(600, 600);
				s2.setBackground(background);
				s2.setContent(new Label(theModel.getOlyimpcs().GetAllAthlets()));
				showLayout2.getChildren().addAll(s2, MenuButton2);
				showLayout2.setAlignment(Pos.CENTER);
				Scene showAthletes = new Scene(showLayout2, 600, 600);
				window.setScene(showAthletes);
			}
		});

		VBox layout2 = new VBox(20);
		layout2.setAlignment(Pos.CENTER);
		layout2.getChildren().addAll(option2, tx2, country2, c2, type2, sportType2, confirm2);
		scene2 = new Scene(layout2, 600, 600);
		layout2.setBackground(background);

		// scene 3 - add stadium
		Label option3 = new Label("Enter the stadium name:");
		TextField tx3 = new TextField();
		tx3.setPromptText("Write your text here...");
		Label location3 = new Label("Enter the location of the stadium:");
		TextField c3 = new TextField();
		c3.setPromptText("Write your text here...");
		Label seats3 = new Label("Enter how many seats the stadium has:");
		TextField seats = new TextField();
		seats.setPromptText("Write your text here...");
		Button confirm3 = new Button("Confirm");
		confirm3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					if (seats.getText().isEmpty()) {
						addStadium(tx3.getText(), c3.getText(), -1, 1);
						tx3.clear();
						c3.clear();
						seats.clear();
						VBox showLayout3 = new VBox(10);
						showLayout3.setBackground(background);
						Button MenuButton3 = new Button("Back To Menu");
						MenuButton3.setOnAction(e -> window.setScene(scene));
						ScrollPane s3 = new ScrollPane();
						s3.setBackground(background);
						s3.setPrefSize(600, 600);
						s3.setContent(new Label(theModel.GetStadiumInfo()));
						showLayout3.getChildren().addAll(s3, MenuButton3);
						showLayout3.setAlignment(Pos.CENTER);
						Scene showStadium = new Scene(showLayout3, 600, 600);
						window.setScene(showStadium);
					} else {
						addStadium(tx3.getText(), c3.getText(), Integer.parseInt(seats.getText()), 1);
						tx3.clear();
						c3.clear();
						seats.clear();
						VBox showLayout3 = new VBox(10);
						showLayout3.setBackground(background);
						Button MenuButton3 = new Button("Back To Menu");
						MenuButton3.setOnAction(e -> window.setScene(scene));
						ScrollPane s3 = new ScrollPane();
						s3.setBackground(background);
						s3.setPrefSize(600, 600);
						s3.setContent(new Label(theModel.GetStadiumInfo()));
						showLayout3.getChildren().addAll(s3, MenuButton3);
						showLayout3.setAlignment(Pos.CENTER);
						Scene showStadium = new Scene(showLayout3, 600, 600);
						window.setScene(showStadium);
					}
				} catch (NumberFormatException e) {
					addStadium(tx3.getText(), c3.getText(), -1, 1);
					tx3.clear();
					c3.clear();
					seats.clear();
					VBox showLayout3 = new VBox(10);
					Button MenuButton3 = new Button("Back To Menu");
					MenuButton3.setOnAction(e1 -> window.setScene(scene));
					ScrollPane s3 = new ScrollPane();
					s3.setBackground(background);
					s3.setPrefSize(600, 600);
					s3.setContent(new Label(theModel.GetStadiumInfo()));
					showLayout3.getChildren().addAll(s3, MenuButton3);
					showLayout3.setAlignment(Pos.CENTER);
					showLayout3.setBackground(background);
					Scene showStadium = new Scene(showLayout3, 600, 600);
					window.setScene(showStadium);
				}
			}
		});

		VBox layout3 = new VBox(20);
		layout3.setBackground(background);
		layout3.setAlignment(Pos.CENTER);
		layout3.getChildren().addAll(option3, tx3, location3, c3, seats3, seats, confirm3);
		scene3 = new Scene(layout3, 600, 600);

		// scene 4 - add referee
		Label option4 = new Label("Enter the referee name :");
		TextField tx4 = new TextField();
		tx4.setPromptText("Write your text here...");
		Label country4 = new Label("Enter the where he comes from(country):");
		TextField c4 = new TextField();
		c4.setPromptText("Write your text here...");
		Label type4 = new Label("Choose the sports type");
		Button confirm4 = new Button("Confirm");
		confirm4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				addReferee(tx4.getText(), c4.getText(), sportType4.getSelectionModel().getSelectedIndex() + 1, 1);
				tx4.clear();
				c4.clear();
				VBox showLayout4 = new VBox(10);
				showLayout4.setBackground(background);
				Button MenuButton4 = new Button("Back To Menu");
				MenuButton4.setOnAction(e -> window.setScene(scene));
				ScrollPane s4 = new ScrollPane();
				s4.setBackground(background);
				s4.setPrefSize(600, 600);
				s4.setContent(new Label(theModel.GetAllRefereesInfo()));
				showLayout4.getChildren().addAll(s4, MenuButton4);
				showLayout4.setAlignment(Pos.CENTER);
				Scene showReferees = new Scene(showLayout4, 600, 600);
				window.setScene(showReferees);
			}
		});

		VBox layout4 = new VBox(20);
		layout4.setBackground(background);
		layout4.setAlignment(Pos.CENTER);
		layout4.getChildren().addAll(option4, tx4, country4, c4, type4, sportType4, confirm4);
		scene4 = new Scene(layout4, 600, 600);

		// scene 5 - add competition
		Label option5 = new Label("Choose the competition type:");
		Label SoloOrTeam = new Label("Choose if the competition is solo or team:");
		Label ref = new Label("Choose referee for the competition:");
		Button confirm5 = new Button("Confirm");
		confirm5.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				addCompetition(sportType5.getSelectionModel().getSelectedIndex() + 1,
						compType.getSelectionModel().getSelectedIndex() + 1,
						referees.getSelectionModel().getSelectedIndex());
				VBox showLayout5 = new VBox(10);
				showLayout5.setBackground(background);
				Button MenuButton5 = new Button("Back To Menu");
				MenuButton5.setOnAction(e -> window.setScene(scene));
				ScrollPane s5 = new ScrollPane();
				s5.setBackground(background);
				s5.setPrefSize(600, 600);
				s5.setContent(new Label(theModel.getOlyimpcs().getAllCompetitionInfo()));
				showLayout5.getChildren().addAll(s5, MenuButton5);
				showLayout5.setAlignment(Pos.CENTER);
				Scene showCompetition = new Scene(showLayout5, 600, 600);
				window.setScene(showCompetition);
			}
		});

		VBox layout5 = new VBox(20);
		layout5.setBackground(background);
		layout5.setAlignment(Pos.CENTER);
		layout5.getChildren().addAll(option5, sportType5, SoloOrTeam, compType, ref, referees, confirm5);
		scene5 = new Scene(layout5, 600, 600);

		// scene 6 - show details
		Button showCountries = new Button("Countries Athletes");
		showCountries.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				VBox showLayout6 = new VBox(10);
				Button MenuButton6 = new Button("Back To Menu");
				MenuButton6.setOnAction(e -> window.setScene(scene));
				ScrollPane s6 = new ScrollPane();
				s6.setPrefSize(600, 600);
				s6.setBackground(background);
				s6.setContent(new Label(theModel.GetCountryAthletes()));
				showLayout6.getChildren().addAll(s6, MenuButton6);
				showLayout6.setAlignment(Pos.CENTER);
				Scene showC = new Scene(showLayout6, 600, 600);
				window.setScene(showC);
			}
		});

		Button showAthletes = new Button("Show Athletes");
		showAthletes.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				VBox showLayout7 = new VBox(10);
				showLayout7.setBackground(background);
				Button MenuButton7 = new Button("Back To Menu");
				MenuButton7.setOnAction(e -> window.setScene(scene));
				ScrollPane s7 = new ScrollPane();
				s7.setPrefSize(600, 600);
				s7.setBackground(background);
				s7.setContent(new Label(theModel.getOlyimpcs().GetAllAthlets()));
				showLayout7.getChildren().addAll(s7, MenuButton7);
				showLayout7.setAlignment(Pos.CENTER);
				Scene showA = new Scene(showLayout7, 600, 600);
				window.setScene(showA);
			}
		});

		Button showStadiums = new Button("Show Stadiums");
		showStadiums.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				VBox showLayout8 = new VBox(10);
				showLayout8.setBackground(background);
				Button MenuButton8 = new Button("Back To Menu");
				MenuButton8.setOnAction(e -> window.setScene(scene));
				ScrollPane s8 = new ScrollPane();
				s8.setPrefSize(600, 600);
				s8.setBackground(background);
				s8.setContent(new Label(theModel.GetStadiumInfo()));
				showLayout8.getChildren().addAll(s8, MenuButton8);
				showLayout8.setAlignment(Pos.CENTER);
				Scene showS = new Scene(showLayout8, 600, 600);
				window.setScene(showS);
			}
		});

		Button showReferees = new Button("Show Referees");
		showReferees.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				VBox showLayout9 = new VBox(10);
				showLayout9.setBackground(background);
				Button MenuButton9 = new Button("Back To Menu");
				MenuButton9.setOnAction(e -> window.setScene(scene));
				ScrollPane s9 = new ScrollPane();
				s9.setBackground(background);
				s9.setPrefSize(600, 600);
				s9.setContent(new Label(theModel.GetAllRefereesInfo()));
				showLayout9.getChildren().addAll(s9, MenuButton9);
				showLayout9.setAlignment(Pos.CENTER);
				Scene showR = new Scene(showLayout9, 600, 600);
				window.setScene(showR);
			}
		});

		Button showCompetitions = new Button("Show Competitions");
		showCompetitions.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				VBox showLayout10 = new VBox(10);
				showLayout10.setBackground(background);
				Button MenuButton10 = new Button("Back To Menu");
				MenuButton10.setOnAction(e -> window.setScene(scene));
				ScrollPane s10 = new ScrollPane();
				s10.setBackground(background);
				s10.setPrefSize(600, 600);
				s10.setContent(new Label(theModel.getOlyimpcs().getAllCompetitionInfo()));
				showLayout10.getChildren().addAll(s10, MenuButton10);
				showLayout10.setAlignment(Pos.CENTER);
				Scene showCN = new Scene(showLayout10, 600, 600);
				window.setScene(showCN);
			}
		});
		HBox layout6 = new HBox(20);
		layout6.setBackground(background);
		layout6.setAlignment(Pos.CENTER);
		layout6.getChildren().addAll(showCountries, showAthletes, showStadiums, showReferees, showCompetitions);
		scene6 = new Scene(layout6, 600, 600);

		window.setScene(startOly);
		window.show();

	}

	public void deleteDB(Stage window) {
		String delAth = "drop table athletes;";
		String createAth = "create table athletes(\r\n"
				+ "Name varchar(255) NOT NULL, CID int NOT NULL,SportsType int NOT NULL,\r\n"
				+ "AID int UNIQUE NOT NULL, PRIMARY KEY (AID), foreign key(CID) references Country(CID));";
		String delComp = "drop table competition;";
		String createComp = "create table competition(\r\n"
				+ "CID int NOT NULL, RID int NOT NULL,SID int NOT NULL,type int NOT NULL, foreign key(SID) references stadium(SID),foreign key(RID) references referee(RID), primary key(CID));";
		String query3 = "TRUNCATE table referee;";
		String query4 = "TRUNCATE table stadium;";
		String query5 = "TRUNCATE table country;";
		String delJump = "drop table jumpteam";
		String createJump = "create table jumpteam(AID int NOT NULL, Name varchar(255) NOT NULL, CID int NOT NULL, foreign key(CID) references country(CID));";
		String delRun = "drop table runteam";
		String createRun = "create table RunTeam(AID int NOT NULL, Name varchar(255) NOT NULL, CID int NOT NULL, foreign key(CID) references country(CID));";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/olympics", "root", "root");
			Statement stmt = con.createStatement();
			stmt.executeUpdate(delComp);
			stmt.executeUpdate(delJump);
			stmt.executeUpdate(delRun);
			stmt.executeUpdate(delAth);
			stmt.executeUpdate(query3);
			stmt.executeUpdate(query4);
			stmt.executeUpdate(query5);
			stmt.executeUpdate(createAth);
			stmt.executeUpdate(createComp);
			stmt.executeUpdate(createJump);
			stmt.executeUpdate(createRun);
			System.out.println("Data Base Cleared!");
			window.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void addCountry(String name) {
		for (UIEventsListener l : allListeners) {
			l.addCountryToUI(name);
		}

	}

	@Override
	public void addReferee(String name, String country, int type, int RID) {
		for (UIEventsListener l : allListeners) {
			l.addRefereeToUI(name, country, type);
		}
	}

	@Override
	public void addStadium(String name, String location, int seats, int SID) {
		for (UIEventsListener l : allListeners) {
			l.addStadiumToUI(name, location, seats);
		}
	}

	@Override
	public void addAthletes(int sportType, String name, String country, int AID) {
		for (UIEventsListener l : allListeners) {
			l.addAthletesToUI(sportType, name, country);
		}
	}

	@Override
	public void addCompetition(int compType, int type, int i) {
		for (UIEventsListener l : allListeners) {
			l.addCompetitionToUI(compType, type, i);
		}
	}

	@Override
	public void olympicsRankings() {
		for (UIEventsListener l : allListeners) {
			l.olympicsRankings();
		}
	}

	public void registerListener(UIEventsListener l) {
		allListeners.add(l);
	}

	@Override
	public void actionCompletedMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	@Override
	public void actionFailed(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	@Override
	public void countryEmpty(String string) {
		JOptionPane.showMessageDialog(null, string);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

	}

	@Override
	public void countrySame(String string) {
		JOptionPane.showMessageDialog(null, string);

	}

	@Override
	public void EndOlympicsFailed(String string) {
		JOptionPane.showMessageDialog(null, string);

	}

}
