package application;

import javafx.scene.control.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;

public class PlayerPane {
	String name;
	boolean bar;
	boolean member;
	boolean hansefit;
	boolean child;
	boolean student;
	double sum;
	
	BorderPane borderPane = new BorderPane();
	
	GridPane gridLayout = new GridPane();
		CheckBox checkBoxBar = new CheckBox();
		CheckBox checkBoxMember = new CheckBox();
		CheckBox checkBoxHansefit = new CheckBox();
		CheckBox checkBoxChild = new CheckBox();
		CheckBox checkBoxStudent = new CheckBox();
	
	public PlayerPane(String name) {
		this.name = name;
		bar = true;
		member = false;
		hansefit = false;
		child = false;
		student = false;
		sum = 0.0;
		
		Label labName = new Label(name);
		Label labSum = new Label(sum+"€");
		
		borderPane.setLeft(labName);
		borderPane.setRight(labSum);
		
		gridLayout.getChildren().addAll(checkBoxBar, checkBoxMember, checkBoxHansefit, checkBoxChild, checkBoxStudent);
		
		borderPane.setCenter(gridLayout);
	}
	
	public BorderPane getBorderPane() {
		return borderPane;
	}
}
