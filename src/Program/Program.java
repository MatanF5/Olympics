package Program;

import Final_Project_TheController.OlympicController;
import Final_Project_TheModel.SysManager;
import Final_Project_TheView.AbstractOlympicView;
import Final_Project_TheView.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Program extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		SysManager theModel = new SysManager();
		AbstractOlympicView theView = new View(primaryStage, theModel);
		OlympicController theController = new OlympicController(theModel, theView);

	}
}
