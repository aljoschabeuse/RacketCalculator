package application;

import javafx.scene.control.*;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;

public class Gui {
	Scene guiScene;
	
	public Scene guiScene () {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root,400,400);
		
		VBox firstPane = new VBox();
		FlowPane bottom = new FlowPane();
		VBox playerSettings = new VBox();
		PlayerPane[] playerPane = new PlayerPane[8];
		for (int i = 0; i < playerPane.length; i++) {
			playerPane[i] = new PlayerPane("Spieler "+i);
			playerSettings.getChildren().addAll(playerPane[i].getBorderPane());
		}
		GridPane checkBoxes = new GridPane();
		VBox sums = new VBox();
		
		guiScene = new Scene(firstPane, 400, 400);
		
		return guiScene;
		
	}
}
