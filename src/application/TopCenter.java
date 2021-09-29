package application;

import java.io.IOException;
import java.util.Hashtable;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class TopCenter {
	
	private double width;
	private double height;
	Hashtable<String, String> filialCodes = new Hashtable<String, String>();
	
	GridPane gp;
		Label labFiliale;//0,0
		HBox  hbFiliale;//1,0
			RadioButton rbFiliale;
		Label labSportart;//0,1
		VBox buttonsSportart;//1,1
			RadioButton[] rbSportart;
		Label labUnits;//0,2
		VBox vbUnits;//1,2
			HBox hbEco;
				Spinner<Integer> spinnerEco;
				Label labEco;
			HBox hbPrime;
				Spinner<Integer> spinnerPrime;
				Label labPrime;			
		
	ToggleGroup tg;
	int sportart;
	
	public GridPane stickTogether() {
		hbFiliale.getChildren().addAll(rbFiliale);	
		
		hbEco.setAlignment(Pos.CENTER_LEFT);
		hbEco.getChildren().addAll(spinnerEco, labEco);
		
		hbPrime.setAlignment(Pos.CENTER_LEFT);
		hbPrime.getChildren().addAll(spinnerPrime, labPrime);
		
		vbUnits.getChildren().addAll(hbEco, hbPrime);
		for (RadioButton rb : rbSportart) {
			if (!rb.getText().equals("")) {
				buttonsSportart.getChildren().add(rb);
			}
		}
		rbSportart[0].setSelected(true);
		
		gp = new GridPane();
		gp.setMinSize(width, height);
		
		gp.getChildren().clear();
		
		gp.add(labFiliale, 0, 0);
		gp.add(hbFiliale, 1, 0);
		gp.add(labSportart, 0, 1);
		gp.add(buttonsSportart, 1, 1);
		gp.add(labUnits, 0, 2);
		gp.add(vbUnits, 1, 2);
		
		gp.setValignment(labSportart, VPos.TOP);
		gp.setValignment(labUnits, VPos.TOP);
		gp.setHalignment(vbUnits, HPos.LEFT);
		gp.setVgap(height / 12.0);
		gp.setHgap(width / 20.0);
		
		return gp;
	}
	
	public TopCenter (double width, double height, Font font) {
		this.width = width;
		this.height = height;
		
		filialCodes = new Hashtable<String, String>();
		filialCodes.put("luestringen", "Lüstringen");
		filialCodes.put("nahne", "Nahne");
		filialCodes.put("oldenburg", "Oldenburg");
		
		Font rbFont = new Font("Verdana", font.getSize() * 0.8);
		
		labFiliale = new Label("Filiale");
		labFiliale.setFont(font);
		
		hbFiliale = new HBox();
		
		rbFiliale = new RadioButton("Lüstringen");
		rbFiliale.setSelected(true);
		
		
		String[] string = null;
		string = new String[] {"Badminton", "Squash"};
		
		labSportart = new Label("Sportart");
		labSportart.setFont(font);
		
		buttonsSportart = new VBox();
		rbSportart = new RadioButton[string.length];
		tg = new ToggleGroup();
		
		for (int i = 0; i < rbSportart.length; i++) {
			rbSportart[i] = new RadioButton(string[i]);
			rbSportart[i].setToggleGroup(tg);
			rbSportart[i].setFont(rbFont);
			if (string[i].equals("Badminton")) {
				rbSportart[i].setOnAction(e -> {
					sportart = 0;
				});
			} else if (string[i].equals("Squash")) {
				rbSportart[i].setOnAction(e -> {
					sportart = 1;
				});
			} else if (string[i].equals("Tennis")) {
				rbSportart[i].setOnAction(e -> {
					sportart = 2;
				});
			} else {
				rbSportart[i].setOnAction(e -> {
					sportart = 3;
				});
			}
		}
		labUnits = new Label("Einheiten");
		labUnits.setFont(font);
		
		vbUnits = new VBox();
		
		hbEco = new HBox();
		
		spinnerEco = new Spinner<Integer>();
		spinnerEco.setEditable(true);
		spinnerEco.getEditor().setFont(font);
		SpinnerValueFactory<Integer> valueFactoryEco = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 6, 0);
		spinnerEco.setValueFactory(valueFactoryEco);
		spinnerEco.setMaxWidth(this.width / 8.0);
		
		labEco = new Label("Eco");
		labEco.setFont(font);
		
		hbPrime = new HBox();
		hbPrime.setAlignment(Pos.CENTER);
		spinnerPrime = new Spinner<Integer>();
		spinnerPrime.setEditable(true);
		spinnerPrime.getEditor().setFont(font);
		SpinnerValueFactory<Integer> valueFactoryPrime = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 6, 0);
		spinnerPrime.setValueFactory(valueFactoryPrime);
		spinnerPrime.setMaxWidth(this.width / 8.0);
		
		labPrime = new Label("Prime (Mo - Do 17:00 - 20:00)");
		labPrime.setFont(font);
	}
}
